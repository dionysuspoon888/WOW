package com.example.wow;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseActivity {


//Animation Background
private LinearLayout mainLayout;
private AnimationDrawable animDrawable;


//Image Slider
private ViewPager viewPager;
private CustomSwipeAdapter adapter;

//Dots Indicator
private LinearLayout sliderDotspanel;
private int dotscount;
private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        //Animation Background
        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        animDrawable = (AnimationDrawable) mainLayout.getBackground();
        animDrawable.setEnterFadeDuration(5500);
        animDrawable.setExitFadeDuration(5500);
        animDrawable.start();


        //Background service
        if(MainService.mCount == 0) {
            startService(new Intent(this, MainService.class));
            MainService.mCount=1;
        }

        //Image Slider
     viewPager =findViewById(R.id.view_pager);
     adapter = new CustomSwipeAdapter(this);
     viewPager.setAdapter(adapter);

        //Dots Indicator
        sliderDotspanel =(LinearLayout)findViewById(R.id.SliderDots);
        dotscount = adapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8,0,8,0);

            sliderDotspanel.addView(dots[i],params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0;i < dotscount;i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));

                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));

            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //Auto sliding
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MytimerTask(),2000,4000);


    }

    // Auto Sliding
    public class MytimerTask extends TimerTask{


        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int x=viewPager.getCurrentItem();
                    int y=adapter.getCount();

                    if(x == y - 1){
                        viewPager.setCurrentItem(0);
                    }else{

                        viewPager.setCurrentItem(x+1);
                    }

                }
            });
        }

        }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



}
