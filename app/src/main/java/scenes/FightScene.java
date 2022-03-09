package scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.game.R;

import java.util.ArrayList;
import java.util.List;

import entities.Enemy;
import game.Game;
import game.GameView;
import entities.Player;

public class FightScene extends Scene {
    protected Context context;
    protected Bitmap bitmap;
    protected Rect bmpHitBox;
    protected Rect arenaHitBox;
    protected Door door_1;
    protected Door door_2;
    protected boolean fight_mode;
    protected Player player;
    protected List<Enemy> enemies;
    protected int floor;
    protected boolean next;
    protected boolean prev;
    protected boolean loaded;


    public FightScene(Context context) {
        this.context = context;
        this.bitmap = null;
        this.enemies = new ArrayList<Enemy>();
        this.bmpHitBox = new Rect(0, 0, 1920, 1080);
        this.arenaHitBox = new Rect(500, 0, 1220, 700);
        this.door_1 = null;
        this.door_2 = null;
        this.fight_mode = false;
        this.player = null;
        this.floor = 700; // задаём высоту пола
        this.loaded = false;
    }


    @Override
    public void loadAssets() {

    }

    @Override
    public void loadAssets(Player player) {

    }

    @Override
    public Player clearAssets() {
        this.bitmap = null;
        //this.bmpHitBox = null;
        //this.arenaHitBox = null;
        //this.door_1 = null;
        //this.door_2 = null;
        this.fight_mode = false;
        this.next = false;
        this.prev = false;
        this.enemies.clear();
        this.floor = 0;
        this.loaded = false;
        return player;
    }

    @Override
    public void draw(Canvas canvas, Paint p) { // метод отвечает за отрисовку всех объектов на сцене
        if (!loaded) return;
        // на заметку: не используй конструкцию цикла "for (Object obj : objects) {}"
        // она создаёт очень много экземпляров ArrayList и переполняет память
        p.setColor(Color.YELLOW);
        canvas.drawRect(arenaHitBox, p);
        player.draw(canvas, p);
        door_1.draw(canvas, p);
        door_2.draw(canvas, p);
        for (int i = 0; i < enemies.size(); i++) {
            Enemy unit = enemies.get(i);
            unit.draw(canvas, p);
        }
    }

    @Override
    public void update(int ms) { // этот метод отвечает за отрисовку всех сущностей, объектов и фонов
        if (!loaded) return;
        //boolean colliding = false;  заготовка для столкновения юнитов
        player.applyGravity(floor);
        player.update(ms, Game.strength, Game.angle);

        if (player.getX() > bmpHitBox.right && !(next && prev)) {
            GameView.nextLevel();
            return;
        }

        if (player.getX() + player.getWidth() < 0 && !(next && prev)) {
            GameView.prevLevel();
            return;
        }

        //this.next = player.getX() > bmpHitBox.right && !(!next && prev);
        //this.prev = player.getX() + player.getWidth() < 0 && !(!next && prev);
        //Log.d("TAG", "next: " + next + " prev: " + prev);

        if (enemies.size() > 0) {
            if (arenaHitBox.contains(player.getHitBoxRect()) && !fight_mode) {
                fight_mode = true;
                door_1.change_state();
                door_2.change_state();
            }
            for (int i = 0; i < enemies.size(); i++) {
                Enemy unit = enemies.get(i);
                unit.applyGravity(floor);
                unit.update(ms);
                if (player.isAttacking() && unit.intersect(player.getAtkRect())) {
                    unit.setHp(unit.getHp() - 30);
                }
                if (!unit.isAlive()) {
                    //unit.resurrect();
                    enemies.remove(i);
                }
                //colliding = colliding || player.intersect(unit);
                //Log.d("TAG", "update: " + colliding);
            }
        } else if (fight_mode) {
            fight_mode = false;
            door_1.change_state();
            door_2.change_state();
        }

        door_1.update(ms);
        door_2.update(ms);
    }

    @Override
    public void setSize(int w, int h) {
        if (!loaded) return;
        bmpHitBox.set(0, 0, w, h);
        arenaHitBox.set(500, 0, w - 500, h);
        door_1.setPos(480, floor - door_1.getHeight());
        door_2.setPos(w - 500, floor - door_2.getHeight());
    }
}