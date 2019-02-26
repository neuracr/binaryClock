package team23.binaryclock;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class ClockService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ClockWidgetItemFactory(getApplicationContext(), intent);
    }

    class ClockWidgetItemFactory implements RemoteViewsFactory{
        private Context context;
        private int appWidgetId;
        //private ClockFace clockFace;

        ClockWidgetItemFactory(Context context, Intent intent){
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            //this.clockFace = new ClockFace()
        }
        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            //when we want to update our widget

        }

        @Override
        public void onDestroy() {
            //close data source
        }

        @Override
        public int getCount() {
            return 24;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_clock);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
