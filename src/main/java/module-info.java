module miapd.ahp {
    requires javafx.controls;
    requires javafx.fxml;
    requires jama;
    requires json.simple;

    opens miapd.ahp to javafx.fxml;
    exports miapd.ahp;
    exports miapd.ahp.controllers;
    opens miapd.ahp.controllers to javafx.fxml;
    exports miapd.ahp.ahp;
    opens miapd.ahp.ahp to javafx.fxml;
    exports miapd.ahp.objects;
    opens miapd.ahp.objects to javafx.fxml;
    exports miapd.ahp.gui;
    opens miapd.ahp.gui to javafx.fxml;
    exports miapd.ahp.utils;
    opens miapd.ahp.utils to javafx.fxml;
}