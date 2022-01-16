package com.example.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.buttonStart);
        button.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent switcher = new Intent(MainActivity.this,Game.class);
                MainActivity.this.startActivity(switcher);
            }});
    }}


