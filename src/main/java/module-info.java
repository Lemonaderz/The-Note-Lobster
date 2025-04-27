module com.example.thenotelobster {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;

    requires java.sql;
    requires java.desktop;


    opens com.example.thenotelobster to javafx.fxml;
    exports com.example.thenotelobster;
    exports com.example.thenotelobster.NotePage;
    opens com.example.thenotelobster.NotePage to javafx.fxml;
}