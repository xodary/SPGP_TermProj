package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.app;

import android.content.Intent;
import android.os.Bundle;

import kr.ac.tukorea.ge.spgp.scgyong.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.MainScene;

public class StackLandsActivity extends GameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MainScene(this).push();
    }
    public void overGameActivity() {
        Intent intent = new Intent(this, GameoverActivity.class);
        startActivity(intent);
    }
}