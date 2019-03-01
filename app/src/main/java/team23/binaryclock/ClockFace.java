package team23.binaryclock;

import android.util.Log;

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
            if (i/2 == 0) {
                row.add(new NfBitTuple(this.h, 7-(i%2)*4));
                row.add(new NfBitTuple(this.h, 6-(i%2)*4));
                row.add(new NfBitTuple(this.h, 5-(i%2)*4));
                row.add(new NfBitTuple(this.h, 4-(i%2)*4));
            }
            else if(i/2 == 1){
                row.add(new NfBitTuple(this.m, 7-(i%2)*4));
                row.add(new NfBitTuple(this.m, 6-(i%2)*4));
                row.add(new NfBitTuple(this.m, 5-(i%2)*4));
                row.add(new NfBitTuple(this.m, 4-(i%2)*4));
            }
            else if(i/2 == 2){
                row.add(new NfBitTuple(this.s, 7-(i%2)*4));
                row.add(new NfBitTuple(this.s, 6-(i%2)*4));
                row.add(new NfBitTuple(this.s, 5-(i%2)*4));
                row.add(new NfBitTuple(this.s, 4-(i%2)*4));
            }
            this.bitGrid.add(row);
        }

        //Log.i("ClockFace", "number of rows:" + table.getChildCount());
        //Log.i("ClockFace", "number of rows:" + ((TableRow)table.getChildAt(0)).getChildCount());

        //ArrayList<View> column = getColumn(0);
        //column.addAll(getColumn(1));
        //this.h = new NumberFace(h_ids, 24, views);
        //column = getColumn(2);
        //column.addAll(getColumn(3));
        //this.m = new NumberFace(m_ids, 60, views);
        //column = getColumn(4);
        //column.addAll(getColumn(5));
        //this.s = new NumberFace(s_ids, 60, views);
    }
/*
    public ClockFace(TableLayout table){
        this.table = table;
        Log.i("ClockFace", "number of rows:" + table.getChildCount());
        Log.i("ClockFace", "number of rows:" + ((TableRow)table.getChildAt(0)).getChildCount());

        ArrayList<View> column = getColumn(0);
        column.addAll(getColumn(1));
        this.h = new NumberFace(column, 24);
        column = getColumn(2);
        column.addAll(getColumn(3));
        this.m = new NumberFace(column, 60);
        column = getColumn(4);
        column.addAll(getColumn(5));
        this.s = new NumberFace(column, 60);
        s.increment();
        s.increment();
        s.increment();
        s.increment();
        m.increment();
    }

    private ArrayList<View> getColumn(int number){
        ArrayList<View> column = new ArrayList<View>();
        for (int i=0 ; i < this.table.getChildCount() ; i++){
            column.add(((TableRow)this.table.getChildAt(i)).getChildAt(number));
        }
        return column;
    }
*/
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
        Log.i("ClockFace","getValue " + x + ", " + y + " val:" + tuple.getNf().getValue());

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
