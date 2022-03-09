package entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public abstract class Enemy extends Entity {
    protected int hp;
    protected int atk;

    protected List<Rect> walkingFrames; // список с анимациями ходьбы
    protected List<Rect> attackFrames; // список с анимациями атаки

    protected int stamina;

    public Enemy(int posX, int posY, int width, int height, int vX, int vY, int frameWidth, int frameHeight, Bitmap bitmap) {
        super(posX, posY, width, height, vX, vY, frameWidth, frameHeight);
        this.setBitmap(bitmap);
        this.walkingFrames = new ArrayList<Rect>();
        this.attackFrames = new ArrayList<Rect>();
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public int getHp() {
        return hp;
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

    public List<Rect> getWalkingFrames() {
        return walkingFrames;
    }

    public void setWalkingFrames(List<Rect> walkingFrames) {
        this.walkingFrames = walkingFrames;
    }

    public List<Rect> getAttackFrames() {
        return attackFrames;
    }

    public void setAttackFrames(List<Rect> attackFrames) {
        this.attackFrames = attackFrames;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean is_attacking) {
        this.isAttacking = is_attacking;
    }

    public boolean isWalking() {
        return isWalking;
    }

    public void setWalking(boolean walking) {
        isWalking = walking;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {this.stamina = stamina;}

    protected int attackRange;

    public void setAttackRange(int attackRange) {this.attackRange=attackRange;}

    public int getAttackRange() {return attackRange;}

    protected int dmg;

    public void setDmg(int dmg) {this.dmg=dmg;}

    public int getDmg() {return dmg;}



    public void resurrect() {
        this.hp = 100;
        this.posX += 200;
    }

    public void draw(@NonNull Canvas canvas, Paint p) {
        canvas.drawRect(this.destination, p);
    }

    public void canAttack(Entity entity){
        if (Math.abs(this.posX-entity.posX)<=getAttackRange()){
            this.isAttacking=true;
        }
        else {
            this.isWalking=true;
        }
    }
}