package com.example.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Iterator;
import java.util.List;

public class Player extends Entity {
    private List<Rect> attackFrames;
    private Rect rectAttack;
    private int hp;
    private int atk_d; // урон от первой атаки в комбо
    private int atk_d1; // урон от второй атаки

    public Player(int posX, int posY, int vX, int vY, int frameWidth, int frameHeight, Bitmap bitmap) {
        super(posX, posY, vX, vY, frameWidth, frameHeight);
        this.setBitmap(bitmap);

        for (int j = 0; j < 8; j++) {
            Rect rect = new Rect(j * this.getFrameWidth(), this.getFrameHeight(),
                    j * this.getFrameWidth() + this.getFrameWidth(),
                    2* this.getFrameHeight());

            this.addFrame(rect);

        }
        this.setFrameTime(1000);
        this.rectAttack = new Rect(posX+frameWidth/2,posY+frameHeight/2,posX+frameWidth,posY+frameHeight);
        for (int i = 0; i < 11; i++) {
            Rect rect = new Rect(i * this.getFrameWidth(), 6 * this.getFrameHeight(),
                    i * this.getFrameWidth() + this.getFrameWidth(),
                    7 * this.getFrameHeight());

            this.attackFrames.add(rect);
        }



        }



    public void update(int ms, int str, int agl) {
        super.update(ms);
        Log.d("TAG", "update: " + (str / 100.0));
        switch (agl) {
            case 0: this.setX((int) (this.getX() + (this.getVX() * (str / 100.0)) / 60.0)); break;
            case 180: this.setX((int) (this.getX() - (this.getVX() * (str / 100.0)) / 60.0)); break;
        }
    }
        }


