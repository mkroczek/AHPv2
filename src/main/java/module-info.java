module com.example.ahpv2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ahpv2 to javafx.fxml;
    exports com.example.ahpv2;
}