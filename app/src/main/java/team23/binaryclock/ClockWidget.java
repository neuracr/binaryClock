package team23.binaryclock;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.widget.RemoteViews;

import java.time.Clock;

/**
 * Implementation of App Widget functionality.
 */
public class ClockWidget extends AppWidgetProvider {
    private final static int TICK_MESSAGE = 0;
    private ClockFace clockFace;
    private RemoteViews views = null;
    private Thread t;
    private Handler handler;
    private AppWidgetManager appWidgetManager;
    private  int appWidgetIds[];

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
    }

    /*
    private void updateAll(){
        clockFace.setTime();

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    } */

    @Override
    public void onReceive(final Context context, Intent intent){
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // refresh all your widgets
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, ClockWidget.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.face);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(
                    context.getPackageName(),
                    R.layout.widget_clock
            );
            Intent intent = new Intent(context, ClockService.class);
            views.setRemoteAdapter(R.id.face, intent);
            appWidgetManager.updateAppWidget(appWidgetId, views);


            /*for (int appWidgetId : appWidgetIds){
            Intent intent = new Intent(context, SettingsActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            Intent serviceIntent = new Intent(context, ClockService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_clock);
            views.setRemoteAdapter(R.id.face, serviceIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);*/
        }
    }

    @Override
    public void onEnabled(Context context) {
        /*// Enter relevant functionality for when the first widget is created
        this.handler = new Handler(){
            @Override
            public void handleMessage(Message m){
                super.handleMessage(m);

                if (m.what == TICK_MESSAGE) {
                    updateAll();
                }
            }
        };*/
        //this.views = new RemoteViews(context.getPackageName(), R.layout.widget_clock);
        //this.clockFace = new ClockFace(views);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

/*
    setContentView(R.layout.activity_settings);

        this.clockFace = new ClockFace((TableLayout)findViewById(R.id.face));
Sample application

The code excerpts in this section are drawn from the StackWidget sample:


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

