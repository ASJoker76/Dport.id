package com.app.dportshipper.view.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.app.dportshipper.R;

public class SplashScreenActivity extends AppCompatActivity {

    private int DELAYED = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setInitSplashScreen();
    }

    private void setInitSplashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                SharedPreferences prefs = getBaseContext().getSharedPreferences("intro", Context.MODE_PRIVATE);
//                int skip_intro = prefs.getInt("skip_intro",0);
//                if(skip_intro==1){
                    startActivity(new Intent(SplashScreenActivity.this, LoginRegisterActivity.class));
                    finish();
//                }
//                else{
////                    startActivity(new Intent(SplashScreenActivity.this, IntroActivity.class));
////                    finish();
//                }
            }
        }, DELAYED);
    }


}