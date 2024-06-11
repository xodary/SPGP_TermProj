package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards;

import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.scgyong.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.CardGenerator;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.CardManager;

public class BoosterPack extends Card {
    public static float BOOSTERPACK_WIDTH = 2.3f;
    public static float BOOSTERPACK_HEIGHT = (float) 524 / 434 * BOOSTERPACK_WIDTH;
    private int buyPrice;
    protected final ArrayList<Card> cards = new ArrayList<>();

    final float width;
    final float height;

    public BoosterPack(int mipmapID, int buyPrice, String name) {
        super(mipmapID, name, "BoosterPack");
        this.buyPrice = buyPrice;
        this.width = BOOSTERPACK_WIDTH;
        this.height = BOOSTERPACK_HEIGHT;

        if (name == "boosterPack_ANewWorld") {
            PushCard(CardGenerator.getInstance().CreateCard("wood"));
            PushCard(CardGenerator.getInstance().CreateCard("rock"));
            PushCard(CardGenerator.getInstance().CreateCard("berry_bush"));
            PushCard(CardGenerator.getInstance().CreateCard("villager"));
            PushCard(CardGenerator.getInstance().CreateCard("coin"));
            PushCard(CardGenerator.getInstance().CreateCard("berry"));
        }
    }

    public BoosterPack PushCard(Card c) {
        cards.add(c);
        return this;
    }

    protected ValueAnimator animator;

    protected ValueAnimator getAnimator() {
        if (animator != null) {
            animator.end();
            return animator;
        }

        float top = Metrics.height / 3;
        float dst = top - dstRect.height();

        animator = ValueAnimator.ofFloat(top, dst);
        animator.setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(updateListener);
        return animator;
    }
    private final ValueAnimator.AnimatorUpdateListener updateListener = (anim)->{
        float y = (Float)anim.getAnimatedValue();
        dstRect.offsetTo(dstRect.left, y);
    };
    public void click() {
        if(!cards.isEmpty()) {
            Card c = cards.get(0);
            cards.remove(0);
            CardManager.cardManager.addCard(c);
            c.setPosition(getX() + cards.size() * 0.3f, getY() + 1, Card.CARD_WIDTH, Card.CARD_HEIGHT);
            if(cards.isEmpty())
                CardManager.cardManager.removeCard(this);
        }
    }
}
