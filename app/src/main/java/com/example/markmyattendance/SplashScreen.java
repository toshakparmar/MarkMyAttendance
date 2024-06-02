package com.example.markmyattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    ImageView logo;
    TextView title, copyright;
    Animation top,middle,bottom;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo = (ImageView) findViewById(R.id.logo);
        title = (TextView) findViewById(R.id.title);
        copyright = (TextView) findViewById(R.id.copyright);

        top= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top);
        middle= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.middle);
        bottom= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bottom);

        logo.setAnimation(top);
        title.setAnimation(middle);
        copyright.setAnimation(bottom);

        Intent intent = new Intent(SplashScreen.this,MainActivity.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        },3000);

    }
}