package digimantra.veedaters.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by dmlabs on 6/11/17.
 */

public class TextViewSemiBold extends TextView
{
    public TextViewSemiBold(Context context) {
        super(context);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans-Semibold.ttf");
        this.setTypeface(typeface);
    }

    public TextViewSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans-Semibold.ttf");
        this.setTypeface(typeface);
    }

    public TextViewSemiBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans-Semibold.ttf");
        this.setTypeface(typeface);
    }

    public TextViewSemiBold(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans-Semibold.ttf");
        this.setTypeface(typeface);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
