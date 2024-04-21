package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards;

public class Alive extends Card {
    private int life;

    public Alive(int life) {
        super();
        this.life = life;
    }
}

class RedCard extends Alive {
    public RedCard(int life) {
        super(life);
    }
}


class YellowCard extends Alive {
    public YellowCard(int life) {
        super(life);
    }
}
