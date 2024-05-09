package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards;

public class BlackCard extends Card{
    private int sellPrice;

    public BlackCard(int mipmapID, int sellPrice, String name) {
        super(mipmapID, name);
        this.sellPrice = sellPrice;
    }
}

