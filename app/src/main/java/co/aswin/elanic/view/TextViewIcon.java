package co.aswin.elanic.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import co.aswin.elanic.R;


public class TextViewIcon extends android.support.v7.widget.AppCompatTextView {

    public TextViewIcon(Context context) {
        super(context);
    }

    public TextViewIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTypeface(Typeface tf) {
        Typeface linearIcons = Typeface.createFromAsset( getContext().getAssets(),
                getContext().getString(R.string.materialicons_ttf) );
        super.setTypeface(linearIcons);
    }
}
