package team23.binaryclock;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class SettingsActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private Random rand;
    private Bit previewBit; //used for the skin preview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.tableLayout = findViewById(R.id.tablePreview);
        this.rand = new Random();
        this.previewBit = new Bit();



        //updates the spinners
        fillSpinner((Spinner) findViewById(R.id.on_shape_spinner));
        fillSpinner((Spinner) findViewById(R.id.off_shape_spinner));

        //set the textView listeners
        ((TextView) ((LinearLayout)findViewById(R.id.on_color_layout)).getChildAt(0)).addTextChangedListener(new NewColorChange());
        ((TextView) ((LinearLayout)findViewById(R.id.off_color_layout)).getChildAt(0)).addTextChangedListener(new NewColorChange());


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

    //preview generated randomly
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

    private int[] getColors(int layoutId){
        LinearLayout layout = findViewById(layoutId);
        int colors[] = new int[]{layout.getChildCount()};
        for (int i=0 ; i<layout.getChildCount(); i++){
            TextView tv = (TextView) layout.getChildAt(i);
            colors[i] = Integer.parseInt(tv.getText().toString());
            Log.i("Integer.parseInt: ", Integer.toHexString(colors[i]));
        }
        return new int[] {0};
    }

    private class NewColorChange implements TextWatcher {
        //not used for me
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        //not used for me
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            updatePreview();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
