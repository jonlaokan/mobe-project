package com.example.camera;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class
MainActivity extends AppCompatActivity {

    private StickerView canvas;
    private ImageView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        canvas = (StickerView) findViewById(R.id.sticker_canvas);
        test = findViewById(R.id.dog);

        //test.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.dog_smiling));
        //test.callOnClick();
    }

    public void addDogSticker(View view) { canvas.addSticker(view, 0); }
    public void addSunSticker(View view) { canvas.addSticker(view, 1); }
    public void addRobotSticker(View view) {
        canvas.addSticker(view, 2);
    }
    public void addKidSticker(View view) { canvas.addSticker(view, 3); }
    public void addCTRSticker(View view) { canvas.addSticker(view, 4); }

    public void clearStickers(View view) {
        canvas.clearStickers(view);
    }
}
