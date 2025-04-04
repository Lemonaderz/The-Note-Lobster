module com.example.thenotelobster {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.thenotelobster to javafx.fxml;
    exports com.example.thenotelobster;
    exports com.example.thenotelobster.NotePage;
    opens com.example.thenotelobster.NotePage to javafx.fxml;
}