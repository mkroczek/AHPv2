package miapd.ahpv2.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import miapd.ahpv2.AHPApp;

public class ExpertController {
    @FXML
    ListView<Integer> pairsPane;

    @FXML
    ListView<Integer> categoriesPane;

    private AHPApp app;
    private ComparisonAgent agent;

    public void setApp(AHPApp app){
        this.app = app;
        agent = app.getCurrentAgent();
    }

    public void fillObjects(){
    }

    public void fillCategories(){

    }



}
