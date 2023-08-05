package digimantra.veedaters.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by dmlabs on 8/11/17.
 */

public class TextArialBold extends TextView {
    public TextArialBold(Context context) {
        super(context);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/ARIALBD.TTF");
        this.setTypeface(typeface);
    }

    public TextArialBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/ARIALBD.TTF");
        this.setTypeface(typeface);
    }

    public TextArialBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/ARIALBD.TTF");
        this.setTypeface(typeface);
    }

    public TextArialBold(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/ARIALBD.TTF");
        this.setTypeface(typeface);
    }
    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }
}
