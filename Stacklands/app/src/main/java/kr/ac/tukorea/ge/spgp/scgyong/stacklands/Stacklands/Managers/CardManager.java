package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.scgyong.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.scgyong.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.scgyong.framework.util.CollisionHelper;
import kr.ac.tukorea.ge.spgp.scgyong.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.Card;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.OrangeCard;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.MainScene;

public class CardManager implements IGameObject {

    private static final String TAG = CardManager.class.getSimpleName();
    private final MainScene scene;
    private final ArrayList<Card> cards = new ArrayList<>();
    public ArrayList<Card> clickingCard = new ArrayList<>();
    public RecipeManager recipeManager = new RecipeManager(this);
    public FeedVillager feedVillager = new FeedVillager(this);
    public RectF collisionBox = new RectF();
    public GameTimer timer;
    public CardManager(MainScene scene) {
        this.scene = scene;
        // cards.add(CardGenerator.getInstance().CreateCard("boosterPack_ANewWorld"));
        addCard("boosterPack_ANewWorld",0,0);
        addCard("flint",3,0);
        feedVillager.addVillager(addCard("villager",0,5));
        addCard("berry_bush",-3,0);
        feedVillager.addFood((OrangeCard) addCard("berry",-3,-5));
        feedVillager.addFood((OrangeCard) addCard("berry",-2,-5));
    }

    public Card addCard(String str, float offsetX, float offsetY){
        Card c = CardGenerator.getInstance().CreateCard(str);
        c.setPosition(Metrics.width / 2 - offsetX, Metrics.height / 2 - offsetY,
                Card.CARD_WIDTH, Card.CARD_HEIGHT);
        cards.add(c);
        scene.add(MainScene.Layer.Card, c);
        return c;
    }

    public void removeCard(Card c){
        cards.remove(c);
        scene.remove(MainScene.Layer.Card, c);
    }
    @Override
    public void update(float elapsedSeconds) {
        feedVillager.update(elapsedSeconds);
        recipeManager.update(elapsedSeconds);
        for(Card c : recipeManager.generatedCards){
            cards.add(c);
            scene.add(MainScene.Layer.Card, c);
        }
        recipeManager.generatedCards.clear();

        if(!clickingCard.isEmpty()){
            Card c = isCollided();
            if(c != null) c.collided();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        recipeManager.draw(canvas);
    }

    public void bringOnTop(ArrayList<Card> cardList) {
        Scene scene = Scene.top();
        if (scene == null) {
            Log.e(TAG, "Scene stack is empty in addToScene() " + this.getClass().getSimpleName());
            return;
        }
        cards.removeAll(cardList);
        cards.addAll(cardList);
        for(Card cc : cardList) {
            scene.remove(MainScene.Layer.Card, cc);
        }
        for(Card cc : cardList) {
            scene.add(MainScene.Layer.Card, cc);
        }
    }

    public boolean onTouch(MotionEvent event) {
        float[] pts = Metrics.fromScreen(event.getX(), event.getY());
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                if (!clickingCard.isEmpty()) {
                    Card c = isCollided();
                    if(c != null) {
                        for (int i = 0; i < clickingCard.size(); ++i) {
                            clickingCard.get(i).collide(c, i);
                            clickingCard.get(i).clicking = false;
                        }
                    }
                    recipeManager.findRecipe(c, clickingCard);
                    clickingCard.removeAll(clickingCard);
                    return true;
                }
                return false;
            case MotionEvent.ACTION_DOWN:
                if(clickingCard.isEmpty()) {
                    for (int i = cards.size() - 1; i >= 0; i--){
                        Card c = cards.get(i);
                        if(c.isContains(pts[0], pts[1])){
                            c.click_offset[0] = c.getX() - pts[0];
                            c.click_offset[1] = c.getY() - pts[1];
                            c.clicking = true;
                            clickingCard = recipeManager.isInDummy(c, clickingCard);
                            bringOnTop(clickingCard);
                            return true;
                        }
                    }
                    return false;
                }
            case MotionEvent.ACTION_MOVE:
                if(!clickingCard.isEmpty()) {
                    for(int i = 0; i < clickingCard.size(); ++i)
                        clickingCard.get(i).setPosition(
                                pts[0] + clickingCard.get(0).click_offset[0],
                                pts[1] + clickingCard.get(0).click_offset[1] + Card.CARD_OFFSET * i,
                                        Card.CARD_WIDTH, Card.CARD_HEIGHT);
                    Card c = isCollided();
                    if(c != null) c.collided();
                    return true;
                }
                return false;
        }
        return false;
    }

    public Card isCollided(){
        if(clickingCard.isEmpty()) return null;
        for (int i = cards.size() - 1; i >= 0; i--){
            Card c = cards.get(i);
            if(clickingCard.contains(c)) continue;
            collisionBox = new RectF(clickingCard.get(0).getCollisionRect());
            collisionBox.bottom = clickingCard.get(clickingCard.size()-1).getCollisionRect().bottom;
            if (CollisionHelper.collides(c, collisionBox)) {
                return c;
            }
        }
        return null;
    }


    public void GameOver() {
    }
}
