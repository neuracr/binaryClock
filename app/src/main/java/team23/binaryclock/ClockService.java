package team23.binaryclock;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
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

    class ClockWidgetItemFactory implements RemoteViewsFactory{
        private List<Bit> bitList;
        private Context context;
        private ClockFace clockFace;
        private Handler tickHandler;
        private GradientDrawable bit_off_draw;
        private GradientDrawable bit_on_draw;
        private Bitmap bit_off;
        private Bitmap bit_on;
        private boolean rect = false;

        ClockWidgetItemFactory(Context aContext,  List<Bit> bitList){
            this.context = aContext;
            this.bitList = bitList;
            this.clockFace = new ClockFace();
            this.clockFace.setTime();
            this.tickHandler = new Handler();
        }
        @Override
        public void onCreate() {
            Log.i("callback", "onCreate()");

            //bit_off creation
            this.bit_off_draw = (GradientDrawable) getResources().getDrawable(R.drawable.bit_off, getTheme());
            this.bit_off = convertToBitmap(this.bit_off_draw, 20,20);



            this.bit_on_draw = (GradientDrawable) getResources().getDrawable(R.drawable.bit_on, getTheme());
            this.bit_on = convertToBitmap(this.bit_on_draw, 20,20);


            final Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    int TICKS_IN_A_ROW = 50;
                    try {
                        Thread.sleep(500);
                    }
                    catch (Exception e){
                    }

                    //tries to land on the top of each second
                    while(true) {
                        Calendar c = Calendar.getInstance();
                        int offset = c.get(Calendar.MILLISECOND);

                        long nextTime = SystemClock.uptimeMillis() + 1000 - offset;
                        for (int i=0 ; i < TICKS_IN_A_ROW ; i++) {
                            tickHandler.postAtTime(new Runnable() {
                                @Override
                                public void run() {
                                    askUpdate();
                                }
                            }, nextTime + i * 1000);
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

        private void askUpdate(){
            this.clockFace.setTime();
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, ClockWidget.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.face);
        }

        @Override
        public void onDataSetChanged() {
            Log.i("callback", "onDatasetChanged()");

            SharedPreferences settings = getSharedPreferences("team23.binaryClock", 0);
            this.rect = settings.getBoolean("rect", true);
            if(this.rect){
                this.bit_off_draw.setShape(GradientDrawable.RECTANGLE);
                this.bit_on_draw.setShape(GradientDrawable.RECTANGLE);
            }
            else{
                this.bit_off_draw.setShape(GradientDrawable.OVAL);
                this.bit_on_draw.setShape(GradientDrawable.OVAL);
            }
            this.bit_on = convertToBitmap(this.bit_on_draw, 60,60);
            this.bit_off = convertToBitmap(this.bit_off_draw, 60,60);

        }

        @Override
        public void onDestroy() {
            Log.i("callback", "onDestroy()");

            //close data source
        }

        @Override
        public int getCount() {
            Log.i("callback", "getCount()");

            return bitList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Log.i("callback", "getViewAt("+position+")");
            if (position == AdapterView.INVALID_POSITION){
                return null;
            }
            int[] enabled = new int[] {-android.R.attr.state_enabled};
            int[] disabled = new int[] {};
            int [][] states = new int[][] {enabled, disabled};

            //ColorStateList csl = new ColorStateList(states, new int[] {0x0, 0xffffff});

            //ShapeDrawable bit = new ShapeDrawable();
            //bit.setShape(new OvalShape());
            //bit.setIntrinsicHeight(15);
            //bit.setIntrinsicWidth(15);
            //bit.setTintList(csl);
            //ShapeDrawable bit_on = (ShapeDrawable) getResources().getDrawable(R.drawable.bit_on);
            //bit_on.setColorFilter( 0xffff0000, PorterDuff.Mode.MULTIPLY );
            //StateListDrawable sld = (StateListDrawable) getResources().getDrawable(R.drawable.bit);
            //sld.addState(new int[] {-android.R.attr.state_enabled}, bit_on);
            RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.bit);



            ////// To load an image from the external storage
            //Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath() + "/icon.png");
            //view.setImageViewBitmap(R.id.bitImage,  bm);
            ////////


            boolean on = this.clockFace.get(position%6, position/6);
            if (on){
                view.setImageViewBitmap(R.id.bitImage,  bit_on);
            }
            else{
                view.setImageViewBitmap(R.id.bitImage,  bit_off);
            }
            Log.i("getViewAt","x:"+position%6 +", y:"+position/6+", pos:" + position + ", on:"+on);
            //view.setBoolean(R.id.bitImage, "setEnabled", on);
            return view;
        }


        public Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
            Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(mutableBitmap);
            drawable.setBounds(0, 0, widthPixels, heightPixels);
            drawable.draw(canvas);

            return mutableBitmap;
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
            Log.i("callback", "getItemId() -> "+5000+position);
            return 5000+position;
        }

        @Override
        public boolean hasStableIds() {
            Log.i("callback", "hasStableIds()");
            return true;
        }
    }
}
