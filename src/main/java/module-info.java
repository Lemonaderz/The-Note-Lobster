module com.example.thenotelobster {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;

    requires java.sql;
    requires java.desktop;
    requires org.jsoup;
    requires jdk.compiler;


    opens com.example.thenotelobster to javafx.fxml;
    exports com.example.thenotelobster;
    exports com.example.thenotelobster.NotePage;
    exports com.example.thenotelobster.QuizClasses;
    opens com.example.thenotelobster.NotePage to javafx.fxml;
}