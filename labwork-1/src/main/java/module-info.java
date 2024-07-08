module com.example.labwork1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.task1 to javafx.fxml;
    exports com.example.task1;
}