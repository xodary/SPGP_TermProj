package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.app;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import kr.ac.tukorea.ge.spgp.scgyong.stacklands.R;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.MainScene;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.framework.activity.GameActivity;

public class StackLandsActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MainScene().push();
    }
}