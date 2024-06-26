package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers;

import static java.util.Collections.sort;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import kr.ac.tukorea.ge.spgp.scgyong.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.Card;

class Dummy {
    ArrayList<Card> materials = new ArrayList<>();
    ArrayList<Card> deleteCard = new ArrayList<>();
    boolean isInRecipe = false;
    ArrayList<Card> result;
    Float fullTime;
    Float spendedTime;

}

class Recipe {

    class CardResult{
        AbstractMap.SimpleEntry<String, Integer> resultNameNum; // 결과 카드의 이름, 갯수
        Float time;
    }
    protected HashMap<ArrayList<String>, CardResult> recipes = new HashMap<>();

    public void addRecipe(String resultName, Integer cardNum, Float time, String... materials) {
        CardResult result = new CardResult();
        result.resultNameNum = new AbstractMap.SimpleEntry<>(resultName, cardNum);
        result.time = time;
        ArrayList<String> material_list = new ArrayList<>(Arrays.asList(materials));
        sort(material_list);
        recipes.put(material_list, result);
    }
    public Recipe() {
        addRecipe("orange_berry",4,3.0f, "yellow_villager", "black_berry_bush");
        addRecipe("silver_stick",1,3.0f, "yellow_villager", "silver_wood");
        addRecipe("silver_wood",4,3.0f, "yellow_villager", "tree");
        addRecipe("silver_stone",4,3.0f, "yellow_villager", "black_rock");
        addRecipe("silver_wood",4,3.0f, "yellow_villager", "black_tree");
    }

    public boolean inRecipe(Dummy dummy){
        ArrayList<String> materials = new ArrayList<>();
        for(Card c : dummy.materials)
            materials.add(c.getName());
        sort(materials);
        if (!recipes.containsKey(materials)) return false;
        CardResult result = recipes.get(materials);
        ArrayList<Card> resultCard = new ArrayList<>();
        for(int i =0; i < result.resultNameNum.getValue();++i) {
            resultCard.add(CardGenerator.getInstance().CreateCard(result.resultNameNum.getKey()));
        }
        dummy.result = resultCard;
        dummy.fullTime = result.time;
        dummy.spendedTime = result.time;
        dummy.isInRecipe = true;
        return true;
    }
}
public class RecipeManager implements IGameObject {
    private static final String TAG = RecipeManager.class.getSimpleName();
    public ArrayList<Dummy> dummys = new ArrayList<>();
    public Recipe recipe = new Recipe();
    public ArrayList<Card> generatedCards = new ArrayList<>();
    public static RecipeManager recipeManager;
    public RecipeManager() {
        recipeManager = this;
    }
    @Override
    public void update(float elapsedSeconds) {
        // 더미 중에서 1개인게 있으면 더미에서 삭제한다.
        Dummy remove = null;
        for(Dummy dummy : dummys) {
            if (dummy.materials.size() <= 1)
                remove = dummy;
        }
        if(remove != null) dummys.remove(remove);

        Card removeCard = null;
        // 더미들을 검사한다.
        for(Dummy dummy : dummys){
            if(!dummy.isInRecipe) continue;
            if(dummy.spendedTime > 0.0f)
                dummy.spendedTime -= elapsedSeconds;
            else{
                dummy.spendedTime = dummy.fullTime;
                Card c = dummy.result.get(dummy.result.size() - 1);
                dummy.result.remove(dummy.result.size() - 1);
                c.setPosition(dummy.materials.get(0).getX()+(dummy.result.size() - 1) * 0.5f,
                        dummy.materials.get(dummy.materials.size() - 1).getY() + 2.5f,
                        Card.CARD_WIDTH, Card.CARD_HEIGHT);
                generatedCards.add(c);
                if(dummy.result.isEmpty()) {
                    dummy.isInRecipe = false;
                    for(Card m : dummy.materials) {
                        if (m.color == "Black" || m.color == "Silver") {
                            removeCard = m;
                        }
                    }
                    dummy.materials.removeAll(dummy.deleteCard);
                    dummy.deleteCard.removeAll(dummy.deleteCard);
                }
            }
        }
        CardManager.cardManager.removeCard(removeCard);
    }

    public void findRecipe(Card collided, ArrayList<Card> collides) {
        boolean inDummys = false;
        if(collided == null && collides.size() == 1) return;
        if(collided == null && collides.size() > 1){
            addDummy(collides);
            return;
        }
        for (Dummy d : dummys){
            if(d.materials.contains(collided) && !d.materials.contains(collides)){
                d.materials.addAll(collides);
                recipe.inRecipe(d);
                inDummys = true;
            }
        }
        if(!inDummys){
            collides.add(0, collided);
            addDummy(collides);
        }
    }

    public Dummy addDummy(ArrayList<Card> cards){
        Dummy d = new Dummy();
        d.materials.addAll(cards);
        dummys.add(d);
        recipe.inRecipe(d);
        return d;
    }
    Paint outlinePaint = null;
    Paint inlinePaint = null;
    @Override
    public void draw(Canvas canvas) {
        if(outlinePaint == null) {
            outlinePaint = new Paint();
            outlinePaint.setColor(Color.GRAY);
            outlinePaint.setStyle(Paint.Style.FILL);

            inlinePaint = new Paint();
            inlinePaint.setColor(Color.BLACK);
            inlinePaint.setStyle(Paint.Style.FILL);
        }

        for(Dummy dummy : dummys) {
            if(!dummy.isInRecipe) continue;
            float x = dummy.materials.get(0).getX();
            float y = dummy.materials.get(0).getY() - Card.CARD_HEIGHT / 2 - 0.5f;
            float width = Card.CARD_WIDTH;
            float height = 0.5f;
            RectF or = new RectF(x - width/2, y - height/2, x + width/2, y + height/2);
            canvas.drawRect(or, outlinePaint);

            width -= 0.2f;
            float new_width = width * (dummy.spendedTime / dummy.fullTime);
            height -= 0.2f;
            RectF ir = new RectF(x - width/2, y - height/2, x - width/2 + new_width, y + height/2);
            canvas.drawRect(ir, inlinePaint);
        }
    }

    public ArrayList<Card> isInDummy(Card c, ArrayList<Card> mainSceneCards) {
        for(Dummy d : dummys){
            if(d.materials.contains(c)) {
                int idx = d.materials.indexOf(c);
                if(idx == 0){
                    mainSceneCards = d.materials;
                    return mainSceneCards;
                }
                List<Card> list = d.materials.subList(idx, d.materials.size());
                Dummy newDummy = addDummy(new ArrayList<>(list));
                d.materials.removeAll(list);
                mainSceneCards = newDummy.materials;
                return mainSceneCards;
            }
        }
        mainSceneCards.add(c);
        return mainSceneCards;
    }

    public void removeCard(Card c) {
        for(Dummy d : dummys){
            if(d.materials.contains(c)) {
                d.materials.remove(c);
                return;
            }
        }
    }
}
