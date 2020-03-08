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
        test = findViewById(R.id.imageView2);

        //test.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.dog_smiling));
        //test.callOnClick();
    }

    public void addDogSticker(View view) {
        canvas.addDogSticker(view);
    }

    public void addSunSticker(View view) {
        canvas.addSunSticker(view);
    }

    public void clearStickers(View view) {
        canvas.clearStickers(view);
    }
}
