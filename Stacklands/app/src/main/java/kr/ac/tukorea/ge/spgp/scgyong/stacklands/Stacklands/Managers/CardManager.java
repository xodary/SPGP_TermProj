package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.scgyong.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.scgyong.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.scgyong.framework.util.CollisionHelper;
import kr.ac.tukorea.ge.spgp.scgyong.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.R;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.BoosterPack;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.Card;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.MainScene;

public class CardManager implements IGameObject {
    private static final String TAG = CardManager.class.getSimpleName();
    private final MainScene scene;
    private final ArrayList<Card> cards = new ArrayList<>();
    public ArrayList<Card> clickingCard = new ArrayList<>();
    public static CardManager cardManager;
    public RectF collisionBox = new RectF();
    public FeedVillager feedVillager = new FeedVillager();

    private SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);;
    private int soundId;

    public CardManager(MainScene scene) {
        cardManager = this;
        this.scene = scene;
        addBoosterPack("boosterPack_ANewWorld",0,0);
        soundId = soundPool.load(scene.activity, R.raw.putdown, 1);
    }
    public Card addBoosterPack(String str, float offsetX, float offsetY) {
        Card c = CardGenerator.getInstance().CreateCard(str);

        c.setPosition(Metrics.width / 2 - offsetX, Metrics.height / 2 - offsetY,
                BoosterPack.BOOSTERPACK_WIDTH, BoosterPack.BOOSTERPACK_HEIGHT);
        addCard(c);
        return c;
    }

    public Card addCard(Card c){
        if(c.color == "Yellow")
            feedVillager.addVillager(c);
        else if(c.color == "Orange")
            feedVillager.addFood(c);
        cards.add(c);
        scene.add(MainScene.Layer.Card, c);
        return c;
    }

    public void removeCard(Card c){
        cards.remove(c);
        scene.remove(MainScene.Layer.Card, c);
        RecipeManager.recipeManager.removeCard(c);
    }
    @Override
    public void update(float elapsedSeconds) {
        feedVillager.update(elapsedSeconds);
        RecipeManager.recipeManager.update(elapsedSeconds);
        for(Card c : RecipeManager.recipeManager.generatedCards){
            addCard(c);
        }
        RecipeManager.recipeManager.generatedCards.clear();

        if(!clickingCard.isEmpty()){
            Card c = isCollided();
            if(c != null) c.collided();
        }
    }

    @Override
    public void draw(Canvas canvas) {
    }

    public void bringOnTop(ArrayList<Card> cardList) {
        Scene scene = Scene.top();
        cards.removeAll(cardList);
        cards.addAll(cardList);
        for(Card cc : cardList) {
            scene.remove(MainScene.Layer.Card, cc);
        }
        for(Card cc : cardList) {
            scene.add(MainScene.Layer.Card, cc);
        }
    }

    public void bringOnTop(Card c) {
        Scene scene = Scene.top();
        cards.remove(c);
        cards.add(c);
        scene.remove(MainScene.Layer.Card, c);
        scene.add(MainScene.Layer.Card, c);
    }

    public boolean onTouch(MotionEvent event) {
        if(!GameTimer.gameTimer.isActive) return false;
        float[] pts = Metrics.fromScreen(event.getX(), event.getY());
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                if (!clickingCard.isEmpty()) {
                    if(clickingCard.size() == 1 && clickingCard.get(0).color == "BoosterPack") {
                        if (event.getEventTime() - event.getDownTime() < 200) {
                            clickingCard.get(0).click();
                        }
                    }
                    else {
                        Card c = isCollided();
                        if (c != null && c.color != "BoosterPack") {
                            for (int i = 0; i < clickingCard.size(); ++i) {
                                clickingCard.get(i).collide(c, i);
                                clickingCard.get(i).clicking = false;
                            }
                        }
                        RecipeManager.recipeManager.findRecipe(c, clickingCard);
                    }
                    clickingCard.removeAll(clickingCard);
                    soundPool.play(soundId, 1, 1, 0, 0, 1);
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
                            clickingCard = RecipeManager.recipeManager.isInDummy(c, clickingCard);
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
                                clickingCard.get(0).width, clickingCard.get(0).height);
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

    void GameOver(){
        scene.popAll();
        scene.activity.overGameActivity();
    }
}
