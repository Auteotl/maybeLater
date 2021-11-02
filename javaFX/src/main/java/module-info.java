module com.example.maybelater {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.maybelater to javafx.fxml;
    exports com.example.maybelater;
}