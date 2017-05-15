package co.aswin.elanic.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.aswin.elanic.R;
import co.aswin.elanic.utils.ChangeUiUtils;

/**
 * Dialog showing the image of {@link co.aswin.elanic.model.Movie} object
 */
public class ImageDialog extends Dialog {

    /**
     * Context of activity
     */
    private Context mContext;

    /**
     * imageurl used for populating view
     */
    private String mImageUrl;

    /**
     * Views in the dialog
     */
    @BindView(R.id.image)
    ImageView image;

    /**
     * Dialog constructor
     *
     * @param context activity context
     * @param imageUrl iimage url
     */
    public ImageDialog(Context context, String imageUrl) {
        super(context);
        mContext = context;
        mImageUrl = imageUrl;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(getWindow() != null){
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        setContentView(R.layout.dialog_image);
        ButterKnife.bind(this);
        initializeDialog();
    }

    /**
     * Initializes the dialog by populating iage using {@link com.bumptech.glide.Glide} library
     */
    private void initializeDialog() {
        ChangeUiUtils.populateImageFromUrlToView(mContext, mImageUrl, image);
    }
}
