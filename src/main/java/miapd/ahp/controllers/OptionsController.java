package miapd.ahp.controllers;

import javafx.fxml.FXML;
import miapd.ahp.AHPApp;
import miapd.ahp.ahp.CalculationOptions;
import miapd.ahp.gui.CategoryEvaluation;
import miapd.ahp.gui.OptionsWindow;

public class OptionsController implements IModelController<AHPApp> {
    private AHPApp app;
    private CalculationOptions<String> appSessionOptions;

    @FXML
    OptionsWindow optionsWindow;

    @Override
    public void setModel(AHPApp app) {
        this.app = app;
        appSessionOptions = app.getSessionOptions();
        optionsWindow.fillData(appSessionOptions);
    }

    public void proceed(){
        optionsWindow.saveResults(appSessionOptions);
        app.optionsChosen();
    }
}
