package team23.binaryclock;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Defines the main face of the widget_clock, containing some numbers
 */
public class ClockFace{
    private ArrayList<ArrayList<NfBitTuple>> bitGrid;
    private NumberFace h;
    private NumberFace m;
    private NumberFace s;

    public ClockFace(){
        this.h = new NumberFace(8,24);
        this.m = new NumberFace(8,60);
        this.s = new NumberFace(8,60);

        this.bitGrid = new ArrayList<>(6);
        for (int i=0; i < 6 ; i++){
            ArrayList<NfBitTuple> row = new ArrayList<NfBitTuple>(4);
            switch(i/2) {
                case(0):
                    row = this.addRows(row, i, this.h);
                    break;
                case(1):
                    row = this.addRows(row, i, this.m);
                    break;
                case(2):
                    row = this.addRows(row, i, this.s);
                    break;
            }
            this.bitGrid.add(row);
        }

    }

    /* Factorised Method to add bit tuple in an Array */
    private ArrayList<NfBitTuple> addRows(ArrayList<NfBitTuple> row, int i, NumberFace timeValue) {
        row.add(new NfBitTuple(timeValue, 7-(i%2)*4));
        row.add(new NfBitTuple(timeValue, 6-(i%2)*4));
        row.add(new NfBitTuple(timeValue, 5-(i%2)*4));
        row.add(new NfBitTuple(timeValue, 4-(i%2)*4));
    }

    public void setTime(){
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int min = now.get(Calendar.MINUTE);
        int sec = now.get(Calendar.SECOND);

        this.h.setNumber(hour);
        this.m.setNumber(min);
        this.s.setNumber(sec);

    }

    public boolean get(int x, int y){
        if (x < 0 || x > 6 || y < 0 || y > 4){
            return false;
        }
        NfBitTuple tuple = this.bitGrid.get(x).get(y);

        return tuple.getNf().getBit(tuple.getBit());
    }

    class NfBitTuple{
        private int bit;
        private NumberFace nf;
        public NfBitTuple(NumberFace nf, int bit){
            this.nf = nf;
            this.bit = bit;
        }

        public int getBit() {
            return bit;
        }

        public NumberFace getNf() {
            return nf;
        }
    }

}
