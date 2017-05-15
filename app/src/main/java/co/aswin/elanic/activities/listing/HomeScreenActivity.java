package co.aswin.elanic.activities.listing;

import java.util.List;

import javax.inject.Inject;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

import co.aswin.elanic.BaseApplicationClass;
import co.aswin.elanic.R;
import co.aswin.elanic.activities.DetailsActivity;
import co.aswin.elanic.adapter.CustomViewPagerAdapter;
import co.aswin.elanic.config.TransitionConfig;
import co.aswin.elanic.model.Movie;
import co.aswin.elanic.utils.TypeUtils;

/**
 * Activity showing Home screen of the application containing a {@link MoviesListingFragment}
 */
public class HomeScreenActivity extends AppCompatActivity implements
        MoviesListingInteractor,
        MoviesListingFragment.OnFragmentInteractionListener{

    /**
     * Page no of data being fetched from network
     */
    private int pageNo = 1;

    /**
     * Injecting dependencies using Dagger
     */
    @Inject MoviesListingService moviesListingService;

    /**
     * Views in the activity
     */
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.editIext) EditText editText;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.iconBack) TextView iconBack;
    @BindView(R.id.iconClear) TextView iconClear;
    @BindView(R.id.iconSearch) TextView iconSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        BaseApplicationClass.component().inject(this);
        ButterKnife.bind(this);
        moviesListingService.setView(this);
        moviesListingService.obtainMovies(pageNo);
        onAttachListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        moviesListingService.destroy();
    }

    @Override
    public void passMovies(List<Movie> movies) {
        if(pageNo > 1){
            EventBus.getDefault().post(movies);
        }else {
            reInitializeViewPager(movies);
        }
    }

    @Override
    public void passSearchResult(List<Movie> movies) {
        reInitializeViewPager(movies);
    }

    @Override
    public void loadingStarted() {
        showLoadingScreen();
    }

    @Override
    public void loadingFailed(String errorMessage) {
        showSnackBar(getString(R.string.error_unknown));
    }

    @Override
    public void loadingFinished() {
        hideLoadingScreen();
    }


    @Override
    public void onLoadMoreMovies() {
        if(!(editText.getText().length() > 0)){
            pageNo++;
            moviesListingService.obtainMovies(pageNo);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onMovieSelected(Movie movie, ImageView image, TextView title, TextView description) {
        Intent intent = new Intent(HomeScreenActivity.this, DetailsActivity.class);
        intent.putExtra(TransitionConfig.EXTRA_MOVIE_DATA,
                Parcels.wrap(movie));
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Pair<View, String> a1 = Pair.create((View)image, getString(R.string.anim_image));
            Pair<View, String> a2 = Pair.create((View) title, getString(R.string.anim_title));
            Pair<View, String> a3 = Pair.create((View) description, getString(R.string.anim_description));
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(HomeScreenActivity.this, a1, a2, a3);
            startActivity(intent, options.toBundle());
        }else {
            startActivity(intent);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onMovieSelected(Movie movie, ImageView image, TextView title) {
        Intent intent = new Intent(HomeScreenActivity.this, DetailsActivity.class);
        intent.putExtra(TransitionConfig.EXTRA_MOVIE_DATA,
                Parcels.wrap(movie));
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Pair<View, String> a1 = Pair.create((View)image, getString(R.string.anim_image));
            Pair<View, String> a2 = Pair.create((View) title, getString(R.string.anim_title));
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(HomeScreenActivity.this, a1, a2);
            startActivity(intent, options.toBundle());
        }else {
            startActivity(intent);
        }
    }

    /**
     * Handling listeners in the activity
     */
    private void onAttachListeners() {
        iconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchScreen();
            }
        });

        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalScreen();
                editText.setText("");
                pageNo = 1;
                moviesListingService.obtainMovies(1);
            }
        });

        iconClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 1){
                    moviesListingService.obtainMovies(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * re initializes view pager with new {@link Movie} objects while performing search
     *
     * @param movies list of {@link Movie} objects
     */
    private void reInitializeViewPager(List<Movie> movies) {
        CustomViewPagerAdapter adapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragments(MoviesListingFragment
                .restoreInstance(TypeUtils.VIEW_TYPE_LINEAR, movies), "List");
        adapter.addFragments(MoviesListingFragment
                .restoreInstance(TypeUtils.VIEW_TYPE_GRID, movies), "Grid");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * shows the screen when user is searching for movies
     */
    private void showSearchScreen(){
        editText.setVisibility(View.VISIBLE);
        iconClear.setVisibility(View.VISIBLE);
        iconBack.setVisibility(View.VISIBLE);
        title.setVisibility(View.INVISIBLE);
        iconSearch.setVisibility(View.INVISIBLE);
    }

    /**
     * shows the default screen
     */
    private void showNormalScreen(){
        editText.setVisibility(View.INVISIBLE);
        iconClear.setVisibility(View.INVISIBLE);
        iconBack.setVisibility(View.INVISIBLE);
        title.setVisibility(View.VISIBLE);
        iconSearch.setVisibility(View.VISIBLE);
    }

    /**
     * shows the loading screen
     */
    private void showLoadingScreen(){
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * hides the loading screen
     */
    private void hideLoadingScreen(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Shows the custom snack bar with an action to re hit the server and obtain data.
     *
     * @param message message to be displayed
     */
    private void showSnackBar(String message){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        moviesListingService.obtainMovies(pageNo);
                    }
                });
        snackbar.setActionTextColor(ContextCompat.getColor(this,R.color.customgreen));
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }
}
