package mobe.m2dl.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class FilterActivity extends AppCompatActivity implements View.OnTouchListener, SensorEventListener{

    private SensorManager sm;
    private FilterManager fm = new FilterManager();
    private float[] gyrValues;
    private float[] magnValues;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Intent intent = getIntent();
        imagePath = intent.getExtras().getString("photoPath");

        ImageView imageFiltered = findViewById(R.id.filtered);
        Bitmap photo = BitmapFactory.decodeFile(imagePath);
        imageFiltered.setImageBitmap(photo);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magnSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
        sm.registerListener(this, accSensor,SensorManager.SENSOR_DELAY_UI);
        sm.registerListener(this, magnSensor,SensorManager.SENSOR_DELAY_UI);
        Button LightBtn = findViewById(R.id.magnField);
        LightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageFiltered = findViewById(R.id.filtered);
                BitmapDrawable tmp = (BitmapDrawable)imageFiltered.getDrawable();
                int value = -1 * ((int)magnValues[0] - (int)magnValues[1]);
                imageFiltered.setImageBitmap(fm.applyLigthFilter(value, tmp.getBitmap()));
            }
        });
        Button accButton = findViewById(R.id.accelerometer);
        accButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageFiltered = findViewById(R.id.filtered);
                BitmapDrawable tmp = (BitmapDrawable)imageFiltered.getDrawable();
                imageFiltered.setImageBitmap(fm.applyColorFilter(gyrValues, tmp.getBitmap()));
            }
        });
        Button stickerButton = findViewById(R.id.stickers);
        stickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File output = new File(imagePath);
                OutputStream os = null;
                ImageView imageFiltered = findViewById(R.id.filtered);
                BitmapDrawable tmp = (BitmapDrawable)imageFiltered.getDrawable();
                Bitmap imageToBeSaved = tmp.getBitmap();
                try {
                    os = new FileOutputStream(output);
                    imageToBeSaved.compress(Bitmap.CompressFormat.PNG, 100, os);
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent myIntent = new Intent(FilterActivity.this, StickerActivity.class);
                myIntent.putExtra("photoPath", imagePath);
                FilterActivity.this.startActivity(myIntent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Sensor accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magnSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
        sm.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_UI);
        sm.registerListener(this, magnSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onStop() {
        sm.unregisterListener(this, sm.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER
        ));
        sm.unregisterListener(this, sm.getDefaultSensor(
                Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED
        ));
        super.onStop();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensor = event.sensor.getType();
        if (sensor == Sensor.TYPE_ACCELEROMETER) {
            gyrValues = event.values;
        }
        if (sensor == Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) {
            magnValues = event.values;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
