package game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.NonNull;

public class Floor extends StaticObject {

    public Floor(int posX, int posY, int width, int height, boolean isColliding) {
        super(posX, posY, width, height, isColliding);
    }

    public void draw(@NonNull Canvas canvas, Paint p) {
        //canvas.drawBitmap(bitmap, bmpHitBox, hitBox, p);
        p.setColor(Color.BLUE);
        canvas.drawRect(hitBox, p);
    }
}
