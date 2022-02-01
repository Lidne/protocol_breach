package game;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface Scene {
    void draw(Canvas canvas, Paint p);
    void update(int ms);
    Player getPLayer();
    void setPLayer(Player pLayer);
}
