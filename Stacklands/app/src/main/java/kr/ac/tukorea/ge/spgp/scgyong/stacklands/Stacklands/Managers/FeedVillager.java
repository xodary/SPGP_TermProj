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

    public void reset(){
        satiety = 0;
        isFull = false;
    }
}
public class FeedVillager implements IGameObject {
    public static FeedVillager feedVillager;
    boolean timerStart = false;
    static float FEEDTIME = 2.f;
    float spendedTime = 0.0f;
    ArrayList<FoodForVillager> foodForVillager = new ArrayList<>();
    ArrayList<OrangeCard> foods = new ArrayList<>();

    public FeedVillager(){ feedVillager = this; }
    public void addVillager(Card v){
        foodForVillager.add(new FoodForVillager(v));
    }
    public void addFood(Card f){ foods.add((OrangeCard)f); }

    public void update(float elapsedSeconds) {
        if(timerStart) {
            spendedTime += elapsedSeconds;
            for(FoodForVillager villager : foodForVillager){
                if(!villager.isEnough) {
                    CardManager.cardManager.GameOver();
                }
                if(spendedTime > FEEDTIME) {
                    for(Card c : villager.foods)
                        CardManager.cardManager.removeCard(c);
                    villager.isEnough = false;
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
            for(FoodForVillager villager : foodForVillager){
                villager.reset();
                foods.removeAll(villager.foods);
            }
            GameTimer.gameTimer.reset();
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }

    public void startFeeding(){
        timerStart = true;
        int i = 0;
        for(FoodForVillager villager : foodForVillager) {
            while(!villager.isEnough)
                villager.Feed(foods.get(i++));
        }
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
