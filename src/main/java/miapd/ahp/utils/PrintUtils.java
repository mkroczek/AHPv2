package miapd.ahp.utils;

import java.util.Arrays;

public class PrintUtils {
    public static String arr2DToString(double[][] array){
        String result = "";
        for (int i = 0; i < array.length; i++){
            result += Arrays.toString(array[i])+"\n";
        }
        return result;
    }
}
