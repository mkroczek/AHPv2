package miapd.ahpv2;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Loader {
    private final JSONParser parser = new JSONParser();
    private JSONObject jsonObject = null;

    public void loadJSON() {
        try {
            String localDir = System.getProperty("user.dir");
            Object obj;
            if(System.getProperty("file.separator").equals("/")){
                obj = parser.parse(new FileReader(localDir + "/src/main/java/miapd/ahpv2/objects.json"));
            }
            else {
                obj = parser.parse(new FileReader(localDir + "\\src\\main\\java\\miapd\\ahpv2\\objects.json"));
            }
            jsonObject = (JSONObject) obj;
        } catch (ParseException | IOException parseException) {
            parseException.printStackTrace();
        }
    }

    public ArrayList<ComparisonObject> createObjectList(String objectType, String[] properties) {
        ArrayList<ComparisonObject> objectsList = new ArrayList<>();
        JSONArray objectsJSON = (JSONArray) jsonObject.get(objectType);
        Iterator<JSONObject> iterator = objectsJSON.iterator();
        JSONObject object;
        while (iterator.hasNext()) {
            object = iterator.next();
            ComparisonObject tmpObject = new ComparisonObject(objectType);
            for (String property:properties){
                tmpObject.addProperty(property, (String)object.get(property));
            }
            objectsList.add(tmpObject);
        }
        assert jsonObject != null;
        return objectsList;
    }

}
