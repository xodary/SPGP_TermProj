package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.scgyong.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.Card;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.OrangeCard;

class FoodForVillager {
    Card villager;
    ArrayList<OrangeCard> foods =  new ArrayList<>();
    boolean isEnough = false;
    boolean isFull = false;
    int satiety;

    public FoodForVillager(Card villager){
        this.villager = villager;
    }

    public void Feed(OrangeCard food){
        this.satiety += food.satiety;
        foods.add(food);
        if(this.satiety >= 2) isEnough = true;
    }
}
public class FeedVillager implements IGameObject {
    public static FeedVillager feedVillager;
    boolean timerStart = false;
    static float FEEDTIME = 2.f;
    float spendedTime = 0.0f;
    ArrayList<FoodForVillager> foodForVillager = new ArrayList<>();

    public FeedVillager(){ feedVillager = this; }
    public void addVillager(Card v){
        foodForVillager.add(new FoodForVillager(v));
    }
    public void addFood(Card f){
        for(FoodForVillager villager : foodForVillager) {
            if(!villager.isEnough)
                villager.Feed((OrangeCard)f);
        }
    }

    public void update(float elapsedSeconds) {
        if(timerStart) {
            spendedTime += elapsedSeconds;
            for(FoodForVillager villager : foodForVillager){
                if(!villager.isEnough) CardManager.cardManager.GameOver();
                if(spendedTime > FEEDTIME) {
                    for(Card c : villager.foods)
                        CardManager.cardManager.removeCard(c);
                    villager.isFull = true;
                    spendedTime  = 0.0f;
                }
                if(villager.isFull) continue;
                for(OrangeCard food : villager.foods){
                    float x = villager.villager.getX() * (spendedTime/FEEDTIME) +
                            food.originX * (1 - spendedTime/FEEDTIME);
                    float y = villager.villager.getY() * (spendedTime/FEEDTIME) +
                            food.originY * (1 - spendedTime/FEEDTIME);
                    food.setPosition(x, y, Card.CARD_WIDTH, Card.CARD_HEIGHT);
                }
                return;
            }
            timerStart = false;
            GameTimer.gameTimer.reset();
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }

    public void startFeeding(){
        timerStart = true;
        for(FoodForVillager villager : foodForVillager){
            for(OrangeCard food : villager.foods){
                food.originX = food.getX();
                food.originY = food.getY();
            }
        }
    }

    public boolean isDoneFeeding() {
        return !timerStart;
    }
}
