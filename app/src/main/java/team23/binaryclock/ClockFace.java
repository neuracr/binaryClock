package team23.binaryclock;

import team23.binaryclock.NumberFace;
import android.content.Context;

/**
 * Defines the main face of the clock, containing some numbers
 */
public class ClockFace{
    private Context context;
    private NumberFace h;
    private NumberFace m;
    private NumberFace s;

    public ClockFace(){
        this.context = context;
        h = new NumberFace();
        //m = new NumberFace();
        //s = new NumberFace();
    }
}
