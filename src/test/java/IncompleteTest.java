package java;

import miapd.ahp.objects.ComparisonAgent;
import miapd.ahp.objects.ComparisonObject;
import miapd.ahp.objects.ComparisonPair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class IncompleteTest {
    @Test
    public void incompleteTest(){
        ArrayList<ComparisonObject> objects = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            objects.add(new ComparisonObject("obj"+i));
        }
        ArrayList<ComparisonObject> categories = new ArrayList<>();
        ComparisonObject category = new ComparisonObject("category");
        category.addProperty("name", "category0");
        categories.add(category);
        ComparisonAgent agent = new ComparisonAgent(categories, objects);

        ArrayList<ComparisonPair> pairs = agent.getPairs();

        agent.updateRating("category0", pairs.get(0), 2.0/3.0);
        agent.updateRating("category0", pairs.get(3), 9.0);
        agent.updateRating("category0", pairs.get(5), 7.0/4.0);
//        agent.updateRating("category0", pairs.get(8), 1.0/3.0);
//        agent.updateRating("category0", pairs.get(9), 9.0);

//        double[] result = agent.calculateSingleEVRanking();
//        double[] res = agent.calculateSingleGMRanking();
//        System.out.println("EVM res = "+Arrays.toString(result));
//        System.out.println("GMM res = "+Arrays.toString(res));
    }
}
