module com.example.ati {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.ati to javafx.fxml;
    exports com.example.ati;
}