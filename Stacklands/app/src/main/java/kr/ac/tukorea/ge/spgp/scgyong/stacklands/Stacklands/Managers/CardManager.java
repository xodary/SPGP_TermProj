package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.scgyong.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.scgyong.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.scgyong.framework.util.CollisionHelper;
import kr.ac.tukorea.ge.spgp.scgyong.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.Card;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.MainScene;

public class CardManager implements IGameObject {

    private static final String TAG = CardManager.class.getSimpleName();
    private final MainScene scene;
    private final ArrayList<Card> cards = new ArrayList<>();
    public Card clickingCard = null;
    public RecipeManager recipeManager = new RecipeManager();
    public CardManager(MainScene scene) {
        this.scene = scene;
        // cards.add(CardGenerator.getInstance().CreateCard("boosterPack_ANewWorld"));
        addCard("boosterPack_ANewWorld",0,0);
        addCard("flint",3,0);
        addCard("villager",0,5);
        addCard("berry_bush",-3,0);
        addCard("berry",-3,-5);
    }

    public void addCard(String str, float offsetX, float offsetY){
        Card c = CardGenerator.getInstance().CreateCard(str);
        c.setPosition(Metrics.width / 2 - offsetX, Metrics.height / 2 - offsetY,
                Card.CARD_WIDTH, Card.CARD_HEIGHT);
        cards.add(c);
        scene.add(MainScene.Layer.Card, c);
    }

    @Override
    public void update(float elapsedSeconds) {
        recipeManager.update(elapsedSeconds);
        for(Card c : recipeManager.cards){
            cards.add(c);
            scene.add(MainScene.Layer.Card, c);
        }
        recipeManager.cards.clear();

        if(clickingCard != null){
            Card c = isCollided();
            if(c != null) c.collided();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        recipeManager.draw(canvas);
    }

    public void bringOnTop(Card c) {
        Scene scene = Scene.top();
        if (scene == null) {
            Log.e(TAG, "Scene stack is empty in addToScene() " + this.getClass().getSimpleName());
            return;
        }
        cards.remove(c);
        cards.add(c);
        scene.remove(MainScene.Layer.Card, c);
        scene.add(MainScene.Layer.Card, c);
    }

    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                if (clickingCard != null){
                    Card c = isCollided();
                    if(c != null) {
                        clickingCard.collide(c);
                        recipeManager.findRecipe(c, clickingCard);
                    }
                    clickingCard.clicking = false;
                    clickingCard = null;
                    return true;
                }
                return false;
            case MotionEvent.ACTION_DOWN:
                if(clickingCard == null) {
                    for (int i = cards.size() - 1; i >= 0; i--){
                        Card c = cards.get(i);
                        if(c.onTouch(event)) {
                            clickingCard = c;
                            break;
                        }
                    }
                    if(clickingCard != null) {
                        bringOnTop(clickingCard);
                        return true;
                    }
                    return false;
                }
            case MotionEvent.ACTION_MOVE:
                if(clickingCard != null && clickingCard.onTouch(event)) {
                    Card c = isCollided();
                    if(c != null) c.collided();
                    return true;
                }
                return false;
        }
        return false;
    }

    public Card isCollided(){
        for (int i = cards.size() - 1; i >= 0; i--){
            Card c = cards.get(i);
            if(c == clickingCard) continue;
            if (CollisionHelper.collides(c, clickingCard)) {
                return c;
            }
        }
        return null;
    }
}
