package com.example.game;

import android.graphics.Bitmap;

public class Cyborg extends Enemy {

    public Cyborg(int posX, int posY, int vX, int vY, int frameWidth, int frameHeight, Bitmap bitmap) {
        super(posX, posY, vX, vY, frameWidth, frameHeight, bitmap);
        this.hp = 100;
        this.atk = 30;
        this.is_walking = false;
        this.is_attacking = false;
        this.stamina = 3;
    }
}
