package co.aswin.elanic.activities;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.aswin.elanic.R;
import co.aswin.elanic.config.TransitionConfig;
import co.aswin.elanic.model.Movie;
import co.aswin.elanic.utils.ChangeUiUtils;

/**
 * Activity displaying movie details
 */
public class DetailsActivity extends AppCompatActivity {

    /**
     * Bundle extra  for {@link Movie} object
     */
    private static final String ARG_MOVIE = "movie";

    /**
     * Data from {@link co.aswin.elanic.activities.listing.HomeScreenActivity} containing {@link Movie} object
     */
    private Movie movie;

    /**
     * Views in the activity
     */
    @BindView(R.id.appBarLayout) AppBarLayout appBarLayout;
    @BindView(R.id.layoutToolbar) View layoutToolbar;
    @BindView(R.id.imageCollapsed) ImageView imageCollapsed;
    @BindView(R.id.titleCollapsed) TextView titleCollapsed;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.description) TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        if(savedInstanceState != null){
            movie = Parcels.unwrap(savedInstanceState.getParcelable(ARG_MOVIE));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(movie == null){
            initializeActivity();
        }else {
            populateData();
        }
        onAttachListeners();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(movie != null){
            outState.putParcelable(ARG_MOVIE, Parcels.wrap(movie));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

    /**
     * Initializes the activity by receiving intent object from {@link co.aswin.elanic.activities.listing.HomeScreenActivity}
     */
    private void initializeActivity() {
        Intent intent = getIntent();
        movie = Parcels.unwrap(intent.getParcelableExtra(TransitionConfig.EXTRA_MOVIE_DATA));
        populateData();
    }

    /**
     * Handles listeners in the activity
     */
    private void onAttachListeners() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                layoutToolbar.setAlpha(Math.abs(verticalOffset / (float)
                        appBarLayout.getTotalScrollRange()));
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movie != null){
                    ChangeUiUtils.showImageDialog(DetailsActivity.this, movie.getPosterPath());
                }
            }
        });
    }

    /**
     * Populates data into the views using {@link Movie}
     */
    private void populateData() {
        title.setText(movie.getTitle());
        titleCollapsed.setText(movie.getTitle());
        description.setText(movie.getOverview());
        ChangeUiUtils.populateImageFromUrlToView(this,
                movie.getPosterPath(),
                image);
        ChangeUiUtils.populateImageFromUrlToView(this,
                movie.getPosterPath(),
                imageCollapsed);
    }
}
