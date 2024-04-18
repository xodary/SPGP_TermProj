package kr.ac.tukorea.ge.spgp.scgyong.stacklands;

import java.util.ArrayList;
import android.content.res.Resources;

public class Recipe {
    protected final ArrayList<Integer> resourceNames = new ArrayList<>();
    private Card resultCard;
    public Recipe() {
    }

    public void init(){

    }

    public Recipe addMaterial(int material){
        resourceNames.add(material);
        return this;
    }

    public Recipe setResult(Card card){
        resultCard = card;
        return this;
    }

    public Card getResult(){ return resultCard; }
}

class RecipeManager{
    protected final ArrayList<Recipe> recipes = new ArrayList<>();
    public RecipeManager(){

    }

    public void init(Resources res){
        recipes.add(new Recipe()
                .addMaterial(R.mipmap.black_rock)
                .addMaterial(R.mipmap.yellow_villager)
                .setResult(SilverCardManager.getInstance().GetStone(res)));
    }
}