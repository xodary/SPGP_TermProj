package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards;


import static java.util.Collections.sort;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Recipe {
    private static Recipe instance;

    public static Recipe getInstance() {
        if (instance == null) {
            instance = new Recipe();
        }
        return instance;
    }
    protected HashMap<ArrayList<String>, AbstractMap.SimpleEntry<String, Float>> recipes = new HashMap<>();

    public void addRecipe(String resultName, Float time, String... materials) {
        AbstractMap.SimpleEntry<String, Float> result = new AbstractMap.SimpleEntry<>(resultName, time);
        ArrayList<String> material_list = new ArrayList<>(Arrays.asList(materials));
        sort(material_list);
        recipes.put(material_list, result);
    }

    public Recipe() {
        addRecipe("berry",1.0f, "villager", "berry_bush");
    }

    public AbstractMap.SimpleEntry<String, Float> inRecipe(ArrayList<Card> cards){
        ArrayList<String> materials = new ArrayList<>();
        for(Card c : cards)
            materials.add(c.getName());
        sort(materials);
        return recipes.get(materials);
    }
}