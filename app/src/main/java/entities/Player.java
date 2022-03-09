package entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import game.Game;

public class Player extends Entity {
    private int hp;
    private boolean isWalking;
    private Rect atkRect;
    private int atkDamage; // урон от первой атаки в комбо
    private int atk1Damage; // урон от второй атаки
    private boolean isDashing; // состояние уклонения
    private int dashRadius; // дальность дэша в пикселях
    private boolean isAttacking; // состояние атаки
    private boolean isDefending; // состояние поднятия щита

    private List<Rect> walkingFrames; // список с анимациями ходьбы
    private List<Rect> attackFrames; // список с анимациями атаки
    private List<Rect> walkingMirrorFrames; // список с анимациями ходьбы
    private List<Rect> attackMirrorFrames; // список с анимациями атаки
    private List<Rect> mirrorFrames;
    //private List<Rect> attack1Frames;

    public Player(int posX, int posY, int width, int height, int vX, int vY, int frameWidth, int frameHeight, Bitmap bitmap) {
        super(posX, posY, width, height, vX, vY, frameWidth, frameHeight);
        this.setBitmap(bitmap);
        this.hp = 100;
        this.isWalking = false;
        this.atkRect = new Rect(posX + (this.getFrameWidth() / 2), posY + (this.getFrameHeight() / 2),
                posX + (this.getFrameWidth() / 4 * 3),
                posY + (this.getFrameHeight()));
        this.atkDamage = 20;
        this.atk1Damage = 40;
        this.dashRadius = 100;
        this.walkingFrames = new ArrayList<Rect>();
        this.attackFrames = new ArrayList<Rect>();
        this.walkingMirrorFrames = new ArrayList<Rect>();
        this.attackMirrorFrames = new ArrayList<Rect>();
        this.mirrorFrames = new ArrayList<Rect>();
        this.hitBox = new Rect(posX + (frameWidth / 2 - 70), posY + 170, posX + (frameWidth / 2 + 70),
                posY + frameHeight - 1);
        this.destination = new Rect();
        //this.attack1Frames = new ArrayList<Rect>();
        this.isAttacking = false;
        this.isDefending = false;
        this.isDashing = false;
        this.isFalling = false;
        //this.setPadding(100);

        // добавляем фреймы в их списки
        setDefaultFrames();
        setWalkingFrames();
        setAttackFrames();

        setMirrorFrames();
        setWalkingMirrorFrames();
        setAttackMirrorFrames();
    }

