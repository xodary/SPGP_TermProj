package kr.ac.tukorea.ge.spgp.scgyong.framework.interfaces;

import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.Card;

public interface IBoxCollidable {
    public RectF getCollisionRect();

    public void collide(Card collidedCard);
    public void collided();
}
