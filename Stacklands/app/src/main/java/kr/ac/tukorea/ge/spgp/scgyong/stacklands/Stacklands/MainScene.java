package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands;

import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.scgyong.framework.objects.ScrollBackground;
import kr.ac.tukorea.ge.spgp.scgyong.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.R;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.Card;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.CardManager;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.GameTimer;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.RecipeManager;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final ArrayList<Card> cooking_cards = new ArrayList<>();
    CardManager cardManager = null;
    RecipeManager recipeManager;
    public enum Layer {
        BG, CardPack, Card, controller, timer, COUNT
    }
    public MainScene() {
        initLayers(Layer.COUNT);
        cardManager = new CardManager(this);
        // add(Layer.controller, new CollisionChecker(this));

        add(Layer.BG, new ScrollBackground(R.mipmap.background, 1.0f));
        add(Layer.controller, cardManager);
        add(Layer.timer, new GameTimer(this));
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        return cardManager.onTouch(event);
    }


}
