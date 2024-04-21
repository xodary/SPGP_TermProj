package kr.ac.tukorea.ge.spgp.scgyong.stacklands.framework.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards.Card;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers.SilverCardManager;
import kr.ac.tukorea.ge.spgp.scgyong.stacklands.framework.scene.Scene;


enum eCardPack {pack_a_new_world, pack_curious_cuisine, };
public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();private final Activity activity;
    public static final float SCREEN_WIDTH = 9.0f;
    public static final float SCREEN_HEIGHT = 16.0f;
    private final Matrix transformMatrix = new Matrix();
    private final ArrayList<Card> cards = new ArrayList<>();
    private final RectF borderRect = new RectF(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    private final Paint borderPaint;
    public static Resources res;
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
    private boolean running = true;
    private long previousNanos = 0;
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
        Scene scene = Scene.top();
        if (scene != null) {
            boolean handled = scene.onTouch(event);
            if (handled) return true;
        }
        return super.onTouchEvent(event);
    }
//    public boolean onTouchEvent(MotionEvent event) {
//        super.onTouchEvent(event);
//        final float x = event.getX();
//        final float y = event.getY();
//        final int action = event.getAction();
//
//        if(clickingCard != null){
//            float dx = x - oldX;
//            float dy = y - oldY;
//
//            clickingCard.Move(dx, dy);
//
//            oldX = x;
//            oldY = y;
//        }
//        else {
//            oldX = x;
//            oldY = y;
//            for (Card card : cards) {
//                if (card.inRect(x, y)) {
//                    clickingCard = card;
//                    clickingCard.bClick = true;
//                    return true;
//                }
//            }
//        }
//
//        if (action == MotionEvent.ACTION_UP) {
//            if (clickingCard != null) {
//                clickingCard.bClick = false;
//                clickingCard = null;
//            }
//        }
//
//        return false;
//    }
    public void onBackPressed() {
        Scene scene = Scene.top();
        if (scene == null) {
            Scene.finishActivity();
            return;
        }
        boolean handled = scene.onBackPressed();
        if (handled) return;

        Scene.pop();
    }

    public void pauseGame() {
        running = false;
        Scene.pauseTop();
    }

    public void resumeGame() {
        if (running) return;
        running = true;
        previousNanos = 0;
        scheduleUpdate();
        Scene.resumeTop();
    }

    public void destroyGame() {
        Scene.popAll();
    }
}