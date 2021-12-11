package miapd.ahpv2.gui;

import javafx.scene.layout.GridPane;

import java.util.*;

public class CategoriesWindow extends GridPane {
    private HashMap<Category, CategoryEvaluation> categoryWidgets = new HashMap<>();
    private int nRates;

    public CategoriesWindow(int nRates){
        this.nRates = nRates;
    }

    public void setnRates(int nRates){
        this.nRates = nRates;
    }

    public void addCategory(Category category){
        CategoryEvaluation node = new CategoryEvaluation(category.getName(), nRates);
        categoryWidgets.put(category, node);
        int nCategory = categoryWidgets.size() - 1;
        add(node, 0, nCategory);
    }

    public void addAll(Collection<Category> categories){
        for (Category category : categories){
            addCategory(category);
        }
    }

    public void saveResults(ComparisonEngine engine){
        for (Map.Entry<Category, CategoryEvaluation> entry : categoryWidgets.entrySet()){
            engine.update(entry.getKey(), entry.getValue().getSelectedId());
        }
    }

    public void fill(ComparisonEngine engine){
        for (Map.Entry<Category, CategoryEvaluation> entry : categoryWidgets.entrySet()){
            entry.getValue().selectToggle(engine.getRateID(entry.getKey()));
        }
    }
}

