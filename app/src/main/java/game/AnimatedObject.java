package game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class AnimatedObject {
    protected int posX;
    protected int posY;
    protected int width;
    protected int height;

    protected Bitmap bitmap;
    protected List<Rect> frames; // список фреймов анимации

    protected Rect hitBox;
    protected int frameWidth; // ширина кадра анимации
    protected int frameHeight; // высота кадра анимации
    protected int currentFrame; // номер текущего кадра
    protected double frameTime; // время на 1 кадр
    protected double timeForCurrentFrame; // время с последней смены кадра

    public AnimatedObject(int posX, int posY, int width, int height, Bitmap bitmap) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.bitmap = bitmap;
        this.currentFrame = 0;
        this.timeForCurrentFrame = 0.0;
        this.frameTime = 0.1;
        this.frameWidth = bitmap.getWidth();
        this.frameHeight = bitmap.getHeight();
        this.frames = new ArrayList<Rect>();
        this.hitBox = new Rect(posX, posY, posX + width, posY + height);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPos(int x, int y) {
        this.hitBox.set(x, y, x + width, y + height);
        this.posX = x;
        this.posY = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public List<Rect> getFrames() {
        return frames;
    }

    public Rect getHitBox() {
        return hitBox;
    }

    public void setHitBox(int x, int y, int x1, int y1) {
        this.hitBox.set(x, y, x1, y1);
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public double getFrameTime() {
        return frameTime;
    }

    public void setFrameTime(double frameTime) {
        this.frameTime = frameTime;
    }

    public double getTimeForCurrentFrame() {
        return timeForCurrentFrame;
    }

    public void setTimeForCurrentFrame(double timeForCurrentFrame) {
        this.timeForCurrentFrame = timeForCurrentFrame;
    }

    public void setFrames(List<Rect> frames) {
        this.frames = frames;
    }

    public void draw(Canvas canvas, Paint p) {
    }

    public void update(int ms) {  // обновление кадра анимации
        timeForCurrentFrame += ms;
    }
}
