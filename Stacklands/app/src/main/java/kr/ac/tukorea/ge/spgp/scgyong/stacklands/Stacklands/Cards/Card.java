package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp.scgyong.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp.scgyong.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp.scgyong.framework.view.Metrics;

public class Card extends Sprite implements IBoxCollidable {
    public static float CARD_WIDTH = 2;
    public static float CARD_HEIGHT = (float) 524 / 434 * CARD_WIDTH;

    public static float CARD_OFFSET = 0.47F;
    protected String name;
    public String color;
    public float[] click_offset = {0, 0};

    public Card(int mipmapID, String name, String color) {
        super(mipmapID);
        this.name = name;
        this.color = color;
        this.width = CARD_WIDTH;
        this.height = CARD_HEIGHT;
        setPosition(Metrics.width / 2, Metrics.height / 2, this.width, this.height);
    }

    Paint paint;
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(collided){
            if(paint == null) {
                paint = new Paint();
                paint.setColor(Color.GRAY);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(0.1f);
            }
            RectF newRect = new RectF();
            newRect.left = dstRect.left - 0.1f;
            newRect.top = dstRect.top - 0.1f;
            newRect.right = dstRect.right + 0.1f;
            newRect.bottom = dstRect.bottom + 0.1f;
            canvas.drawRect(newRect, paint);
            collided = false;
        }
    }
    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
    }

    public boolean clicking = false;
    public boolean collided = false;
    public boolean onTouch(MotionEvent event) {
        return true;
    }

    public boolean isContains(float x, float y){
        return super.dstRect.contains(x, y);
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    @Override
    public void collide(Card collidedCard, int n) {
        setPosition(collidedCard.getX(), collidedCard.getY() + CARD_OFFSET * (n+1), this.width, this.height);
    }

    @Override
    public void collided() {
        collided = true;
    }

    public String getName() {
        return name;
    }

    public void click() {
    }

    public int getPrice() {
        return -1;
    }
}

