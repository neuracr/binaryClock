package com.example.binaryclock;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity {
    private boolean state = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button sw = findViewById(R.id.button);
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView bit = findViewById(R.id.d12);
                if (state){
                    bit.setBackgroundDrawable(getDrawable(R.drawable.bit_off));
                }
                else{
                    bit.setBackgroundDrawable(getDrawable(R.drawable.bit_on));
                }
                state = !state;
            }
        });

    }
}
