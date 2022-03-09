package scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import com.example.game.R;

import java.util.ArrayList;

import entities.Cyborg;
import entities.Enemy;
import entities.Player;

public class Room2 extends FightScene implements SceneLoad {
    public Room2(Context context) {
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

        this.door_1 = new Door(480, 550, 20, 150, true, bitmap_d);
        this.door_2 = new Door(1720, 550, 20, 150, true, bitmap_d);
        Log.d("TAG", "loadAssets: " + door_1.toString());

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
}
