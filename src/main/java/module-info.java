module com.example.calculadora2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires webcam.capture;
    requires java.desktop;


    opens com.example.calculadora2 to javafx.fxml;
    exports com.example.calculadora2;
}