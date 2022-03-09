package game;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import entities.Player;
import scenes.FightScene;
import scenes.Room1;
import scenes.Room2;
import scenes.Scene;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private DrawThread drawThread;
    private static int scene_width;
    private static int scene_height;
    private static ArrayList<Scene> levels;
    private boolean running;
    private final int timerInterval = 150;
    private static Scene currentScene;
    private static int sceneId;


    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        levels = new ArrayList<Scene>();
        sceneId = 0;

        running = true;
        levels.add(new Room1(context));
        levels.add(new Room2(context));
        currentScene = levels.get(sceneId);
        currentScene.loadAssets();
        currentScene.setSize(getWidth(), getHeight());

        scene_width = getWidth();
        scene_height = getHeight();

        getHolder().addCallback(this);
        //this.run();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getContext(), getHolder(), this);
        drawThread.start();
        scene_width = getWidth();
        scene_height = getHeight();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // изменение размеров SurfaceView
        scene_width = width;
        scene_height = height;
        currentScene.setSize(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        threadStop();
    }

    private void threadStop() {
        drawThread.requestStop();
        running = false;
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

    public Scene getScene() {
        return currentScene;
    }

    @Override
    public void run() {
        while (running) {
            if (currentScene.isNext() && !(currentScene.isNext() && currentScene.isPrev())) {
                nextLevel();
            }
        }
    }

    public static void nextLevel() {
        //threadStop();
        DrawThread.draw = false;
        Player player = currentScene.clearAssets();
        sceneId = ((sceneId + 1) % levels.size());
        currentScene = levels.get(sceneId);
        currentScene.loadAssets(player);
        currentScene.setSize(GameView.scene_width, GameView.scene_height);
        DrawThread.setCurrentScene(currentScene);
        DrawThread.draw = true;
        //drawThread.start();
    }

    public static void prevLevel() {
        //threadStop();
        if (sceneId == 0) return;
        DrawThread.draw = false;
        Player player = currentScene.clearAssets();
        sceneId--;
        currentScene = levels.get(sceneId);
        Log.d("TAG", "prevLevel: " + sceneId);
        currentScene.loadAssets(player);
        currentScene.setSize(GameView.scene_width, GameView.scene_height);
        DrawThread.setCurrentScene(currentScene);
        DrawThread.draw = true;
        //drawThread.start();
    }
}

