package com.example.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;
    private final int timerInterval = 30;

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getContext(), getHolder());
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // изменение размеров SurfaceView
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //
            }
        }
    }

    public class DrawThread extends Thread {

        private SurfaceHolder surfaceHolder;
        private Player player;

        private volatile boolean running = true; //флаг для остановки потока
        private Paint backgroundPaint = new Paint();
        private final int timerInterval = 100;

        {
            backgroundPaint.setColor(Color.BLACK);
            backgroundPaint.setStyle(Paint.Style.FILL);
        }

        public DrawThread(Context context, SurfaceHolder surfaceHolder) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_knight); // загружаем картинку с фреймами

            int w = bitmap.getWidth()/28;
            int h = bitmap.getHeight()/13;
            player = new Player(100, 200, 200, 0, w, h, bitmap); // создаём героя

            this.surfaceHolder = surfaceHolder;

            // таймер для отрисовки нового кадра анимации
            Timer t = new Timer();
            t.start();
        }

        public void requestStop() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    try {
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        player.draw(canvas);
                        update();
                    } finally {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }

        class Timer extends CountDownTimer {
            public Timer() {
                super(Integer.MAX_VALUE, timerInterval);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                update();
            }

            @Override
            public void onFinish() {
            }
        }

        protected void update() {
            player.update(timerInterval, MainActivity.strength, MainActivity.angle);
        }
    }
}
