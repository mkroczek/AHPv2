package miapd.ahpv2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import miapd.ahpv2.controllers.IModelController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AHPApp extends Application {
    private ArrayList<ComparisonAgent> agents = new ArrayList<ComparisonAgent>();
    private ArrayList<ComparisonObject> objectsToCompare = new ArrayList<ComparisonObject>();
    private ArrayList<ComparisonObject> categoriesToCompare = new ArrayList<ComparisonObject>();
    private int currentAgentId;
    private Map<String, Scene> scenes = new HashMap<>();

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

    public void setComparisonScene(ComparisonPair pair){

        if(selectedDishes.getSize() >= 2){
            ComparisonEngine engine = new ComparisonEngine(selectedDishes.getAll(), categories);
            engine.addObserver(this);
            engines.put(ComparedObjects.DISH, engine);
            currentComparedObject = ComparedObjects.DISH;

            ComparisonEngine categoryEngine = new ComparisonEngine(categories, list);
            categoryEngine.addObserver(this);
            engines.put(ComparedObjects.CATEGORY, categoryEngine);

            notifyObserver(SceneType.COMPARISON);
            switchScene(SceneType.COMPARISON);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AHPApp.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}