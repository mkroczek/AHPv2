package miapd.ahp.ahp;

import java.util.HashMap;
import java.util.Map;

public class AHPRates{
    private static final Map<Integer, Double> idToRate = new HashMap<>();
    private static final Map<Double, Integer> rateToId = new HashMap<>();

    static {
        double[] rates = {9.0, 7.0, 5.0,3.0, 1.0, 1.0/3.0, 1.0/5.0, 1.0/7.0, 1.0/9.0};
        int i = 0;
        for (Double rate : rates){
            idToRate.put(i, rate);
            rateToId.put(rate, i);
            i++;
        }
    }

    public static Double getRate(int id){
        return idToRate.get(id);
    }

    public static Double getOppositeRate(int id){
        int oppositeId = (idToRate.size()-1-id)%idToRate.size();
        return idToRate.get(oppositeId);
    }

    public static int getNRates(){
        return idToRate.size();
    }

    public static int getId(Double rate){
        return rateToId.get(rate);
    }
}
