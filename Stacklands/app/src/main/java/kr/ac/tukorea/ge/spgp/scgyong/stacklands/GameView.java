package kr.ac.tukorea.ge.spgp.scgyong.stacklands;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;


enum eCardPack {pack_a_new_world, pack_curious_cuisine, };
public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();private final Activity activity;
    public static final float SCREEN_WIDTH = 9.0f;
    public static final float SCREEN_HEIGHT = 16.0f;
    private final Matrix transformMatrix = new Matrix();
    private final ArrayList<Card> cards = new ArrayList<>();
    private final RectF borderRect = new RectF(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    private final Paint borderPaint;

    // public static final Resources res = null;
    public GameView(Context context) {
        super(context);
        this.activity = (Activity)context;

        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(0.1f);
        borderPaint.setColor(Color.RED);

        setFullScreen();

        Resources res = getResources();

        cards.add(SilverCardManager.getInstance().GetStone(res));
    }

    private void scheduleUpdate() {
        Choreographer.getInstance().postFrameCallback(this);
    }
    @Override
    public void doFrame(long nanos) {
        update();
        invalidate();
        if (isShown()) {
            scheduleUpdate();
        }
    };
    public void setFullScreen() {
        int flags = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        setSystemUiVisibility(flags);
    }

    public Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
    private void update() {
        for (Card card : cards) {
            card.update();
        }
    }
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float view_ratio = (float)w / (float)h;
        float game_ratio = SCREEN_WIDTH / SCREEN_HEIGHT;

        if (view_ratio > game_ratio) {
            float scale = h / SCREEN_HEIGHT;
            transformMatrix.setTranslate((w - h * game_ratio) / 2, 0);
            transformMatrix.preScale(scale, scale);
        } else {
            float scale = w / SCREEN_WIDTH;
            transformMatrix.setTranslate(0, (h - w / game_ratio) / 2);
            transformMatrix.preScale(scale, scale);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.concat(transformMatrix);
        for (Card card : cards) {
            card.draw(canvas);
        }
        canvas.restore();
    }

    float oldX = 0;
    float oldY = 0;
    private Card clickingCard;
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        final float x = event.getX();
        final float y = event.getY();
        final int action = event.getAction();

        if(clickingCard != null){
            float dx = x - oldX;
            float dy = y - oldY;

            clickingCard.Move(dx, dy);

            oldX = x;
            oldY = y;
        }
        else {
            oldX = x;
            oldY = y;
            for (Card card : cards) {
                if (card.inRect(x, y)) {
                    clickingCard = card;
                    clickingCard.bClick = true;
                    return true;
                }
            }
        }

        if (action == MotionEvent.ACTION_UP) {
            if (clickingCard != null) {
                clickingCard.bClick = false;
                clickingCard = null;
            }
        }

        return false;
    }
}