package miapd.ahp.gui;

import javafx.scene.layout.GridPane;
import miapd.ahp.ahp.AHPRates;
import miapd.ahp.objects.ComparisonAgent;
import miapd.ahp.objects.ComparisonPair;

import java.util.*;

public class CategoriesWindow extends GridPane {
    private HashMap<String, CategoryEvaluation> categoryWidgets = new HashMap<>();
    private int nRates = AHPRates.getNRates();

    public void reset(){
        getChildren().clear();
        categoryWidgets.clear();
    }

    public void addCategory(String category){
        CategoryEvaluation node = new CategoryEvaluation(category, nRates);
        categoryWidgets.put(category, node);
        int nCategory = categoryWidgets.size() - 1;
        add(node, 0, nCategory);
    }

    public void addAll(Collection<String> categories){
        for (String category : categories){
            addCategory(category);
        }
    }

    public void saveResults(ComparisonAgent agent, ComparisonPair pair){
        for (Map.Entry<String, CategoryEvaluation> entry : categoryWidgets.entrySet()){
            int selectedId = entry.getValue().getSelectedId();
            if (selectedId >= 0)
                agent.updateRating(entry.getKey(), pair, selectedId);
        }
    }

    public void fill(String category, int rateId){
        categoryWidgets.get(category).selectToggle(rateId);
    }
}

