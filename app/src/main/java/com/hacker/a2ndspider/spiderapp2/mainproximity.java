package com.hacker.a2ndspider.spiderapp2;

/**
 * Created by hacker on 2/7/16.
 */
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;


public class mainproximity extends AppCompatActivity implements SensorEventListener  {
    ImageView slidingimage;
    SensorManager mSensorManager;
    Sensor mSensor;
    public int currentimageindex = 0;


    private int[] IMAGE_IDS = {
            R.drawable.flash, R.drawable.daredevil,R.drawable.joker
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_proximity);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        slidingimage=(ImageView)findViewById(R.id.slidingimage);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {

        super.onPause();
        mSensorManager.unregisterListener( this);

    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



    public void onSensorChanged(SensorEvent event) {


        if (event.values[0] == 0) {

            slidingimage.setImageResource(IMAGE_IDS[currentimageindex % IMAGE_IDS.length]);

        } else  {

            slidingimage.setImageResource(IMAGE_IDS[currentimageindex % IMAGE_IDS.length]);

        }

        currentimageindex++;

    }
}
