package java;

import miapd.ahp.objects.ComparisonAgent;
import miapd.ahp.objects.ComparisonObject;
import miapd.ahp.objects.ComparisonPair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class SaatyTest {

    @Test
    public void completeSaatyTest(){
        ArrayList<ComparisonObject> objects = new ArrayList<>();
        for (int i = 0; i < 7; i++){
            objects.add(new ComparisonObject("obj"+i));
        }
        ArrayList<ComparisonObject> categories = new ArrayList<>();
        ComparisonObject category = new ComparisonObject("category");
        category.addProperty("name", "category0");
        categories.add(category);
        ComparisonAgent agent = new ComparisonAgent(categories, objects);

        ArrayList<ComparisonPair> pairs = agent.getPairs();

//        agent.updateRating("category0", pairs.get(0), 7.0);
//        agent.updateRating("category0", pairs.get(1), 1.0/6.0);
//        agent.updateRating("category0", pairs.get(2), 1.0/2.0);
//        agent.updateRating("category0", pairs.get(3), 1.0/4.0);
//        agent.updateRating("category0", pairs.get(4), 1.0/6.0);
//        agent.updateRating("category0", pairs.get(5), 4.0);
//        //
//        agent.updateRating("category0", pairs.get(6), 1.0/3.0);
//        agent.updateRating("category0", pairs.get(7), 5.0);
//        agent.updateRating("category0", pairs.get(8), 1.0/5.0);
//        agent.updateRating("category0", pairs.get(9), 1.0/7.0);
//        agent.updateRating("category0", pairs.get(10), 5.0);
//        //
//        agent.updateRating("category0", pairs.get(11), 6.0);
//        agent.updateRating("category0", pairs.get(12), 3.0);
//        agent.updateRating("category0", pairs.get(13), 2.0);
//        agent.updateRating("category0", pairs.get(14), 8.0);
//        //
//        agent.updateRating("category0", pairs.get(15), 8.0);
//        agent.updateRating("category0", pairs.get(16), 1.0/5.0);
//        agent.updateRating("category0", pairs.get(17), 8.0);
//        //
//        agent.updateRating("category0", pairs.get(18), 1.0/9.0);
//        agent.updateRating("category0", pairs.get(19), 2.0);
        //
//        agent.updateRating("category0", pairs.get(20), 2.0);

        System.out.println("Saaty CI = "+agent.getMatrix("category0").calculateSaatysIC());
    }
}
