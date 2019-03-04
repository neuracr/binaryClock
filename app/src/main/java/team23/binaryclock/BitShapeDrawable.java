package team23.binaryclock;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;

public class BitShapeDrawable extends ShapeDrawable {
    public void setTheme(Bundle bundle){
        int shape = bundle.getInt("shape");
        int colour = bundle.getInt("colour");

        if (shape == 1) {
            super.setShape(new OvalShape());
        }
        else{
            super.setShape(new RectShape());
        }
    }
}
