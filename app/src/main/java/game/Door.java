package game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import androidx.annotation.NonNull;

public class Door extends AnimatedObject {
    private int top_posY;
    private int bot_posY;
    private boolean isColliding;
    private boolean isOpened;
    private boolean isPlayingAnimation;

    public Door(int posX, int posY, int width, int height, boolean isColliding, Bitmap bitmap) {
        super(posX, posY, width, height, bitmap);
        this.isColliding = isColliding;
        this.frameWidth = 380;
        this.frameHeight = 100;
        this.isOpened = true;
        this.isPlayingAnimation = false;
        addFrames();
    }

    @Override
    public void draw(@NonNull Canvas canvas, Paint p) {
        //Log.d("TAG", "draw: " + currentFrame);
        canvas.drawBitmap(bitmap, frames.get(currentFrame), hitBox, p);
    }

    @Override
    public void update(int ms) {
        super.update(ms);
        Log.d("TAG", "state: " + isOpened + " isPLaying: " + isPlayingAnimation);
        if (timeForCurrentFrame >= frameTime) {
            if (isOpened && isPlayingAnimation) {
                if ((currentFrame + 1) < (frames.size() / 2)) {
                    currentFrame = (currentFrame + 1) % frames.size();
                } else {
                    currentFrame = frames.size() / 2;
                    isOpened = false;
                    isPlayingAnimation = false;
                }
            }
            if (!isOpened && isPlayingAnimation) {
                if (currentFrame - 1 >= 0) {
                    Log.d("TAG", "state: " + currentFrame);
                    //currentFrame = (currentFrame + 1) % frames.size();
                    currentFrame--;
                } else {
                    currentFrame = 0;
                    isOpened = true;
                    isPlayingAnimation = false;
                }
            }
        }

        timeForCurrentFrame = (timeForCurrentFrame - frameTime);
    }

    private void addFrames() {
        for (int i = 0; i < frameWidth; i += 20) {
            Rect rect = new Rect(i, 0, i + 20, 100);
            frames.add(rect);
        }
    }

    public void change_state() {
        Log.d("TAG", "change_state: " + isOpened + " " + isPlayingAnimation);
        isPlayingAnimation = true;

        //Log.d("TAG", "change_state: " + isPlayingAnimation);
    }



    public int getTop_posY() {
        return top_posY;
    }

    public void setTop_posY(int top_posY) {
        this.top_posY = top_posY;
    }

    public int getBot_posY() {
        return bot_posY;
    }

    public void setBot_posY(int bot_posY) {
        this.bot_posY = bot_posY;
    }

    public boolean isColliding() {
        return isColliding;
    }

    public void setColliding(boolean colliding) {
        isColliding = colliding;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public boolean isPlayingAnimation() {
        return isPlayingAnimation;
    }

    public void setPlayingAnimation(boolean playingAnimation) {
        isPlayingAnimation = playingAnimation;
    }
}
