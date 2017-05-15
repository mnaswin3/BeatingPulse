package co.aswin.elanic.activities.listing;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

import co.aswin.elanic.R;
import co.aswin.elanic.adapter.EndlessVerticalOnScrollListener;
import co.aswin.elanic.adapter.MovieListAdapter;
import co.aswin.elanic.model.Movie;
import co.aswin.elanic.utils.ChangeUiUtils;
import co.aswin.elanic.utils.TypeUtils;

public class MoviesListingFragment extends Fragment{

    /**
     * Bundle extra for type of view(linear or grid)
     */
    private static final String ARG_TYPE = "type";

    /**
     * Bundle extra for list of {@link Movie} object
     */
    private static final String ARG_MOVIE_LIST = "movie_list";

    /**
     * type of view(linear or grid)
     */
    private int type;

    /**
     * list of {@link Movie} objects
     */
    private List<Movie> list;

    /**
     * Recycler mAdapter for movie.
     */
    private MovieListAdapter mAdapter;

    /**
     * Flag to check if network response is loading or not.
     */
    private Boolean isLoading = false;

    /**
     * Views in the fragment
     */
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.nestedScrollView) NestedScrollView nestedScrollView;

    public MoviesListingFragment() {}

    /**
     * initializes the fragment with data
     *
     * @param type type of view
     * @param list list of {@link Movie} objects
     * @return fragment
     */
    public static MoviesListingFragment restoreInstance(int type, List<Movie> list) {
        MoviesListingFragment fragment = new MoviesListingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        args.putParcelable(ARG_MOVIE_LIST, Parcels.wrap(list));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            type = getArguments().getInt(ARG_TYPE);
            list = Parcels.unwrap(getArguments().getParcelable(ARG_MOVIE_LIST));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_listing, container, false);
        ButterKnife.bind(this, view);
        initializeView();
        initializeFragment();
        onAttachListeners();
        return  view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fragment interaction listener.
     */
    private OnFragmentInteractionListener mListener;

    /**
     * Fragment interfaces
     */
     interface OnFragmentInteractionListener {
        void onLoadMoreMovies();
        void onMovieSelected(Movie movie, ImageView image, TextView title, TextView description);
        void onMovieSelected(Movie movie, ImageView image, TextView title);
    }

    /**
     * Initializes the views in the fragment
     */
    private void initializeView() {
        if(type == TypeUtils.VIEW_TYPE_LINEAR){
            LinearLayoutManager mLinearLayoutManager =
                    new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLinearLayoutManager);
        }else if(type == TypeUtils.VIEW_TYPE_GRID){
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 2);
            recyclerView.setLayoutManager(mGridLayoutManager);
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * Initializes the fragment
     */
    private void initializeFragment() {
        mAdapter = new MovieListAdapter(getContext(), list, type);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * Attaches listeners to the fragment
     */
    private void onAttachListeners(){

        mAdapter.setListener(new MovieListAdapter.OnAdapterInteractionListener() {
            @Override
            public void onMovieSelected(Movie movie, ImageView image, TextView title, TextView description) {
                if(mListener != null){
                    mListener.onMovieSelected(movie, image, title, description);
                }
            }

            @Override
            public void onMovieSelected(Movie movie, ImageView image, TextView title) {
                if(mListener != null){
                    mListener.onMovieSelected(movie, image, title);
                }
            }

            @Override
            public void onImageClicked(String imageUrl) {
                ChangeUiUtils.showImageDialog(getActivity(), imageUrl);
            }
        });

        EndlessVerticalOnScrollListener scrollListener = new EndlessVerticalOnScrollListener() {
            @Override
            public void onLoad() {
                if(mListener != null){
                    if(!isLoading){
                        mListener.onLoadMoreMovies();
                        isLoading = true;
                    }
                }
            }
        };
        nestedScrollView.setOnScrollChangeListener(scrollListener);
    }

    /**
     * Eventbus handling endless scrolling of objects
     *
     * @param movies list of {@link Movie} objects
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddMoreMoviesEvent(List<Movie> movies) {
        isLoading = false;
        for(Movie movie : movies){
            list.add(movie);
        }
        mAdapter.notifyDataSetChanged();
    }
}
