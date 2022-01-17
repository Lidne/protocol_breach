package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Cyborg extends Enemy {
    //Timer timer;

    public Cyborg(int posX, int posY, int vX, int vY, int frameWidth, int frameHeight, Bitmap bitmap) {
        super(posX, posY, vX, vY, frameWidth, frameHeight, bitmap);
        this.hp = 100;
        this.atk = 30;
        this.is_walking = false;
        this.is_attacking = false;
        this.stamina = 3;
        this.setPadding(50);

        //timer = this.new Timer(1000);

        setDefaultFrames();
        setAttackFrames();
        setWalkingFrames();
    }

    // временно, потом сделать свой метод
    private void setDefaultFrames() {
        // Функция загружает кадры ожидания (перс стоит на месте)
        for (int i = 0; i < 8; i++) {
            Rect rect = new Rect(i * this.getFrameWidth(), 0,
                    i * this.getFrameWidth() + this.getFrameWidth(), this.getFrameHeight());
            this.frames.add(rect);
        }
    }

    // временно, потом сделать свой метод
    private void setWalkingFrames() {
        // Функция загружает кадры ходьбы
        for (int i = 0; i < 8; i++) {
            Rect rect = new Rect(i * this.getFrameWidth(), this.getFrameHeight(),
                    i * this.getFrameWidth() + this.getFrameWidth(),
                    2 * this.getFrameHeight());

            this.walkingFrames.add(rect);
        }
    }

    // временно, потом сделать свой метод
    private void setAttackFrames() {
        // Функция загружает кадры атаки
        for (int i = 0; i < 11; i++) {
            Rect rect = new Rect(i * this.getFrameWidth(), 6 * this.getFrameHeight(),
                    i * this.getFrameWidth() + this.getFrameWidth(),
                    7 * this.getFrameHeight());

            this.attackFrames.add(rect);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Paint p = new Paint();
        Rect destination = new Rect(posX, posY, posX + getFrameWidth(),posY + getFrameHeight());
        if (is_walking)
            canvas.drawBitmap(bitmap, walkingFrames.get(getCurrentFrame()), destination, p);
        else if (is_attacking)
            canvas.drawBitmap(bitmap, attackFrames.get(getCurrentFrame()), destination, p);
        else canvas.drawBitmap(bitmap, frames.get(getCurrentFrame()), destination, p);
    }

    public void update(int ms) {
        super.update(ms);

        if (this.getCurrentFrame() == this.attackFrames.size() - 1) {
            this.setCurrentFrame(0);
            this.is_attacking = false;
        }

        if (this.getTimeForCurrentFrame() >= this.getFrameTime()) {
            if (is_walking) {
                this.setCurrentFrame((this.getCurrentFrame() + 1) % walkingFrames.size());
            } else if (is_attacking) {
                this.setCurrentFrame((this.getCurrentFrame() + 1) % attackFrames.size());
            } else {
                this.setCurrentFrame((this.getCurrentFrame() + 1) % frames.size());
            }

            this.setTimeForCurrentFrame(this.getTimeForCurrentFrame() - this.getFrameTime());
        }
    }

    public void resurrect() {
        this.hp = 100;
        this.posX += 200;
    }

    private void tickUpdate() {
        Log.d("TAG", "tickUpdate: ");
    }
}
