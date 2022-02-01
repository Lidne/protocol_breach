package game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Cyborg extends Enemy {

    public Cyborg(int posX, int posY, int vX, int vY, int frameWidth, int frameHeight, Bitmap bitmap) {
        super(posX, posY, vX, vY, frameWidth, frameHeight, bitmap);
        this.hp = 100;
        this.atk = 30;
        this.isWalking = false;
        this.isAttacking = false;
        this.stamina = 3;
        this.hitBox = new Rect(posX + (frameWidth / 2 - 70), posY + 170, posX + (frameWidth / 2 + 70),
                posY + frameHeight - 1);

        setDefaultFrames();
        setAttackFrames();
        setWalkingFrames();
    }

    // временно, потом сделать свой метод
    private void setDefaultFrames() {
        // Функция загружает кадры ожидания (перс стоит на месте)
        for (int i = 0; i < 8; i++) {
            this.addFrames(new Rect(i * this.getFrameWidth(), 0,
                    i * this.getFrameWidth() + this.getFrameWidth(), this.getFrameHeight()), frames);
        }
    }

    // временно, потом сделать свой метод
    private void setWalkingFrames() {
        // Функция загружает кадры ходьбы
        for (int i = 0; i < 8; i++) {
            this.addFrames(new Rect(i * this.getFrameWidth(), this.getFrameHeight(),
                    i * this.getFrameWidth() + this.getFrameWidth(),
                    2 * this.getFrameHeight()), walkingFrames);
        }
    }

    // временно, потом сделать свой метод
    private void setAttackFrames() {
        // Функция загружает кадры атаки
        for (int i = 0; i < 11; i++) {
            this.addFrames(new Rect(i * this.getFrameWidth(), 6 * this.getFrameHeight(),
                    i * this.getFrameWidth() + this.getFrameWidth(),
                    7 * this.getFrameHeight()), attackFrames);
        }
    }

    @Override
    public void draw(@NonNull Canvas canvas, Paint p) {
        //p.setColor(Color.BLUE);
        //canvas.drawRect(destination, p);
        p.setColor(Color.GREEN);
        canvas.drawRect(this.getHitBoxRect(), p);
        if (isWalking)
            canvas.drawBitmap(bitmap, walkingFrames.get(getCurrentFrame()), this.destination, p);
        else if (isAttacking)
            canvas.drawBitmap(bitmap, attackFrames.get(getCurrentFrame()), this.destination, p);
        else canvas.drawBitmap(bitmap, frames.get(getCurrentFrame()), this.destination, p);
    }

    public void update(int ms) {
        super.update(ms);

        if (this.getCurrentFrame() == this.attackFrames.size() - 1) {
            this.setCurrentFrame(0);
            this.isAttacking = false;
        }

        if (this.getTimeForCurrentFrame() >= this.getFrameTime()) {
            if (isWalking) {
                this.setCurrentFrame((this.getCurrentFrame() + 1) % walkingFrames.size());
            } else if (isAttacking) {
                this.setCurrentFrame((this.getCurrentFrame() + 1) % attackFrames.size());
            } else {
                this.setCurrentFrame((this.getCurrentFrame() + 1) % frames.size());
            }

            this.setTimeForCurrentFrame(this.getTimeForCurrentFrame() - this.getFrameTime());
        }

        move();

        // обновление хитбокса и места отрисовки кадра
        this.hitBox.set(posX + (frameWidth / 2 - 70), posY + 170, posX + (frameWidth / 2 + 70),
                posY + frameHeight - 1);
        this.destination.set(posX, posY, posX + getFrameWidth(), posY + getFrameHeight());
    }

    private void move() {
        // функция перемещения персонажа
        if (this.isFalling) {
            this.posY += this.vY;
            //Log.d("TAG", "move: " + );
        }
    }

    public void resurrect() {
        this.hp = 100;
        this.posX += 200;
    }
}
