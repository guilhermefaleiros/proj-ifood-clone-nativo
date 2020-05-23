package com.guifaleiros.ifood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.guifaleiros.ifood.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openAuth();
            }
        }, 3000);
    }

    private void openAuth(){
        Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

}
