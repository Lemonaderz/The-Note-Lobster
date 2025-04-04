module com.example.thenotelobster {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;


    opens com.example.thenotelobster to javafx.fxml;
    exports com.example.thenotelobster;
}