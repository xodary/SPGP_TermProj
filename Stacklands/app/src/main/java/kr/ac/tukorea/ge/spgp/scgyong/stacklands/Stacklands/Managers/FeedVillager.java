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
    static float FEEDTIME = 0.5f;
    float spendedTime = 0.0f;
    ArrayList<FoodForVillager> foodForVillager = new ArrayList<>();
    ArrayList<OrangeCard> foods = new ArrayList<>();

    public FeedVillager() {
        feedVillager = this;
    }

    public void addVillager(Card v) {

        foodForVillager.add(new FoodForVillager(v));
    }

    public void addFood(Card f) {
        foods.add((OrangeCard) f);
    }

    int i = 0;

    public void update(float elapsedSeconds) {
        if (timerStart) {
            spendedTime += elapsedSeconds;
            FoodForVillager villager = foodForVillager.get(i);
            if (spendedTime > FEEDTIME) {
                OrangeCard food = villager.foods.get(0);
                villager.foods.remove(food);
                this.foods.remove(food);
                CardManager.cardManager.removeCard(food);
                spendedTime = 0.0f;
                if (villager.foods.size() == 0) {
                    if (!villager.isEnough) {
                        CardManager.cardManager.GameOver();
                        return;
                    }
                    else {
                        i += 1;
                        if (foodForVillager.size() >= i) {
                            doneFeeding();
                            return;
                        }
                        else villager = foodForVillager.get(i);
                    }
                }
            }
            OrangeCard food = villager.foods.get(0);
            CardManager.cardManager.bringOnTop(food);
            float x = villager.villager.getX() * (spendedTime / FEEDTIME) +
                    food.originX * (1 - spendedTime / FEEDTIME);
            float y = villager.villager.getY() * (spendedTime / FEEDTIME) +
                    food.originY * (1 - spendedTime / FEEDTIME);
            food.setPosition(x, y, Card.CARD_WIDTH, Card.CARD_HEIGHT);
        }
    }


    @Override
    public void draw(Canvas canvas) {

    }

    public void startFeeding() {
        timerStart = true;
        int i = 0;
        for (FoodForVillager villager : foodForVillager) {
            while (!villager.isEnough) {
                if (foods.size() > i) villager.Feed(foods.get(i++));
                else break;
            }
        }
        for (FoodForVillager villager : foodForVillager) {
            for (OrangeCard food : villager.foods) {
                food.originX = food.getX();
                food.originY = food.getY();
            }
        }
    }

    void doneFeeding(){
        timerStart = false;
        for (FoodForVillager villager : foodForVillager) {
            villager.isEnough = false;
            villager.reset();
            foods.removeAll(villager.foods);
        }
        GameTimer.gameTimer.reset();
        i = 0;
    }
}
