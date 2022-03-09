package scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.List;

import entities.Enemy;
import entities.Player;

public abstract class Scene {
    private Context context;
    private Bitmap bitmap;
    private Rect bmpHitBox;
    private Rect arenaHitBox;
    private Door door_1;
    private Door door_2;
    private Rect checkpoint1;
    private Rect checkpoint2;
    private boolean fight_mode;
    private Player player;
    private List<Enemy> enemies;
    private int floor;
    private boolean next;
    private boolean prev;
    public abstract void loadAssets();
    public abstract void loadAssets(Player player);
    public abstract Player clearAssets();
    public abstract void draw(Canvas canvas, Paint p);
    public abstract void update(int ms);
    public abstract void setSize(int w, int h);

    public Player getPLayer() {
        return player;
    }

    public void setPLayer(Player pLayer) {
        this.player = player;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Rect getBmpHitBox() {
        return bmpHitBox;
    }

    public void setBmpHitBox(Rect bmpHitBox) {
        this.bmpHitBox = bmpHitBox;
    }

    public Rect getArenaHitBox() {
        return arenaHitBox;
    }

    public void setArenaHitBox(Rect arenaHitBox) {
        this.arenaHitBox = arenaHitBox;
    }

    public Door getDoor_1() {
        return door_1;
    }

    public void setDoor_1(Door door_1) {
        this.door_1 = door_1;
    }

    public Door getDoor_2() {
        return door_2;
    }

    public void setDoor_2(Door door_2) {
        this.door_2 = door_2;
    }

    public Rect getCheckpoint1() {
        return checkpoint1;
    }

    public void setCheckpoint1(Rect checkpoint1) {
        this.checkpoint1 = checkpoint1;
    }

    public Rect getCheckpoint2() {
        return checkpoint2;
    }

    public void setCheckpoint2(Rect checkpoint2) {
        this.checkpoint2 = checkpoint2;
    }

    public boolean isFight_mode() {
        return fight_mode;
    }

    public void setFight_mode(boolean fight_mode) {
        this.fight_mode = fight_mode;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public boolean isPrev() {
        return prev;
    }

    public void setPrev(boolean prev) {
        this.prev = prev;
    }
}
