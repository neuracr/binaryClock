package team23.binaryclock;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.net.LinkAddress;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.BoringLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

import top.defaults.colorpicker.ColorPickerPopup;

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
        //((TextView) ((LinearLayout)findViewById(R.id.on_color_layout)).getChildAt(0)).addTextChangedListener(new NewColorChange());
        ((LinearLayout)((LinearLayout)findViewById(R.id.off_color_layout)).getChildAt(0)).getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(v);
            }
        });


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

    private void popup(View v) {
        new ColorPickerPopup.Builder(this)
                .initialColor(0xFFFFFFFF)
                .enableAlpha(true)
                .okTitle("Choose")
                .cancelTitle("Cancel")
                .showIndicator(true)
                .showValue(true)
                .onlyUpdateOnTouchEventUp(true)
                .build()
                .show(new ColorPickerPopup.ColorPickerObserver() {
                    @Override
                    public void onColorPicked(int color) {
                        Log.i("color", "" + color);
                    }
                });
    }

    private void fillSpinner(Spinner spinner){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.shapes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    //genererate a BitSkin object based on the data in the layout and saves it into the SharedPreferences
    //the topLayout should correspond to the boolean bit (bit on or off)
    private BitSkin generateSkin(LinearLayout topLayout, Boolean on){
        //TODO: refactor the skin persistence and use something else than SharedPreferences (to store complex data)
        //prepare to save the skin
        //keys: bit_true_color, bit_true_shape, bit_false_color, bit_false_shape
        SharedPreferences settings = getSharedPreferences("team23.binaryClock", 0);
        final SharedPreferences.Editor editor = settings.edit();

        //get the color information
        LinearLayout colorLayout = (LinearLayout) topLayout.getChildAt(1);
        int[] colors = getColors(colorLayout);

        //get the shape information
        Spinner sp = (Spinner) ((LinearLayout) topLayout.getChildAt(2)).getChildAt(1);
        int shape = sp.getSelectedItem().toString().equals("Rectangle") ? GradientDrawable.RECTANGLE : GradientDrawable.OVAL;

        //save the skin
        editor.putInt("bit_"+on.toString()+"_color", colors[0]);
        editor.putInt("bit_"+on.toString()+"_shape", shape);
        editor.apply();

        //instantiates the skin, with a default sweep gradient and size //TODO: customize gradient type and size
        BitSkin skin = new GradientSkin(shape, colors, GradientDrawable.SWEEP_GRADIENT, 60,60);
        return skin;
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

    private int[] getColors(LinearLayout colorLayout){
        @ColorInt int colors[] = new int[]{colorLayout.getChildCount()};
        //TODO:code this
        return new int[] {0xFFFFFFFF};
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
            //generateSkin();
            updatePreview();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
