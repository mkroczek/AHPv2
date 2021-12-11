package miapd.ahpv2.gui;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class CategoryEvaluation extends GridPane {
    private Label categoryName;
    private ToggleGroup group = new ToggleGroup();
    private ArrayList<RadioButton> radioButtons = new ArrayList<>();

    public CategoryEvaluation(String name, int nRadioButtons){
        super();
        categoryName = new Label(name);
        this.add(categoryName, 0, 0, nRadioButtons, 1);
        for (int i = 0 ; i < nRadioButtons; i++){
            RadioButton radioButton = new RadioButton();
            radioButton.setUserData(i);
            radioButton.setToggleGroup(group);
            radioButtons.add(radioButton);
            this.add(radioButton, i, 1);
        }
    }

    public void selectToggle(int id){
        group.selectToggle(radioButtons.get(id));
    }

    public int getSelectedId(){
        return (int)group.getSelectedToggle().getUserData();
    }

}

