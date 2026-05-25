package com.example.gui;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;


public class TimeTableGenerator extends DataBaseLayer {


    HashMap<Integer, String> programList;
    private int programChoice ;


    TimeTableGenerator(){

        programList = new HashMap<>();
    }

    public void generateProgramWise(){



        try {


            String choice = null;
            System.out.println("\t\t================================================");
            System.out.println("\t\t\t    Generate Time Table");
            System.out.println("\t\t================================================\n");
            System.out.print("\n\t\tDo you want to Reset the slots?(Y/N): ");
            choice = sc.nextLine();

            if (choice.equals("Y") || choice.equals("y") ){

                statement = DataBaseLayer.connection.prepareStatement("SET SQL_SAFE_UPDATES = 0");
                statement.execute();
                statement = DataBaseLayer.connection.prepareStatement("delete from FixedSlotsTable");
                statement.execute();
                statement = DataBaseLayer.connection.prepareStatement("SET SQL_SAFE_UPDATES = 1");
                statement.execute();
                System.out.println("\n\t\tSlots Reset Successfully.. ");


            }

            try {

                System.out.print("\n\t\tEnter to Continue...........");
                System.in.read();


            } catch (IOException e) {

                e.printStackTrace();
            }


            statement = DataBaseLayer.connection.prepareStatement("select programID, name FROM programTable");
            resultSet = retrieve(statement);

            while (resultSet.next()) {

                programList.put(resultSet.getInt(1), resultSet.getString(2));

            }


            do {

                System.out.println("\t\t================================================");
                System.out.println("\t\t\t    Generate Time Table");
                System.out.println("\t\t================================================\n");


                for (Integer id : programList.keySet()) {

                    System.out.println("\t\t\t\t" + id + ". " + programList.get(id));

                }

                System.out.print("\t\t\t\t0. Return ");

                System.out.print("\n\t\t\tEnter your Choice: ");
                programChoice = sc.nextInt();

                if (!programList.containsKey(programChoice)){

                    System.out.println("Invalid choice. Please select from the provided options.");

                }

            }while(!programList.containsKey(programChoice));

            String storedProcedureCall = "{CALL GenerateTimeTableForProgram(?,?)}";
            CallableStatement statement = connection.prepareCall(storedProcedureCall);

            statement.setInt(1, programChoice);
            statement.registerOutParameter(2, Types.VARCHAR);
            statement.execute();
            String message = statement.getString(2);

            System.out.println("Message from stored procedure: " + message);
            CallableStatement statement1 = DataBaseLayer.connection.prepareCall("{CALL sp_PrintSemesterwiseTimeTables()}");
            statement1.execute();
            System.out.println("Generating time table.................");


        } catch (SQLException e) {


            System.out.println(e.getMessage());
            System.out.println("Issue in procedure.");
            sc.nextLine();

        }

        generateReport(2);
        System.out.println("Time Table Generated Successfully........");


    }

    void generate(){



        try {



            String storedProcedureCall = "{CALL GenerateTimeTableForAllSessions(?)}";
            CallableStatement statement = connection.prepareCall(storedProcedureCall);

            // Register the output parameter
            statement.registerOutParameter(1, Types.VARCHAR);

            // Execute the stored procedure
            statement.execute();




        } catch (SQLException e) {

            System.out.println(e.getMessage());
            System.out.println("Issue in procedure.");
            sc.nextLine();

        }


    }

    public void generateSemesterWise(){

        // generate();
        generateReport(1);

    }

    public void generateTeacherWise(){

        System.out.println("\t\t================================================");
        System.out.println("\t\t\t    Generate Time Table");
        System.out.println("\t\t================================================\n");
        teacherWise();
    }


