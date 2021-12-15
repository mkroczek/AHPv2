package miapd.ahp.objects;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ComparisonObject {
    private String type;
    private HashMap<String, String> properties = new LinkedHashMap<>();


    public ComparisonObject(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public String getProperty(String property){
        return this.properties.get(property);
    }

    public Set<Map.Entry<String, String>> getProperties(){
        return properties.entrySet();
    }

    public void addProperty(String key, String value){
        this.properties.put(key, value);
    }


}
