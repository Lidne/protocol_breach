package scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import com.example.game.R;

import java.util.ArrayList;
import java.util.List;

import entities.Cyborg;
import entities.Enemy;
import entities.Player;

public class Room1 extends FightScene implements SceneLoad {
    public Room1(Context context) {
        super(context);
    }

    @Override
    public void loadAssets() {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_knight1); // загружаем картинку с фреймами
        Bitmap bitmap_d = BitmapFactory.decodeResource(context.getResources(), R.drawable.door);
        //Bitmap bitmap_f = BitmapFactory.decodeResource(context.getResources(), R.drawable.floor_1);

        int w = bitmap.getWidth() / 28; // ширина фрейма тестовой анимации
        int h = bitmap.getHeight() / 26; // длина тестовой анимации
        this.player = new Player(200, 580, 70, 117, 200, 10, w, h, bitmap); // создаём героя

        Cyborg en1 = new Cyborg(900, 580, 70, 117, 100, 10, w, h, bitmap); // создаём врага
        this.enemies.add(en1);
        Cyborg en2 = new Cyborg(1200, 580, 70, 117, 100, 10, w, h, bitmap); // создаём врага
        this.enemies.add(en2);

        this.door_1 = new Door(480, 550, 20, 150, true, bitmap_d);
        this.door_2 = new Door(1720, 550, 20, 150, true, bitmap_d);

        bitmap = null;
        bitmap_d = null;
        this.loaded = true;
    }

    @Override
    public void loadAssets(Player player) {
        this.loadAssets();
        this.player = player;
        this.player.setX(200);
    }

    /*@Override
    public void draw(Canvas canvas, Paint p) { // метод отвечает за отрисовку всех объектов на сцене
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
        //boolean colliding = false;  заготовка для столкновения юнитов
        player.applyGravity(floor);
        player.update(ms, Game.strength, Game.angle);

        if (enemies.size() > 0) {
            if (arenaHitBox.contains(player.hitBox) && !fight_mode) {
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
        door_1.setPos(480, floor - door_1.height);
        door_2.setPos(w - 500, floor - door_2.height);
        //checkpoint2.set(w - 30, 0, w, h);
    }*/
}

