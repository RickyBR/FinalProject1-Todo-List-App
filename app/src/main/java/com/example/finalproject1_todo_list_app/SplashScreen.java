package com.example.finalproject1_todo_list_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {
    Animation app_splash, btt;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        logo = findViewById(R.id.logo);
        logo.startAnimation(app_splash);
        Intent i = new Intent(SplashScreen.this,MainActivity.class);
        new Handler().postDelayed(() -> {
            startActivity(i);
            finish();
        },1000);
    }
}