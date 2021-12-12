package miapd.ahp.gui;

import javafx.scene.layout.GridPane;
import miapd.ahp.ahp.CalculationOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class OptionsWindow extends GridPane {
    private HashMap<String, OptionEvaluation> optionsWidgets = new HashMap<>();

    public void reset(){
        getChildren().clear();
        optionsWidgets.clear();
    }

    public void addOption(String option, ArrayList<String> alternatives){
        OptionEvaluation node = new OptionEvaluation(option, alternatives);
        optionsWidgets.put(option, node);
        int nCategory = optionsWidgets.size() - 1;
        add(node, 0, nCategory);
    }

    public void fillData(CalculationOptions<String> options){
        for (String option : options.getOptionsSet()){
            addOption(option, options.getAlternatives(option));
            fill(option, options.getChosen(option));
        }
    }

    public void saveResults(CalculationOptions<String> options){
        for (String option : options.getOptionsSet()){
            String selectedAlternative = optionsWidgets.get(option).getSelectedAlternative();
            if (selectedAlternative != null)
                options.setChosen(option, selectedAlternative);
        }
    }

    public void fill(String option, String alternative){
        optionsWidgets.get(option).selectToggle(alternative);
    }
}
