package kr.ac.tukorea.ge.spgp.scgyong.framework.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import kr.ac.tukorea.ge.spgp.scgyong.framework.view.Metrics;

public class ScrollBackground extends Sprite {
    private final float speed;
    public ScrollBackground(int bitmapResId, float speed) {
        super(bitmapResId);
        this.width = bitmap.getWidth() * Metrics.height / bitmap.getHeight();
        setPosition(Metrics.width / 2, Metrics.height / 2, width, Metrics.height);
        this.speed = speed;
    }
    @Override
    public void update(float elapsedSeconds) {
        this.x += speed * elapsedSeconds; // y 값을 스크롤된 양으로 사용한다
    }
    Paint paint = null;
    @Override
    public void draw(Canvas canvas) {
        if(paint == null) {
            paint = new Paint();
            paint.setColor(Color.rgb(144, 238, 144));
            paint.setAlpha(64);
        }
        //super.draw(canvas);
        float curr = x % width;
        if (curr > 0) curr -= width;
        while (curr < Metrics.width) {
            dstRect.set(curr, 0, curr + width, Metrics.height);
            canvas.drawBitmap(bitmap, null, dstRect, null);
            curr += width;
        }
        canvas.drawRect(0, 0, Metrics.width, Metrics.height, paint);
    }
}
