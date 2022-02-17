package game;

import android.graphics.Canvas;
import android.graphics.Paint;

abstract class Scene {
    Player player;
    abstract void draw(Canvas canvas, Paint p);
    abstract void update(int ms);
    abstract void setSize(int w, int h);
    abstract Player getPLayer();
    abstract void setPLayer(Player pLayer);
}
