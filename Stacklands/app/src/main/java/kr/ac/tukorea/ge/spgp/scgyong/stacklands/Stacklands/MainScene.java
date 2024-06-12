package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp.scgyong.framework.objects.ScrollBackground;
import kr.ac.tukorea.ge.spgp.scgyong.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.R;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.CardManager;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.GameTimer;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.Market;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.RecipeManager;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.app.StackLandsActivity;

public class MainScene extends Scene {
    public StackLandsActivity activity;
    private static final String TAG = MainScene.class.getSimpleName();
    public enum Layer {
        BG, Market, Card, controller, timer, COUNT
    }
    public MainScene(StackLandsActivity activity) {
        this.activity = activity;

        initLayers(Layer.COUNT);
        add(Layer.BG, new ScrollBackground(R.mipmap.background, 0.1f));
        add(Layer.controller, new CardManager(this));
        add(Layer.controller, new RecipeManager());
        add(Layer.timer, new GameTimer());
        add(Layer.Market, new Market());
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
