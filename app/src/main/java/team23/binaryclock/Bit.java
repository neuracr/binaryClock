package team23.binaryclock;

import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;

public class Bit {
    private BitSkin skinOn;
    private BitSkin skinOff;

    public Bit(){
        //TODO: default to gradient skin?
        this.skinOn = new GradientSkin(GradientDrawable.RECTANGLE, new int[] {0xFF55FF33,0x00,0xFF55FF33}, GradientDrawable.SWEEP_GRADIENT,60,60);
        this.skinOff = new GradientSkin(GradientDrawable.OVAL, new int[] {0xFF550033, 0x00,0xFFFF0000}, GradientDrawable.SWEEP_GRADIENT, 60,60);
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
