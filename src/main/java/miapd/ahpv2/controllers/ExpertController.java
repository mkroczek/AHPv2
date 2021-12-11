package miapd.ahpv2.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import miapd.ahpv2.AHPApp;
import miapd.ahpv2.ComparisonAgent;
import miapd.ahpv2.ComparisonPair;
import miapd.ahpv2.gui.CategoriesWindow;

public class ExpertController implements IModelController<AHPApp>{
    @FXML
    ListView<ComparisonPair> pairsList;

    @FXML
    CategoriesWindow compareWindow;

    private AHPApp app;
    private ComparisonAgent agent;

    public void setModel(AHPApp app){
        this.app = app;
        agent = app.getCurrentAgent();
    }

    @FXML
    public void initialize(){
        pairsList.setCellFactory(param -> new ListCell<>(){
            @Override
            protected void updateItem(ComparisonPair item, boolean empty){
                super.updateItem(item, empty);
                if (empty){
                    setText(null);
                } else{
                    String firstObjName = item.getFirstObject().getProperty("name");
                    String secondObjName = item.getSecondObject().getProperty("name");
                    setText(firstObjName+" "+secondObjName);
                }
            }
        });

        pairsList.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    if(newValue != null) comparePair(newValue);
                }));
    }

    private void comparePair(ComparisonPair pair){
        app.
    }

    public void fillObjects(){
    }

    public void fillCategories(){

    }



}
