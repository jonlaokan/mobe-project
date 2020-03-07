package com.example.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class TouchAppView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float x, y;
    private boolean touching;

    private Sticker dog;
    private Sticker sun;


    private Context c;
    public TouchAppView(Context context, AttributeSet attrs) {
        super(context, attrs);
        c = context;
        loadStickers();
    }

    private void loadStickers(){
        dog = new Sticker(BitmapFactory.decodeResource(getResources(),
                R.drawable.dog_smiling), 528, 1827 );

        sun = new Sticker(BitmapFactory.decodeResource(getResources(),
                R.drawable.sun), 1000, 1800);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (dog.shouldBeDrawn()) {
           canvas.drawBitmap(dog.getScaledBitmap(), dog.getxPos(), dog.getyPos(), paint);
        }
        if (sun.shouldBeDrawn()) {
            canvas.drawBitmap(sun.getScaledBitmap(), sun.getxPos(), sun.getyPos() , paint);
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

                dragSticker(dog, (int) x, (int) y);
                dragSticker(sun, (int) x, (int) y);
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                touching = true;

                dropSticker(dog, (int) x, (int) y);
                dropSticker(sun, (int) x, (int) y);
                break;
            default:
                dog.setTouched(false);
                sun.setTouched(false);
                touching = false;
        }
        // Redraw the canvas
        invalidate();
        return true;
    }

    private void dragSticker(Sticker sticker, int x , int y) {
        int xPos = sticker.getxPos();
        int yPos = sticker.getyPos();

        if ((x > xPos) && (x < xPos + sticker.getWidth())
                && (y > sticker.getyPos()) && (y < sticker.getyPos() + sticker.getHeight())) {
            //Toast.makeText(c,(CharSequence) sticker.shoutPosition(),Toast.LENGTH_SHORT).show();
            sticker.setxOffset(x - xPos);
            sticker.setyOffset(y - yPos);
            sticker.setTouched(true);
        }
    }

    private void dropSticker(Sticker sticker, int x, int y) {
        if (sticker.isTouched()) {
            sticker.setxPos(x - sticker.getxOffset());
            sticker.setyPos(y - sticker.getyOffset());
        }
    }

    public void addDogSticker(View view) {
        dog.setisWanted(true);
        invalidate();
    }

    public void addSunSticker(View view) {
        sun.setisWanted(true);
        invalidate();
    }

    public void clearStickers(View view) {
        dog.reset();
        sun.reset();
        invalidate();
    }
}
