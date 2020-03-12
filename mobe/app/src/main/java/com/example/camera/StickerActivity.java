package com.example.camera;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class
StickerActivity extends AppCompatActivity {

    private StickerView stickerView;
    private int imageID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        stickerView = (StickerView) findViewById(R.id.sticker_canvas);
    }

    public void saveCanvas(View view) {

        Bitmap imageToBeSaved = getBitmapFromView(stickerView);

        File dir = new File("/sdcard/myPictures");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File output = new File(dir, "IMG" + Integer.toString(imageID) + ".jpg");
        imageID++;
        OutputStream os = null;

        try {
            os = new FileOutputStream(output);
            imageToBeSaved.compress(Bitmap.CompressFormat.JPEG, 100, os);
            scanImage(output);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.layout(0, 0, view.getWidth(), view.getHeight());
        view.draw(canvas);
        return bitmap;
    }

    public void showToast(final String toast) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(StickerActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void scanImage(File output) {
        MediaScannerConnection.scanFile(getApplicationContext(), new String[] { output.toString() }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        showToast("Image saved in gallery!");
                    }
                });
    }

    public void addDogSticker(View view) { stickerView.addSticker(view, 0); }
    public void addSunSticker(View view) { stickerView.addSticker(view, 1); }
    public void addRobotSticker(View view) {
        stickerView.addSticker(view, 2);
    }
    public void addKidSticker(View view) { stickerView.addSticker(view, 3); }
    public void addCTRSticker(View view) { stickerView.addSticker(view, 4); }

    public void clearStickers(View view) {
        stickerView.clearStickers(view);
    }
}
