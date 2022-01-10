package com.example.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

public class Player extends Entity {
    private int hp;
    private int atk_d; // урон от первой атаки в комбо
    private int atk_d1; // урон от второй атаки

    public Player(float posX, float posY, float vX, float vY, int frameWidth, int frameHeight, Bitmap bitmap) {
        super(posX, posY, vX, vY, frameWidth, frameHeight);
        this.setBitmap(bitmap);

        for (int j = 0; j < 8; j++) {
            Rect rect = new Rect(j * this.getFrameWidth(), this.getFrameHeight(),
                    j * this.getFrameWidth() + this.getFrameWidth(),
                    2* this.getFrameHeight());

            this.addFrame(rect);

        }
        this.setFrameTime(1000);
        }


    public void update(int ms, int str, int agl) {
        super.update(ms);
        Log.d("TAG", "update: " + (str / 100.0));
        switch (agl) {
            case 0: this.setX((float) (this.getX() + (this.getVX() * (str / 100.0)) / 60.0)); break;
            case 180: this.setX((float) (this.getX() - (this.getVX() * (str / 100.0)) / 60.0)); break;
        }
    }
}
