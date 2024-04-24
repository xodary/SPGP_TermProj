package kr.ac.tukorea.ge.spgp.scgyong.stacklands.Stacklands.Cards;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp.scgyong.stacklands.framework.objects.Sprite;

public class Card extends Sprite {
    public final RectF posRect = new RectF();
    public static final int CARD_IMAGE_WIDTH = 434;
    public static final int CARD_IMAGE_HEIGHT = 524;
    public boolean bClick = false;

    public Card(int mipmapID) {
        super(mipmapID);
        SetPosition(4.5f, 8.0f);
    }

    public void SetPosition(float centerX, float centerY)
    {
        float cardWidth = 2;
        float cardHeight = (float) CARD_IMAGE_HEIGHT / CARD_IMAGE_WIDTH * cardWidth;
        posRect.set(centerX - cardWidth/2, centerY - (cardHeight)/2,
            centerX + cardWidth/2, centerY + cardHeight/2);
    }
    private static Bitmap bitmap;
    public void setBitmap(Bitmap bitmap) {
        Card.bitmap = bitmap;
    }
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, posRect, null);
    }
    public void update() {}

    public boolean inRect(float x, float y) {
        return posRect.contains(x, y);
    }

    public void Move(float dx, float dy) {
        posRect.left += dx;
        posRect.right += dx;
        posRect.bottom += dy;
        posRect.top += dy;
    }
}