    public void update(int ms, int str, int agl) {
        super.update(ms);

        // если сменилось состояние, то обнуляем кадр, потому что кол-во кадров в анимациях разное
        if (this.isWalking != Game.walking && !isAttacking) {
            this.setCurrentFrame(0);
        }
        if (this.isAttacking != Game.attacking) {
            this.setCurrentFrame(0);
        }

        if (!isAttacking) isWalking = Game.walking;
        isAttacking = Game.attacking;

        if (isWalking && isAttacking) {
            isWalking = false;
        }

        if (this.getCurrentFrame() == this.attackFrames.size() - 1) {
            this.setCurrentFrame(0);
            this.isAttacking = false;
            Game.attacking = false;
        }

        if (this.getTimeForCurrentFrame() >= this.getFrameTime()) {
            if (isWalking) {
                //Log.d("ACTION", "WALKING");
                this.setCurrentFrame((this.getCurrentFrame() + 1) % walkingFrames.size());
            } else if (isAttacking) {
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

        if (this.isFalling) {
            this.posY += this.vY;
        }
        if (str >= 30 && isWalking) { // если сила наклона меньше 30, то не двигаем перса
            switch (agl) {
                case 0:
                    //if (!isCollidingRight) {
                    this.setDirection(0);
                    this.posX = (int) (this.posX + (this.vX * (str / 100.0)) / 60.0);
                    break;
                //}
                case 180:
                    //if (!isCollidingLeft) {
                    this.setDirection(1);
                    this.posX = (int) (this.posX - (this.vX * (str / 100.0)) / 60.0);
                    break;
                //}
            }
        }

        this.atkRect.set(posX + (this.getFrameWidth() / 2) - 265, posY + (this.getFrameHeight() / 2) - 177,
                posX + (this.getFrameWidth() / 4 * 3) - 265,
                posY + this.getFrameHeight() - 177);
        this.destination.set(posX - 265, posY - 177,
                posX + getFrameWidth() - 265, posY + getFrameHeight() - 177);
        //this.hitBox.set(posX + (frameWidth / 2 - 70), posY, posX + (frameWidth / 2 + 70),
        //        posY + frameHeight - 1);
        this.hitBox.set(posX, posY, posX + width, posY + height);
    }

    private void setDefaultFrames() {
        // Функция загружает кадры ожидания (перс стоит на месте)
        for (int i = 0; i < 8; i++) {
            Rect rect = new Rect(i * this.getFrameWidth(), 0,
                    i * this.getFrameWidth() + this.getFrameWidth(), this.getFrameHeight());
            this.addFrames(rect, frames);
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

    private void setMirrorFrames() {
        // Функция загружает зеркальные кадры ожидания
        for (int i = 0; i < 8; i++) {
            Rect rect = new Rect(i * this.getFrameWidth(), 13 * this.getFrameHeight(),
                    i * this.getFrameWidth() + this.getFrameWidth(),
                    14 * this.getFrameHeight());

            this.mirrorFrames.add(rect);
        }
    }

    private void setAttackMirrorFrames() {
        // Функция загружает зеркальные кадры атаки
        for (int i = 0; i < 11; i++) {
            Rect rect = new Rect(i * this.getFrameWidth(), 19 * this.getFrameHeight(),
                    i * this.getFrameWidth() + this.getFrameWidth(),
                    20 * this.getFrameHeight());

            this.attackMirrorFrames.add(rect);
        }
    }

    private void setWalkingMirrorFrames() {
        // Функция загружает зеркальные кадры ходьбы
        for (int i = 0; i < 8; i++) {
            Rect rect = new Rect(i * this.getFrameWidth(),  14* this.getFrameHeight(),
                    i * this.getFrameWidth() + this.getFrameWidth(),
                    15* this.getFrameHeight());

            this.walkingMirrorFrames.add(rect);
        }
    }


    public void draw(@NonNull Canvas canvas, Paint p) {
        // прорисовка на SurfaceView
        // p.setColor(Color.BLUE);
        //canvas.drawRect(destination, p);
        p.setColor(Color.RED);
        canvas.drawRect(this.atkRect, p);
        p.setColor(Color.GREEN);
        canvas.drawRect(this.hitBox, p);
        if (isWalking) {
            if (direction >0) {
                canvas.drawBitmap(bitmap, walkingMirrorFrames.get(getCurrentFrame()), this.destination, p);
            } else  {
                canvas.drawBitmap(bitmap, walkingFrames.get(getCurrentFrame()), this.destination, p);
            }
        } else if (isAttacking) {
            if (direction > 0) {
                canvas.drawBitmap(bitmap, attackMirrorFrames.get(getCurrentFrame()), this.destination, p);
            } else  {
                canvas.drawBitmap(bitmap, attackFrames.get(getCurrentFrame()), this.destination, p);
            }
        } else {
            if (direction > 0) {
                canvas.drawBitmap(bitmap, mirrorFrames.get(getCurrentFrame()), this.destination, p);
            } else {

                canvas.drawBitmap(bitmap, frames.get(getCurrentFrame()), this.destination, p);
            }
        }
    }

    /*public Rect getNewPos(ArrayList<Enemy> enemies) {
        for (int i = 0; i < enemies.size(); i++) {

        }
    }*/

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

    public int getAtkDamage() {
        return atkDamage;
    }

    public void setAtkDamage(int atkDamage) {
        this.atkDamage = atkDamage;
    }

    public int getAtk1Damage() {
        return atk1Damage;
    }

    public void setAtk1Damage(int atk1Damage) {
        this.atk1Damage = atk1Damage;
    }

    public boolean isWalking() {
        return isWalking;
    }

    public void setWalking(boolean is_walking) {
        this.isWalking = is_walking;
    }

    public boolean isDashing() {
        return isDashing;
    }

    public void setDashing(boolean is_dashing) {
        this.isDashing = is_dashing;
    }

    public int getDashRadius() {
        return dashRadius;
    }

    public void setDashRadius(int dashRadius) {
        this.dashRadius = dashRadius;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean is_attacking) {
        this.isAttacking = is_attacking;
    }

    public boolean isDefending() {
        return isDefending;
    }

    public void setDefending(boolean is_defending) {
        this.isDefending = is_defending;
    }
}
