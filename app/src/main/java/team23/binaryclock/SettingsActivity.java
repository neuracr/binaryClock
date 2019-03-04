package team23.binaryclock;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private int num = 0;
    private final static int TICK_MESSAGE = 0;
    private ClockFace clockFace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_clock);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);

        }


        //final GridView gridView = (GridView) findViewById(R.id.face);
        //gridView.setAdapter(new TestAdapter(this, getBitList()));



/*
        this.clockFace = new ClockFace((TableLayout)findViewById(R.id.face));

        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int TICKS_IN_A_ROW = 50;
                //set the widget_clock for the first time
                tick.sendEmptyMessage(TICK_MESSAGE);

                //tries to land on the top of each second
                while(true) {
                    Calendar c = Calendar.getInstance();
                    int offset = c.get(Calendar.MILLISECOND);

                    long nextTime = SystemClock.uptimeMillis() + 1000 - offset;
                    for (int i=0 ; i < TICKS_IN_A_ROW ; i++) {
                        tick.sendEmptyMessageAtTime(TICK_MESSAGE, nextTime + i*1000);
                    }
                    try {
                        Thread.sleep(TICKS_IN_A_ROW * 1000);
                    }
                    catch (InterruptedException e){
                    }
                }
            }
        });
        t.start();
        */
    }

    private List<Bit> getBitList(){
        int i=0;
        ArrayList<Bit> bitList = new ArrayList<Bit>(24);
        while (i < 24) {
            bitList.add(new Bit());
            i++;
        }
        return bitList;
    }

/*
    final private Handler tick = new Handler(){
        @Override
        public void handleMessage(Message m){
            super.handleMessage(m);

            if (m.what == TICK_MESSAGE) {
                clockFace.setTime();
            }
        }
    };

*/
}
