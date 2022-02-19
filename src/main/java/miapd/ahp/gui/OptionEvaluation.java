package miapd.ahp.gui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class OptionEvaluation extends VBox {
    private Label optionName;
    private ToggleGroup group = new ToggleGroup();
    private Map<String, RadioButton> radioButtons = new LinkedHashMap<>();

    public OptionEvaluation(String name, ArrayList<String> options){
        super();
        this.setAlignment(Pos.CENTER);
        optionName = new Label(name);
        this.getChildren().add(optionName);
        GridPane optionsPart = new GridPane();
        optionsPart.setAlignment(Pos.CENTER);
        optionsPart.setHgap(10);
        for (int i = 0 ; i < options.size(); i++){
            String optionName = options.get(i);
            RadioButton radioButton = new RadioButton();
            radioButton.setUserData(optionName);
            radioButton.setToggleGroup(group);
            Label buttonTitle = new Label(optionName);
            radioButtons.put(optionName, radioButton);
            optionsPart.add(buttonTitle, i, 1);
            optionsPart.add(radioButton, i, 2);
            GridPane.setHalignment(radioButton, HPos.CENTER);
        }
        this.getChildren().add(optionsPart);
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
