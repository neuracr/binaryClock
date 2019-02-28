package team23.binaryclock;

import team23.binaryclock.NumberFace;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RemoteViews;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Defines the main face of the widget_clock, containing some numbers
 */
public class ClockFace{
    private Context context;
    /*private NumberFace h;
    private int[] h_ids = {R.id.d00, R.id.d10, R.id.d20, R.id.d30, R.id.d01, R.id.d11, R.id.d21, R.id.d31};
    private NumberFace m;
    private int[] m_ids = {R.id.d02, R.id.d12, R.id.d22, R.id.d32, R.id.d03, R.id.d13, R.id.d23, R.id.d33};
    private NumberFace s;
    private int[] s_ids = {R.id.d04, R.id.d14, R.id.d24, R.id.d34, R.id.d05, R.id.d15, R.id.d25, R.id.d35};*/
    private TableLayout table;

    public ClockFace(RemoteViews views){

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
/*
        this.h.setNumber(hour);
        this.m.setNumber(min);
        this.s.setNumber(sec);
*/
    }


}
