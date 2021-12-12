package miapd.ahp.ahp;

import java.util.*;

public class CalculationOptions<T> {
    private Map<String, ArrayList<T>> alternativesMap = new HashMap<>();
    private Map<String, T> chosenMap = new HashMap<>();

    public void addOption(String optionName, ArrayList<T> alternatives, T chosenAlternative){
        alternativesMap.put(optionName, alternatives);
        chosenMap.put(optionName, chosenAlternative);
    }

    public void setChosen(String option, T alternative){
        chosenMap.put(option, alternative);
    }

    public T getChosen(String optionName){
        return chosenMap.get(optionName);
    }

    public ArrayList<T> getAlternatives(String optionName){
        return alternativesMap.get(optionName);
    }

    public Set<String> getOptionsSet(){
        return alternativesMap.keySet();
    }
}
