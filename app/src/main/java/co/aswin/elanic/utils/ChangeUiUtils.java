package co.aswin.elanic.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import co.aswin.elanic.R;
import co.aswin.elanic.dialog.ImageDialog;

/**
 * Utility class for making ui changes.
 */
public class ChangeUiUtils {

    /**
     * Sets thumbnail image using {@link Glide} if the link is not null.
     *
     * @param context context
     * @param imageUrl image url link
     * @param view image view
     */
    public static void populateImageFromUrlToView(Context context,
                                                  String imageUrl, ImageView view){
        if(imageUrl != null){
            String link = "https://image.tmdb.org/t/p/w500" + imageUrl;
            Glide.with(context)
                    .load(link)
                    .placeholder(R.color.cloudburst)
                    .dontAnimate()
                    .error(R.color.black)
                    .into(view);
        }else {
            view.setImageResource(R.color.black);
        }
    }

    /**
     * Shows a custom dialog to display complete image
     *
     * @param context context
     * @param imageUrl image url
     */
    public static void showImageDialog(Context context, String imageUrl){
        ImageDialog dialog = new ImageDialog(context, imageUrl);
        dialog.show();
    }
}
