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
    exports com.example.thenotelobster.model;
    exports com.example.thenotelobster.model.QuizClasses;
    opens com.example.thenotelobster.model to javafx.fxml;
    exports com.example.thenotelobster.controller;
    opens com.example.thenotelobster.controller to javafx.fxml;
    opens com.example.thenotelobster.model.QuizClasses to javafx.fxml;
    exports com.example.thenotelobster.model.NoteClasses;
    opens com.example.thenotelobster.model.NoteClasses to javafx.fxml;
    exports com.example.thenotelobster.model.UserClasses;
    opens com.example.thenotelobster.model.UserClasses to javafx.fxml;
}