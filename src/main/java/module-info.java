module miapd.ahpv2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jama;
    requires json.simple;


    opens miapd.ahpv2 to javafx.fxml;
    exports miapd.ahpv2;
}