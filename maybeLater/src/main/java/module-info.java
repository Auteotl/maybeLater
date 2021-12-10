module com.example.maybelater {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.maybelater to javafx.fxml;
    exports com.example.maybelater;
    exports SomePack;
    opens SomePack to javafx.fxml;
}