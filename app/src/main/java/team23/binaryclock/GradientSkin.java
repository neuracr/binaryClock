package team23.binaryclock;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

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

        this.generateDrawable();
        this.generateBitmap();
    }


    public GradientSkin(Parcel in){
        super(60,60);
        this.height = in.readInt();
        this.width = in.readInt();

        this.colors = new int[]{0,0};
        this.shape = in.readInt();
        in.readIntArray(this.colors);
        this.gradientType = in.readInt();

        this.generateDrawable();
        this.generateBitmap();
    }

    private void generateDrawable(){
        this.drawable = new GradientDrawable();
        this.drawable.setColors(this.colors);
        this.drawable.setShape(this.shape);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(height);
        dest.writeInt(width);

        dest.writeInt(shape);
        Log.i("GradientSkin","length of colors:"+colors.length);
        dest.writeIntArray(colors);
        dest.writeInt(gradientType);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public GradientSkin createFromParcel(Parcel in) {
            return new GradientSkin(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[0];
        }

    };


    @Override
    public int describeContents(){
        return 0;
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
