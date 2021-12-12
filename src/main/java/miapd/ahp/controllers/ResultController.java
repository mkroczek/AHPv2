package miapd.ahp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import miapd.ahp.AHPApp;

import java.util.ArrayList;

public class ResultController implements IModelController<AHPApp> {
    private AHPApp app;

    @FXML
    TextArea resultArea;

    @Override
    public void setModel(AHPApp app) {
        this.app = app;
        fill(app.getRankingResults());
    }

    private void fill(ArrayList<String> results){
        for (String text : results){
            resultArea.appendText(text+"\n");
        }
    }
}
