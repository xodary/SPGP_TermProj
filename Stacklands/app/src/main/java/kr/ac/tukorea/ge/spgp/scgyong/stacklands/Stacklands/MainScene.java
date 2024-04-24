package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp.scgyong.stacklands.framework.scene.Scene;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();

    public enum Layer {
        BG, CardPack, Card, COUNT
    }
    public MainScene() {
        initLayers(Layer.COUNT);

    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        return false;
    }
}
