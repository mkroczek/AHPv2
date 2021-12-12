package miapd.ahp.gui;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class OptionEvaluation extends GridPane {
    private Label optionName;
    private ToggleGroup group = new ToggleGroup();
    private Map<String, RadioButton> radioButtons = new LinkedHashMap<>();

    public OptionEvaluation(String name, ArrayList<String> options){
        super();
        optionName = new Label(name);
        this.add(optionName, 0, 0, options.size(), 1);
        for (int i = 0 ; i < options.size(); i++){
            String optionName = options.get(i);
            RadioButton radioButton = new RadioButton();
            radioButton.setUserData(optionName);
            radioButton.setToggleGroup(group);
            Label buttonTitle = new Label(optionName);
            radioButtons.put(optionName, radioButton);
            this.add(buttonTitle, i, 1);
            this.add(radioButton, i, 2);
        }
    }

    public void selectToggle(String alternative){
        group.selectToggle(radioButtons.get(alternative));
    }

    public String getSelectedAlternative(){
        Toggle selected = group.getSelectedToggle();
        if (selected != null){
            return (String) selected.getUserData();
        }
        return null;
    }
}
