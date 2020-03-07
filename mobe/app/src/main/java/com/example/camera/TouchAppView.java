package com.example.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class TouchAppView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float x, y;
    private boolean touching, drawingTouchDog, drawingTouchSun;
    //ArrayList<Bitmap> stickers = new ArrayList<>();
    Bitmap drawingDogBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog_smiling);
    Bitmap drawingSunBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sun);
    Bitmap scaledDog = Bitmap.createScaledBitmap(drawingDogBitmap, 400, 400, true);
    Bitmap scaledSun = Bitmap.createScaledBitmap(drawingSunBitmap, 200, 200, true);

    Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.brick);

//    HashMap<Bitmap, Integer> currentPicX = new HashMap<>();
//    HashMap<Bitmap, Integer> currentPicY = new HashMap<>();;
//    HashMap<Bitmap, Integer> currentPicWidth = new HashMap<>();;
//    HashMap<Bitmap, Integer> currentPicHeight = new HashMap<>();;
//    HashMap<Bitmap, Integer> currentPicOffsetX = new HashMap<>();;
//    HashMap<Bitmap, Integer> currentPicOffsetY = new HashMap<>();;
    int drawingpic_x = 0, drawingpic_y = 0;
    int drawingpic_x_sun = 1100, drawingpic_y_sun = 1900;
    int drawingPicOffset_x = 0, drawingPicOffset_y = 0;
    int drawingPicHeight = scaledDog.getHeight();
    int drawingPicWidth = scaledDog.getWidth();
    int sunPicHeight = scaledSun.getHeight();
    int sunPicWidth = scaledSun.getWidth();

    private boolean dogSticker;
    private boolean sunSticker;

    private Context c;
    public TouchAppView(Context context, AttributeSet attrs) {
        super(context, attrs);
        c = context;
        dogSticker = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //int offset = 100;

        if (dogSticker) {
           canvas.drawBitmap(scaledDog, drawingpic_x, drawingpic_y , paint);
        }
        if (sunSticker) {
            canvas.drawBitmap(scaledSun, drawingpic_x_sun, drawingpic_y_sun , paint);

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();

                touching = true;
                if ((x > drawingpic_x) && (x < drawingpic_x + drawingPicWidth)
                        && (y > drawingpic_y) && (y < drawingpic_y + drawingPicHeight)) {
                    Toast.makeText(c,(CharSequence) "Waf",Toast.LENGTH_SHORT).show();
                    drawingPicOffset_x = (int) x - drawingpic_x;
                    drawingPicOffset_y = (int) y - drawingpic_y;
                    drawingTouchDog = true;
                }
                else if ((x > drawingpic_x_sun) && (x < drawingpic_x_sun + sunPicWidth)
                        && (y > drawingpic_y_sun) && (y < drawingpic_y_sun + sunPicHeight)) {
                    Toast.makeText(c,(CharSequence) "Ouch",Toast.LENGTH_SHORT).show();
                    drawingPicOffset_x = (int) x - drawingpic_x_sun;
                    drawingPicOffset_y = (int) y - drawingpic_y_sun;
                    drawingTouchSun = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                touching = true;
                if (drawingTouchDog) {
                    drawingpic_x = (int) x-drawingPicOffset_x;
                    drawingpic_y = (int) y-drawingPicOffset_y;
                }
                else if (drawingTouchSun) {
                    drawingpic_x_sun = (int) x-drawingPicOffset_x;
                    drawingpic_y_sun = (int) y-drawingPicOffset_y;
                }
                break;
            case MotionEvent.ACTION_UP:
                   /* drawingpic_x=0;
                    drawingpic_y=0;*/
            default:
                drawingTouchSun = false;
                drawingTouchDog = false;
                touching = false;
        }
        invalidate();
        return true;
    }

    public void addDogSticker(View view, ImageView myDogSticker) {
        dogSticker = true;
//        int dogCount = 0;
        //Bitmap newDogSticker = BitmapFactory.decodeResource(getResources(), R.drawable.dog_smiling);
        //stickers.add(newDogSticker);
//        populateHashes();
//        dogCount++;
        Toast.makeText(c,(CharSequence) Float.toString(view.getX())+ " " + Float.toString(view.getY()),Toast.LENGTH_SHORT).show();

        invalidate();
    }

    public void addSunSticker(View view) {
        sunSticker = true;
        Toast.makeText(c,(CharSequence) Float.toString(view.getX())+ " " + Float.toString(view.getY()),Toast.LENGTH_SHORT).show();

       // Bitmap newSunSticker = BitmapFactory.decodeResource(getResources(), R.drawable.sun);
        //stickers.add(newSunSticker);
//        populateHashes();
//        sunCount++;

        invalidate();
    }

    public void clearStickers(View view) {
        dogSticker = false;
        sunSticker = false;
        invalidate();
    }

//    private void populateHashes(Bitmap b) {
//        currentPicX.put(b,0);
//        currentPicY.put(b,0);
//        currentPicHeight.put(b,b.getHeight());
//        currentPicWidth.put(b, b.getWidth());
//        currentPicOffsetX.put(b, 0);
//        currentPicOffsetY.put(b, 0);
//    }

}
