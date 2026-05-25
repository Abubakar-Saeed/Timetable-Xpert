module com.example.gui {



    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.poi;

    requires jasperreports;
    requires org.apache.pdfbox;


    opens com.example.gui to javafx.fxml;
    exports com.example.gui;
}