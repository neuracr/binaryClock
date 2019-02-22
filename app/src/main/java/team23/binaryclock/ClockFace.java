package team23.binaryclock;

import team23.binaryclock.NumberFace;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Defines the main face of the clock, containing some numbers
 */
public class ClockFace{
    private Context context;
    private NumberFace h;
    private NumberFace m;
    private NumberFace s;
    private TableLayout table;

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

    public void setTime(){
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int min = now.get(Calendar.MINUTE);
        int sec = now.get(Calendar.SECOND);

        this.h.setNumber(hour);
        this.m.setNumber(min);
        this.s.setNumber(sec);
    }


}
