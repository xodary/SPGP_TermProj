package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards;

import java.util.ArrayList;

public class BoosterPack extends Card {
    private int buyPrice;
    protected final ArrayList<Card> cards = new ArrayList<>();

    public BoosterPack(int mipmapID, int buyPrice, String name) {
        super(mipmapID, name, "BoosterPack");
        this.buyPrice = buyPrice;
    }

    public BoosterPack PushCard(Card c){
        cards.add(c);
        return this;
    }
}
