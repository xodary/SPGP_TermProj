package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.app;

import android.os.Bundle;

import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.MainScene;
import kr.ac.tukorea.ge.spgp.scgyong.framework.activity.GameActivity;

public class StackLandsActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MainScene().push();
    }
}