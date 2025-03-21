module com.example.thenotelobster {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.thenotelobster to javafx.fxml;
    exports com.example.thenotelobster;
}