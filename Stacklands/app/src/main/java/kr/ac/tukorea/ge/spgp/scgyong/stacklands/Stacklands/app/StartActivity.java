package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.spgp.scgyong.stacklands.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }
    private void startGameActivity() {
        Intent intent = new Intent(this, StackLandsActivity.class);
        startActivity(intent);
    }
    public void onBtnStartGame(View view) {
        startGameActivity();
    }
    public void onBtnExitGame(View view){finishAndRemoveTask();}
}