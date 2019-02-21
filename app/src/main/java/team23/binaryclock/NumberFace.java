package team23.binaryclock;

import android.view.View;

import java.util.ArrayList;

public class NumberFace {
    private int modulo;
    private ArrayList<View> bits;

    public NumberFace(ArrayList<View> bits, int modulo){
        this.modulo = modulo;
        this.bits = bits;

        for (View v: bits){
            v.setActivated(false);
        }
    }

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
}
