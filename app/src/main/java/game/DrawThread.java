package game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.SurfaceHolder;

import scenes.Scene;

public class DrawThread extends Thread {

    private SurfaceHolder surfaceHolder;

    public static boolean draw;

    private volatile boolean running = true; //флаг для остановки потока
    private Paint backgroundPaint = new Paint();
    private final int timerInterval = 150;
    private GameView view;
    private static scenes.Scene currentScene;

    private final Paint p = new Paint();

    {
        backgroundPaint.setColor(Color.BLACK);
        backgroundPaint.setStyle(Paint.Style.FILL);
    }

    public DrawThread(Context context, SurfaceHolder surfaceHolder, GameView view) {
        this.surfaceHolder = surfaceHolder;
        this.view = view;
        currentScene = view.getScene();
        draw = true;

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
            if (canvas != null && draw) {
                try {
                    synchronized (view.getHolder()) {
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // очищаем экран
                        currentScene.draw(canvas, p);
                        currentScene.update(timerInterval);
                        //Log.d("TAG", "run: " + currentScene.getClass().getName());
                    }
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public static void setCurrentScene(Scene scene) {
        currentScene = scene;
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
}
