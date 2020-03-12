package mobe.m2dl.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.graphics.Bitmap;
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


public class FilterActivity extends AppCompatActivity implements View.OnTouchListener, SensorEventListener{

    private SensorManager sm;
    private FilterManager fm = new FilterManager();
    private float[] gyrValues;
    private float[] magnValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        // permet de restreindre le niveau de sÃ©curitÃ© (entre autre l'accÃ©s Ã  des fichiers)
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        // affiche une popup si l'utilisateur doit accepter manuellement une permission
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        setContentView(R.layout.filter_activity);

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

        File photo = new File(Environment.getExternalStorageDirectory()+"/tmp/", "Pic.jpg");

        Uri selectedImage = Uri.fromFile(photo);
        getContentResolver().notifyChange(selectedImage, null);
        ImageView imageFiltered = findViewById(R.id.filtered);

        ContentResolver cr = getContentResolver();
        try {
            Bitmap bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, selectedImage);
            imageFiltered.setImageBitmap(bitmap);
            Sensor accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            Sensor magnSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
            sm.registerListener(this, accSensor,SensorManager.SENSOR_DELAY_UI);
            sm.registerListener(this, magnSensor,SensorManager.SENSOR_DELAY_UI);
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
            Log.e("Camera", e.toString());
        }

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
