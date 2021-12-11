module miapd.ahpv2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jama;


    opens miapd.ahpv2 to javafx.fxml;
    exports miapd.ahpv2;
    exports miapd.ahpv2.controllers;
    opens miapd.ahpv2.controllers to javafx.fxml;
}