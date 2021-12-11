package miapd.ahpv2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class AHPApp extends Application {
    private ArrayList<ComparisonAgent> agents = new ArrayList<ComparisonAgent>();
    private ArrayList<ComparisonObject> objectsToCompare = new ArrayList<ComparisonObject>();
    private ArrayList<ComparisonObject> categoriesToCompare = new ArrayList<ComparisonObject>();
    private ArrayList<Pair> objectPairs = new ArrayList<>();
    private ArrayList<Pair> categoriesPairs = new ArrayList<>();
    private int currentAgentId;

    public ComparisonAgent getCurrentAgent(){
        return agents.get(currentAgentId);
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