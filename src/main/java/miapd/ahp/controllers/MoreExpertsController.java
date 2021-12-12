package miapd.ahp.controllers;

import javafx.fxml.FXML;
import miapd.ahp.AHPApp;

public class MoreExpertsController implements IModelController<AHPApp> {
    private AHPApp app;

    @Override
    public void setModel(AHPApp app) {
        this.app = app;
    }

    public void no(){
        app.agentsEnd();
    }

    public void yes(){
        app.nextAgent();
    }
}
