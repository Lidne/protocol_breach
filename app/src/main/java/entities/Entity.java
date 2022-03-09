package entities;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import scenes.StaticObject;

public class Entity {
    protected int posX; // позиция по x
    protected int posY;  // позиция по y
    protected int width;
    protected int height;
    protected int vX; // скорость по x
    protected int vY; // скорость по у

    protected Bitmap bitmap; // изображение с фреймами
    protected List<Rect> frames; // список фреймов анимации

    protected boolean isWalking; // состояние ходьбы
    protected boolean isAttacking; // состояние атаки
    protected boolean isFalling; // состояние падения (если не касается преграды)

    protected Rect hitBox;
    protected Rect destination;
    protected int direction; // направление движения
    protected int frameWidth; // ширина кадра анимации
    protected int frameHeight; // высота кадра анимации
    protected int currentFrame; // номер текущего кадра
    protected double frameTime; // время на 1 кадр
    protected double timeForCurrentFrame; // время с последней смены кадра
    protected int padding; // отступы от краёв фрейма

    public Entity(int posX, int posY, int width, int height, int vX, int vY, int frameWidth, int frameHeight) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.vX = vX;
        this.vY = vY;
        this.timeForCurrentFrame = 0.0;
        this.frameTime = 0.1;
        this.frames = new ArrayList<Rect>();
        this.currentFrame = 0;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameTime = 1000;
        this.destination = new Rect(0, 0, 0, 0);
        this.hitBox = new Rect(posX, posY, posX + width, posY + height);
    }

    public void setX(int posX) {
        this.posX = posX;
        this.hitBox.set(posX, posY, posX + width, posY + height);
    }

    public void setY(int posY) {
        this.posY = posY;
        this.hitBox.set(posX, posY, posX + width, posY + height);
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public int getVX() {
        return vX;
    }

    public int getVY() {
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
        this.direction = direction % 2;
    }

    public void addFrames(Rect frame, List<Rect> frames) {
        frames.add(frame);
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

    public Rect getHitBoxRect() {
        return hitBox;
    }

    public boolean intersect(Entity s) {
        return getHitBoxRect().intersect(s.getHitBoxRect());
    }

    public boolean colliding(StaticObject object) {
        return this.hitBox.intersect(object.getHitBox());
    }

    public boolean intersect(Rect rect) {
        return getHitBoxRect().intersect(rect);
    }

    /*public void applyGravity(@NonNull List<Floor> floors) {
        boolean some = true;
        for (int i = 0; i < floors.size(); i++) {
            Floor floor = floors.get(i);
            some = !(getHitBoxRect().intersect(floor.getHitBox()) && floor.isColliding()) && some;
        }
        this.isFalling = some;
        //Log.d("TAG", "applyGravity: " + getHitBoxRect().intersect(floor.getHitBox()));
    }*/

    public void applyGravity(int floor) {
        isFalling = !(posY + height > floor);
        //Log.d("TAG", "applyGravity: " + getHitBoxRect().intersect(floor.getHitBox()));
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

    /*private void tickUpdate() {
        // функция, которая выполняется по таймеру
    }

    class Timer extends CountDownTimer {
        public Timer(long countDownInterval) {
            super(Integer.MAX_VALUE, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tickUpdate();
        }

        @Override
        public void onFinish() {
        }
    }*/
}