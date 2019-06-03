package com.acampdev.apprealm;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashEndActivity extends AppCompatActivity {
    private static final long SPLASH_SCREEN_DELAY=3500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_end);
        SplashScreen();
    }
    private  void SplashScreen(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashEndActivity.this,LoginActivity.class));
                finish();
            }
        },SPLASH_SCREEN_DELAY);
    }
}
