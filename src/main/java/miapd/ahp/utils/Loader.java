package miapd.ahp.utils;

import miapd.ahp.objects.ComparisonObject;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Loader {
    private final JSONParser parser = new JSONParser();
    private JSONObject jsonObject = null;

    public void loadJSON(String filename) {
        try {
            String localDir = System.getProperty("user.dir");
            Object obj;
            if(System.getProperty("file.separator").equals("/")){
                obj = parser.parse(new FileReader(localDir+"/"+filename));
            }
            else {
                obj = parser.parse(new FileReader(localDir+"/"+filename));
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
            //iterate through each object
            object = iterator.next();
            ComparisonObject tmpObject = new ComparisonObject(objectType);
            Iterator<String> propertyIterator = object.keySet().iterator();
            while(propertyIterator.hasNext()){
                String key = propertyIterator.next();
                tmpObject.addProperty(key, (String)object.get(key));
            }
//            for (String property:properties){
//                tmpObject.addProperty(property, (String)object.get(property));
//            }
            objectsList.add(tmpObject);
        }
        assert jsonObject != null;
        return objectsList;
    }

    public JSONObject getJsonObject(){
        return jsonObject;
    }

}
