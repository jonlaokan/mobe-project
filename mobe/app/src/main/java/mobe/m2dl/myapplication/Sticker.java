package mobe.m2dl.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class Sticker {

    private Bitmap originalBitmap;
    private Bitmap scaledBitmap;
    private int height;
    private int width;

    private int xPos = 0, yPos = 0;
    private int xOffset = 0, yOffset = 0;
    private int defaultx, defaulty;

    private boolean isTouched = false;
    public boolean isWanted = false;

    Sticker(Bitmap bitmap, int firstx, int firsty) {
        setOriginalBitmap(bitmap);
        defaultx = firstx;
        defaulty = firsty;

        setxPos(defaultx);
        setyPos(defaulty);

       setScaledBitmap(Bitmap.createScaledBitmap(bitmap, 200, 200, true));
       setHeight(scaledBitmap.getHeight());
       setWidth(scaledBitmap.getWidth());
    }

    public Bitmap getOriginalBitmap() {
        return originalBitmap;
    }

    public void setOriginalBitmap(Bitmap originalBitmap) {
        this.originalBitmap = originalBitmap;
    }

    public Bitmap getScaledBitmap() {
        return scaledBitmap;
    }

    public void setScaledBitmap(Bitmap scaledBitmap) {
        this.scaledBitmap = scaledBitmap;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getxOffset() {
        return xOffset;
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isTouched() {
        return isTouched;
    }


    public void setTouched(boolean touched) {
        isTouched = touched;
    }

    public String shoutPosition() {
        return "I am at " + getxPos() + "," + getyPos();
    }

    public void setisWanted(boolean choice) {
        isWanted = choice;
    }

    public boolean shouldBeDrawn() {
        return isWanted;
    }

    public void reset() {
        setxPos(defaultx);
        setyPos(defaulty);
        setTouched(false);
        setisWanted(false);
    }



}
