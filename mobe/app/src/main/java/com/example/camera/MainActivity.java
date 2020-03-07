package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.text.method.Touch;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class
MainActivity extends AppCompatActivity {

//    private CanvasView customCanvas;
//    private ImageView dogImage;
//    private LinearLayout backView;
    private TouchAppView canvas;
    private ImageView dogSticker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        canvas = (TouchAppView) findViewById(R.id.sticker_canvas);

//      canvas = findViewById(R.id.sticker_canvas);

//        customCanvas =    findViewById(R.id.signature_canvas);
//        backView = findViewById(R.id.layoutTL);
//        findViewById(R.id.layoutTL).setOnDragListener(new MyDragListener());
//        findViewById(R.id.layoutT).setOnDragListener(new MyDragListener());
//        findViewById(R.id.layoutTR).setOnDragListener(new MyDragListener());
//        findViewById(R.id.imageView2).setOnTouchListener(new MyTouchListener());
//        dogImage = findViewById(R.id.imageView2);
    }

    public void addDogSticker(View view) {
        canvas.addDogSticker(view, dogSticker);
    }

    public void addSunSticker(View view) {
        canvas.addSunSticker(view);
    }

    public void clearStickers(View view) {
        canvas.clearStickers(view);
    }
    // This defines your touch listener
//    private final class MyTouchListener implements View.OnTouchListener {
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                ClipData data = ClipData.newPlainText("", "");
//                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
//                        view);
//                view.startDrag(data, shadowBuilder, view, 0);
//                view.setVisibility(View.INVISIBLE);
//                dogImage.setVisibility(View.VISIBLE);
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }
//
//    class MyDragListener implements View.OnDragListener {
//        Drawable enterShape = getResources().getDrawable(
//                R.drawable.square);
//        Drawable normalShape = getResources().getDrawable(R.drawable.dog_smiling);
//
//        @Override
//        public boolean onDrag(View v, DragEvent event) {
//            int action = event.getAction();
//            switch (event.getAction()) {
//                case DragEvent.ACTION_DRAG_STARTED:
//                    // do nothing
//                    break;
//                case DragEvent.ACTION_DRAG_ENTERED:
//                  //  v.invalidate();
//                    v.setBackgroundDrawable(enterShape);
//                    break;
//                case DragEvent.ACTION_DRAG_EXITED:
//                   // v.invalidate();
//                    v.setBackgroundDrawable(null);
//                    break;
//                case DragEvent.ACTION_DROP:
//                    // Dropped, reassign View to ViewGroup
//                    View view = (View) event.getLocalState();
//                    ViewGroup owner = (ViewGroup) view.getParent();
//                    owner.removeView(view);
//                    LinearLayout container = (LinearLayout) v;
//                    container.addView(view);
//                    view.setVisibility(View.VISIBLE);
//                    break;
//                case DragEvent.ACTION_DRAG_ENDED:
//                    //v.setBackgroundDrawable(normalShape);
//                default:
//                    break;
//            }
//            return true;
//        }
//    }
////    public void clearCanvas(View v) {
////        customCanvas.clearCanvas();
////    }
//
//    public void loadSticker(View v) {
//        if (dogImage == null) {
//            System.exit(1);
//        }
//        //RectF rect = new RectF();
//        //ect.set(1,1,1,1);
//        //Bitmap mBitmap2 = BitmapFactory.decodeFile("/home/jonlaokan/Code/mobe/mobe-project/mobe/app/src/main/res/drawable/dog_smiling.png");
//        dogImage.setImageResource(R.drawable.dog_smiling);
//    }
}