    void generateReport(int flag){   // false represent teacherwise and true will represent SemesterWise


        Workbook wb = new HSSFWorkbook();
        String programName = null;

        try {
            // Execute stored procedure
            PreparedStatement statement = null;


            statement = DataBaseLayer.connection.prepareStatement("{CALL sp_PrintSemesterwiseTimeTables()}");
            statement.execute();

            int numSlots = 0;
            ResultSet resultSet  = null;
            ResultSet resultSet1 = null;
            ResultSet resultSet2 = null;
            PreparedStatement statement1 = null;
            PreparedStatement statement2 = null;
            Sheet sheet = null;

           if (flag == 2){

                try{


                    statement = DataBaseLayer.connection.prepareStatement("select name  from programTable where programID = ? limit 1");
                    statement.setInt(1, programChoice);
                    resultSet = retrieve(statement);

                }catch(SQLException e){

                    System.out.println(e.getMessage());

                }

                if (resultSet.next()){

                    programName = resultSet.getString(1);

                }

                sheet =  wb.createSheet(programName + " Time Table");

            }


            if (flag == 0) {

                int num = 0;

                statement2 = DataBaseLayer.connection.prepareStatement("select MAX(TeacherID) from AllTeacherTimeTable");
                resultSet = retrieve(statement2);
                if (resultSet.next()){

                    num = resultSet.getInt(1);
                }
                statement2 = DataBaseLayer.connection.prepareStatement("select count(*) from AllTeacherTimeTable where TeacherID = ?");
                statement2.setInt(1, num);
                resultSet = retrieve(statement2);

                if(resultSet.next()){

                    numSlots = resultSet.getInt(1);
                }
                statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM AllTeacherTimeTable where teacherName is not null");
                statement1 = DataBaseLayer.connection.prepareStatement("SELECT * FROM AllTeacherTimeTable where teacherName is not null");

                numSlots = 10;

            }else {

                statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM AllSemesterTimeTable");
                statement1 = DataBaseLayer.connection.prepareStatement("SELECT * FROM AllSemesterTimeTable");
                statement2 = DataBaseLayer.connection.prepareStatement("SELECT COUNT(*) FROM AllSemesterTimeTable where timeTableID = 1 limit 1");
                numSlots = 5;
            }

            resultSet = retrieve(statement);
            resultSet1 = retrieve(statement1);
            resultSet2 = retrieve(statement2);

            if (resultSet2 != null && resultSet2.next()){

                numSlots = resultSet2.getInt(1);


            }


            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.getRow().setHeightInPoints(40);
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,6));
            sheet.createFreezePane(0, 2);   // to fix the first heading
            Font titleFont = wb.createFont();
            titleFont.setColor(IndexedColors.WHITE.getIndex());
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short)16);
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
            font.setFontHeightInPoints((short)15);
            headStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            while (resultSet1.next()) {

                sheet.setColumnWidth(rowCount , 7000);
                Row headerRow = sheet.createRow(1);

                // Create header cells
                for (int i = 0; i <= 6; i++) {

                    sheet.setColumnWidth(i , 7000);
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

                            headerCell.setCellValue(flag == 0 ? "Teacher Name" : "Semester Title");
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
                        mergeFont.setFontHeightInPoints((short)15);

                        CellStyle mergeStyle = wb.createCellStyle();
                        mergeStyle.setWrapText(true);
                        mergeStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        mergeStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                        mergeStyle.setBorderTop(BorderStyle.THIN);
                        mergeStyle.setBorderBottom(BorderStyle.THIN);
                        mergeStyle.setAlignment(HorizontalAlignment.CENTER);
                        mergeStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                        mergeStyle.setFont(mergeFont);
                        sheet.addMergedRegion(new CellRangeAddress(mergeStart , (rowCount + 1), 0, 0)); // Merge cells from mergeStart to rowCount
                        Row mergedRow = sheet.getRow(mergeStart); // Get the merged row
                        Cell mergedCell = mergedRow.createCell(0); // Get the merged cell
                        mergedCell.setCellStyle(mergeStyle);
                        mergedCell.setCellValue( resultSet1.getString(j + 1)); // Set the value for the merged cell
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
            sheet.addMergedRegion(new CellRangeAddress( lastRow,lastRow + 1,0,6));
            footerCell.getRow().setHeightInPoints(20);
            titleFont = wb.createFont();
            titleFont.setColor(IndexedColors.WHITE.getIndex());
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short)10);
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
            footerCell.setCellValue("Generated By: IT Department" );

            while (resultSet.next()) {

                if (rowCount % numSlots == 0) {

                    if (sheet != null) {

                        // Close previous sheet if it's not null
                        sheetCounter++;
                        if (flag == 0){

                            sheet = wb.createSheet(resultSet.getString(2));
                        }else if (flag == 1){

                            sheet = wb.createSheet("Time Table" + sheetCounter);
                        }else {

                            sheet = wb.createSheet("Time Table" + sheetCounter);

                        }

                        titleRow = sheet.createRow(0);
                        titleCell = titleRow.createCell(0);
                        titleCell.getRow().setHeightInPoints(40);
                        sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
                        sheet.createFreezePane(0, 2);   // to fix the first heading
                        titleFont = wb.createFont();
                        titleFont.setColor(IndexedColors.WHITE.getIndex());
                        titleFont.setBold(true);
                        titleFont.setFontHeightInPoints((short)16);
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

                        sheet.setColumnWidth(i , 7000);
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
                for ( j = 1; j < 7; j++) { // Adjust column index

                    if ( j == 1 ){

                        Font timeFont = wb.createFont();
                        timeFont.setColor(IndexedColors.WHITE.getIndex());
                        timeFont.setBold(true);
                        timeFont.setFontHeightInPoints((short)13);
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
                        timeCell.setCellValue(resultSet.getString(j+2)); // Adjust column index

                    }else{

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
                        cell.setCellValue(resultSet.getString(j+2)); // Adjust column index
                    }


                }


                if (rowCount + 1 == numSlots + 1){

                    row = sheet.createRow(numSlots + 2);
                    footerCell = row.createCell(0);
                    sheet.addMergedRegion(new CellRangeAddress( numSlots + 2,numSlots + 2,0,5));
                    footerCell.getRow().setHeightInPoints(20);
                    titleFont = wb.createFont();
                    titleFont.setColor(IndexedColors.WHITE.getIndex());
                    titleFont.setBold(true);
                    titleFont.setFontHeightInPoints((short)10);
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
                    footerCell.setCellValue("Generated By: IT Department" );


                }



            }


        } catch (SQLException e) {

            throw new RuntimeException("Error retrieving data from database", e);
        }finally {
            // Close the Workbook in a finally block to ensure it's closed even if an exception occurs

            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e) {
                    // Handle IOException if unable to close Workbook
                    e.printStackTrace();
                }
            }
        }


        try {

            FileOutputStream file = null;



                file = new FileOutputStream("Teacher Wise Time Table.xls");


            wb.write(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }




    public void teacherWise(){


        Workbook wb = new HSSFWorkbook();
        String programName = null;

        try {

            statement = DataBaseLayer.connection.prepareStatement("{call sp_PrintTeacherwiseTimeTables()}");
            statement.execute();
            int numSlots = 0;
            ResultSet resultSet  = null;
            ResultSet resultSet1 = null;
            ResultSet resultSet2 = null;
            PreparedStatement statement1 = null;
            PreparedStatement statement2 = null;
            Sheet sheet = null;
            sheet = wb.createSheet("All Teachers Time Table"); // for all semester teacher sheet


            int num = 0;

            statement2 = DataBaseLayer.connection.prepareStatement("select MAX(TeacherID) from AllTeacherTimeTable");
            resultSet = retrieve(statement2);
            assert resultSet != null;
            if (resultSet.next()){

                num = resultSet.getInt(1);
            }
            statement2 = DataBaseLayer.connection.prepareStatement("select count(*) from AllTeacherTimeTable where TeacherID = ?");
            statement2.setInt(1, num);
            resultSet = retrieve(statement2);

            assert resultSet != null;
            if(resultSet.next()){

                numSlots = resultSet.getInt(1);
            }
            statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM AllTeacherTimeTable where teacherName is not null");
            statement1 = DataBaseLayer.connection.prepareStatement("SELECT * FROM AllTeacherTimeTable where teacherName is not null");

            numSlots = 10;

            resultSet = retrieve(statement);
            resultSet1 = retrieve(statement1);
            resultSet2 = retrieve(statement2);

            if (resultSet2 != null && resultSet2.next()){

                numSlots = resultSet2.getInt(1);


            }

            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.getRow().setHeightInPoints(40);
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,6));
            sheet.createFreezePane(0, 2);   // to fix the first heading
            Font titleFont = wb.createFont();
            titleFont.setColor(IndexedColors.WHITE.getIndex());
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short)16);
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

            titleCell.setCellValue("All Teachers Time Table");

            int rowCount = 0;
            int sheetCounter = 0;
            int mergeStart = 2; // Initialize mergeStart to 2

            CellStyle style = wb.createCellStyle();
            CellStyle headStyle = wb.createCellStyle();

            Font font = wb.createFont();
            font.setColor(IndexedColors.BLACK.getIndex());
            font.setBold(true);
            font.setFontHeightInPoints((short)15);
            headStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            while (resultSet1.next()) {

                sheet.setColumnWidth(rowCount , 7000);
                Row headerRow = sheet.createRow(1);

                // Create header cells
                for (int i = 0; i <= 6; i++) {

                    sheet.setColumnWidth(i , 7000);
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

                            headerCell.setCellValue("Teacher Name" );
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
                        mergeFont.setFontHeightInPoints((short)15);

                        CellStyle mergeStyle = wb.createCellStyle();
                        mergeStyle.setWrapText(true);
                        mergeStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        mergeStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
                        mergeStyle.setBorderTop(BorderStyle.THIN);
                        mergeStyle.setBorderBottom(BorderStyle.THIN);
                        mergeStyle.setAlignment(HorizontalAlignment.CENTER);
                        mergeStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment to center
                        mergeStyle.setFont(mergeFont);
                        sheet.addMergedRegion(new CellRangeAddress(mergeStart , (rowCount + 1), 0, 0)); // Merge cells from mergeStart to rowCount
                        Row mergedRow = sheet.getRow(mergeStart); // Get the merged row
                        Cell mergedCell = mergedRow.createCell(0); // Get the merged cell
                        mergedCell.setCellStyle(mergeStyle);
                        mergedCell.setCellValue( resultSet1.getString(j + 1)); // Set the value for the merged cell
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
            sheet.addMergedRegion(new CellRangeAddress( lastRow,lastRow + 1,0,6));
            footerCell.getRow().setHeightInPoints(20);
            titleFont = wb.createFont();
            titleFont.setColor(IndexedColors.WHITE.getIndex());
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short)10);
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
            footerCell.setCellValue("Generated By: IT Department" );

            while (resultSet.next()) {

                if (rowCount % numSlots == 0) {

                    if (sheet != null) {

                        // Close previous sheet if it's not null
                        sheetCounter++;


                            sheet = wb.createSheet(resultSet.getString(2));


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




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


}

