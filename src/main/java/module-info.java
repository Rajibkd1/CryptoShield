module com.example.locker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.locker to javafx.fxml;
    exports com.example.locker;

}