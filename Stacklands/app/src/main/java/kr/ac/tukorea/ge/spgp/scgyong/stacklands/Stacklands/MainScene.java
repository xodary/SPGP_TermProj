package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp.scgyong.stacklands.R;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.framework.objects.Score;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.framework.objects.VertScrollBackground;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.framework.scene.Scene;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    Score score; // package private

    public enum Layer {
        BG, CardPack, Card, COUNT
    }
    public MainScene() {
        initLayers(Layer.COUNT);

    }

    public void addScore(int amount) {
        score.add(amount);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
    }
}
