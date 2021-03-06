package com.example.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    protected float posX; // позиция по x
    private float posY;  // позиция по y
    private float vX = 0; // скорость по x
    private float vY = 0; // скорость по у

    private Bitmap bitmap; // изображение с фреймами
    private List<Rect> frames; // список фреймов анимации

    private int frameWidth;
    private int frameHeight;
    private int currentFrame; // номер текущего кадра
    private double frameTime; // время на 1 кадр
    private double timeForCurrentFrame; // время с последней смены кадра
    private int padding; // отступы от краёв (хз зачем, а удалять боюсь)

    public Entity(float posX, float posY, float vX, float vY, int frameWidth, int frameHeight) {
        this.posX = posX;
        this.posY = posY;
        this.vX = vX;
        this.vY = vY;
        this.timeForCurrentFrame = 0.0;
        this.frameTime = 0.1;
        this.frames = new ArrayList<Rect>();
        this.currentFrame = 0;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
    }

    public void setX(float posX) {
        this.posX = posX;
    }

    public void setY(float posY) {
        this.posY = posY;
    }

    public float getX() {
        return posX;
    }

    public float getY() {
        return posY;
    }

    public float getVX() {
        return vX;
    }

    public float getVY() {
        return vY;
    }

    public void setVX(float vX) {
        this.vX = vX;
    }

    public void setVY(float vY) {
        this.vY = vY;
    }

    public List<Rect> getFrames() {
        return frames;
    }

    public void setFrames(List<Rect> frames) {
        this.frames = frames;
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
        this.timeForCurrentFrame = Math.abs(timeForCurrentFrame);
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void update(int ms) {  // обновление кадра анимации

        timeForCurrentFrame += ms;

        if (timeForCurrentFrame >= frameTime) {
            currentFrame = (currentFrame + 1) % frames.size();
            timeForCurrentFrame = timeForCurrentFrame - frameTime;
        }
    }

    public void draw(Canvas canvas) { // прорисовка на SurfaceView
        Paint p = new Paint();
        Rect destination = new Rect((int) this.posX, (int) this.posY, (int) (this.posX + this.frameWidth), (int) (this.posY + this.frameHeight));
        p.setColor(Color.RED);
       // canvas.drawRect(destination, p);
        canvas.drawBitmap(bitmap, frames.get(currentFrame), destination, p);
    }

    public void addFrame(Rect frame) {
        frames.add(frame);
    } // добавление фрейма

    public Rect getBoundingBoxRect() {
        return new Rect((int) posX + padding, (int) posY + padding, (int) (posX + frameWidth - 2 * padding),
                (int) (posY + frameHeight - 2 * padding));
    }

    public boolean intersect(Entity s) {
        return getBoundingBoxRect().intersect(s.getBoundingBoxRect());
    }
}
