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
    protected int posX; // позиция по x
    protected int posY;  // позиция по y
    protected int vX = 0; // скорость по x
    protected int vY = 0; // скорость по у

    protected Bitmap bitmap; // изображение с фреймами
    protected List<Rect> frames; // список фреймов анимации

    private int direction;
    private int frameWidth;
    private int frameHeight;
    private int currentFrame; // номер текущего кадра
    private double frameTime; // время на 1 кадр
    private double timeForCurrentFrame; // время с последней смены кадра
    private int padding; // отступы от краёв (хз зачем, а удалять боюсь)

    public Entity(int posX, int posY, int vX, int vY, int frameWidth, int frameHeight) {
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

    public void setX(int posX) {
        this.posX = posX;
    }

    public void setY(int posY) {
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

    public void setVX(int vX) {
        this.vX = vX;
    }

    public void setVY(int vY) {
        this.vY = vY;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
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
    }

    public Rect getBoundingBoxRect() {
        return new Rect((int) posX + padding, (int) posY + padding, (int) (posX + frameWidth - 2 * padding),
                (int) (posY + frameHeight - 2 * padding));
    }

    public boolean intersect(Entity s) {
        return getBoundingBoxRect().intersect(s.getBoundingBoxRect());
    }
}
