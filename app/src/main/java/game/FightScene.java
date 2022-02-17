package game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.game.R;

import java.util.ArrayList;
import java.util.List;

public class FightScene extends Scene {
    private Bitmap bitmap;
    private Rect bmpHitBox;
    private Rect arenaHitBox;
    private Door door_1;
    private Door door_2;
    private boolean fight_mode;
    private Player player;
    private List<Enemy> enemies;
    private Floor floor;

    public FightScene(Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_knight); // загружаем картинку с фреймами
        Bitmap bitmap_d = BitmapFactory.decodeResource(context.getResources(), R.drawable.door);
        //Bitmap bitmap_f = BitmapFactory.decodeResource(context.getResources(), R.drawable.floor_1);

        this.enemies = new ArrayList<Enemy>();
        this.bmpHitBox = new Rect(0, 0, 1920, 1080);
        this.arenaHitBox = new Rect(500, 0, 1220, 700);
        this.fight_mode = false;

        int w = bitmap.getWidth() / 28; // ширина фрейма тестовой анимации
        int h = bitmap.getHeight() / 13; // длина тестовой анимации
        this.player = new Player(0, 0, 200, 10, w, h, bitmap); // создаём героя
        Cyborg en1 = new Cyborg(600, 200, 100, 10, w, h, bitmap); // создаём врага
        this.enemies.add(en1);
        // далее идёт создание опоры (пола)
        this.floor = new Floor(0, 700, 2000, 200, true);
        this.door_1 = new Door(500, 550, 20, 150, true, bitmap_d);
        this.door_2 = new Door(1700, 550, 20, 150, true, bitmap_d);
        Log.d("TAG", "FightScene: " + bitmap_d.getWidth());
    }

    @Override
    public void draw(Canvas canvas, Paint p) { // метод отвечает за отрисовку всех объектов на сцене
        // на заметку: не используй конструкцию цикла "for (Object obj : objects) {}"
        // она создаёт очень много экземпляров ArrayList и переполняет память
        p.setColor(Color.GRAY);
        canvas.drawRect(arenaHitBox, p);
        floor.draw(canvas, p);
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
        //boolean colliding = false;  заготовка для столкновения юнитов
        player.applyGravity(floor);
        player.update(ms, Game.strength, Game.angle);

        if (enemies.size() > 0) {
            if (player.hitBox.intersect(arenaHitBox) && !fight_mode) {
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
        bmpHitBox.set(0, 0, w, h);
        arenaHitBox.set(500, 0, w - 500, h);
        door_1.setPos(500, floor.getPosY() - door_1.height);
        door_2.setPos(w - 520, floor.getPosY() - door_2.height);
        floor.setSize(w, h - floor.getPosX());
    }

    @Override
    public Player getPLayer() {
        return player;
    }

    @Override
    public void setPLayer(Player pLayer) {
        this.player = player;
    }
}
