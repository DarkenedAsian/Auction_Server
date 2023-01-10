module com.example.javafxloginvideo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.javafxloginvideo to javafx.fxml;
    exports com.example.javafxloginvideo;
}