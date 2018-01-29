package com.example.wow;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MusicPlayer extends BaseActivity {
    //List & MusicPlayer
    ListView listView;
    List<String> list;
    ListAdapter adapter;

    MediaPlayer mediaplayer;

    //SeekBar
    private SeekBar seekBar_Music;
    private double startTime, endTime;
    private int mseekBar;
    private Handler handler = new Handler();


    //Controlled Buttons
    private FloatingActionButton button_Music_main, button_Music_back, button_Music_forward;
    private int mbuttonMain, mPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musicplayer);

        if (MainService.mCount == 1) {
            stopService(new Intent(this, MainService.class));
            MainService.mCount = 0;
        }


        //Basic Setting of ListView

        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();

        Field[] fields = R.raw.class.getFields();
        for (int i = 0; i < fields.length; i++) {
            list.add(fields[i].getName());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        //Select the song by clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mediaplayer != null) {
                    mediaplayer.release();
                    mseekBar = 0;
                }


                int resID = getResources().getIdentifier(list.get(i), "raw", getPackageName());
                mediaplayer = MediaPlayer.create(MusicPlayer.this, resID);
                startMusic();
                mPosition = i;

            }
        });

        //SeekBar Setting
        seekBar_Music = (SeekBar) findViewById(R.id.seekBar_Music);
        seekBar_Music.setClickable(true);


        //Button Setting


        button_Music_main = (FloatingActionButton) findViewById(R.id.button_Music_main);
        button_Music_back = (FloatingActionButton) findViewById(R.id.button_Music_back);
        button_Music_forward = (FloatingActionButton) findViewById(R.id.button_Music_forward);

        //Start/Pause Music by button
        button_Music_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mbuttonMain == 0) {
                    if (mediaplayer == null) {
                        int resID = getResources().getIdentifier(list.get(0), "raw", getPackageName());
                        mediaplayer = MediaPlayer.create(MusicPlayer.this, resID);
                        startMusic();
                    } else {
                        startMusic();
                    }

                } else if (mbuttonMain == 1) {
                    pauseMusic();
                }
            }
        });

        //Start Music backward
        button_Music_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPosition == 0) {
                    if (mediaplayer != null) {
                        mediaplayer.release();
                        mseekBar = 0;
                    }
                    int resID = getResources().getIdentifier(list.get(0), "raw", getPackageName());
                    mediaplayer = MediaPlayer.create(MusicPlayer.this, resID);

                    startMusic();

                } else {
                    if (mediaplayer != null) {
                        mediaplayer.release();
                        mseekBar = 0;
                    }
                    int resID = getResources().getIdentifier(list.get(mPosition - 1), "raw", getPackageName());
                    mediaplayer = MediaPlayer.create(MusicPlayer.this, resID);
                    startMusic();
                    mPosition--;
                }

            }
        });

        //Start Music forward
        button_Music_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mPosition == listView.getAdapter().getCount() - 1) {

                    if (mediaplayer != null) {
                        mediaplayer.release();
                        mseekBar = 0;
                    }
                    int resID = getResources().getIdentifier(list.get(listView.getAdapter().getCount() - 1), "raw", getPackageName());
                    mediaplayer = MediaPlayer.create(MusicPlayer.this, resID);
                    startMusic();

                } else {

                    if (mediaplayer != null) {
                        mediaplayer.release();
                        mseekBar = 0;
                    }
                    int resID = getResources().getIdentifier(list.get(mPosition + 1), "raw", getPackageName());
                    mediaplayer = MediaPlayer.create(MusicPlayer.this, resID);
                    startMusic();
                    mPosition++;

                }

            }
        });


    }

    public void startMusic() {
        if (mediaplayer != null) {
            mediaplayer.start();
            button_Music_main.setImageResource(R.drawable.ic_pause_black_24dp);
            mbuttonMain = 1;
            SeekBarProgress();
        }
    }

    public void pauseMusic() {
        if (mediaplayer != null) {
            mediaplayer.pause();
            button_Music_main.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            mbuttonMain = 0;
            SeekBarProgress();
        }

    }

    public void SeekBarProgress() {
        if (mediaplayer != null) {
            startTime = mediaplayer.getCurrentPosition();
            endTime = mediaplayer.getDuration();
            if (mseekBar == 0) {
                seekBar_Music.setMax((int) endTime);
                seekBar_Music.setProgress(0);
                mseekBar = 1;
            }
            seekBar_Music.setProgress((int) startTime);

            handler.postDelayed(updatedProgress, 1000);

            //Control music playing by clicking/pulling seekBar
            seekBar_Music.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser == true) {
                        mediaplayer.seekTo(progress);
                        startMusic();
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    mediaplayer.pause();
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mediaplayer.start();
                }
            });


        }

    }

    public Runnable updatedProgress = new Runnable() {
        @Override
        public void run() {

            startTime = mediaplayer.getCurrentPosition();
            seekBar_Music.setProgress((int) startTime);

            handler.postDelayed(this, 1000);

        }
    };



    @Override
    public void onPause() {
        super.onPause();
        if (mediaplayer != null) {

            mediaplayer.pause();
        }
    }


}
