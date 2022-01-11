package com.example.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    private int hp;
    private boolean is_walking;
    private int atk_d; // урон от первой атаки в комбо
    private int atk_d1; // урон от второй атаки
    private boolean is_dashing; // состояние уклонения
    private int dash_r; // дальность дэша в пикселях
    private boolean is_attacking; // состояние атаки
    private boolean is_defending; // состояние поднятия щита

    private List<Rect> walkingFrames; // список с анимациями ходьбы
    private List<Rect> attackFrames; // список с анимациями атаки
    //private List<Rect> attack1Frames;

    public Player(float posX, float posY, float vX, float vY, int frameWidth, int frameHeight, Bitmap bitmap) {
        super(posX, posY, vX, vY, frameWidth, frameHeight);
        this.setBitmap(bitmap);
        this.hp = 100;
        is_walking = false;
        this.atk_d = 20;
        this.atk_d1 = 40;
        this.dash_r = 100;
        this.walkingFrames = new ArrayList<Rect>();
        this.attackFrames = new ArrayList<Rect>();
        //this.attack1Frames = new ArrayList<Rect>();
        is_attacking = false;
        is_defending = false;
        is_dashing = false;

        this.setFrameTime(1000);

        // добавляем фреймы в их списки
        setDefaultFrames();
        setWalkingFrames();
        setAttackFrames();
    }

    public void update(int ms, int str, int agl) {
        super.update(ms);
        if (str > 30) { // если сила наклона меньше 20, то не двигаем перса
            switch (agl) {
                case 0:
                    this.setX((float) (this.getX() + (this.getVX() * (str / 100.0)) / 60.0));
                    break;
                case 180:
                    this.setX((float) (this.getX() - (this.getVX() * (str / 100.0)) / 60.0));
                    break;
            }
        }
        // если сменилось состояние, то обнуляем кадр, потому что кол-во кадров в анимациях разное
        if (this.isWalking() != MainActivity.walking) this.setCurrentFrame(0);
        this.setWalking(MainActivity.walking); // обновляем состояние
        if (this.isAttacking() != MainActivity.attacking) this.setCurrentFrame(0);
        this.setAttacking(MainActivity.attacking);
    }

    private void setDefaultFrames() {
        // Функция загружает кадры ожидания (перс стоит на месте)
        for (int i = 0; i < 8; i++) {
            Rect rect = new Rect(i * this.getFrameWidth(), 0,
                    i * this.getFrameWidth() + this.getFrameWidth(), this.getFrameHeight());
            this.frames.add(rect);
        }
    }

    private void setWalkingFrames() {
        // Функция загружает кадры ходьбы
        for (int i = 0; i < 8; i++) {
            Rect rect = new Rect(i * this.getFrameWidth(), this.getFrameHeight(),
                    i * this.getFrameWidth() + this.getFrameWidth(),
                    2 * this.getFrameHeight());

            this.walkingFrames.add(rect);
        }
    }

    private void setAttackFrames() {
        // Функция загружает кадры атаки
        for (int i = 0; i < 11; i++) {
            Rect rect = new Rect(i * this.getFrameWidth(), 6 * this.getFrameHeight(),
                    i * this.getFrameWidth() + this.getFrameWidth(),
                    7 * this.getFrameHeight());

            this.attackFrames.add(rect);
        }
    }

    public void draw(Canvas canvas) {
        // прорисовка на SurfaceView
        Paint p = new Paint();
        Rect destination = new Rect((int) posX, (int) posY, (int) (posX + getFrameWidth()), (int) (posY + getFrameHeight()));
        if (this.is_walking)
            canvas.drawBitmap(bitmap, walkingFrames.get(getCurrentFrame()), destination, p);
        else canvas.drawBitmap(bitmap, frames.get(getCurrentFrame()), destination, p);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk_d() {
        return atk_d;
    }

    public void setAtk_d(int atk_d) {
        this.atk_d = atk_d;
    }

    public int getAtk_d1() {
        return atk_d1;
    }

    public void setAtk_d1(int atk_d1) {
        this.atk_d1 = atk_d1;
    }

    public boolean isWalking() {
        return is_walking;
    }

    public void setWalking(boolean is_walking) {
        this.is_walking = is_walking;
    }

    public boolean isDashing() {
        return is_dashing;
    }

    public void setDashing(boolean is_dashing) {
        this.is_dashing = is_dashing;
    }

    public int getDash_r() {
        return dash_r;
    }

    public void setDash_r(int dash_r) {
        this.dash_r = dash_r;
    }

    public boolean isAttacking() {
        return is_attacking;
    }

    public void setAttacking(boolean is_attacking) {
        this.is_attacking = is_attacking;
    }

    public boolean isDefending() {
        return is_defending;
    }

    public void setDefending(boolean is_defending) {
        this.is_defending = is_defending;
    }
}
