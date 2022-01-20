module com.example.etlap {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens com.example.etlap to javafx.fxml;
    exports com.example.etlap;
    exports com.example.etlap.controllerek;
    opens com.example.etlap.controllerek to javafx.fxml;
}