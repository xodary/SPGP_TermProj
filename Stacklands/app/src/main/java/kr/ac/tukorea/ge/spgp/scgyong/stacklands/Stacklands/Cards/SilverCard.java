package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards;

public class SilverCard extends Card {
    private int sellPrice;

    public SilverCard(int mipmapID, int sellPrice, String name) {
        super(mipmapID, name, "Silver");
        this.sellPrice = sellPrice;
    }
    public int getPrice(){
        return sellPrice;
    }
}
