package com.example.thenotelobster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {

    public static final String TITLE = "The Note Lobster";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 640;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        String stylesheet = HelloApplication.class.getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getInstance();
        UserAccountDAO userAccountDAO = new UserAccountDAO();
        userAccountDAO.createTable();

//        userAccountDAO.insert(new UserAccount("Hudson", "Password1"));
//        userAccountDAO.insert(new UserAccount("Lucas", "Password2"));
//        userAccountDAO.insert(new UserAccount("Harrison", "Password3"));
//        userAccountDAO.insert(new UserAccount("Alvin", "Password4"));

//        UserAccount account = userAccountDAO.getByUsername("Hudson");
//        System.out.println(account.getUserName());

        launch();

        userAccountDAO.close();

    }
}