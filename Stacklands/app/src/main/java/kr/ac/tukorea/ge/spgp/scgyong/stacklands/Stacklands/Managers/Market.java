package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.scgyong.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.scgyong.framework.util.CollisionHelper;
import kr.ac.tukorea.ge.spgp.scgyong.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.BoosterPack;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.Card;

class sellingCard{
    static float pad = 0.2f;
    public String showName;
    public String keyName;
    public Card card;
    public RectF rect;

    sellingCard(String showName, String keyName, int idx){
        this.showName = showName;
        this.keyName = keyName;
        if(keyName != "sell") card = CardGenerator.getInstance().CreateCard(keyName);
        rect = new RectF(pad + (Card.CARD_WIDTH + pad) * idx, Metrics.height - pad - Card.CARD_HEIGHT,
                (pad + Card.CARD_WIDTH) * (idx + 1), Metrics.height - pad);
    }
}
public class Market implements IGameObject {

    public sellingCard[] sellingCards = new sellingCard[4];
    public static Market market;

    public Market() {
        market = this;
        sellingCards[0] = new sellingCard("sell", "sell", 0);
        sellingCards[1] = new sellingCard("Humble Beginnings", "boosterPack_humble_beginnings", 1);
        sellingCards[2] = new sellingCard("Seeking Wisdom", "boosterPack_seeking_wisdom",2);
        sellingCards[3] = new sellingCard("Reap & Sow", "boosterPack_reap_n_sow",3);
    }

    @Override
    public void update(float elapsedSeconds) {

    }
    Paint linePaint;
    Paint textPaint;
    @Override
    public void draw(Canvas canvas) {
        if (linePaint == null) {
            linePaint = new Paint();
            linePaint.setColor(Color.BLACK);
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setStrokeWidth(0.05f);

            textPaint = new Paint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(0.4f);
            textPaint.setLinearText(true);
        }
        for (int i = 0; i < sellingCards.length; ++i) {
            canvas.drawRect(sellingCards[i].rect, linePaint);
            String[] words = sellingCards[i].showName.split(" ");
            StringBuilder line = new StringBuilder();
            float y = -0.5f;
            for (String word : words) {
                if (line.length() < 7) {
                    line.append(word).append(" ");
                } else {
                    canvas.drawText(line.toString(), 0.1f + sellingCards[i].rect.left,
                            (sellingCards[i].rect.top + sellingCards[i].rect.bottom) / 2 + y, textPaint);
                    line = new StringBuilder(word + " ");
                    y += 0.5f;
                }
            }
            if (line.length() > 0) {
                canvas.drawText(line.toString(), 0.1f + sellingCards[i].rect.left,
                        (sellingCards[i].rect.top + sellingCards[i].rect.bottom) / 2 + y, textPaint);
            }
            if (sellingCards[i].showName != "sell") {
                y += 0.5f;
                BoosterPack boosterPack = (BoosterPack) sellingCards[i].card;
                canvas.drawText("$ " + boosterPack.buyPrice, 0.1f + sellingCards[i].rect.left,
                        (sellingCards[i].rect.top + sellingCards[i].rect.bottom) / 2 + y, textPaint);
            }
        }
    }

    boolean GiveCard(ArrayList<Card> clickingCards){
        for(sellingCard sellingCard : sellingCards) {
            if (CollisionHelper.collides(clickingCards.get(0), sellingCard.rect)) {
                if(sellingCard.showName == "sell") {
                    int price = clickingCards.get(0).getPrice();
                    if (price == -1) return false;
                    for (int i = 0; i < price; ++i) {
                        Card c = CardGenerator.getInstance().CreateCard("gold_coin");
                        c.setPosition(sellingCard.rect.right + 0.5f*i, sellingCard.rect.top - 2,
                                Card.CARD_WIDTH, Card.CARD_HEIGHT);
                        CardManager.cardManager.addCard(c);
                    }
                    CardManager.cardManager.removeCard(clickingCards.get(0));
                    return true;
                }
                for(Card c : clickingCards)
                    if(c.getName() != "gold_coin") return false;
                BoosterPack boosterPack = (BoosterPack) sellingCard.card;
                if(clickingCards.size() >= boosterPack.buyPrice){
                    CardManager.cardManager.addBoosterPack(boosterPack);
                    for(int i = 0; i < boosterPack.buyPrice; ++i)
                        CardManager.cardManager.removeCard(clickingCards.get(0));
                    sellingCard.card = CardGenerator.getInstance().CreateCard(sellingCard.keyName);
                    return true;
                }
                else return false;
            }
        }
        return false;
    }
}
