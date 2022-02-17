package game;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;
    private final int timerInterval = 30;
    private FightScene fightScene;


    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);


        fightScene = new FightScene(context);

        getHolder().addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        drawThread = new DrawThread(getContext(), getHolder(), this);
        drawThread.start();
        fightScene.setSize(getWidth(), getHeight());
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

    public FightScene getScene() {
        return fightScene;
    }
}

