package kr.ac.tukorea.ge.spgp.scgyong.stacklands;

public class NotAlive extends Card {
    private int sellPrice;

    public NotAlive(int price) {
        super();
        this.sellPrice = price;
    }
}

class BlackCard extends NotAlive{
    public BlackCard(int price) {
        super(price);
    }
}

class SilverCard extends NotAlive{
    public SilverCard(int price) {
        super(price);
    }
}

