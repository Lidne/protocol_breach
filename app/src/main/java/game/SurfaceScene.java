package game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.game.R;

import java.util.ArrayList;
import java.util.List;

public class SurfaceScene implements Scene {
    private Player player;
    private List<Enemy> enemies;
    private List<Floor> floors;
    public SurfaceScene(Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_knight); // загружаем картинку с фреймами
        Bitmap bitmap_f = BitmapFactory.decodeResource(context.getResources(), R.drawable.floor_1); // загружаем картинку с фреймами

        int w = bitmap.getWidth() / 28;
        int h = bitmap.getHeight() / 26;
        this.enemies = new ArrayList<Enemy>();
        this.floors = new ArrayList<Floor>();

        player = new Player(100, 0, 200, 10, w, h, bitmap); // создаём героя
        Cyborg en1 = new Cyborg(600, 200, 100, 10, w, h, bitmap); // создаём врага
        enemies.add(en1);
        // далее идёт создание опор
        Floor floor1 = new Floor(0, 500, 600, 200, true, bitmap_f);
        floors.add(floor1);
        Floor floor2 = new Floor(600, 701, 600, 200, true, bitmap_f);
        floors.add(floor2);
        Floor floor3 = new Floor(1200, 701, 600, 200, true, bitmap_f);
        floors.add(floor3);
    }

    @Override
    public void draw(Canvas canvas, Paint p) {
        // на заметку: не используй конструкцию цикла "for (Object obj : objects) {}"
        // она создаёт очень много классов ArrayList и переполняет память
        for (int i = 0; i < floors.size(); i++) {
            Floor floor = floors.get(i);
            floor.draw(canvas, p);
        }
        player.draw(canvas, p);
        for (int i = 0; i < enemies.size(); i++) {
            Enemy unit = enemies.get(i);
            if (!unit.isAlive()) {
                unit.resurrect();
            } else {
                unit.draw(canvas, p);
            }
        }
    }

    @Override
    public void update(int ms) {
        //boolean colliding = false;  заготовка для столкновения юнитов
        for (int i = 0; i < enemies.size(); i++) {
            Enemy unit = enemies.get(i);
            unit.applyGravity(floors);
            unit.update(ms);
            if (player.isAttacking() && unit.attacked(player.getAtkRect())) {
                unit.setHp(unit.getHp() - 30);
            }
            //colliding = colliding || player.intersect(unit);
            //Log.d("TAG", "update: " + colliding);
        }
        player.applyGravity(floors);
        player.update(ms, Game.strength, Game.angle);

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
