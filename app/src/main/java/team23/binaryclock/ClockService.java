package team23.binaryclock;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ClockService extends RemoteViewsService {
    private ClockWidgetItemFactory clockWidgetItemFactory;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        this.clockWidgetItemFactory = new ClockWidgetItemFactory(getApplicationContext(), getBitList());
        return this.clockWidgetItemFactory;
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

    public void startTicking(final AppWidgetManager appWidgetManager){
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int TICKS_IN_A_ROW = 50;
                //set the widget_clock for the first time
                clockWidgetItemFactory.onDataSetChanged();

                /*
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
                }*/
            }
        });
        t.start();
    }


    class ClockWidgetItemFactory implements RemoteViewsFactory{
        private List<Bit> listData;
        private LayoutInflater layoutInflater;
        private Context context;
        private boolean bool = true;

        ClockWidgetItemFactory(Context aContext,  List<Bit> listData){
            this.context = aContext;
            this.listData = listData;
            layoutInflater = LayoutInflater.from(aContext);
        }
        @Override
        public void onCreate() {
            Log.i("callback", "onCreate()");

        }

        @Override
        public void onDataSetChanged() {
            Log.i("callback", "onDataSetChanged()");
            //when we want to update our widget
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_clock);
            ClockFace face = new ClockFace(views);
            face.setTime();


        }

        @Override
        public void onDestroy() {
            //close data source
        }

        @Override
        public int getCount() {
            Log.i("callback", "getCount()");

            return listData.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Log.i("callback", "getViewAt("+position+")");
            if (position == AdapterView.INVALID_POSITION){
                return null;
            }
            RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.bit);
            view.setImageViewResource(R.id.bitImage, R.drawable.bit);
            view.setBoolean(R.id.bitImage, "setEnabled", bool);
            bool = !bool;
            return view;
        }

        @Override
        public RemoteViews getLoadingView() {
            Log.i("callback", "getLoadingView()");

            return null;
        }

        @Override
        public int getViewTypeCount() {
            Log.i("callback", "getViewTypeCount()");

            return 1;
        }

        @Override
        public long getItemId(int position) {
            Log.i("callback", "getItemId()");
            return 5000+position;
        }

        @Override
        public boolean hasStableIds() {
            Log.i("callback", "hasStableIds()");
            return false;
        }
    }
}
