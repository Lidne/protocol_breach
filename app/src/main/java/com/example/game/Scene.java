package com.example.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private Player player;
    private List<Cyborg> enemies;

    public Scene(Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_knight); // загружаем картинку с фреймами

        int w = bitmap.getWidth() / 28;
        int h = bitmap.getHeight() / 13;
        this.enemies = new ArrayList<Cyborg>();

        player = new Player(100, 200, 200, 0, w, h, bitmap); // создаём героя
        Cyborg en1 = new Cyborg(600, 200, 100, 0, w, h, bitmap);
        enemies.add(en1);
    }

    public void draw(Canvas canvas) {
        player.draw(canvas);
        for (Cyborg unit : enemies) {
            if (unit.isAlive()) unit.draw(canvas);
        }
    }

    public void update(int ms) {
        //boolean colliding = false;  заготовка для столкновения юнитов
        for (Cyborg unit : enemies) {
            unit.update(ms);
            if (player.isAttacking() && unit.attacked(player.getAtkRect())) {
                unit.setHp(unit.getHp() - 30);
            }
            //colliding = colliding || player.intersect(unit);
            //Log.d("TAG", "update: " + colliding);
        }
        player.update(ms, MainActivity.strength, MainActivity.angle);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
