package com.example.game;

import androidx.core.view.WindowCompat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends Activity {
    public static int angle;
    public static int strength;
    public static boolean walking;
    public static boolean attacking;
    //public static boolean

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // запускаем игру

        TextView Angle = (TextView) findViewById(R.id.textView_angle);
        TextView Strength = (TextView) findViewById(R.id.textView_strength);

        JoystickView joystickLeft = (JoystickView) findViewById(R.id.joystickView_left);
        joystickLeft.setButtonDirection(-1);
        JoystickView.OnMoveListener listener = new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                MainActivity.angle = angle;
                MainActivity.strength = strength;
                Angle.setText(angle + "°");
                Strength.setText(strength + "%");
                MainActivity.walking = (strength >= 30);
            }
        };
        Log.d("MY_TAG", "onCreate: " + joystickLeft);
        joystickLeft.setOnMoveListener(listener);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);
    }
}