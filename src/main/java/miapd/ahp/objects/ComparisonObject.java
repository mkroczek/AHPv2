package miapd.ahp.objects;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ComparisonObject {
    private String type;
    private HashMap<String, String> properties = new LinkedHashMap<>();


    public ComparisonObject(String name) {
        this.type = name;
    }

    public String getType(){
        return type;
    }

    public String getProperty(String property){
        return this.properties.get(property);
    }

    public void addProperty(String key, String value){
        this.properties.put(key, value);
    }


}
