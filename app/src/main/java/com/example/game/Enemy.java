package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public abstract class Enemy extends Entity {
    protected int hp;
    protected int atk;

    protected List<Rect> attackFrames;
    protected List<Rect> walkingFrames;

    protected boolean is_walking;
    protected boolean is_attacking;
    protected int stamina; //

    public Enemy(int posX, int posY, int vX, int vY, int frameWidth, int frameHeight, Bitmap bitmap) {
        super(posX, posY, vX, vY, frameWidth, frameHeight);
        this.setBitmap(bitmap);
        this.attackFrames = new ArrayList<Rect>();
        this.walkingFrames = new ArrayList<Rect>();
    }

    public abstract void draw(Canvas canvas);

    public int getHp() {
        return hp;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public List<Rect> getAttackFrames() {
        return attackFrames;
    }

    public void setAttackFrames(List<Rect> attackFrames) {
        this.attackFrames = attackFrames;
    }

    public List<Rect> getWalkingFrames() {
        return walkingFrames;
    }

    public void setWalkingFrames(List<Rect> walkingFrames) {
        this.walkingFrames = walkingFrames;
    }

    public boolean isIs_attacking() {
        return is_attacking;
    }

    public void setIs_attacking(boolean is_attacking) {
        this.is_attacking = is_attacking;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }
}