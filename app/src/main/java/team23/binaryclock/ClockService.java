package team23.binaryclock;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ClockService extends RemoteViewsService {
    private ClockWidgetItemFactory clockWidgetItemFactory;

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        this.clockWidgetItemFactory = new ClockWidgetItemFactory(getApplicationContext(), createBitList(24));
        return this.clockWidgetItemFactory;
    }

    public static List<Bit> createBitList(int number){
        int i=0;
        ArrayList<Bit> bitList = new ArrayList<>(number);
        while (i < number) {
            bitList.add(new Bit());
            i++;
        }
        return bitList;
    }


    class ClockWidgetItemFactory implements RemoteViewsFactory{
        private List<Bit> bitList;
        private Context context;
        private ClockFace clockFace;
        private Handler tickHandler;
        private Thread t;

        ClockWidgetItemFactory(Context aContext,  List<Bit> bitList){
            this.context = aContext;
            this.bitList = bitList;
            this.clockFace = new ClockFace();
            this.clockFace.setTime();
            this.tickHandler = new Handler();
        }
        @Override
        public void onCreate() {
            //Log.i("callback", "onCreate()");

            loadSkinFromPreferences(true);
            loadSkinFromPreferences(false);

            //TODO: perf optimization, stop ticking when not showing ?
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    int TICKS_IN_A_ROW = 1;
                    try {
                        Thread.sleep(500);
                        //tries to land on the top of each second
                        while (true) {
                            //Log.i("Thread", "TICK");
                            Calendar c = Calendar.getInstance();
                            int offset = c.get(Calendar.MILLISECOND);

                            long nextTime = SystemClock.uptimeMillis() + 1000 - offset;
                            for (int i = 0; i < TICKS_IN_A_ROW; i++) {
                                tickHandler.postAtTime(new Runnable() {
                                    @Override
                                    public void run() {
                                        askUpdate();
                                    }
                                }, nextTime + i * 1000);
                            }
                            Thread.sleep(TICKS_IN_A_ROW * 1000);
                        }
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                });
            t.start();

        }

        private void loadSkinFromPreferences(Boolean on){
            SharedPreferences settings = getSharedPreferences("team23.binaryClock", 0);

            //loads the bit_on data
            int color = settings.getInt("bit_"+on.toString()+"_color", 0xFFAAAAAA);
            int shape = settings.getInt("bit_"+on.toString()+"_shape", GradientDrawable.OVAL);

            //TODO: change the duplicate color hack
            BitSkin skin = new GradientSkin(shape, new int[]{color, color}, GradientDrawable.SWEEP_GRADIENT, 60,60);
            setSkin(skin, on);
        }

        public void setSkin(BitSkin bitSkin, Boolean on){
            //Log.i("ClockService", "setSkin");
            for (int i=0; i < bitList.size() ; i++){
                bitList.get(i).setSkin(bitSkin, on);
            }
        }

        private void askUpdate(){
            this.clockFace.setTime();
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, ClockWidget.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.face);
        }

        @Override
        public void onDataSetChanged() {
            //Log.i("callback", "onDatasetChanged()");
        }

        @Override
        public void onDestroy() {
            //Log.i("callback", "onDestroy()");
            this.t.interrupt();
        }

        @Override
        public int getCount() {
            //Log.i("callback", "getCount()");

            return bitList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            //Log.i("callback", "getViewAt("+position+")");
            if (position == AdapterView.INVALID_POSITION){
                return null;
            }
            RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.bit);



            ////// To load an image from the external storage
            //Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath() + "/icon.png");
            //view.setImageViewBitmap(R.id.bitImage,  bm);
            ////////


            boolean on = this.clockFace.get(position%6, position/6);
            view.setImageViewBitmap(R.id.bitImage,  bitList.get(position).getBitmap(on));
            //Log.i("getViewAt","x:"+position%6 +", y:"+position/6+", pos:" + position + ", on:"+on);
            return view;
        }

        /*
        public Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
            Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(mutableBitmap);
            drawable.setBounds(0, 0, widthPixels, heightPixels);
            drawable.draw(canvas);

            return mutableBitmap;
        }*/

        @Override
        public RemoteViews getLoadingView() {
            //Log.i("callback", "getLoadingView()");

            return null;
        }

        @Override
        public int getViewTypeCount() {
            //Log.i("callback", "getViewTypeCount()");

            return 1;
        }

        @Override
        public long getItemId(int position) {
            //Log.i("callback", "getItemId() -> "+5000+position);
            return 5000+position;
        }

        @Override
        public boolean hasStableIds() {
            //Log.i("callback", "hasStableIds()");
            return true;
        }
    }
}
