package com.example.camera;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class StickerView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float x, y;
    private boolean touching;

    private ArrayList<Sticker> stickers = new ArrayList<>();

    private Context c;
    public StickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        c = context;
        loadStickers();
    }

    private void loadStickers(){
        Sticker dog;
        Sticker sun;
        dog = new Sticker(BitmapFactory.decodeResource(getResources(),
                //R.drawable.dog_smiling), 528, 1827 );
                R.drawable.dog_smiling), 200, 200 );

        sun = new Sticker(BitmapFactory.decodeResource(getResources(),
                //R.drawable.sun), 1000, 1800);
                R.drawable.sun), 400, 400);
        stickers.add(dog);
        stickers.add(sun);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawMenu(canvas);
        for (Sticker s : stickers) {
            drawSticker(canvas,s);
        }

    }

    private void drawMenu(Canvas canvas) {
        Sticker dogPinned = new Sticker(BitmapFactory.decodeResource(getResources(),R.drawable.dog_smiling), 1000, 200);
        canvas.drawBitmap(dogPinned.getScaledBitmap(), dogPinned.getxPos(), dogPinned.getyPos(), paint);
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

                for (Sticker s : stickers) {
                    dragSticker(s, (int) x, (int) y);
                }

                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                touching = true;

                for (Sticker s : stickers) {
                    dropSticker(s, (int) x, (int) y);
                }
                break;
            default:
                for (Sticker s : stickers) {
                    s.setTouched(false);
                }
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

    private void drawSticker(Canvas canvas,Sticker sticker) {
        if (sticker.shouldBeDrawn())
            canvas.drawBitmap(sticker.getScaledBitmap(), sticker.getxPos(), sticker.getyPos(), paint);
    }

    public void addDogSticker(View view) {
        stickers.get(0).setisWanted(true);
        invalidate();
    }

    public void addSunSticker(View view) {
        stickers.get(1).setisWanted(true);
        invalidate();
    }

    public void clearStickers(View view) {
        for (Sticker s : stickers) {
            s.reset();
        }
        invalidate();
    }
}
