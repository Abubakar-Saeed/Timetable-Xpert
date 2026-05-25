package com.example.gui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Print {

    public Print(String child, Connection connection) throws JRException {

        try {
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("ID", child);

            JasperDesign design = JRXmlLoader.load(getClass().getResourceAsStream("Semester.jrxml"));

            JasperReport compile = JasperCompileManager.compileReport(design);
            JasperPrint filledReport = JasperFillManager.fillReport(compile, parameters, connection);

            JasperViewer.viewReport(filledReport, true);

            String outputPath = "4th_Semester.pdf"  ;  // Ensure the file extension is .pdf
            JasperExportManager.exportReportToPdfFile(filledReport, outputPath);

            System.out.println("Report generated successfully at: " + outputPath);
        } catch (JRException e) {
            e.printStackTrace();
            System.err.println("JasperReports Error: " + e.getMessage());
        }

    }
    }
