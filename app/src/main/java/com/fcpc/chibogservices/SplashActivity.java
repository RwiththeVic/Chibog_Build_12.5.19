package com.fcpc.chibogservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends AppCompatActivity {

    private Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        h = new Handler(this.getMainLooper());

        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent goMain = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(goMain);
                finish();

            }
        },1500);

    }

    /**
     * TODO: Check for internet connection
     *
     * @return
     */
    private boolean hasInternetConnection(){

        return true;
    }
}
