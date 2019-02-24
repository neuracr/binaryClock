package team23.binaryclock;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {
    private int num = 0;
    private final static int TICK_MESSAGE = 0;
    private ClockFace clockFace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.clockFace = new ClockFace((TableLayout)findViewById(R.id.face));

        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int TICKS_IN_A_ROW = 50;
                //set the clock for the first time
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
    }

    final private Handler tick = new Handler(){
        @Override
        public void handleMessage(Message m){
            super.handleMessage(m);

            if (m.what == TICK_MESSAGE) {
                clockFace.setTime();
            }
        }
    };


}
