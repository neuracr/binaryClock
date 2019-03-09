package team23.binaryclock;

import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;

public class Bit {
    private BitSkin skinOn;
    private BitSkin skinOff;

    public Bit(){
        //TODO: default to gradient skin?
        this.skinOn = new GradientSkin(GradientDrawable.OVAL, new int[] {0xFFFFFFFF,0xFFFFFFFF,0xFFAAAAAA}, GradientDrawable.SWEEP_GRADIENT,60,60);
        this.skinOff = new GradientSkin(GradientDrawable.OVAL, new int[] {0xFF000000, 0xFF333333}, GradientDrawable.SWEEP_GRADIENT, 60,60);
    }

    public Bitmap getBitmap(boolean on){
        if (on)
            return this.skinOn.getBitmap();
        return this.skinOff.getBitmap();
    }

    public void setSkin(BitSkin skin, boolean on){
        if (on){
            this.skinOn = skin;
        }
        else{
            this.skinOff = skin;
        }
    }
}
