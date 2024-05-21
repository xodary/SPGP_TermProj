package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp.scgyong.framework.objects.ScrollBackground;
import kr.ac.tukorea.ge.spgp.scgyong.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.R;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.CardManager;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.FeedVillager;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.GameTimer;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    CardManager cardManager;
    public enum Layer {
        BG, CardPack, Card, controller, timer, COUNT
    }
    public MainScene() {
        initLayers(Layer.COUNT);
        cardManager = new CardManager(this);
        FeedVillager feedVillager = new FeedVillager();
        GameTimer gameTimer = new GameTimer(feedVillager);

        add(Layer.BG, new ScrollBackground(R.mipmap.background, 1.0f));
        add(Layer.controller, cardManager);
        add(Layer.timer, gameTimer);
        add(Layer.timer, feedVillager);
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
