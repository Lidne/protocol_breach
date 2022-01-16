package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {

    private SurfaceHolder surfaceHolder;

    private volatile boolean running = true; //флаг для остановки потока
    private Paint backgroundPaint = new Paint();
    private final int timerInterval = 150;
    private GameView view;
    private Player player;

    {
        backgroundPaint.setColor(Color.BLACK);
        backgroundPaint.setStyle(Paint.Style.FILL);
    }

    public DrawThread(Context context, SurfaceHolder surfaceHolder, GameView view) {
        this.surfaceHolder = surfaceHolder;
        this.view = view;
        this.player = view.getPlayer();

        // таймер для отрисовки нового кадра анимации
        //Timer t = new Timer();
        //t.start();
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
                    synchronized (view.getHolder()) {
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // очищаем экран
                        player.draw(canvas);
                        update();
                    }
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    /*class Timer extends CountDownTimer {
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
    }*/

    protected void update() {
        player.update(timerInterval, Game.strength, Game.angle);
    }
}
