module com.example.javafxtest2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.google.gson;

    opens com.example.GUI to javafx.fxml;
    exports com.example.GUI;
}