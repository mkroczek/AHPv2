package miapd.ahp.objects;

import java.util.ArrayList;

public class ComparisonPair {
    private int x;
    private int y;
    private ComparisonObject firstObject;
    private ComparisonObject secondObject;
    private ArrayList<String> categories;

    public ComparisonPair(int x, int y, ComparisonObject firstObject, ComparisonObject secondObject, ArrayList<String> toCompare){
        this.x = x;
        this.y = y;
        this.firstObject = firstObject;
        this.secondObject = secondObject;
        this.categories = toCompare;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public ComparisonObject getFirstObject(){
        return this.firstObject;
    }

    public ComparisonObject getSecondObject(){
        return this.secondObject;
    }

    public ArrayList<String> getCategories(){
        return this.categories;
    }
}
