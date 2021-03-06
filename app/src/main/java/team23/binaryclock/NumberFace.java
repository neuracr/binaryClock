package team23.binaryclock;


public class NumberFace {
    private int modulo;
    private boolean[] bits; //bits[i] -> bit of power i
    private int value;

    public NumberFace(int size, int modulo){
        this.modulo = modulo;
        this.bits = new boolean[size];
        this.value = 0;

        for (int id=0; id < bits.length ; id++){
            bits[id] = false;
        }
    }

    public void setNumber(int value){
        int i=0;
        this.value = value;
        while(i < 8){
            if (value % 2 != 0){
                this.bits[i] = true;
            }
            else{
                this.bits[i] = false;
            }
            value /= 2;
            i++;
        }
    }

    public int getValue(){
        return this.value;
    }

    public boolean getBit(int position){
        //Log.i("NumberFace", "getBit "+position);
        if (position > bits.length){
            return false;
        }
        return bits[position];
    }
}
