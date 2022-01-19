import miapd.ahp.objects.ComparisonAgent;
import miapd.ahp.objects.ComparisonMatrix;
import miapd.ahp.objects.ComparisonObject;
import miapd.ahp.utils.Loader;
import miapd.ahp.utils.SingularValueError;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class OverallTest {
    private final JSONParser parser = new JSONParser();

    public double[][] getMatrixFromJson(JSONArray array){
        int size = array.size();
        double[][] matrix = new double[size][size];

        for (int i = 0; i < size; i++){
            JSONArray row = (JSONArray) array.get(i);
            for (int j = 0; j < size; j++){
                matrix[i][j] = ((Number) row.get(j)).doubleValue();
            }
        }
        return matrix;
    }

    public double[] getRankingFromJson(JSONArray array){
        int size = array.size();
        double[] matrix = new double[size];

        for (int i = 0; i < size; i++){
            matrix[i] = ((Number) array.get(i)).doubleValue();
        }
        return matrix;
    }

    @Test
    public void mainTest(){
        System.out.println("SINGLE CRITERION TESTS");
        Loader loader = new Loader();
        loader.loadJSON("examples.json");
        JSONObject file = loader.getJsonObject();
        JSONArray singleCriterion= (JSONArray) file.get("singleCriterion");
        Iterator<JSONObject> iterator = singleCriterion.iterator();
        JSONObject object;
        while (iterator.hasNext()) {
            //iterate through each object
            object = iterator.next();
            String method = (String)object.get("method");
            JSONArray criterionRanking = (JSONArray) object.get("criterions");
            JSONArray expectedRanking = (JSONArray) object.get("results");
            int size = criterionRanking.size();
            //create categories
            ArrayList<ComparisonObject> categories = new ArrayList<>();
            ComparisonObject categoryObj = new ComparisonObject("category");
            categoryObj.addProperty("name", "category1");
            categories.add(categoryObj);
            double[][] categoriesComp = new double[1][1];
            categoriesComp[0][0] = 1.0;

            //create objects
            ArrayList<ComparisonObject> objects = new ArrayList<>();
            for (int i = 0; i < size; i++){
                ComparisonObject obj = new ComparisonObject("object");
                obj.addProperty("name", "alternative"+i);
                objects.add(obj);
            }

            //create agent
            ComparisonAgent agent = new ComparisonAgent(categories, objects);
            ComparisonMatrix category1Matrix = new ComparisonMatrix(getMatrixFromJson(criterionRanking));
            agent.setMatrix("category1", category1Matrix);
            agent.setMatrix("categories", new ComparisonMatrix(categoriesComp));

            oneAgentTest(agent, getRankingFromJson(expectedRanking), method);

        }
        System.out.println("\nMULTIPLE CRITERION TESTS");
        JSONArray multipleCriterion= (JSONArray) file.get("multipleCriterions");
        Iterator<JSONObject> mulIterator = multipleCriterion.iterator();
        while (mulIterator.hasNext()) {
            //iterate through each object
            object = mulIterator.next();
            String method = (String)object.get("method");
            long size = (Long) object.get("numberOfAlternatives");
            JSONArray criterionRankings = (JSONArray) object.get("criterions");
            JSONArray expectedRanking = (JSONArray) object.get("results");
            //create categories
            ArrayList<ComparisonObject> categories = new ArrayList<>();
            for (int i = 0; i < criterionRankings.size(); i++){
                ComparisonObject cat = new ComparisonObject("category");
                cat.addProperty("name", "category"+i);
                categories.add(cat);
            }

            JSONArray categoriesCompJson = (JSONArray) object.get("weights");
            double[][] categoriesComp = getMatrixFromJson(categoriesCompJson);

            //create objects
            ArrayList<ComparisonObject> objects = new ArrayList<>();
            for (int i = 0; i < size; i++){
                ComparisonObject obj = new ComparisonObject("object");
                obj.addProperty("name", "alternative"+i);
                objects.add(obj);
            }

            //create agent
            ComparisonAgent agent = new ComparisonAgent(categories, objects);
            //assign comparison matrices to agent
            for (int i = 0; i < criterionRankings.size(); i++){
                JSONArray categoryMatrix = (JSONArray) criterionRankings.get(i);
                agent.setMatrix("category"+i, new ComparisonMatrix(getMatrixFromJson(categoryMatrix)));
            }
            agent.setMatrix("categories", new ComparisonMatrix(categoriesComp));

            oneAgentTest(agent, getRankingFromJson(expectedRanking), method);

        }
    }

    public void oneAgentTest(ComparisonAgent agent, double[] expectedResult, String method){
        double[] result = new double[1];
        if (method.equals("EV")){
            result = agent.calculateSingleEVRanking();
        }
        else if (method.equals("GM")){
            try {
                result = agent.calculateSingleGMRanking();
            } catch (SingularValueError e) {
                e.printStackTrace();
            }
        }
        System.out.println("Our result:"+ Arrays.toString(result));
        System.out.println("Expected result:"+ Arrays.toString(expectedResult)+"\n");
    }
}
