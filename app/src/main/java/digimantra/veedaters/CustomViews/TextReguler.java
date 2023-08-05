package digimantra.veedaters.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by dmlabs on 6/11/17.
 */

public class TextReguler extends TextView {
    public TextReguler(Context context) {
        super(context);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans-Regular.ttf");
        this.setTypeface(typeface);
    }

    public TextReguler(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans-Regular.ttf");
        this.setTypeface(typeface);
    }

    public TextReguler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans-Regular.ttf");
        this.setTypeface(typeface);
    }

    public TextReguler(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans-Regular.ttf");
        this.setTypeface(typeface);
    }
    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }
}
