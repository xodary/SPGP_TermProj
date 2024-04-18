package kr.ac.tukorea.ge.spgp.scgyong.stacklands;

import java.util.ArrayList;

public class CardPack extends Card {
    private int nCard;
    private int buyPrice;
    protected final ArrayList<Card> cards = new ArrayList<>();

    public CardPack(int nCard, int buyPrice) {
        super();
        this.nCard = nCard;
        this.buyPrice = buyPrice;
    }

    public CardPack PushCard(Card c){
        cards.add(c);
        return this;
    }
}
