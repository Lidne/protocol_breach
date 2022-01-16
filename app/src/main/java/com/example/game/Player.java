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

public class Player extends Entity {
    private int hp;
    private boolean is_walking;
    private Rect atkRect;
    private int atk_d; // урон от первой атаки в комбо
    private int atk_d1; // урон от второй атаки
    private boolean is_dashing; // состояние уклонения
    private int dash_r; // дальность дэша в пикселях
    private boolean is_attacking; // состояние атаки
    private boolean is_defending; // состояние поднятия щита

    private List<Rect> walkingFrames; // список с анимациями ходьбы
    private List<Rect> attackFrames; // список с анимациями атаки
    //private List<Rect> attack1Frames;

    public Player(int posX, int posY, int vX, int vY, int frameWidth, int frameHeight, Bitmap bitmap) {
        super(posX, posY, vX, vY, frameWidth, frameHeight);
        this.setBitmap(bitmap);
        this.hp = 100;
        is_walking = false;
        this.atkRect = new Rect(posX + (this.getFrameWidth() / 2), posY + (this.getFrameHeight() / 2),
                posX + (this.getFrameWidth() / 4 * 3),
                posY + (this.getFrameHeight()));
        this.atk_d = 20;
        this.atk_d1 = 40;
        this.dash_r = 100;
        this.walkingFrames = new ArrayList<Rect>();
        this.attackFrames = new ArrayList<Rect>();
        //this.attack1Frames = new ArrayList<Rect>();
        is_attacking = false;
        is_defending = false;
        is_dashing = false;
        this.setPadding(50);

        // добавляем фреймы в их списки
        setDefaultFrames();
        setWalkingFrames();
        setAttackFrames();
    }

    public void update(int ms, int str, int agl) {
        super.update(ms);

        // если сменилось состояние, то обнуляем кадр, потому что кол-во кадров в анимациях разное
        if (this.is_walking != MainActivity.walking && !is_attacking) {
            this.setCurrentFrame(0);
        }
        if (this.is_attacking != MainActivity.attacking) {
            this.setCurrentFrame(0);
        }

        if (!is_attacking) is_walking = MainActivity.walking;
        is_attacking = MainActivity.attacking;

        if (is_walking && is_attacking) {
            is_walking = false;
        }

        if (this.getCurrentFrame() == this.attackFrames.size() - 1) {
            this.setCurrentFrame(0);
            this.is_attacking = false;
            MainActivity.attacking = false;
        }

        if (this.getTimeForCurrentFrame() >= this.getFrameTime()) {
            if (is_walking) {
                //Log.d("ACTION", "WALKING");
                this.setCurrentFrame((this.getCurrentFrame() + 1) % walkingFrames.size());
            } else if (is_attacking) {
                //Log.d("ACTION", "ATTACKING");
                this.setCurrentFrame((this.getCurrentFrame() + 1) % attackFrames.size());
            } else {
                //Log.d("ACTION", "STANDING");
                this.setCurrentFrame((this.getCurrentFrame() + 1) % frames.size());
            }

            this.setTimeForCurrentFrame(this.getTimeForCurrentFrame() - this.getFrameTime());
        }
        // заготовка для столкновения юнитов
        //if (!colliding) {
        move(str, agl);
        //}
    }

    private void move(int str, int agl) {
        if (str > 30 && is_walking) { // если сила наклона меньше 20, то не двигаем перса
            switch (agl) {
                case 0:
                    this.setDirection(0);
                    this.posX = (int) (this.posX + (this.vX * (str / 100.0)) / 60.0);

                    break;
                case 180:
                    this.setDirection(1);
                    this.setX((int) (this.getX() - (this.getVX() * (str / 100.0)) / 60.0));
                    break;
            }
            this.atkRect.set(posX + (this.getFrameWidth() / 2), posY + (this.getFrameHeight() / 2),
                    posX + (this.getFrameWidth() / 4 * 3),
                    posY + (this.getFrameHeight()));
        }
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
        p.setColor(Color.RED);
        canvas.drawRect(this.atkRect, p);
        Rect destination = new Rect((int) posX, (int) posY, (int) (posX + getFrameWidth()), (int) (posY + getFrameHeight()));
        if (is_walking) {
            canvas.drawBitmap(bitmap, walkingFrames.get(getCurrentFrame()), destination, p);
        } else if (is_attacking) {
            canvas.drawBitmap(bitmap, attackFrames.get(getCurrentFrame()), destination, p);
        } else canvas.drawBitmap(bitmap, frames.get(getCurrentFrame()), destination, p);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Rect getAtkRect() {
        return atkRect;
    }

    public void setAtkRect(Rect atkRect) {
        this.atkRect = atkRect;
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
