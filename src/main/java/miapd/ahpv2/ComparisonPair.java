package miapd.ahpv2;

import java.util.ArrayList;

public class ComparisonPair {
    private int x;
    private int y;
    private ArrayList<String> matrices;

    public ComparisonPair(int x, int y, ArrayList<String> toCompare){
        this.x = x;
        this.y = y;
        this.matrices = toCompare;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public ArrayList<String> getMatrices(){
        return this.matrices;
    }
}
