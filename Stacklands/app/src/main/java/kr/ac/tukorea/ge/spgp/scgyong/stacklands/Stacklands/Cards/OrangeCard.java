package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards;

public class OrangeCard extends Card {
    int sell_price;
    public int satiety;
    public float originX;
    public float originY;
    public OrangeCard(int mipmapID, int sell_price, int satiety, String name) {
        super(mipmapID, name, "Orange");
        this.sell_price = sell_price;
        this.satiety = satiety;
    }
    public int getPrice(){
        return sell_price;
    }
}
