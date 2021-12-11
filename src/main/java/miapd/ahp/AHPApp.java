package miapd.ahp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import miapd.ahp.controllers.IModelController;
import miapd.ahp.objects.ComparisonAgent;
import miapd.ahp.objects.ComparisonObject;
import miapd.ahp.utils.Loader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AHPApp extends Application {
    private ArrayList<ComparisonAgent> agents = new ArrayList<>();
    private ArrayList<ComparisonObject> objectsToCompare;
    private ArrayList<ComparisonObject> categoriesToCompare;
    private int currentAgentId = 0;
    private Map<String, Scene> scenes = new HashMap<>();
    private Stage stage;

    public ComparisonAgent getCurrentAgent(){
        return agents.get(currentAgentId);
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

    private void addAgent(){
        ComparisonAgent agent = new ComparisonAgent(categoriesToCompare, objectsToCompare);
        agents.add(agent);
        int agentId = agents.size();
        addScene("agent"+agentId, "expert-view.fxml");
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        loadData();

        addAgent();
        addScene("agent0", "expert-view.fxml");
        stage.setTitle("Agent 0");
        stage.setScene(scenes.get("agent0"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}