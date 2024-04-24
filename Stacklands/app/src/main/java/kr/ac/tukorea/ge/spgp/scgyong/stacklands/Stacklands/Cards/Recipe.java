package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Recipe {
    protected HashMap<ArrayList<String>, String> recipes = new HashMap<>();

    public void addCardPack(String result, String... materials) {
        recipes.put((ArrayList<String>) Arrays.asList(materials), result);
    }

    public Recipe() {
        addCardPack("berry", "village", "berry_bush");
    }
}