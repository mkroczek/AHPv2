package miapd.ahp.gui;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import miapd.ahp.objects.ComparisonObject;

import java.util.Map;

public class ObjectDescription extends GridPane {
    private Label title = new Label();
    private TextArea description = new TextArea();

    public void fill(ComparisonObject object){
        getChildren().clear();
        title = new Label();
        description = new TextArea();
        description.setWrapText(true);
        description.setEditable(false);
        title.setText(object.getProperty("name"));
        add(title, 0, 0);
//        if (object.getProperties() != null) {
//            for (Map.Entry<String, String> entry : object.getProperties().entrySet()) {
//                String name = entry.getKey();
//                String value = entry.getValue();
//                description.appendText(name+"\n"+value+"\n\n");
//            }
//            add(description, 0, 1);
//        }
        for (Map.Entry<String, String> entry : object.getProperties()) {
            String name = entry.getKey();
            String value = entry.getValue();
            description.appendText(name+"\n"+value+"\n\n");
        }
        add(description, 0, 1);
    }
}
