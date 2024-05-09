package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.tukorea.ge.spgp.scgyong.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.Card;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.Recipe;

public class RecipeManager implements IGameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    public ArrayList<AbstractMap.SimpleEntry<AbstractMap.SimpleEntry<Card, Float>, Integer>> cookingCards = new ArrayList<>();
    public ArrayList<ArrayList<Card>> cardDummy = new ArrayList<>();
    public HashMap<Integer, Float> spendedTime = new HashMap<>();
    public Recipe recipe = new Recipe();
    public RecipeManager() {
    }
    @Override
    public void update(float elapsedSeconds) {
        for(AbstractMap.SimpleEntry<AbstractMap.SimpleEntry<Card, Float>, Integer> cooklist : cookingCards) {
            float time = cooklist.getKey().getValue() - elapsedSeconds;
            if(time >= 0.0f)
                cooklist.getKey().setValue(time);
            else {
                Card newCard = cooklist.getKey().getKey();
            }
        }
    }

    public void findRecipe(Card collided, Card collide) {
        for(int i = 0; i < cardDummy.size(); ++i){
            ArrayList<Card> cooklist = cardDummy.get(i);
            if(cooklist.contains(collided)){
                cooklist.add(collide);
                AbstractMap.SimpleEntry<String, Float> result = recipe.inRecipe(cooklist);
                if(result == null) return;
                Card c = CardGenerator.getInstance().CreateCard(result.getKey());
                AbstractMap.SimpleEntry<Card, Float> newcard = new AbstractMap.SimpleEntry<>(c, result.getValue());
                AbstractMap.SimpleEntry<AbstractMap.SimpleEntry<Card, Float>, Integer> a = new AbstractMap.SimpleEntry<>(newcard, i);
                cookingCards.add(a);
                spendedTime.put(i, newcard.getValue());
                return;
            }
        }
        // 만약 리스트에 없다면 처음으로 2개가 된 카드 더미이다.
        ArrayList<Card> array = new ArrayList<>();
        array.add(collided);
        array.add(collide);
        cardDummy.add(array);
        AbstractMap.SimpleEntry<String, Float> result = recipe.inRecipe(array);
        if(result == null) return;
        Card c = CardGenerator.getInstance().CreateCard(result.getKey());
        AbstractMap.SimpleEntry<Card, Float> newcard = new AbstractMap.SimpleEntry<>(c, result.getValue());
        AbstractMap.SimpleEntry<AbstractMap.SimpleEntry<Card, Float>, Integer> a = new AbstractMap.SimpleEntry<>(newcard, cardDummy.size()-1);
        cookingCards.add(a);
        spendedTime.put(cardDummy.size()-1, newcard.getValue());
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
        for(AbstractMap.SimpleEntry<AbstractMap.SimpleEntry<Card, Float>, Integer> cooklist : cookingCards) {
            float x = cardDummy.get(cooklist.getValue()).get(0).getX();
            float y = cardDummy.get(cooklist.getValue()).get(0).getY() - Card.CARD_HEIGHT / 2 - 0.5f;
            float width = Card.CARD_WIDTH;
            float height = 0.5f;
            RectF or = new RectF(x - width/2, y - height/2, x + width/2, y + height/2);
            canvas.drawRect(or, outlinePaint);

            width -= 0.2f;
            float spended_time = cooklist.getKey().getValue();
            float full_time = spendedTime.get(cooklist.getValue());
            float new_width = width * (spended_time / full_time);
            height -= 0.2f;
            RectF ir = new RectF(x - width/2, y - height/2, x - width/2 + new_width, y + height/2);
            canvas.drawRect(ir, inlinePaint);
        }
    }
}
