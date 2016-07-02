package com.hacker.a2ndspider.spiderapp2;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.os.Handler;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity  {

    Thread player;
    ArrayAdapter<String> stringArrayAdapter;
    public static final int REQUEST_CODE = 1;
    public static final String MODE = "MODE";
    static MediaPlayer mediaPlayer;
    Spinner spinner;
    Button enable,disable,plays;
    boolean mode = true;
    boolean state=false;
    public int count = 0, ctr = 0;
    public int currentimageindex = 0;


    private int[] IMAGE_IDS = {
            R.drawable.flash, R.drawable.daredevil,R.drawable.joker
    };
    TextView myTextView;

    ImageView slidingimage;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mediaPlayer = MediaPlayer.create(this,R.raw.hymm);
        plays = (Button) findViewById(R.id.starts);
        String[] song = {"Hymm for the weekend", "Tamil fever", "Writing's on the wall"};
        stringArrayAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        song);


        spinner= (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.
                OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id)
            {
                if (state)
                {
                    state = false;
                    mediaPlayer.release();

                }

                if (position == 0) {
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.hymm);
                } else if (position == 1) {
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.tamil);
                } else if (position == 2) {
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.wall);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.hymm);


            }
        });

}


    public void onStart(final View view) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (count <= 3)
                                    updateTextView();

                            }
                        });
                    }
                } catch (InterruptedException e) {

                }
            }
        };
        t.start();
        final Handler mHandler = new Handler();
        final Runnable mUpdateResults = new Runnable() {
            public void run() {
                ctr = 0;
                if (count <= 3) {
                    count++;
                    SlideShow(view);
                }
            }

        };
        int delay = 1000;
        int period = 5000;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {

                mHandler.post(mUpdateResults);
            }

        }, delay, period);
    }
    private void updateTextView() {
        myTextView=(TextView)findViewById(R.id.mytextView);
        ctr++;
        myTextView.setText(" " + ctr);
    }




    private void SlideShow(View view) {

        slidingimage = (ImageView) findViewById(R.id.imageview);
        slidingimage.setImageResource(IMAGE_IDS[currentimageindex % IMAGE_IDS.length]);
        currentimageindex++;
        if(!state)
            play(view);
    }



    public void play(View view) {

        if(!state)
        {
            state = true;
            player = new Thread(){
                @Override
                public void run(){
                    while(state) {
                        mediaPlayer.start();
                    }
                    mediaPlayer.pause();
                }

            };

            player.start();


        }

        else{

            state = false;
        }



    }

    public void proximity(View view)
    {
        Intent i= new Intent(this,mainproximity.class);
        startActivity(i);
        if(!state)
            play(view);
    }






}

