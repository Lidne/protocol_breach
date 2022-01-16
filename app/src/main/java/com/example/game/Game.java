package com.example.game;

import androidx.core.view.WindowCompat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class Game extends Activity {
    public static int angle;
    public static int strength;
    public static boolean walking;
    public static boolean attacking;
    //public static boolean

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game); // запускаем игру

        TextView Angle = (TextView) findViewById(R.id.textView_angle);
        TextView Strength = (TextView) findViewById(R.id.textView_strength);
        Button btn = (Button) findViewById(R.id.btn);

        JoystickView joystickLeft = (JoystickView) findViewById(R.id.joystickView_left);
        joystickLeft.setButtonDirection(-1);
        JoystickView.OnMoveListener listener_j = new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                Game.angle = angle;
                Game.strength = strength;
                Angle.setText(angle + "°");
                Strength.setText(strength + "%");
                Game.walking = (strength >= 30);
            }
        };
        joystickLeft.setOnMoveListener(listener_j);

        View.OnClickListener listener_btn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game.attacking = true;
            }
        };
        btn.setOnClickListener(listener_btn);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        int UI_OPTIONS = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(UI_OPTIONS);
    }
}