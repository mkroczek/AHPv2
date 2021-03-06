package miapd.ahp.objects;

import miapd.ahp.ahp.AHPRates;
import miapd.ahp.utils.SingularValueError;

import java.util.*;

public class ComparisonAgent {
    private ArrayList<ComparisonObject> categories = new ArrayList<>();
    private ArrayList<ComparisonObject> comparisonObjects = new ArrayList<>();
    private Map<String, ComparisonMatrix> objectsComparison = new HashMap<>();
    private ArrayList<ComparisonPair> pairsComparison = new ArrayList<>();

    public ComparisonAgent(ArrayList<ComparisonObject> categories, ArrayList<ComparisonObject> comparisonObjects){
        this.categories = categories;
        this.comparisonObjects = comparisonObjects;

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

    public void loadRating(String category, ComparisonMatrix matrix){
        objectsComparison.put(category, matrix);
    }

    public void updateRating(String category, ComparisonPair pair, int rateId){
        //takes indices from current pair (id1, id2) and sets matrix[id1, id2] = rating for the given category
        double rating = AHPRates.getRate(rateId);
        int x = pair.getX();
        int y = pair.getY();
        ComparisonMatrix tmpMatrix = objectsComparison.get(category);
        tmpMatrix.setRating(x, y, rating);
        tmpMatrix.setRating(y, x, AHPRates.getOppositeRate(rateId));
    }

    //uncomment for test

    public void updateRating(String category, ComparisonPair pair, double rate){
        //takes indices from current pair (id1, id2) and sets matrix[id1, id2] = rating for the given category
        int x = pair.getX();
        int y = pair.getY();
        ComparisonMatrix tmpMatrix = objectsComparison.get(category);
        tmpMatrix.setRating(x, y, rate);
        tmpMatrix.setRating(y, x, 1/rate);
    }

    public double getPairRating(String category, ComparisonPair pair){
        int x = pair.getX();
        int y = pair.getY();
        ComparisonMatrix tmpMatrix = objectsComparison.get(category);
        return tmpMatrix.getRating(x, y);
    }

    public ArrayList<ComparisonPair> getPairs(){
        return pairsComparison;
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

    public double[] calculateSingleGMRanking() throws SingularValueError {
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

    public Set<Map.Entry<String, ComparisonMatrix>> getResultsEntrySet(){
        //returns entry of objectComparisons - which contains category and corresponding matrix
        return objectsComparison.entrySet();
    }

    public ComparisonMatrix getMatrix(String name){
        return this.objectsComparison.get(name);
    }

    public void setMatrix(String category, ComparisonMatrix matrix){
        objectsComparison.put(category, matrix);
    }


}
