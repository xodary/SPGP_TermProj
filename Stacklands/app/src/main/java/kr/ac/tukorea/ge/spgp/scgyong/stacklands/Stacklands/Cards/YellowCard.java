package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards;

public class YellowCard extends Card {
    int life;
    public YellowCard(int mipmapID, int life, String name) {
        super(mipmapID, name, "Yellow");
        this.life = life;
    }
}
