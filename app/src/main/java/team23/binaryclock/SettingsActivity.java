package team23.binaryclock;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class SettingsActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private Random rand;
    private Bit previewBit; //used for the skin preview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.tableLayout = (TableLayout) findViewById(R.id.tablePreview);
        this.rand = new Random();
        this.previewBit = new Bit();



        //updates the spinners
        fillSpinner((Spinner) findViewById(R.id.on_shape_spinner));
        fillSpinner((Spinner) findViewById(R.id.off_shape_spinner));

        //setups the preview
        updatePreview();


        //I'll deal with this later to let the user add his own bit images
        /*
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
        */

        /*
        SharedPreferences settings = getSharedPreferences("team23.binaryClock", 0);
        final SharedPreferences.Editor editor = settings.edit();



        Switch sw = findViewById(R.id.rect_switch);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //commit prefs on change
                editor.putBoolean("rect", isChecked);
                editor.apply();
            }
        });
        */
    }

    private void fillSpinner(Spinner spinner){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.shapes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void updatePreview(){
        for (int i =0; i < this.tableLayout.getChildCount(); i++){
            //TODO: be carefull with the casts
            TableRow row = (TableRow) this.tableLayout.getChildAt(i);
            for (int j=0; j < row.getChildCount(); j++){
                ImageView iv = (ImageView) row.getChildAt(j);
                Bitmap b = this.previewBit.getBitmap(this.rand.nextBoolean());
                iv.setImageBitmap(b);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
