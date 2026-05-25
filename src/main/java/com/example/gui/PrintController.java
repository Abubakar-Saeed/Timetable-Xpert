package com.example.gui;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.FileInputStream;
import java.io.InputStream;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.fonts.SimpleFontExtensionHelper;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;
import javax.security.auth.login.AccountNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.File;
import java.util.*;
import java.sql.SQLException;

public class PrintController extends DataBaseLayer implements Initializable {


    @FXML
    private AnchorPane rootWindow;
    @FXML
    private  CheckBox room_check_box;
    @FXML
    private Button print_button;
    @FXML
    private CheckBox excel_chk_box;
    @FXML
    private CheckBox pdf_chk_box;
    @FXML
    private CheckBox semester_check_box;
    @FXML
    private CheckBox teacher_check_box;
    @FXML
    private ProgressIndicator progressIndicator;
    Alert alert;



    public void print(ActionEvent event) {

        if ((!semester_check_box.isSelected() && !teacher_check_box.isSelected() && !room_check_box.isSelected()) || (!excel_chk_box.isSelected() && !pdf_chk_box.isSelected())) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the options to print.");
            alert.showAndWait();
            return;

        }

        progressIndicator.setVisible(true);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> {
                    rootWindow.setDisable(true);
                    progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                });

                printTimeTable();

                return null;
            }

            @Override
            protected void succeeded() {
                rootWindow.setDisable(false);
                progressIndicator.setVisible(false);

                Platform.runLater(() -> {

                    progressIndicator.setProgress(1);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Printed Successfully");
                    alert.showAndWait();

                });
            }

            @Override
            protected void failed() {
                rootWindow.setDisable(false);
                Platform.runLater(() -> {
                    progressIndicator.setProgress(0);
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to Print.");
                    alert.showAndWait();
                });
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void printTimeTable() {

        createFolder();
        try {

            statement = DataBaseLayer.connection.prepareStatement("{call sp_PrintRoomwiseTimeTables()}");
            statement.execute();
            statement = DataBaseLayer.connection.prepareStatement("{call sp_PrintLabwiseTimeTables()}");
            statement.execute();
            statement = DataBaseLayer.connection.prepareStatement("{CALL sp_PrintSemesterwiseTimeTables()}");
            statement.execute();
            statement = DataBaseLayer.connection.prepareStatement("{call sp_PrintTeacherwiseTimeTables()}");
            statement.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());
        }

        if (excel_chk_box.isSelected() && pdf_chk_box.isSelected()) {


            if (semester_check_box.isSelected()) {

                generateDepartmentWise();
                reportDepartmentPDF();
            }
            if (teacher_check_box.isSelected()) {

                teacherWise();
                reportTeacherPDF();
            }
            if (room_check_box.isSelected()) {

                reportRoomWisePDF();
                generateRoomWise();
                reportLabWisePDF();
                generateLabWise();

            }
        } else if (excel_chk_box.isSelected()) {

            if (semester_check_box.isSelected()) {
                generateDepartmentWise();
            }
            if (teacher_check_box.isSelected()) {
                teacherWise();
            }
            if (room_check_box.isSelected()) {

                generateRoomWise();
                generateLabWise();
            }
        } else if (pdf_chk_box.isSelected()) {
            if (semester_check_box.isSelected()) {
                reportDepartmentPDF();
            }
            if (teacher_check_box.isSelected()) {
                reportTeacherPDF();
            }
            if (room_check_box.isSelected()) {

                reportRoomWisePDF();
                reportLabWisePDF();
            }
        }
    }


    public void createFolder(){



        String folderPath = "Time Table";
        File folder = new File(folderPath);

        // Check if the folder already exists
        if (folder.exists()) {
            // Try to delete the folder
            if (deleteFolder(folder)) {
                System.out.println("Folder deleted successfully at " + folderPath);
            } else {
                System.out.println("Failed to delete the folder.");
            }
        }
        // Try to create the folder
        if (folder.mkdirs()) {
            System.out.println("Folder created successfully at " + folderPath);
        } else {
            System.out.println("Failed to create the folder.");
        }

        folderPath = "Time Table\\Excel\\Department Wise";
        folder = new File(folderPath);

        if (!folder.exists()) {
            // Try to create the folder
            if (folder.mkdirs()) {
                System.out.println("Folder created successfully at " + folderPath);
            } else {
                System.out.println("Failed to create the folder.");
            }
        } else {
            System.out.println("Folder already exists at " + folderPath);
        }

        folderPath = "Time Table\\Excel\\Teacher Wise";
        folder = new File(folderPath);

        if (!folder.exists()) {
            // Try to create the folder
            if (folder.mkdirs()) {
                System.out.println("Folder created successfully at " + folderPath);
            } else {
                System.out.println("Failed to create the folder.");
            }
        } else {
            System.out.println("Folder already exists at " + folderPath);
        }
        folderPath = "Time Table\\Excel\\Room Wise";
        folder = new File(folderPath);

        if (!folder.exists()) {
            // Try to create the folder
            if (folder.mkdirs()) {
                System.out.println("Folder created successfully at " + folderPath);
            } else {
                System.out.println("Failed to create the folder.");
            }
        } else {
            System.out.println("Folder already exists at " + folderPath);
        }

        folderPath = "Time Table\\Excel\\Lab Wise";
        folder = new File(folderPath);

        if (!folder.exists()) {
            // Try to create the folder
            if (folder.mkdirs()) {
                System.out.println("Folder created successfully at " + folderPath);
            } else {
                System.out.println("Failed to create the folder.");
            }
        } else {
            System.out.println("Folder already exists at " + folderPath);
        }


        folderPath = "Time Table\\PDF\\Department Wise";
        folder = new File(folderPath);

        if (!folder.exists()) {
            // Try to create the folder
            if (folder.mkdirs()) {
                System.out.println("Folder created successfully at " + folderPath);
            } else {
                System.out.println("Failed to create the folder.");
            }
        } else {
            System.out.println("Folder already exists at " + folderPath);
        }

        folderPath = "Time Table\\PDF\\Teacher Wise";
        folder = new File(folderPath);

        if (!folder.exists()) {
            // Try to create the folder
            if (folder.mkdirs()) {
                System.out.println("Folder created successfully at " + folderPath);
            } else {
                System.out.println("Failed to create the folder.");
            }
        } else {

            System.out.println("Folder already exists at " + folderPath);

        }


        folderPath = "Time Table\\PDF\\Room Wise";
        folder = new File(folderPath);

        if (!folder.exists()) {
            // Try to create the folder
            if (folder.mkdirs()) {
                System.out.println("Folder created successfully at " + folderPath);
            } else {
                System.out.println("Failed to create the folder.");
            }
        } else {

            System.out.println("Folder already exists at " + folderPath);

        }
        folderPath = "Time Table\\PDF\\Lab Wise";
        folder = new File(folderPath);

        if (!folder.exists()) {
            // Try to create the folder
            if (folder.mkdirs()) {
                System.out.println("Folder created successfully at " + folderPath);
            } else {
                System.out.println("Failed to create the folder.");
            }
        } else {

            System.out.println("Folder already exists at " + folderPath);

        }


    }
    private  boolean deleteFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!deleteFolder(file)) {
                        return false;
                    }
                }
            }
        }
        return folder.delete();
    }
    public void reportDepartmentPDF() {

        int minProgramID = -1;
        int maxProgramID = -1;

        try {


            // Getting the minimum programID
            PreparedStatement statement = DataBaseLayer.connection.prepareStatement("select min(programID) from allsemestertimetable");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                minProgramID = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();

            // Getting the maximum programID
            statement = DataBaseLayer.connection.prepareStatement("select max(programID) from allsemestertimetable");
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                maxProgramID = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();

            HashMap<String, Object> parameters = new HashMap<>();

            for (int i = minProgramID; i <= maxProgramID; i++) {

                JasperReport compile = null;
                JasperPrint filledReport = null;
                int programID = -1;

                // Getting the programID
                statement = DataBaseLayer.connection.prepareStatement("select programID from allsemestertimetable where programID = ?");
                statement.setInt(1, i);
                resultSet = statement.executeQuery();

                if (resultSet.next()) {

                    programID = resultSet.getInt(1);

                }
                resultSet.close();
                statement.close();

                int totalTimeTable = -1;
                int minTimeTableID = -1;
                int maxTimeTableID = -1;

                String programName = null;
                if (programID != -1) {
                    // Getting the total count of timeTableID for the program
                    statement = DataBaseLayer.connection.prepareStatement("select count(timeTableID) from allsemestertimetable where programID = ?");
                    statement.setInt(1, programID);
                    resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        totalTimeTable = resultSet.getInt(1);
                    }
                    resultSet.close();
                    statement.close();

                    // Getting the minimum timeTableID for the program
                    statement = DataBaseLayer.connection.prepareStatement("select min(timeTableID) from allsemestertimetable where programID = ?");
                    statement.setInt(1, programID);
                    resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        minTimeTableID = resultSet.getInt(1);
                    }
                    resultSet.close();
                    statement.close();

                    // Getting the maximum timeTableID for the program
                    statement = DataBaseLayer.connection.prepareStatement("select max(timeTableID) from allsemestertimetable where programID = ?");
                    statement.setInt(1, programID);
                    resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        maxTimeTableID = resultSet.getInt(1);
                    }
                    resultSet.close();
                    statement.close();

                    List<String> pdfPaths = new ArrayList<>();

                    for (int j = minTimeTableID; j <= maxTimeTableID; j++) {
                        int timeTableID = -1;

                        // Getting the timeTableID
                        statement = DataBaseLayer.connection.prepareStatement("select timeTableID from allsemestertimetable where timeTableID = ? and programID = ?");
                        statement.setInt(1, j);
                        statement.setInt(2,programID);
                        resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                            timeTableID = resultSet.getInt(1);
                        }
                        resultSet.close();
                        statement.close();

                        if (timeTableID != -1) {

                            parameters.put("ID", String.valueOf(programID));
                            parameters.put("timeID", String.valueOf(timeTableID));

                            JasperDesign design = JRXmlLoader.load(getClass().getResourceAsStream("Semester.jrxml"));
                            compile = JasperCompileManager.compileReport(design);
                            filledReport = JasperFillManager.fillReport(compile, parameters, DataBaseLayer.connection);

                            // Fetching the program name
                            statement = DataBaseLayer.connection.prepareStatement("select name from programTable where programID = ?");
                            statement.setInt(1, programID);
                            resultSet = statement.executeQuery();

                            programName = null;
                            if (resultSet.next()) {
                                programName = resultSet.getString(1);
                            }
                            resultSet.close();
                            statement.close();

                            String outputPath = "Time Table\\PDF\\Department Wise\\" + programName + String.valueOf(j) + ".pdf"; // Ensure the file extension is .pdf
                            JasperExportManager.exportReportToPdfFile(filledReport, outputPath);
                            pdfPaths.add(outputPath);
                            System.out.println("Report generated successfully at: " + outputPath);
                        }
                    }

                    if (!pdfPaths.isEmpty()) {
                        PDFMergerUtility pdfMerger = new PDFMergerUtility();
                        for (String pdfPath : pdfPaths) {
                            pdfMerger.addSource(pdfPath);
                        }


                        String mergedOutputPath = "Time Table\\PDF\\Department Wise\\" + programName + ".pdf";
                        pdfMerger.setDestinationFileName(mergedOutputPath);
                        pdfMerger.mergeDocuments(null);

                        System.out.println("Merged PDF created successfully at: " + mergedOutputPath);

                        for (String path : pdfPaths) {

                            File file = new File(path);

                            file.delete();
                        }
                    }
                }
            }
        } catch (JRException e) {
            e.printStackTrace();
            System.err.println("JasperReports Error: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQL Error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO Error: " + e.getMessage());
        }
    }
    public void reportTeacherPDF(){



        int minTeacherID = -1;
        int maxTeacherID = -1;

        try {

            // Getting the minimum programID
            PreparedStatement statement = DataBaseLayer.connection.prepareStatement("select min(TEACHERID) from allteachertimetable");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                minTeacherID = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();

            statement = DataBaseLayer.connection.prepareStatement("select max(TEACHERID) from allteachertimetable");
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                maxTeacherID = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();

            HashMap<String, Object> parameters = new HashMap<>();
            for (int i = minTeacherID; i <= maxTeacherID; i++) {
                JasperReport compile = null;
                JasperPrint filledReport = null;
                int teacherID = -1;

                // Getting the programID
                statement = DataBaseLayer.connection.prepareStatement("select TEACHERID from allteachertimetable where TEACHERID = ?");
                statement.setInt(1, i);
                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    teacherID = resultSet.getInt(1);
                }
                resultSet.close();
                statement.close();

                if (teacherID != -1) {



                    // Fetching the program name
                    statement = DataBaseLayer.connection.prepareStatement("select TEACHERNAME from allteachertimetable where TEACHERID = ?");
                    statement.setInt(1, teacherID);
                    resultSet = statement.executeQuery();

                    String teacherName = null;
                    if (resultSet.next()) {
                        teacherName = resultSet.getString(1);
                    }
                    resultSet.close();
                    statement.close();
                    parameters.put("ID", String.valueOf(teacherID));

                    try {
                        JasperDesign design = JRXmlLoader.load(getClass().getResourceAsStream("Teacher.jrxml"));
                        compile = JasperCompileManager.compileReport(design);
                        filledReport = JasperFillManager.fillReport(compile, parameters, DataBaseLayer.connection);

                        String outputPath = "Time Table\\PDF\\Teacher Wise\\" + teacherName + ".pdf"; // Ensure the file extension is .pdf
                        JasperExportManager.exportReportToPdfFile(filledReport, outputPath);


                    }catch (JRException e){

                        System.err.println(e.getMessage());
                    }


                }
            }
        }catch (SQLException e){

            System.out.println(e.getMessage());
        }
    }

    public void reportRoomWisePDF(){

        int minProgramID = -1;
        int maxProgramID = -1;

        try {


            // Getting the minimum programID
            PreparedStatement statement = DataBaseLayer.connection.prepareStatement("select min(programID) from allroomtimetable");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                minProgramID = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();

            // Getting the maximum programID
            statement = DataBaseLayer.connection.prepareStatement("select max(programID) from allroomtimetable");
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                maxProgramID = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();

            HashMap<String, Object> parameters = new HashMap<>();

            for (int i = minProgramID; i <= maxProgramID; i++) {

                JasperReport compile = null;
                JasperPrint filledReport = null;
                int programID = -1;

                // Getting the programID
                statement = DataBaseLayer.connection.prepareStatement("select programID from allroomtimetable where programID = ?");
                statement.setInt(1, i);
                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    programID = resultSet.getInt(1);
                }
                resultSet.close();
                statement.close();

                int totalTimeTable = -1;
                int minTimeTableID = -1;
                int maxTimeTableID = -1;

                String programName = null;
                if (programID != -1) {

                    // Getting the total count of timeTableID for the program
                    statement = DataBaseLayer.connection.prepareStatement("select count(roomID) from allroomtimetable where programID = ?");
                    statement.setInt(1, programID);
                    resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        totalTimeTable = resultSet.getInt(1);
                    }
                    resultSet.close();
                    statement.close();

                    // Getting the minimum timeTableID for the program
                    statement = DataBaseLayer.connection.prepareStatement("select min(roomID) from allroomtimetable where programID = ?");
                    statement.setInt(1, programID);
                    resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        minTimeTableID = resultSet.getInt(1);
                    }
                    resultSet.close();
                    statement.close();

                    // Getting the maximum timeTableID for the program
                    statement = DataBaseLayer.connection.prepareStatement("select max(roomID) from allroomtimetable where programID = ?");
                    statement.setInt(1, programID);
                    resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        maxTimeTableID = resultSet.getInt(1);
                    }
                    resultSet.close();
                    statement.close();

                    List<String> pdfPaths = new ArrayList<>();

                    for (int j = minTimeTableID; j <= maxTimeTableID; j++) {

                        int timeTableID = -1;

                        // Getting the timeTableID
                        statement = DataBaseLayer.connection.prepareStatement("select roomID from allroomtimetable where roomID = ? and programID = ?");
                        statement.setInt(1, j);
                        statement.setInt(2,programID);
                        resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                            timeTableID = resultSet.getInt(1);
                        }
                        resultSet.close();
                        statement.close();
                        System.out.println(timeTableID);
                        if (timeTableID != -1) {

                            parameters.put("ID", String.valueOf(programID));
                            parameters.put("timeID", String.valueOf(timeTableID));

                            JasperDesign design = JRXmlLoader.load(getClass().getResourceAsStream("Room.jrxml"));
                            compile = JasperCompileManager.compileReport(design);
                            filledReport = JasperFillManager.fillReport(compile, parameters, DataBaseLayer.connection);

                            // Fetching the program name
                            statement = DataBaseLayer.connection.prepareStatement("select name from programTable where programID = ?");
                            statement.setInt(1, programID);
                            resultSet = statement.executeQuery();

                            programName = null;
                            if (resultSet.next()) {

                                programName = resultSet.getString(1);

                            }
                            resultSet.close();
                            statement.close();

                            String outputPath = "Time Table\\PDF\\Room Wise\\" + programName + String.valueOf(j) + ".pdf"; // Ensure the file extension is .pdf
                            JasperExportManager.exportReportToPdfFile(filledReport, outputPath);
                            pdfPaths.add(outputPath);
                            System.out.println("Report generated successfully at: " + outputPath);
                        }
                    }

                    if (!pdfPaths.isEmpty()) {
                        PDFMergerUtility pdfMerger = new PDFMergerUtility();
                        for (String pdfPath : pdfPaths) {
                            pdfMerger.addSource(pdfPath);
                        }


                        String mergedOutputPath = "Time Table\\PDF\\Room Wise\\" + programName + ".pdf";
                        pdfMerger.setDestinationFileName(mergedOutputPath);
                        pdfMerger.mergeDocuments(null);

                        System.out.println("Merged PDF created successfully at: " + mergedOutputPath);

                        for (String path : pdfPaths) {

                            File file = new File(path);

                            file.delete();
                        }
                    }
                }
            }
        } catch (JRException e) {
            e.printStackTrace();
            System.err.println("JasperReports Error: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQL Error: " + e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO Error: " + e.getMessage());
        }

    }

    public void reportLabWisePDF(){

        int minProgramID = -1;
        int maxProgramID = -1;

        try {

            // Getting the minimum programID
            PreparedStatement statement = DataBaseLayer.connection.prepareStatement("select min(programID) from alllabtimetable");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                minProgramID = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();

            // Getting the maximum programID
            statement = DataBaseLayer.connection.prepareStatement("select max(programID) from alllabtimetable");
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                maxProgramID = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();

            HashMap<String, Object> parameters = new HashMap<>();

            for (int i = minProgramID; i <= maxProgramID; i++) {

                JasperReport compile = null;
                JasperPrint filledReport = null;
                int programID = -1;

                // Getting the programID
                statement = DataBaseLayer.connection.prepareStatement("select programID from alllabtimetable where programID = ?");
                statement.setInt(1, i);
                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    programID = resultSet.getInt(1);
                }
                resultSet.close();
                statement.close();

                int totalTimeTable = -1;
                int minTimeTableID = -1;
                int maxTimeTableID = -1;

                String programName = null;
                if (programID != -1) {

                    // Getting the total count of timeTableID for the program
                    statement = DataBaseLayer.connection.prepareStatement("select count(roomID) from alllabtimetable where programID = ?");
                    statement.setInt(1, programID);
                    resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        totalTimeTable = resultSet.getInt(1);
                    }
                    resultSet.close();
                    statement.close();

                    // Getting the minimum timeTableID for the program
                    statement = DataBaseLayer.connection.prepareStatement("select min(roomID) from alllabtimetable where programID = ?");
                    statement.setInt(1, programID);
                    resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        minTimeTableID = resultSet.getInt(1);
                    }
                    resultSet.close();
                    statement.close();

                    // Getting the maximum timeTableID for the program
                    statement = DataBaseLayer.connection.prepareStatement("select max(roomID) from alllabtimetable where programID = ?");
                    statement.setInt(1, programID);
                    resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        maxTimeTableID = resultSet.getInt(1);
                    }
                    resultSet.close();
                    statement.close();

                    List<String> pdfPaths = new ArrayList<>();

                    for (int j = minTimeTableID; j <= maxTimeTableID; j++) {

                        int timeTableID = -1;

                        // Getting the timeTableID
                        statement = DataBaseLayer.connection.prepareStatement("select roomID from alllabtimetable where roomID = ? and programID = ?");
                        statement.setInt(1, j);
                        statement.setInt(2,programID);
                        resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                            timeTableID = resultSet.getInt(1);
                        }
                        resultSet.close();
                        statement.close();
                        System.out.println(timeTableID);
                        if (timeTableID != -1) {

                            parameters.put("ID", String.valueOf(programID));
                            parameters.put("timeID", String.valueOf(timeTableID));

                            JasperDesign design = JRXmlLoader.load(getClass().getResourceAsStream("Lab.jrxml"));
                            compile = JasperCompileManager.compileReport(design);
                            filledReport = JasperFillManager.fillReport(compile, parameters, DataBaseLayer.connection);

                            // Fetching the program name
                            statement = DataBaseLayer.connection.prepareStatement("select name from programTable where programID = ?");
                            statement.setInt(1, programID);
                            resultSet = statement.executeQuery();

                            programName = null;
                            if (resultSet.next()) {

                                programName = resultSet.getString(1);

                            }
                            resultSet.close();
                            statement.close();

                            String outputPath = "Time Table\\PDF\\Lab Wise\\" + programName + String.valueOf(j) + ".pdf"; // Ensure the file extension is .pdf
                            JasperExportManager.exportReportToPdfFile(filledReport, outputPath);
                            pdfPaths.add(outputPath);
                            System.out.println("Report generated successfully at: " + outputPath);
                        }
                    }

                    if (!pdfPaths.isEmpty()) {
                        PDFMergerUtility pdfMerger = new PDFMergerUtility();
                        for (String pdfPath : pdfPaths) {
                            pdfMerger.addSource(pdfPath);
                        }


                        String mergedOutputPath = "Time Table\\PDF\\Lab Wise\\" + programName + ".pdf";
                        pdfMerger.setDestinationFileName(mergedOutputPath);
                        pdfMerger.mergeDocuments(null);

                        System.out.println("Merged PDF created successfully at: " + mergedOutputPath);

                        for (String path : pdfPaths) {

                            File file = new File(path);

                            file.delete();
                        }
                    }
                }
            }
        } catch (JRException e) {
            e.printStackTrace();
            System.err.println("JasperReports Error: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQL Error: " + e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO Error: " + e.getMessage());
        }

    }

    void generateDepartmentWise(){


        int programStart = -1;
        int programEnd = -1;
        String programName = null;


        try {

            int numSlots = 5;
            ResultSet resultSet = null;
            ResultSet resultSet1 = null;
            ResultSet resultSet2 = null;
            PreparedStatement statement1 = null;
            PreparedStatement statement2 = null;


            statement = DataBaseLayer.connection.prepareStatement("select MIN(programID) from AllSemesterTimeTable where programID > 0");
            resultSet = retrieve(statement);

            if (resultSet.next()) {

                programStart = resultSet.getInt(1);
            }
            statement = DataBaseLayer.connection.prepareStatement("select Max(programID) from AllSemesterTimeTable where programID > 0");
            resultSet = retrieve(statement);

            if (resultSet.next()) {

                programEnd = resultSet.getInt(1);

            }



            System.out.println(programEnd);


            for (int l = programStart; l <= programEnd; l++) {

                statement = DataBaseLayer.connection.prepareStatement("select name  from programTable where programID = ? limit 1");
                statement.setInt(1, l);
                resultSet = retrieve(statement);

                if (resultSet.next()) {

                    programName = resultSet.getString(1);

                }


                statement1 = DataBaseLayer.connection.prepareStatement("SELECT * FROM AllSemesterTimeTable where programID = ?");
                statement1.setInt(1,l);
                resultSet1 = retrieve(statement1);



                Workbook wb = new HSSFWorkbook();
                Sheet sheet = null;

                sheet = wb.createSheet("All Sections"); // for all semester sheets
                Row titleRow = sheet.createRow(0);
                Cell titleCell = titleRow.createCell(0);
                titleCell.getRow().setHeightInPoints(40);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
                sheet.createFreezePane(0, 2);   // to fix the first heading
                Font titleFont = wb.createFont();
                titleFont.setColor(IndexedColors.WHITE.getIndex());
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 16);
                CellStyle titleStyle = wb.createCellStyle();
                titleStyle.setWrapText(true);
                titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                titleStyle.setFillForegroundColor(IndexedColors.DARK_YELLOW.index);
                titleStyle.setBorderTop(BorderStyle.THIN);
                titleStyle.setBorderBottom(BorderStyle.THIN);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                titleStyle.setFont(titleFont);
                titleCell.setCellStyle(titleStyle);

                titleCell.setCellValue(programName + " Time Table");


                int rowCount = 0;
                int sheetCounter = 0;
                int mergeStart = 2; // Initialize mergeStart to 2

                CellStyle style = wb.createCellStyle();
                CellStyle headStyle = wb.createCellStyle();

                Font font = wb.createFont();
                font.setColor(IndexedColors.BLACK.getIndex());
                font.setBold(true);
                font.setFontHeightInPoints((short) 15);
                headStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                while (resultSet1.next()) {

                    sheet.setColumnWidth(rowCount, 7000);
                    Row headerRow = sheet.createRow(1);

                    // Create header cells
                    for (int i = 0; i <= 6; i++) {

                        sheet.setColumnWidth(i, 7000);
                        Cell headerCell = headerRow.createCell(i);
                        headStyle.setWrapText(true);
                        headStyle.setAlignment(HorizontalAlignment.CENTER);
                        headStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                        headStyle.setBorderLeft(BorderStyle.THIN);
                        headStyle.setBorderRight(BorderStyle.THIN);
                        headStyle.setFont(font);
                        headerCell.getRow().setHeightInPoints(35);

                        headerCell.setCellStyle(headStyle);

                        switch (i) {
                            case 0:

                                headerCell.setCellValue("Semester Title");
                                break;
                            case 1:
                                headerCell.setCellValue("Time");
                                break;
                            case 2:
                                headerCell.setCellValue("Monday");
                                break;
                            case 3:
                                headerCell.setCellValue("Tuesday");
                                break;
                            case 4:
                                headerCell.setCellValue("Wednesday");
                                break;
                            case 5:
                                headerCell.setCellValue("Thursday");
                                break;
                            case 6:

                                headerCell.setCellValue("Friday");
                                break;
                        }
                    }


                    // Create row and populate data
                    rowCount++;
                    Row row = sheet.createRow(rowCount + 1);


                    for (int j = 1; j <= 7; j++) { // Adjust column index

                        Cell cell = row.createCell(j - 1);

                        if ((j == 1 && rowCount % numSlots == 0 && rowCount != 0)) { // Check if it's time to merge cells

                            Font mergeFont = wb.createFont();
                            mergeFont.setColor(IndexedColors.WHITE.getIndex());
                            mergeFont.setBold(true);
                            mergeFont.setFontHeightInPoints((short) 15);

                            CellStyle mergeStyle = wb.createCellStyle();
                            mergeStyle.setWrapText(true);
                            mergeStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            mergeStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                            mergeStyle.setBorderTop(BorderStyle.THIN);
                            mergeStyle.setBorderBottom(BorderStyle.THIN);
                            mergeStyle.setAlignment(HorizontalAlignment.CENTER);
                            mergeStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            mergeStyle.setFont(mergeFont);
                            sheet.addMergedRegion(new CellRangeAddress(mergeStart, (rowCount + 1), 0, 0)); // Merge cells from mergeStart to rowCount
                            Row mergedRow = sheet.getRow(mergeStart); // Get the merged row
                            Cell mergedCell = mergedRow.createCell(0); // Get the merged cell
                            mergedCell.setCellStyle(mergeStyle);
                            mergedCell.setCellValue(resultSet1.getString(j + 1)); // Set the value for the merged cell
                            mergeStart = rowCount + 2; // Update merge start index

                        } else {


                            cell.getRow().setHeightInPoints(60);
                            style.setWrapText(true);
                            style.setBorderTop(BorderStyle.THIN);
                            style.setBorderBottom(BorderStyle.THIN);
                            style.setBorderLeft(BorderStyle.THIN);
                            style.setBorderRight(BorderStyle.THIN);
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            cell.setCellStyle(style);
                            cell.setCellValue(resultSet1.getString(j + 1)); // Adjust column index
                        }
                    }
                }

                int lastRow = sheet.getLastRowNum() + 1;
                Row footeRow = sheet.createRow(lastRow);
                Cell footerCell = footeRow.createCell(0);
                sheet.addMergedRegion(new CellRangeAddress(lastRow, lastRow + 1, 0, 6));
                footerCell.getRow().setHeightInPoints(20);
                titleFont = wb.createFont();
                titleFont.setColor(IndexedColors.WHITE.getIndex());
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 10);
                titleStyle = wb.createCellStyle();
                titleStyle.setWrapText(true);
                titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                titleStyle.setFillForegroundColor(IndexedColors.DARK_YELLOW.index);
                titleStyle.setBorderTop(BorderStyle.THIN);
                titleStyle.setBorderBottom(BorderStyle.THIN);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                titleStyle.setFont(titleFont);
                footerCell.setCellStyle(titleStyle);
                footerCell.setCellValue("Generated By: IT Department");


                statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM AllSemesterTimeTable where programID = ?");
                statement.setInt(1,l);
                resultSet = retrieve(statement);


                while (resultSet.next()) {


                    if (rowCount % numSlots == 0) {

                        if (sheet != null) {

                            // Close previous sheet if it's not null
                            sheetCounter++;
                            sheet = wb.createSheet("Time Table" + sheetCounter);


                            titleRow = sheet.createRow(0);
                            titleCell = titleRow.createCell(0);
                            titleCell.getRow().setHeightInPoints(40);
                            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
                            sheet.createFreezePane(0, 2);   // to fix the first heading
                            titleFont = wb.createFont();
                            titleFont.setColor(IndexedColors.WHITE.getIndex());
                            titleFont.setBold(true);
                            titleFont.setFontHeightInPoints((short) 16);
                            titleStyle = wb.createCellStyle();
                            titleStyle.setWrapText(true);
                            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            titleStyle.setFillForegroundColor(IndexedColors.DARK_YELLOW.index);
                            titleStyle.setBorderTop(BorderStyle.THIN);
                            titleStyle.setBorderBottom(BorderStyle.THIN);
                            titleStyle.setAlignment(HorizontalAlignment.CENTER);
                            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            titleStyle.setFont(titleFont);
                            titleCell.setCellStyle(titleStyle);
                            titleCell.setCellValue(resultSet.getString(2));

                        }

                        Row headerRow = sheet.createRow(1);
                        // Create header cells
                        for (int i = 0; i <= 5; i++) {

                            sheet.setColumnWidth(i, 7000);
                            Cell headerCell = headerRow.createCell(i);
                            headStyle.setWrapText(true);
                            headStyle.setAlignment(HorizontalAlignment.CENTER);
                            headStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            headStyle.setFont(font);
                            headerCell.getRow().setHeightInPoints(35);
                            headerCell.setCellStyle(headStyle);

                            switch (i) {

                                case 0:
                                    headerCell.setCellValue("Time / Day");
                                    break;
                                case 1:
                                    headerCell.setCellValue("Monday");
                                    break;
                                case 2:
                                    headerCell.setCellValue("Tuesday");
                                    break;
                                case 3:
                                    headerCell.setCellValue("Wednesday");
                                    break;
                                case 4:
                                    headerCell.setCellValue("Thursday");
                                    break;
                                case 5:
                                    headerCell.setCellValue("Friday");
                                    break;
                            }
                        }
                        rowCount = 0; // Reset rowCount for new sheet

                    }

                    // Create row and populate data
                    rowCount++;
                    // k++;  // increment to skip first row for heading title
                    Row row = sheet.createRow(rowCount + 1);
                    int j = 1;
                    for (j = 1; j < 7; j++) { // Adjust column index

                        if (j == 1) {

                            Font timeFont = wb.createFont();
                            timeFont.setColor(IndexedColors.WHITE.getIndex());
                            timeFont.setBold(true);
                            timeFont.setFontHeightInPoints((short) 13);
                            CellStyle timeStyle = wb.createCellStyle();
                            Cell timeCell = row.createCell(j - 1);
                            timeCell.getRow().setHeightInPoints(60);
                            timeStyle.setWrapText(true);
                            timeStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            timeStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                            timeStyle.setAlignment(HorizontalAlignment.CENTER);
                            timeStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            timeStyle.setFont(timeFont);
                            timeStyle.setBorderTop(BorderStyle.THIN);
                            timeStyle.setBorderBottom(BorderStyle.THIN);
                            timeStyle.setBorderLeft(BorderStyle.THIN);
                            timeStyle.setBorderRight(BorderStyle.THIN);

                            timeCell.setCellStyle(timeStyle);
                            timeCell.setCellValue(resultSet.getString(j + 2)); // Adjust column index

                        } else {

                            Cell cell = row.createCell(j - 1);
                            cell.getRow().setHeightInPoints(60);
                            style.setWrapText(true);
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setBorderTop(BorderStyle.THIN);
                            style.setBorderBottom(BorderStyle.THIN);
                            style.setBorderLeft(BorderStyle.THIN);
                            style.setBorderRight(BorderStyle.THIN);
                            style.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            cell.setCellStyle(style);
                            cell.setCellValue(resultSet.getString(j + 2)); // Adjust column index
                        }


                    }


                    if (rowCount + 1 == numSlots + 1) {

                        row = sheet.createRow(numSlots + 2);
                        footerCell = row.createCell(0);
                        sheet.addMergedRegion(new CellRangeAddress(numSlots + 2, numSlots + 2, 0, 5));
                        footerCell.getRow().setHeightInPoints(20);
                        titleFont = wb.createFont();
                        titleFont.setColor(IndexedColors.WHITE.getIndex());
                        titleFont.setBold(true);
                        titleFont.setFontHeightInPoints((short) 10);
                        titleStyle = wb.createCellStyle();
                        titleStyle.setWrapText(true);
                        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        titleStyle.setFillForegroundColor(IndexedColors.DARK_YELLOW.index);
                        titleStyle.setBorderTop(BorderStyle.THIN);
                        titleStyle.setBorderBottom(BorderStyle.THIN);
                        titleStyle.setAlignment(HorizontalAlignment.CENTER);
                        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                        titleStyle.setFont(titleFont);
                        footerCell.setCellStyle(titleStyle);
                        footerCell.setCellValue("Generated By: IT Department");


                    }


                }

                try {

                    FileOutputStream file = null;
                    file = new FileOutputStream("Time Table\\Excel\\Department Wise\\"+ programName + " Time Table.xls");
                    wb.write(file);

                } catch (IOException e) {

                    throw new RuntimeException(e);

                }


            }

        }catch (SQLException e){

            System.out.println(e.getMessage());
        }

    }



    void teacherWise() {

        Workbook wb = new HSSFWorkbook();

        try {


            int numSlots = 10; // Assuming a default value for numSlots
            ResultSet resultSet;
            ResultSet resultSet1;
            ResultSet resultSet2;
            PreparedStatement statement1;
            PreparedStatement statement2;
            Sheet sheet = null;

            int num = 0;
            statement2 = DataBaseLayer.connection.prepareStatement("select MAX(TeacherID) from AllTeacherTimeTable");
            resultSet = retrieve(statement2);
            assert resultSet != null;
            if (resultSet.next()) {
                num = resultSet.getInt(1);
            }
            statement2 = DataBaseLayer.connection.prepareStatement("select count(*) from AllTeacherTimeTable where TeacherID = ?");
            statement2.setInt(1, num);
            resultSet = retrieve(statement2);
            assert resultSet != null;
            if (resultSet.next()) {
                numSlots = resultSet.getInt(1);
            }
            statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM AllTeacherTimeTable where teacherName is not null");
            statement1 = DataBaseLayer.connection.prepareStatement("SELECT * FROM AllTeacherTimeTable where teacherName is not null");

            resultSet = retrieve(statement);
            resultSet1 = retrieve(statement1);
            resultSet2 = retrieve(statement2);

            int rowCount = 0;
            int sheetCounter = 0;

            CellStyle style = wb.createCellStyle();
            CellStyle headStyle = wb.createCellStyle();

            Font font = wb.createFont();
            font.setColor(IndexedColors.BLACK.getIndex());
            font.setBold(true);
            font.setFontHeightInPoints((short) 15);
            headStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Font titleFont = wb.createFont();
            titleFont.setColor(IndexedColors.WHITE.getIndex());
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 10);
            CellStyle titleStyle = wb.createCellStyle();
            titleStyle.setWrapText(true);
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            titleStyle.setFillForegroundColor(IndexedColors.DARK_YELLOW.index);
            titleStyle.setBorderTop(BorderStyle.THIN);
            titleStyle.setBorderBottom(BorderStyle.THIN);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            titleStyle.setFont(titleFont);

            while (resultSet.next()) {
                if (rowCount % numSlots == 0) {
                    if (sheet != null) {
                        // Close previous sheet if it's not null
                        sheetCounter++;
                    }

                    sheet = wb.createSheet(resultSet.getString(2));

                    Row titleRow = sheet.createRow(0);
                    Cell titleCell = titleRow.createCell(0);
                    titleCell.getRow().setHeightInPoints(40);
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
                    sheet.createFreezePane(0, 2);

                    titleFont = wb.createFont();
                    titleFont.setColor(IndexedColors.WHITE.getIndex());
                    titleFont.setBold(true);
                    titleFont.setFontHeightInPoints((short) 16);
                    titleStyle = wb.createCellStyle();
                    titleStyle.setWrapText(true);
                    titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    titleStyle.setFillForegroundColor(IndexedColors.DARK_YELLOW.index);
                    titleStyle.setBorderTop(BorderStyle.THIN);
                    titleStyle.setBorderBottom(BorderStyle.THIN);
                    titleStyle.setAlignment(HorizontalAlignment.CENTER);
                    titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                    titleStyle.setFont(titleFont);
                    titleCell.setCellStyle(titleStyle);
                    titleCell.setCellValue(resultSet.getString(2));

                    Row headerRow = sheet.createRow(1);
                    for (int i = 0; i <= 5; i++) {
                        sheet.setColumnWidth(i, 7000);
                        Cell headerCell = headerRow.createCell(i);
                        headStyle.setWrapText(true);
                        headStyle.setAlignment(HorizontalAlignment.CENTER);
                        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                        headStyle.setFont(font);
                        headerCell.getRow().setHeightInPoints(35);
                        headerCell.setCellStyle(headStyle);

                        switch (i) {
                            case 0:
                                headerCell.setCellValue("Time / Day");
                                break;
                            case 1:
                                headerCell.setCellValue("Monday");
                                break;
                            case 2:
                                headerCell.setCellValue("Tuesday");
                                break;
                            case 3:
                                headerCell.setCellValue("Wednesday");
                                break;
                            case 4:
                                headerCell.setCellValue("Thursday");
                                break;
                            case 5:
                                headerCell.setCellValue("Friday");
                                break;
                        }
                    }
                    rowCount = 0; // Reset rowCount for new sheet
                }

                rowCount++;
                Row row = sheet.createRow(rowCount + 1);
                for (int j = 1; j < 7; j++) {
                    if (j == 1) {
                        Font timeFont = wb.createFont();
                        timeFont.setColor(IndexedColors.WHITE.getIndex());
                        timeFont.setBold(true);
                        timeFont.setFontHeightInPoints((short) 13);
                        CellStyle timeStyle = wb.createCellStyle();
                        Cell timeCell = row.createCell(j - 1);
                        timeCell.getRow().setHeightInPoints(60);
                        timeStyle.setWrapText(true);
                        timeStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        timeStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                        timeStyle.setAlignment(HorizontalAlignment.CENTER);
                        timeStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                        timeStyle.setFont(timeFont);
                        timeStyle.setBorderTop(BorderStyle.THIN);
                        timeStyle.setBorderBottom(BorderStyle.THIN);
                        timeStyle.setBorderLeft(BorderStyle.THIN);
                        timeStyle.setBorderRight(BorderStyle.THIN);
                        timeCell.setCellStyle(timeStyle);
                        timeCell.setCellValue(resultSet.getString(j + 2));
                    } else {
                        Cell cell = row.createCell(j - 1);
                        cell.getRow().setHeightInPoints(60);
                        style.setWrapText(true);
                        style.setAlignment(HorizontalAlignment.CENTER);
                        style.setBorderTop(BorderStyle.THIN);
                        style.setBorderBottom(BorderStyle.THIN);
                        style.setBorderLeft(BorderStyle.THIN);
                        style.setBorderRight(BorderStyle.THIN);
                        style.setVerticalAlignment(VerticalAlignment.CENTER);
                        cell.setCellStyle(style);
                        cell.setCellValue(resultSet.getString(j + 2));
                    }
                }

                if (rowCount + 1 == numSlots + 1) {
                    row = sheet.createRow(numSlots + 2);
                    Cell footerCell = row.createCell(0);
                    sheet.addMergedRegion(new CellRangeAddress(numSlots + 2, numSlots + 2, 0, 5));
                    footerCell.getRow().setHeightInPoints(20);
                    titleFont = wb.createFont();
                    titleFont.setColor(IndexedColors.WHITE.getIndex());
                    titleFont.setBold(true);
                    titleFont.setFontHeightInPoints((short) 10);
                    titleStyle = wb.createCellStyle();
                    titleStyle.setWrapText(true);
                    titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    titleStyle.setFillForegroundColor(IndexedColors.DARK_YELLOW.index);
                    titleStyle.setBorderTop(BorderStyle.THIN);
                    titleStyle.setBorderBottom(BorderStyle.THIN);
                    titleStyle.setAlignment(HorizontalAlignment.CENTER);
                    titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                    titleStyle.setFont(titleFont);
                    footerCell.setCellStyle(titleStyle);
                    footerCell.setCellValue("Generated By: IT Department");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            FileOutputStream file = new FileOutputStream("Time Table\\Excel\\Teacher Wise\\Teacher Wise Time Table.xls");
            wb.write(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    void generateRoomWise(){


        int programStart = -1;
        int programEnd = -1;
        String programName = null;


        try {

            int numSlots = 8;
            ResultSet resultSet = null;
            ResultSet resultSet1 = null;
            ResultSet resultSet2 = null;
            PreparedStatement statement1 = null;
            PreparedStatement statement2 = null;


            statement = DataBaseLayer.connection.prepareStatement("select MIN(programID) from AllRoomTimeTable where programID > 0");
            resultSet = retrieve(statement);

            if (resultSet.next()) {

                programStart = resultSet.getInt(1);
            }
            statement = DataBaseLayer.connection.prepareStatement("select Max(programID) from AllRoomTimeTable where programID > 0");
            resultSet = retrieve(statement);

            if (resultSet.next()) {

                programEnd = resultSet.getInt(1);

            }



            System.out.println(programEnd);


            for (int l = programStart; l <= programEnd; l++) {

                statement = DataBaseLayer.connection.prepareStatement("select name  from programTable where programID = ? limit 1");
                statement.setInt(1, l);
                resultSet = retrieve(statement);

                if (resultSet.next()) {

                    programName = resultSet.getString(1);

                }


                statement1 = DataBaseLayer.connection.prepareStatement("SELECT * FROM AllRoomTimeTable where programID = ?");
                statement1.setInt(1,l);
                resultSet1 = retrieve(statement1);



                Workbook wb = new HSSFWorkbook();
                Sheet sheet = null;

                sheet = wb.createSheet("All Rooms"); // for all semester sheets
                Row titleRow = sheet.createRow(0);
                Cell titleCell = titleRow.createCell(0);
                titleCell.getRow().setHeightInPoints(40);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
                sheet.createFreezePane(0, 2);   // to fix the first heading
                Font titleFont = wb.createFont();
                titleFont.setColor(IndexedColors.WHITE.getIndex());
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 16);
                CellStyle titleStyle = wb.createCellStyle();
                titleStyle.setWrapText(true);
                titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                titleStyle.setFillForegroundColor(IndexedColors.DARK_YELLOW.index);
                titleStyle.setBorderTop(BorderStyle.THIN);
                titleStyle.setBorderBottom(BorderStyle.THIN);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                titleStyle.setFont(titleFont);
                titleCell.setCellStyle(titleStyle);

                titleCell.setCellValue(programName + " Time Table");


                int rowCount = 0;
                int sheetCounter = 0;
                int mergeStart = 2; // Initialize mergeStart to 2

                CellStyle style = wb.createCellStyle();
                CellStyle headStyle = wb.createCellStyle();

                Font font = wb.createFont();
                font.setColor(IndexedColors.BLACK.getIndex());
                font.setBold(true);
                font.setFontHeightInPoints((short) 15);
                headStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                while (resultSet1.next()) {

                    sheet.setColumnWidth(rowCount, 7000);
                    Row headerRow = sheet.createRow(1);

                    // Create header cells
                    for (int i = 0; i <= 6; i++) {

                        sheet.setColumnWidth(i, 7000);
                        Cell headerCell = headerRow.createCell(i);
                        headStyle.setWrapText(true);
                        headStyle.setAlignment(HorizontalAlignment.CENTER);
                        headStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                        headStyle.setBorderLeft(BorderStyle.THIN);
                        headStyle.setBorderRight(BorderStyle.THIN);
                        headStyle.setFont(font);
                        headerCell.getRow().setHeightInPoints(35);

                        headerCell.setCellStyle(headStyle);

                        switch (i) {
                            case 0:

                                headerCell.setCellValue("Title");
                                break;
                            case 1:
                                headerCell.setCellValue("Time");
                                break;
                            case 2:
                                headerCell.setCellValue("Monday");
                                break;
                            case 3:
                                headerCell.setCellValue("Tuesday");
                                break;
                            case 4:
                                headerCell.setCellValue("Wednesday");
                                break;
                            case 5:
                                headerCell.setCellValue("Thursday");
                                break;
                            case 6:

                                headerCell.setCellValue("Friday");
                                break;
                        }
                    }


                    // Create row and populate data
                    rowCount++;
                    Row row = sheet.createRow(rowCount + 1);


                    for (int j = 1; j <= 7; j++) { // Adjust column index

                        Cell cell = row.createCell(j - 1);

                        if ((j == 1 && rowCount % numSlots == 0 && rowCount != 0)) { // Check if it's time to merge cells

                            Font mergeFont = wb.createFont();
                            mergeFont.setColor(IndexedColors.WHITE.getIndex());
                            mergeFont.setBold(true);
                            mergeFont.setFontHeightInPoints((short) 15);

                            CellStyle mergeStyle = wb.createCellStyle();
                            mergeStyle.setWrapText(true);
                            mergeStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            mergeStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                            mergeStyle.setBorderTop(BorderStyle.THIN);
                            mergeStyle.setBorderBottom(BorderStyle.THIN);
                            mergeStyle.setAlignment(HorizontalAlignment.CENTER);
                            mergeStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            mergeStyle.setFont(mergeFont);
                            sheet.addMergedRegion(new CellRangeAddress(mergeStart, (rowCount + 1), 0, 0)); // Merge cells from mergeStart to rowCount
                            Row mergedRow = sheet.getRow(mergeStart); // Get the merged row
                            Cell mergedCell = mergedRow.createCell(0); // Get the merged cell
                            mergedCell.setCellStyle(mergeStyle);
                            mergedCell.setCellValue(resultSet1.getString(j + 1)); // Set the value for the merged cell
                            mergeStart = rowCount + 2; // Update merge start index

                        } else {


                            cell.getRow().setHeightInPoints(60);
                            style.setWrapText(true);
                            style.setBorderTop(BorderStyle.THIN);
                            style.setBorderBottom(BorderStyle.THIN);
                            style.setBorderLeft(BorderStyle.THIN);
                            style.setBorderRight(BorderStyle.THIN);
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            cell.setCellStyle(style);
                            cell.setCellValue(resultSet1.getString(j + 1)); // Adjust column index
                        }
                    }
                }

                int lastRow = sheet.getLastRowNum() + 1;
                Row footeRow = sheet.createRow(lastRow);
                Cell footerCell = footeRow.createCell(0);
                sheet.addMergedRegion(new CellRangeAddress(lastRow, lastRow + 1, 0, 6));
                footerCell.getRow().setHeightInPoints(20);
                titleFont = wb.createFont();
                titleFont.setColor(IndexedColors.WHITE.getIndex());
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 10);
                titleStyle = wb.createCellStyle();
                titleStyle.setWrapText(true);
                titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                titleStyle.setFillForegroundColor(IndexedColors.DARK_YELLOW.index);
                titleStyle.setBorderTop(BorderStyle.THIN);
                titleStyle.setBorderBottom(BorderStyle.THIN);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                titleStyle.setFont(titleFont);
                footerCell.setCellStyle(titleStyle);
                footerCell.setCellValue("Generated By: IT Department");


                statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM AllRoomTimeTable where programID = ?");
                statement.setInt(1,l);
                resultSet = retrieve(statement);


                while (resultSet.next()) {


                    if (rowCount % numSlots == 0) {

                        if (sheet != null) {

                            // Close previous sheet if it's not null
                            sheetCounter++;
                            sheet = wb.createSheet("Time Table" + sheetCounter);


                            titleRow = sheet.createRow(0);
                            titleCell = titleRow.createCell(0);
                            titleCell.getRow().setHeightInPoints(40);
                            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
                            sheet.createFreezePane(0, 2);   // to fix the first heading
                            titleFont = wb.createFont();
                            titleFont.setColor(IndexedColors.WHITE.getIndex());
                            titleFont.setBold(true);
                            titleFont.setFontHeightInPoints((short) 16);
                            titleStyle = wb.createCellStyle();
                            titleStyle.setWrapText(true);
                            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            titleStyle.setFillForegroundColor(IndexedColors.DARK_YELLOW.index);
                            titleStyle.setBorderTop(BorderStyle.THIN);
                            titleStyle.setBorderBottom(BorderStyle.THIN);
                            titleStyle.setAlignment(HorizontalAlignment.CENTER);
                            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            titleStyle.setFont(titleFont);
                            titleCell.setCellStyle(titleStyle);
                            titleCell.setCellValue(resultSet.getString(2));

                        }

                        Row headerRow = sheet.createRow(1);
                        // Create header cells
                        for (int i = 0; i <= 5; i++) {

                            sheet.setColumnWidth(i, 7000);
                            Cell headerCell = headerRow.createCell(i);
                            headStyle.setWrapText(true);
                            headStyle.setAlignment(HorizontalAlignment.CENTER);
                            headStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            headStyle.setFont(font);
                            headerCell.getRow().setHeightInPoints(35);
                            headerCell.setCellStyle(headStyle);

                            switch (i) {

                                case 0:
                                    headerCell.setCellValue("Time / Day");
                                    break;
                                case 1:
                                    headerCell.setCellValue("Monday");
                                    break;
                                case 2:
                                    headerCell.setCellValue("Tuesday");
                                    break;
                                case 3:
                                    headerCell.setCellValue("Wednesday");
                                    break;
                                case 4:
                                    headerCell.setCellValue("Thursday");
                                    break;
                                case 5:
                                    headerCell.setCellValue("Friday");
                                    break;
                            }
                        }
                        rowCount = 0; // Reset rowCount for new sheet

                    }

                    // Create row and populate data
                    rowCount++;
                    // k++;  // increment to skip first row for heading title
                    Row row = sheet.createRow(rowCount + 1);
                    int j = 1;
                    for (j = 1; j < 7; j++) { // Adjust column index

                        if (j == 1) {

                            Font timeFont = wb.createFont();
                            timeFont.setColor(IndexedColors.WHITE.getIndex());
                            timeFont.setBold(true);
                            timeFont.setFontHeightInPoints((short) 13);
                            CellStyle timeStyle = wb.createCellStyle();
                            Cell timeCell = row.createCell(j - 1);
                            timeCell.getRow().setHeightInPoints(60);
                            timeStyle.setWrapText(true);
                            timeStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            timeStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                            timeStyle.setAlignment(HorizontalAlignment.CENTER);
                            timeStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            timeStyle.setFont(timeFont);
                            timeStyle.setBorderTop(BorderStyle.THIN);
                            timeStyle.setBorderBottom(BorderStyle.THIN);
                            timeStyle.setBorderLeft(BorderStyle.THIN);
                            timeStyle.setBorderRight(BorderStyle.THIN);

                            timeCell.setCellStyle(timeStyle);
                            timeCell.setCellValue(resultSet.getString(j + 2)); // Adjust column index

                        } else {

                            Cell cell = row.createCell(j - 1);
                            cell.getRow().setHeightInPoints(60);
                            style.setWrapText(true);
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setBorderTop(BorderStyle.THIN);
                            style.setBorderBottom(BorderStyle.THIN);
                            style.setBorderLeft(BorderStyle.THIN);
                            style.setBorderRight(BorderStyle.THIN);
                            style.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            cell.setCellStyle(style);
                            cell.setCellValue(resultSet.getString(j + 2)); // Adjust column index
                        }


                    }


                    if (rowCount + 1 == numSlots + 1) {

                        row = sheet.createRow(numSlots + 2);
                        footerCell = row.createCell(0);
                        sheet.addMergedRegion(new CellRangeAddress(numSlots + 2, numSlots + 2, 0, 5));
                        footerCell.getRow().setHeightInPoints(20);
                        titleFont = wb.createFont();
                        titleFont.setColor(IndexedColors.WHITE.getIndex());
                        titleFont.setBold(true);
                        titleFont.setFontHeightInPoints((short) 10);
                        titleStyle = wb.createCellStyle();
                        titleStyle.setWrapText(true);
                        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        titleStyle.setFillForegroundColor(IndexedColors.DARK_YELLOW.index);
                        titleStyle.setBorderTop(BorderStyle.THIN);
                        titleStyle.setBorderBottom(BorderStyle.THIN);
                        titleStyle.setAlignment(HorizontalAlignment.CENTER);
                        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                        titleStyle.setFont(titleFont);
                        footerCell.setCellStyle(titleStyle);
                        footerCell.setCellValue("Generated By: IT Department");


                    }


                }

                try {

                    FileOutputStream file = null;
                    file = new FileOutputStream("Time Table\\Excel\\Room Wise\\"+ programName + " Time Table.xls");
                    wb.write(file);

                } catch (IOException e) {

                    throw new RuntimeException(e);

                }


            }

        }catch (SQLException e){

            System.out.println(e.getMessage());
        }

    }
    void generateLabWise(){


        int programStart = -1;
        int programEnd = -1;
        String programName = null;


        try {

            int numSlots = 8;
            ResultSet resultSet = null;
            ResultSet resultSet1 = null;
            ResultSet resultSet2 = null;
            PreparedStatement statement1 = null;
            PreparedStatement statement2 = null;


            statement = DataBaseLayer.connection.prepareStatement("select MIN(programID) from alllabtimetable where programID > 0");
            resultSet = retrieve(statement);

            if (resultSet.next()) {

                programStart = resultSet.getInt(1);
            }
            statement = DataBaseLayer.connection.prepareStatement("select Max(programID) from alllabtimetable where programID > 0");
            resultSet = retrieve(statement);

            if (resultSet.next()) {

                programEnd = resultSet.getInt(1);

            }

            for (int l = programStart; l <= programEnd; l++) {

                statement = DataBaseLayer.connection.prepareStatement("select name  from programTable where programID = ? limit 1");
                statement.setInt(1, l);
                resultSet = retrieve(statement);

                if (resultSet.next()) {

                    programName = resultSet.getString(1);

                }


                statement1 = DataBaseLayer.connection.prepareStatement("SELECT * FROM alllabtimetable where programID = ?");
                statement1.setInt(1,l);
                resultSet1 = retrieve(statement1);



                Workbook wb = new HSSFWorkbook();
                Sheet sheet = null;

                sheet = wb.createSheet("All Labs"); // for all lab sheets
                Row titleRow = sheet.createRow(0);
                Cell titleCell = titleRow.createCell(0);
                titleCell.getRow().setHeightInPoints(40);
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
                sheet.createFreezePane(0, 2);   // to fix the first heading
                Font titleFont = wb.createFont();
                titleFont.setColor(IndexedColors.WHITE.getIndex());
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 16);
                CellStyle titleStyle = wb.createCellStyle();
                titleStyle.setWrapText(true);
                titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                titleStyle.setFillForegroundColor(IndexedColors.DARK_YELLOW.index);
                titleStyle.setBorderTop(BorderStyle.THIN);
                titleStyle.setBorderBottom(BorderStyle.THIN);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                titleStyle.setFont(titleFont);
                titleCell.setCellStyle(titleStyle);

                titleCell.setCellValue(programName + " Time Table");


                int rowCount = 0;
                int sheetCounter = 0;
                int mergeStart = 2; // Initialize mergeStart to 2

                CellStyle style = wb.createCellStyle();
                CellStyle headStyle = wb.createCellStyle();

                Font font = wb.createFont();
                font.setColor(IndexedColors.BLACK.getIndex());
                font.setBold(true);
                font.setFontHeightInPoints((short) 15);
                headStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                while (resultSet1.next()) {

                    sheet.setColumnWidth(rowCount, 7000);
                    Row headerRow = sheet.createRow(1);

                    // Create header cells
                    for (int i = 0; i <= 6; i++) {

                        sheet.setColumnWidth(i, 7000);
                        Cell headerCell = headerRow.createCell(i);
                        headStyle.setWrapText(true);
                        headStyle.setAlignment(HorizontalAlignment.CENTER);
                        headStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                        headStyle.setBorderLeft(BorderStyle.THIN);
                        headStyle.setBorderRight(BorderStyle.THIN);
                        headStyle.setFont(font);
                        headerCell.getRow().setHeightInPoints(35);

                        headerCell.setCellStyle(headStyle);

                        switch (i) {
                            case 0:

                                headerCell.setCellValue("Title");
                                break;
                            case 1:
                                headerCell.setCellValue("Time");
                                break;
                            case 2:
                                headerCell.setCellValue("Monday");
                                break;
                            case 3:
                                headerCell.setCellValue("Tuesday");
                                break;
                            case 4:
                                headerCell.setCellValue("Wednesday");
                                break;
                            case 5:
                                headerCell.setCellValue("Thursday");
                                break;
                            case 6:

                                headerCell.setCellValue("Friday");
                                break;
                        }
                    }


                    // Create row and populate data
                    rowCount++;
                    Row row = sheet.createRow(rowCount + 1);


                    for (int j = 1; j <= 7; j++) { // Adjust column index

                        Cell cell = row.createCell(j - 1);

                        if ((j == 1 && rowCount % numSlots == 0 && rowCount != 0)) { // Check if it's time to merge cells

                            Font mergeFont = wb.createFont();
                            mergeFont.setColor(IndexedColors.WHITE.getIndex());
                            mergeFont.setBold(true);
                            mergeFont.setFontHeightInPoints((short) 15);

                            CellStyle mergeStyle = wb.createCellStyle();
                            mergeStyle.setWrapText(true);
                            mergeStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            mergeStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                            mergeStyle.setBorderTop(BorderStyle.THIN);
                            mergeStyle.setBorderBottom(BorderStyle.THIN);
                            mergeStyle.setAlignment(HorizontalAlignment.CENTER);
                            mergeStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            mergeStyle.setFont(mergeFont);
                            sheet.addMergedRegion(new CellRangeAddress(mergeStart, (rowCount + 1), 0, 0)); // Merge cells from mergeStart to rowCount
                            Row mergedRow = sheet.getRow(mergeStart); // Get the merged row
                            Cell mergedCell = mergedRow.createCell(0); // Get the merged cell
                            mergedCell.setCellStyle(mergeStyle);
                            mergedCell.setCellValue(resultSet1.getString(j + 1)); // Set the value for the merged cell
                            mergeStart = rowCount + 2; // Update merge start index

                        } else {


                            cell.getRow().setHeightInPoints(60);
                            style.setWrapText(true);
                            style.setBorderTop(BorderStyle.THIN);
                            style.setBorderBottom(BorderStyle.THIN);
                            style.setBorderLeft(BorderStyle.THIN);
                            style.setBorderRight(BorderStyle.THIN);
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            cell.setCellStyle(style);
                            cell.setCellValue(resultSet1.getString(j + 1)); // Adjust column index
                        }
                    }
                }

                int lastRow = sheet.getLastRowNum() + 1;
                Row footeRow = sheet.createRow(lastRow);
                Cell footerCell = footeRow.createCell(0);
                sheet.addMergedRegion(new CellRangeAddress(lastRow, lastRow + 1, 0, 6));
                footerCell.getRow().setHeightInPoints(20);
                titleFont = wb.createFont();
                titleFont.setColor(IndexedColors.WHITE.getIndex());
                titleFont.setBold(true);
                titleFont.setFontHeightInPoints((short) 10);
                titleStyle = wb.createCellStyle();
                titleStyle.setWrapText(true);
                titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                titleStyle.setFillForegroundColor(IndexedColors.DARK_YELLOW.index);
                titleStyle.setBorderTop(BorderStyle.THIN);
                titleStyle.setBorderBottom(BorderStyle.THIN);
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                titleStyle.setFont(titleFont);
                footerCell.setCellStyle(titleStyle);
                footerCell.setCellValue("Generated By: IT Department");


                statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM alllabtimetable where programID = ?");
                statement.setInt(1,l);
                resultSet = retrieve(statement);


                while (resultSet.next()) {


                    if (rowCount % numSlots == 0) {

                        if (sheet != null) {

                            // Close previous sheet if it's not null
                            sheetCounter++;
                            sheet = wb.createSheet("Time Table" + sheetCounter);


                            titleRow = sheet.createRow(0);
                            titleCell = titleRow.createCell(0);
                            titleCell.getRow().setHeightInPoints(40);
                            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
                            sheet.createFreezePane(0, 2);   // to fix the first heading
                            titleFont = wb.createFont();
                            titleFont.setColor(IndexedColors.WHITE.getIndex());
                            titleFont.setBold(true);
                            titleFont.setFontHeightInPoints((short) 16);
                            titleStyle = wb.createCellStyle();
                            titleStyle.setWrapText(true);
                            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            titleStyle.setFillForegroundColor(IndexedColors.DARK_YELLOW.index);
                            titleStyle.setBorderTop(BorderStyle.THIN);
                            titleStyle.setBorderBottom(BorderStyle.THIN);
                            titleStyle.setAlignment(HorizontalAlignment.CENTER);
                            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            titleStyle.setFont(titleFont);
                            titleCell.setCellStyle(titleStyle);
                            titleCell.setCellValue(resultSet.getString(2));

                        }

                        Row headerRow = sheet.createRow(1);
                        // Create header cells
                        for (int i = 0; i <= 5; i++) {

                            sheet.setColumnWidth(i, 7000);
                            Cell headerCell = headerRow.createCell(i);
                            headStyle.setWrapText(true);
                            headStyle.setAlignment(HorizontalAlignment.CENTER);
                            headStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            headStyle.setFont(font);
                            headerCell.getRow().setHeightInPoints(35);
                            headerCell.setCellStyle(headStyle);

                            switch (i) {

                                case 0:
                                    headerCell.setCellValue("Time / Day");
                                    break;
                                case 1:
                                    headerCell.setCellValue("Monday");
                                    break;
                                case 2:
                                    headerCell.setCellValue("Tuesday");
                                    break;
                                case 3:
                                    headerCell.setCellValue("Wednesday");
                                    break;
                                case 4:
                                    headerCell.setCellValue("Thursday");
                                    break;
                                case 5:
                                    headerCell.setCellValue("Friday");
                                    break;
                            }
                        }
                        rowCount = 0; // Reset rowCount for new sheet

                    }

                    // Create row and populate data
                    rowCount++;
                    // k++;  // increment to skip first row for heading title
                    Row row = sheet.createRow(rowCount + 1);
                    int j = 1;
                    for (j = 1; j < 7; j++) { // Adjust column index

                        if (j == 1) {

                            Font timeFont = wb.createFont();
                            timeFont.setColor(IndexedColors.WHITE.getIndex());
                            timeFont.setBold(true);
                            timeFont.setFontHeightInPoints((short) 13);
                            CellStyle timeStyle = wb.createCellStyle();
                            Cell timeCell = row.createCell(j - 1);
                            timeCell.getRow().setHeightInPoints(60);
                            timeStyle.setWrapText(true);
                            timeStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            timeStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                            timeStyle.setAlignment(HorizontalAlignment.CENTER);
                            timeStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            timeStyle.setFont(timeFont);
                            timeStyle.setBorderTop(BorderStyle.THIN);
                            timeStyle.setBorderBottom(BorderStyle.THIN);
                            timeStyle.setBorderLeft(BorderStyle.THIN);
                            timeStyle.setBorderRight(BorderStyle.THIN);

                            timeCell.setCellStyle(timeStyle);
                            timeCell.setCellValue(resultSet.getString(j + 2)); // Adjust column index

                        } else {

                            Cell cell = row.createCell(j - 1);
                            cell.getRow().setHeightInPoints(60);
                            style.setWrapText(true);
                            style.setAlignment(HorizontalAlignment.CENTER);
                            style.setBorderTop(BorderStyle.THIN);
                            style.setBorderBottom(BorderStyle.THIN);
                            style.setBorderLeft(BorderStyle.THIN);
                            style.setBorderRight(BorderStyle.THIN);
                            style.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                            cell.setCellStyle(style);
                            cell.setCellValue(resultSet.getString(j + 2)); // Adjust column index
                        }


                    }


                    if (rowCount + 1 == numSlots + 1) {

                        row = sheet.createRow(numSlots + 2);
                        footerCell = row.createCell(0);
                        sheet.addMergedRegion(new CellRangeAddress(numSlots + 2, numSlots + 2, 0, 5));
                        footerCell.getRow().setHeightInPoints(20);
                        titleFont = wb.createFont();
                        titleFont.setColor(IndexedColors.WHITE.getIndex());
                        titleFont.setBold(true);
                        titleFont.setFontHeightInPoints((short) 10);
                        titleStyle = wb.createCellStyle();
                        titleStyle.setWrapText(true);
                        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        titleStyle.setFillForegroundColor(IndexedColors.DARK_YELLOW.index);
                        titleStyle.setBorderTop(BorderStyle.THIN);
                        titleStyle.setBorderBottom(BorderStyle.THIN);
                        titleStyle.setAlignment(HorizontalAlignment.CENTER);
                        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                        titleStyle.setFont(titleFont);
                        footerCell.setCellStyle(titleStyle);
                        footerCell.setCellValue("Generated By: IT Department");


                    }


                }

                try {

                    FileOutputStream file = null;
                    file = new FileOutputStream("Time Table\\Excel\\Lab Wise\\"+ programName + " Time Table.xls");
                    wb.write(file);

                } catch (IOException e) {

                    throw new RuntimeException(e);

                }


            }

        }catch (SQLException e){

            System.out.println(e.getMessage());
        }

    }



    public void enable(){


        semester_check_box.setDisable(true);
        teacher_check_box.setDisable(true);
        room_check_box.setDisable(true);


        if (excel_chk_box.isSelected() || pdf_chk_box.isSelected()) {

            semester_check_box.setDisable(false);
            teacher_check_box.setDisable(false);
            room_check_box.setDisable(false);


        }

    }
    public void initialize(URL url, ResourceBundle resourceBundle) {


        progressIndicator.setVisible(false);
        semester_check_box.setDisable(true);
        teacher_check_box.setDisable(true);
        room_check_box.setDisable(true);

    }


}



