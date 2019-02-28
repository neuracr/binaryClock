package team23.binaryclock;

import android.view.View;
import android.widget.RemoteViews;

import java.math.RoundingMode;
import java.util.ArrayList;

public class NumberFace {
    private int modulo;
    private int[] bits;
    private RemoteViews views;

    public NumberFace(int[] bits, int modulo, RemoteViews views){
        this.modulo = modulo;
        this.bits = bits;
        this.views = views;

        for (int id: bits){
            views.setBoolean(id, "setEnabled", false);
        }
    }
/*
    public void increment(){
        for (int i=bits.size()-1; i>=0; i--){
            if (!bits.get(i).isActivated()){
                bits.get(i).setActivated(true);
                break;
            }
            else{
                bits.get(i).setActivated(false);
            }
        }
    }
*/
    public void setNumber(int value){
        int i=7;
        while(i >= 0){
            if (value % 2 != 0){
                this.views.setBoolean(bits[i],"setEnabled",true);
            }
            else{
                this.views.setBoolean(bits[i],"setEnabled",false);
            }
            value /= 2;
            i--;
        }
    }
}
