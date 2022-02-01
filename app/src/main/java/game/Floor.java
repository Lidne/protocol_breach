package game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.NonNull;

public class Floor extends StaticObject {

    public Floor(int posX, int posY, int width, int height, boolean isColliding, Bitmap bitmap) {
        super(posX, posY, width, height, isColliding, bitmap);
    }

    public void draw(@NonNull Canvas canvas, Paint p) {
        canvas.drawBitmap(bitmap, bmpHitBox, hitBox, p);
    }
}
