package scenes;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class StaticObject {
    protected int posX;
    protected int posY;
    protected int width;
    protected int height;
    //protected int bmpWidth;
    //protected int bmpHeight;
    protected Rect hitBox;
    //protected Rect bmpHitBox;
    //protected Bitmap bitmap;
    protected boolean isColliding;

    public StaticObject(int posX, int posY, int width, int height, boolean isColliding) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        //this.bmpWidth = bitmap.getWidth();
        //this.bmpHeight = bitmap.getHeight();
        //this.bitmap = bitmap;
        this.hitBox = new Rect(posX, posY, posX + width, posY + height);
        //this.bmpHitBox = new Rect(0, 0, bmpWidth, bmpHeight);
        this.isColliding = isColliding;
    }

    public Rect getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rect hitBox) {
        this.hitBox = hitBox;
    }

    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
        this.setHitBox(new Rect(posX, posY, posX + width, posY + height));
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
        this.hitBox.set(posX, posY, posX + width, posY + height);
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
        this.hitBox.set(posX, posY, posX + width, posY + height);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        this.hitBox.set(posX, posY, posX + width, posY + height);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        this.hitBox.set(posX, posY, posX + width, posY + height);
    }

    public boolean isColliding() {
        return isColliding;
    }

    public void setColliding(boolean colliding) {
        isColliding = colliding;
    }
}
