package team23.binaryclock;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class GradientSkin extends BitSkin {
    private int shape;
    private int colors[];
    private int gradientType;
    private GradientDrawable drawable;

    public GradientSkin(int shape, int colors[], int gradientType, int width, int height){
        super(height, width);
        this.shape = shape;
        this.colors = (colors.length == 1) ? new int[]{colors[0], colors[0]} : colors;
        this.gradientType = gradientType;

        this.drawable = new GradientDrawable();
        this.drawable.setColors(this.colors);
        this.drawable.setShape(this.shape);
        //this.drawable.setGradientType(this.gradientType);

        this.generateBitmap();
    }

    public void setShape(int shape) {
        this.shape = shape;
        this.generateBitmap();
    }

    public void setColors(int[] colors) {
        this.colors = colors;
        this.generateBitmap();
    }

    public void setGradientType(int gradientType) {
        this.gradientType = gradientType;
        this.generateBitmap();
    }

    private void generateBitmap(){
        this.bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(this.bitmap);
        this.drawable.setBounds(0, 0, this.width, this.height);
        this.drawable.draw(canvas);
    }
}
