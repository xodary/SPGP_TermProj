package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.CardGenerator;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.CardManager;

public class BoosterPack extends Card {
    public static float BOOSTERPACK_WIDTH = 2.3f;
    public static float BOOSTERPACK_HEIGHT = (float) 524 / 434 * BOOSTERPACK_WIDTH;
    public int buyPrice;
    protected final ArrayList<Card> cards = new ArrayList<>();

    final float width;
    final float height;

    public BoosterPack(int mipmapID, int buyPrice, String name) {
        super(mipmapID, name, "BoosterPack");
        this.buyPrice = buyPrice;
        this.width = BOOSTERPACK_WIDTH;
        this.height = BOOSTERPACK_HEIGHT;

        if (name == "boosterPack_ANewWorld") {
            PushCard(CardGenerator.getInstance().CreateCard("silver_wood"));
            PushCard(CardGenerator.getInstance().CreateCard("black_rock"));
            PushCard(CardGenerator.getInstance().CreateCard("black_berry_bush"));
            PushCard(CardGenerator.getInstance().CreateCard("yellow_villager"));
            PushCard(CardGenerator.getInstance().CreateCard("gold_coin"));
            PushCard(CardGenerator.getInstance().CreateCard("orange_berry"));
        }
        if (name == "boosterPack_humble_beginnings") {
            PushCard(CardGenerator.getInstance().CreateCard("silver_stone"));
            PushCard(CardGenerator.getInstance().CreateCard("black_tree"));
            PushCard(CardGenerator.getInstance().CreateCard("black_rock"));
        }
        if (name == "boosterPack_seeking_wisdom") {
            PushCard(CardGenerator.getInstance().CreateCard("black_berry_bush"));
            PushCard(CardGenerator.getInstance().CreateCard("silver_stick"));
            PushCard(CardGenerator.getInstance().CreateCard("silver_wood"));
            PushCard(CardGenerator.getInstance().CreateCard("black_rock"));
        }
        if (name == "boosterPack_reap_n_sow") {
            PushCard(CardGenerator.getInstance().CreateCard("orange_apple"));
            PushCard(CardGenerator.getInstance().CreateCard("black_rock"));
            PushCard(CardGenerator.getInstance().CreateCard("black_tree"));
            PushCard(CardGenerator.getInstance().CreateCard("black_berry_bush"));
        }
    }

    public BoosterPack PushCard(Card c) {
        cards.add(c);
        return this;
    }

    public void click() {
        if(!cards.isEmpty()) {
            Card c = cards.get(0);
            cards.remove(0);
            CardManager.cardManager.addCard(c);
            c.setPosition(getX() + cards.size() * 0.3f, getY() + 1, Card.CARD_WIDTH, Card.CARD_HEIGHT);
            if(cards.isEmpty())
                CardManager.cardManager.removeCard(this);
        }
    }
}
