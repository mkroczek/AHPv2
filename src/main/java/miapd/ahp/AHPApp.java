package miapd.ahp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import miapd.ahp.ahp.CalculationOption;
import miapd.ahp.ahp.CalculationOptions;
import miapd.ahp.controllers.IModelController;
import miapd.ahp.objects.ComparisonAgent;
import miapd.ahp.objects.ComparisonObject;
import miapd.ahp.utils.Loader;

import java.io.IOException;
import java.util.*;

public class AHPApp extends Application {
    private ArrayList<ComparisonAgent> agents = new ArrayList<>();
    private ArrayList<ComparisonObject> objectsToCompare;
    private ArrayList<ComparisonObject> categoriesToCompare;
    private int currentAgentId = 0;
    private String currentScene;
    private Map<String, Scene> scenes = new HashMap<>();
    private Stage stage;
    private CalculationOptions<String> sessionOptions = new CalculationOptions<>();

    public ComparisonAgent getCurrentAgent(){
        return agents.get(currentAgentId);
    }

    public CalculationOptions<String> getSessionOptions(){
        return sessionOptions;
    }

    private IModelController<AHPApp> addScene(String name, String fxmlFile){
        FXMLLoader fxmlLoader = new FXMLLoader(AHPApp.class.getResource(fxmlFile));
        try {
            Pane root = fxmlLoader.load();
            IModelController<AHPApp> controller = fxmlLoader.getController();
            controller.setModel(this);
            Scene scene = new Scene(root, 1280, 720);
            scenes.put(name, scene);
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadData(){
        Loader loader = new Loader();
        loader.loadJSON();
        objectsToCompare = loader.createObjectList("objects", new String[]{"name"});
        categoriesToCompare = loader.createObjectList("categories", new String[]{"name"});
    }

    private void switchScene(String name){
        stage.setScene(scenes.get(name));
    }

    private int addAgent(){
        ComparisonAgent agent = new ComparisonAgent(categoriesToCompare, objectsToCompare);
        int agentId = agents.size();
        agents.add(agent);
        currentAgentId = agentId;
        addScene("agent"+agentId, "expert-view.fxml");
        return agentId;
    }

    void initializeOptions(){
        ArrayList<String> calculationMethods = new ArrayList<>(Arrays.asList("GMM", "EVM"));
        sessionOptions.addOption("calculationMethod", calculationMethods, "GMM");
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        loadData();
        initializeOptions();

        addScene("moreAgents", "more-experts-view.fxml");
        addScene("options", "options-view.fxml");

        addAgent();
        addScene("agent0", "expert-view.fxml");
        stage.setTitle("AHP");
        stage.setScene(scenes.get("agent0"));
        currentScene = "agent0";
        stage.show();
    }

    public int getCurrentAgentId(){
        return currentAgentId;
    }

    public void agentsEnd(){
        switchScene("options");
    }

    public void optionsChosen(){
        System.out.println("All options has been collected");
    }

    public void nextAgent(){
        addAgent();
        switchScene("agent"+currentAgentId);
    }

    public void comparisonEnd(){
        switchScene("moreAgents");
    }

    public static void main(String[] args) {
        launch();
    }
}