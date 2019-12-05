package com.fcpc.chibogservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Timer;
import java.util.TimerTask;

public class SecondActivity extends AppCompatActivity {

    ViewPager viewPager;

    FloatingActionButton fabOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fabOrder = findViewById(R.id.fabGoOrder);


        fabOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(getApplicationContext(),FoodOrderList.class));
            }
        });

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        Timer timer = new Timer();;
        timer.scheduleAtFixedRate(new MytimerTask(), 2000, 4000);

    }

    public class MytimerTask extends TimerTask{

        @Override
        public void run() {

            SecondActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    } else if (viewPager.getCurrentItem() == 2){
                        viewPager.setCurrentItem(3);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });

        }
    }


}
