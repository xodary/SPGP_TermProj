package kr.ac.tukorea.ge.spgp.scgyong.stacklands;

import static java.lang.Math.random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

public class Card {
    private final RectF posRect = new RectF();
    private static final int CARD_IMAGE_WIDTH = 434;
    private static final int CARD_IMAGE_HEIGHT = 524;

    private static final Random random = new Random();
    public static Card random(){
        return new Card(random.nextFloat() * GameView.SCREEN_WIDTH,
                random.nextFloat() * GameView.SCREEN_HEIGHT);
    }
    public Card(float centerX, float centerY) {
        float cardWidth = 2;
        float cardHeight = (float) CARD_IMAGE_HEIGHT / CARD_IMAGE_WIDTH * cardWidth;
        posRect.set(centerX - cardWidth/2, centerY - (cardHeight)/2,
                centerX + cardWidth/2, centerY + cardHeight/2);
    }

    private static Bitmap bitmap;
    public static void setBitmap(Bitmap bitmap) { // Alt+Insert -> Setter
        Card.bitmap = bitmap;
    }
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, posRect, null);
    }
    public void update() {}
}

