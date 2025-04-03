module com.example.thenotelobster {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.thenotelobster to javafx.fxml;
    exports com.example.thenotelobster;
}