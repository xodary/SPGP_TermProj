package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp.scgyong.framework.objects.ScrollBackground;
import kr.ac.tukorea.ge.spgp.scgyong.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.R;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.CardManager;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.GameTimer;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.RecipeManager;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    public enum Layer {
        BG, CardPack, Card, controller, timer, COUNT
    }
    public MainScene() {
        initLayers(Layer.COUNT);

        add(Layer.BG, new ScrollBackground(R.mipmap.background, 1.0f));
        add(Layer.controller, new CardManager(this));
        add(Layer.controller, new RecipeManager());
        add(Layer.timer, new GameTimer());
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        return CardManager.cardManager.onTouch(event);
    }


}
