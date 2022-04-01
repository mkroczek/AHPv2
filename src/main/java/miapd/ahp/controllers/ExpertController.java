package miapd.ahp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import miapd.ahp.AHPApp;
import miapd.ahp.ahp.AHPRates;
import miapd.ahp.objects.ComparisonAgent;
import miapd.ahp.objects.ComparisonPair;
import miapd.ahp.gui.CategoriesWindow;
import miapd.ahp.gui.ObjectDescription;

public class ExpertController implements IModelController<AHPApp>{
    @FXML
    ListView<ComparisonPair> pairsList;

    @FXML
    CategoriesWindow compareWindow;

    @FXML
    ObjectDescription firstObjectDescription;

    @FXML
    ObjectDescription secondObjectDescription;

    @FXML
    Label sceneTitle;

    private AHPApp app;
    private ComparisonAgent agent;

    public void setModel(AHPApp app){
        this.app = app;
        agent = app.getCurrentAgent();
        sceneTitle.setText("Agent "+app.getCurrentAgentId());
        pairsList.getItems().addAll(agent.getPairs());
        pairsList.getSelectionModel().select(0);
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

    public void saveComparison(){
        ComparisonPair pair = pairsList.getSelectionModel().getSelectedItem();
        if (pair != null){
            compareWindow.saveResults(agent, pair);
        }
    }

    public void proceed(){
        app.comparisonEnd();
    }

    private void comparePair(ComparisonPair pair){
        compareWindow.reset(); //clear categories and ratings from previous pair
        compareWindow.addAll(pair.getCategories());
        firstObjectDescription.fill(pair.getFirstObject());
        secondObjectDescription.fill(pair.getSecondObject());
        for (String category : pair.getCategories()){
            double rating = agent.getPairRating(category, pair);
            if (rating != 0){
                compareWindow.fill(category, AHPRates.getId(rating));
            }
        }
    }

}
