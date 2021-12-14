package miapd.ahp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import miapd.ahp.ahp.CalculationOptions;
import miapd.ahp.controllers.IModelController;
import miapd.ahp.objects.ComparisonAgent;
import miapd.ahp.objects.ComparisonMatrix;
import miapd.ahp.objects.ComparisonObject;
import miapd.ahp.objects.ComparisonPair;
import miapd.ahp.utils.Loader;
import java.math.*;

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

    private IModelController<AHPApp> addScene(String name, String fxmlFile){
        FXMLLoader fxmlLoader = new FXMLLoader(AHPApp.class.getResource(fxmlFile));
        try {
            Pane root = fxmlLoader.load();
            IModelController<AHPApp> controller = fxmlLoader.getController();
            controller.setModel(this);
//            Scene scene = new Scene(root, 1280, 720);
            Scene scene = new Scene(root);
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
        //each agent creates his own scene
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

    public ArrayList<String> getRankingResults(){
        ArrayList<String> result = new ArrayList<>();
        //add session settings
        String options = "Options:\n";
        for (String option : sessionOptions.getOptionsSet()){
            options += option+" "+sessionOptions.getChosen(option)+"\n";
        }
        result.add(options);
        //add rest of results
        String ranking = "Ranking\n";
        double[] res = calculateRankingEV();
        for (int i = 0; i < res.length; i++){
            ranking += objectsToCompare.get(i).getProperty("name")+" "+res[i]+"\n";
        }
        result.add(ranking);
        return result;
    }

    public int getCurrentAgentId(){
        return currentAgentId;
    }

    public ComparisonAgent getCurrentAgent(){
        return agents.get(currentAgentId);
    }

    public CalculationOptions<String> getSessionOptions(){
        return sessionOptions;
    }

    //methods changing program state
    public void agentsEnd(){
        switchScene("options");
    }

    public void optionsChosen(){
        //calculate results here

        addScene("result", "result-view.fxml");
        switchScene("result");
    }

    public void nextAgent(){
        addAgent();
        switchScene("agent"+currentAgentId);
    }

    public void comparisonEnd(){
        switchScene("moreAgents");
    }


    public double[] calculateRankingEV(){
        int objectsToCompSize = objectsToCompare.size();
        System.out.println("Categories to compare size = "+objectsToCompSize);
        System.out.println("Agents size = "+agents.size());
        double[] aggregatedResults = new double[objectsToCompare.size()];

        for(ComparisonAgent agent: agents){
            for(int i = 0; i<objectsToCompSize; i++){
                aggregatedResults[i] = aggregatedResults[i] + agent.calculateSingleEVRanking()[i];
            }
        }

        for(int i = 0; i<objectsToCompSize; i++){
            aggregatedResults[i] = aggregatedResults[i]/ agents.size();
        }

        return aggregatedResults;
    }

    public double[] calculateRankingGM(){
        int objectsToCompSize = objectsToCompare.size();
        double[] aggregatedResults = new double[objectsToCompSize];

        for(int i = 0; i<objectsToCompSize; i++){
            aggregatedResults[i] = 1;
        }

        for(ComparisonAgent agent: agents){
            for(int i = 0; i<objectsToCompSize; i++){
                aggregatedResults[i] = aggregatedResults[i] + agent.calculateSingleGMRanking()[i];
            }
        }

        for(int i = 0; i<objectsToCompSize; i++){
            aggregatedResults[i] = Math.pow(aggregatedResults[i], 1.0/agents.size());
        }

        return aggregatedResults;
    }

    public static void main(String[] args) {
        launch();
    }
}