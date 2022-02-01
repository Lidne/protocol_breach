package game;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class StaticObject {
    protected int posX;
    protected int posY;
    protected int width;
    protected int height;
    protected int bmpWidth;
    protected int bmpHeight;
    protected Rect hitBox;
    protected Rect bmpHitBox;
    protected Bitmap bitmap;
    protected boolean isColliding;

    public StaticObject(int posX, int posY, int width, int height, boolean isColliding, Bitmap bitmap) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.bmpWidth = bitmap.getWidth();
        this.bmpHeight = bitmap.getHeight();
        this.bitmap = bitmap;
        this.hitBox = new Rect(posX, posY, posX + width, posY + height);
        this.bmpHitBox = new Rect(0, 0, bmpWidth, bmpHeight);
        this.isColliding = isColliding;
    }

    public Rect getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rect hitBox) {
        this.hitBox = hitBox;
    }

    public boolean isColliding() {
        return isColliding;
    }

    public void setColliding(boolean colliding) {
        isColliding = colliding;
    }
}
