package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Managers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp.scgyong.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp.scgyong.framework.view.Metrics;

public class GameTimer implements IGameObject {
    public static GameTimer gameTimer;
    static float TURNTIME = 5.f;
    public boolean isActive = true;
    float spendedTime = 0.0f;
    int days = 1;
    static float barWidth = Metrics.width / 2 - 0.5f;
    static float barHeight = 1.0f;
    static RectF outlineRect = new RectF(Metrics.width/2, 0.5f,
            Metrics.width - 0.5f, 0.5f + barHeight);
    static RectF inlineRect = new RectF(outlineRect.left + 0.2f, outlineRect.top + 0.2f,
            outlineRect.right - 0.2f, outlineRect.bottom - 0.2f);

    public GameTimer() {
        gameTimer = this;
    }

    @Override
    public void update(float elapsedSeconds) {
        if(isActive) {
            spendedTime += elapsedSeconds;
            if (spendedTime > TURNTIME) {
                FeedVillager.feedVillager.startFeeding();
                isActive = false;
            }
        }
    }

    Paint outlinePaint;
    Paint inlinePaint;
    Paint textPaint;

    @Override
    public void draw(Canvas canvas) {
        if (outlinePaint == null) {
            outlinePaint = new Paint();
            outlinePaint.setColor(Color.WHITE);
            outlinePaint.setStyle(Paint.Style.FILL);

            inlinePaint = new Paint();
            inlinePaint.setColor(Color.BLACK);
            inlinePaint.setStyle(Paint.Style.FILL);

            textPaint = new Paint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(1.0f);
            textPaint.setLinearText(true);
        }
        canvas.drawRect(outlineRect, outlinePaint);

        inlineRect.left = outlineRect.left + 0.2f + ((spendedTime)/TURNTIME) * (barWidth-0.4f);
        canvas.drawRect(inlineRect, inlinePaint);
        canvas.drawText("Day"+days, 1, 1.3f, textPaint);
    }

    public void reset() {
        isActive = true;
        spendedTime = 0.0f;
        days += 1;
    }
}
