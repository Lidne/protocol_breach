package game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

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
    //private List<Rect> attack1Frames;

    public Player(int posX, int posY, int vX, int vY, int frameWidth, int frameHeight, Bitmap bitmap) {
        super(posX, posY, vX, vY, frameWidth, frameHeight);
        this.setBitmap(bitmap);
        this.hp = 100;
        isWalking = false;
        this.atkRect = new Rect(posX + (this.getFrameWidth() / 2), posY + (this.getFrameHeight() / 2),
                posX + (this.getFrameWidth() / 4 * 3),
                posY + (this.getFrameHeight()));
        this.atkDamage = 20;
        this.atk1Damage = 40;
        this.dashRadius = 100;
        this.walkingFrames = new ArrayList<Rect>();
        this.attackFrames = new ArrayList<Rect>();
        this.hitBox = new Rect(posX + (frameWidth / 2 - 70), posY + 170, posX + (frameWidth / 2 + 70),
                posY + frameHeight - 1);
        //this.attack1Frames = new ArrayList<Rect>();
        isAttacking = false;
        isDefending = false;
        isDashing = false;
        isFalling = false;
        //this.setPadding(100);

        // добавляем фреймы в их списки
        setDefaultFrames();
        setWalkingFrames();
        setAttackFrames();
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
        if (str > 30 && isWalking) { // если сила наклона меньше 20, то не двигаем перса
            switch (agl) {
                case 0:
                    this.setDirection(0);
                    this.posX = (this.posX + (this.vX * (str / 100)) / 60);
                    break;
                case 180:
                    this.setDirection(1);
                    this.posX = (this.posX - (this.vX * (str / 100)) / 60);
                    break;
            }
        }

        if (this.isFalling) {
            this.posY += this.vY;
        }

        this.atkRect.set(posX + (this.getFrameWidth() / 2), posY + (this.getFrameHeight() / 2),
                posX + (this.getFrameWidth() / 4 * 3),
                posY + this.getFrameHeight());
        this.destination.set(posX, posY, posX + getFrameWidth(), posY + getFrameHeight());
        this.hitBox.set(posX + (frameWidth / 2 - 70), posY + 170, posX + (frameWidth / 2 + 70),
                posY + frameHeight - 1);
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

    public void draw(@NonNull Canvas canvas, Paint p) {
        // прорисовка на SurfaceView
        // p.setColor(Color.BLUE);
        //canvas.drawRect(destination, p);
        //p.setColor(Color.RED);
        //canvas.drawRect(this.atkRect, p);
        p.setColor(Color.GREEN);
        canvas.drawRect(this.getHitBoxRect(), p);
        if (isWalking) {
            canvas.drawBitmap(bitmap, walkingFrames.get(getCurrentFrame()), this.destination, p);
        } else if (isAttacking) {
            canvas.drawBitmap(bitmap, attackFrames.get(getCurrentFrame()), this.destination, p);
        } else canvas.drawBitmap(bitmap, frames.get(getCurrentFrame()), this.destination, p);
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
