package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards;

public class OrangeCard extends Card {
    int sell_price;
    public OrangeCard(int mipmapID, int sell_price, String name) {
        super(mipmapID, name);
        this.sell_price = sell_price;
    }
}
