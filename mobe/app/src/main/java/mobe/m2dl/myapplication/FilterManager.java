package mobe.m2dl.myapplication;

import android.graphics.Bitmap;
import android.util.Log;

import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ColorOverlaySubFilter;


public class FilterManager {
    static
    {
        System.loadLibrary("NativeImageProcessor");
    }

    private Filter brigthnessFilter;
    private Filter colorFilter;

    public FilterManager() {
        this.brigthnessFilter = new Filter();
        this.colorFilter = new Filter();
        BrightnessSubFilter bs = new BrightnessSubFilter(200);
        bs.setTag("brigthness");
        brigthnessFilter.addSubFilter(bs);
    }

    public Bitmap applyColorFilter(float values[], Bitmap image){
        colorFilter.clearSubFilters();
        Log.i("d","//"+values[0]+" "+values[1]+" "+values[2]+"///");
        ColorOverlaySubFilter cs = new ColorOverlaySubFilter(100, values[0]-10, values[1]-10, values[2]-10);
        colorFilter.addSubFilter(cs);
        Bitmap bitmap = image.copy(image.getConfig(), true);
        return colorFilter.processFilter(bitmap);
    }

    public Bitmap applyLigthFilter(int brigthness, Bitmap image){
        BrightnessSubFilter bs = (BrightnessSubFilter) brigthnessFilter.getSubFilterByTag("brigthness");
        bs.setBrightness(brigthness);
        Bitmap bitmap = image.copy(image.getConfig(), true);
        return brigthnessFilter.processFilter(bitmap);
    }

}
