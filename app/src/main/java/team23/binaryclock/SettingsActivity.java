package team23.binaryclock;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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
        setSpinnerCallback((Spinner) findViewById(R.id.on_shape_spinner));
        setSpinnerCallback((Spinner) findViewById(R.id.off_shape_spinner));


        //set the color textView listeners for the color popup
        ((LinearLayout)((LinearLayout)findViewById(R.id.on_color_layout)).getChildAt(0)).getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(v);
            }
        });
        ((LinearLayout)((LinearLayout)findViewById(R.id.off_color_layout)).getChildAt(0)).getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(v);
            }
        });


        //load the user skins from the SharedPreferences in the UI and in the previewBit attribute
        loadSkinFromPreferences(true, (LinearLayout) findViewById(R.id.bit_on));
        loadSkinFromPreferences(false, (LinearLayout) findViewById(R.id.bit_off));


        //setups the preview according to the previewBit attribute
        updatePreview();

        findViewById(R.id.reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePreview();
            }
        });


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
    }

    private void popup(final View v) {
        @ColorInt int initial_color =  Color.parseColor("#" + ((TextView) v).getText().toString());
        Log.i("color", ""+Integer.toHexString(initial_color));
        new ColorPickerPopup.Builder(this)
                .initialColor(initial_color)
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
                        BitSkin bitSkin= null;
                        //update the UI
                        ((TextView) v).setText(Integer.toHexString(color).toUpperCase());
                        ((LinearLayout) v.getParent()).getChildAt(0).setBackgroundColor(color);

                        //ask for the preview and persistance
                        LinearLayout topLayout = (LinearLayout) v.getParent().getParent().getParent().getParent();
                        if (topLayout.getId() == R.id.bit_on){
                            previewBit.setSkin(generateSkin(topLayout, true), true);
                        }
                        else{
                            previewBit.setSkin(generateSkin(topLayout, false), false);
                        }
                        updatePreview();
                    }
                });
    }

    private void fillSpinner(Spinner spinner){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.shapes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setSpinnerCallback(final Spinner sp){
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //ask for the preview and persistance
                LinearLayout topLayout = (LinearLayout) sp.getParent().getParent();
                if (topLayout.getId() == R.id.bit_on){
                    previewBit.setSkin(generateSkin(topLayout, true), true);
                }
                else{
                    previewBit.setSkin(generateSkin(topLayout, false), false);
                }
                updatePreview();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //genererate a BitSkin object based on the data in the layout and saves it into the SharedPreferences
    //the topLayout should correspond to the boolean bit (bit on or off)
    private BitSkin generateSkin(LinearLayout topLayout, Boolean on){
        //TODO: refactor the skin persistence and use something else than SharedPreferences (to store complex data)
        //prepare to save the skin
        //keys: bit_true_color, bit_true_shape, bit_false_color, bit_false_shape
        SharedPreferences settings = getSharedPreferences("team23.binaryClock", 0);
        final SharedPreferences.Editor editor = settings.edit();

        int id = topLayout.getId();

        //get the color information
        LinearLayout colorLayout = (LinearLayout) ((LinearLayout) topLayout.getChildAt(1)).getChildAt(2);
        int[] colors = getColors(colorLayout);

        //get the shape information
        Spinner sp = (Spinner) ((LinearLayout) topLayout.getChildAt(2)).getChildAt(1);
        int shape = sp.getSelectedItem().toString().equals("Rectangle") ? GradientDrawable.RECTANGLE : GradientDrawable.OVAL;

        Log.i("generateSkin","color: "+colors[0]);
        Log.i("generateSkin","color: "+shape);


        //save the skin
        editor.putInt("bit_"+on.toString()+"_color", colors[0]);
        editor.putInt("bit_"+on.toString()+"_shape", shape);
        editor.apply();

        //instantiates the skin, with a default sweep gradient and size //TODO: customize gradient type and size
        BitSkin skin = new GradientSkin(shape, colors, GradientDrawable.SWEEP_GRADIENT, 60,60);
        return skin;
    }

    //update the UI and the previewBit attribute from the SharedPreferences
    private void loadSkinFromPreferences(Boolean on, LinearLayout topLayout){
        SharedPreferences settings = getSharedPreferences("team23.binaryClock", 0);

        //loads the bit_on data
        int color = settings.getInt("bit_"+on.toString()+"_color", 0xFFAAAAAA);
        int shape = settings.getInt("bit_"+on.toString()+"_shape", GradientDrawable.OVAL);

        //set the text view for the color (UI)
        LinearLayout oneColor = (LinearLayout) ((LinearLayout) ((LinearLayout) topLayout.getChildAt(1)).getChildAt(2)).getChildAt(0);
        ((TextView) oneColor.getChildAt(1)).setText(Integer.toHexString(color).toUpperCase());
        oneColor.getChildAt(0).setBackgroundColor(color);

        //TODO: update the spinner for the shape (boring)
        setSpinnerPosition((Spinner) ((LinearLayout)topLayout.getChildAt(2)).getChildAt(1), shape);


        //TODO: change the duplicate color hack
        BitSkin skin = new GradientSkin(shape, new int[]{color, color}, GradientDrawable.SWEEP_GRADIENT, 60,60);
        previewBit.setSkin(skin, on);
    }

    private void setSpinnerPosition(Spinner sp, int shape){
        if (shape == GradientDrawable.RECTANGLE){
            sp.setSelection(0);
        }
        else{
            sp.setSelection(1);
        }
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
        int colorNb = colorLayout.getChildCount();
        @ColorInt int colors[] = new int[]{colorNb};
        for (int i=0; i< colorNb; i++){
            TextView tv = (TextView) ((LinearLayout) colorLayout.getChildAt(i)).getChildAt(1);
            int c = Color.parseColor("#"+tv.getText().toString());
            Log.i("getColors", "read: "+tv.getText() + " " + c);

            colors[i] = c;
        }

        if (colorNb == 1){
            return new int[]{colors[0], colors[0]};
        }
        return colors;
    }

}
