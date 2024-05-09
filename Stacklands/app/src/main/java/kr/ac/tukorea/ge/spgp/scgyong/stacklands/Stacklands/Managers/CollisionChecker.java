package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.MainScene;
import kr.ac.tukorea.ge.spgp.scgyong.framework.interfaces.IGameObject;

public class CollisionChecker implements IGameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    private final MainScene scene;

    public CollisionChecker(MainScene scene) {
        this.scene = scene;
    }

    @Override
    public void update(float elapsedSeconds) {
//        ArrayList<IGameObject> clickingCards = scene.objectsAt(MainScene.Layer.ClickCard);
//        if(clickingCards.isEmpty()) return;
//        Card clickingCard = (Card) clickingCards.get(0);
//        ArrayList<IGameObject> cards = scene.objectsAt(MainScene.Layer.Card);
//        for (int b = cards.size() - 1; b >= 0; b--) {
//            Card c = (Card) cards.get(b);
//            if (CollisionHelper.collides(c, clickingCard)) {
//                Log.d(TAG, "Collision !!");
//                clickingCard.collide(c);
//                break;
//            }
//        }
    }


    @Override
    public void draw(Canvas canvas) {

    }
}
