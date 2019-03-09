package team23.binaryclock;

import android.graphics.Bitmap;
import android.os.Parcelable;

public abstract class BitSkin implements Parcelable {
    protected Bitmap bitmap;
    protected int height;
    protected int width;

    public BitSkin(int height, int width){
        this.height = height;
        this.width = width;
        this.bitmap = null;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

}
