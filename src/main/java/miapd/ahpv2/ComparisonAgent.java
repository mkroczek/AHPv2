package miapd.ahpv2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComparisonAgent {
    private ArrayList<ComparisonObject> categories;
    private ArrayList<ComparisonObject> comparisonObjects;
    private Map<String, ComparisonMatrix> objectsComparison = new HashMap<>();
    private ArrayList<ComparisonPair> pairsComparison;


    public ComparisonAgent(ArrayList<ComparisonObject> categories, ArrayList<ComparisonObject> comparisonObjects){
        ArrayList<String> objectsComparisonCategories = new ArrayList<>();
        for (ComparisonObject category: categories){
            objectsComparisonCategories.add(category.getProperty("name"));
            objectsComparison.put(category.getProperty("name"), new ComparisonMatrix(comparisonObjects.size()));
        }
        objectsComparison.put("categories", new ComparisonMatrix(categories.size()));

        for(int i=0; i<comparisonObjects.size(); i++){
            for(int j=i+1; j<comparisonObjects.size(); j++){
                pairsComparison.add(new ComparisonPair(i, j, comparisonObjects.get(i), comparisonObjects.get(j), objectsComparisonCategories));
            }
        }

        ArrayList<String> categoriesComparisonCategories = new ArrayList<>();
        categoriesComparisonCategories.add("categories");
        for(int i=0; i<categories.size(); i++){
            for(int j=i+1; j<categories.size(); j++){
                pairsComparison.add(new ComparisonPair(i, j, categories.get(i), categories.get(j),categoriesComparisonCategories));
            }
        }
    }

    public void updateRating(String category, int pairID, double rating){
        //takes indices from current pair (id1, id2) and sets matrix[id1, id2] = rating for the given category
        ComparisonPair pair = pairsComparison.get(pairID);
        int x = pair.getX();
        int y = pair.getY();
        ComparisonMatrix tmpMatrix = objectsComparison.get(category);
        tmpMatrix.setRating(x, y, rating);
        tmpMatrix.setRating(y, x, 1/rating);
    }

    public double getRating(String category, int pairID){
        ComparisonPair pair = pairsComparison.get(pairID);
        int x = pair.getX();
        int y = pair.getY();
        ComparisonMatrix tmpMatrix = objectsComparison.get(category);
        return tmpMatrix.getRating(x, y);
    }

    public double[] calculateSingleEVRanking(){
        int vectorSize = this.comparisonObjects.size();
        double[] singleEVRanking = new double[vectorSize];

        for (int i=0; i<vectorSize; i++){
            singleEVRanking[i] = 0;
        }

        double[] categoriesRanking = objectsComparison.get("categories").calculateEV();
        int categoryCounter = 0;
        for (ComparisonObject category:categories){
            String currentCategory = category.getProperty("name");
            double[] tmpVector = objectsComparison.get(currentCategory).calculateEV();
            for (int i=0; i<vectorSize; i++){
                singleEVRanking[i] = singleEVRanking[i] + tmpVector[i]*categoriesRanking[categoryCounter];
            }
            categoryCounter = categoryCounter + 1;
        }

        return singleEVRanking;
    }

    public double[] calculateSingleGMRanking(){
        int vectorSize = this.comparisonObjects.size();
        double[] singleGMRanking = new double[vectorSize];

        for (int i=0; i<vectorSize; i++){
            singleGMRanking[i] = 0;
        }

        double[] categoriesRanking = objectsComparison.get("categories").calculateGM();
        int categoryCounter = 0;
        for (ComparisonObject category:categories){
            String currentCategory = category.getProperty("name");
            double[] tmpVector = objectsComparison.get(currentCategory).calculateGM();
            for (int i=0; i<vectorSize; i++){
                singleGMRanking[i] = singleGMRanking[i] + tmpVector[i]*categoriesRanking[categoryCounter];
            }
            categoryCounter = categoryCounter + 1;
        }

        return singleGMRanking;
    }



}
