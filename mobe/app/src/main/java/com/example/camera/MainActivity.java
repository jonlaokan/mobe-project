package com.example.camera;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


public class
MainActivity extends AppCompatActivity {

    private StickerView canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        canvas = (StickerView) findViewById(R.id.sticker_canvas);
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
