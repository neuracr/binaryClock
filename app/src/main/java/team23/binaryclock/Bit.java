package team23.binaryclock;

import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;

public class Bit {
    private BitSkin skinOn;
    private BitSkin skinOff;

    public Bit(){
        this.skinOn = new GradientSkin(GradientDrawable.RECTANGLE, new int[] {0xFF55FF33,0x00}, GradientDrawable.SWEEP_GRADIENT,60,60);
        this.skinOff = new GradientSkin(GradientDrawable.OVAL, new int[] {0xFF550033, 0x00}, GradientDrawable.SWEEP_GRADIENT, 60,60);
    }

    public Bitmap getBitmap(boolean on){
        if (on)
            return this.skinOn.getBitmap();
        return this.skinOff.getBitmap();
    }
}
