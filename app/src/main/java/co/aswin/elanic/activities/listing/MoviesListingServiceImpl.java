package co.aswin.elanic.activities.listing;

import java.util.ArrayList;
import java.util.List;

import co.aswin.elanic.model.Movie;
import co.aswin.elanic.model.MovieListingResponse;
import co.aswin.elanic.utils.LogUtils;
import co.aswin.elanic.utils.RxUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class MoviesListingServiceImpl implements MoviesListingService {

    private final String TAG = MoviesListingServiceImpl.class.getSimpleName();

    private MoviesListingInteractor view;
    private MoviesListingObtainer moviesListingObtainer;
    private Subscription moviesListingSubscription;
    private Subscription searchResultsSubscription;

    MoviesListingServiceImpl(MoviesListingObtainer moviesListingObtainer)
    {
        this.moviesListingObtainer = moviesListingObtainer;
    }

    @Override
    public void setView(MoviesListingInteractor view) {
        this.view = view;
    }

    @Override
    public void obtainMovies(int page) {
        showLoading();
        moviesListingSubscription = moviesListingObtainer.fetchMovies(page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<MovieListingResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                onMovieFetchFailed(e);
            }

            @Override
            public void onNext(MovieListingResponse response) {
                onMovieFetchSuccess(parseResponse(response));
            }
        });
    }

    @Override
    public void obtainMovies(String query) {
        showLoading();
        searchResultsSubscription = moviesListingObtainer.fetchSearchResultMovies(query).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieListingResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onMovieFetchFailed(e);
                    }

                    @Override
                    public void onNext(MovieListingResponse response) {
                        onSearchResultFetchSuccess(parseResponse(response));
                    }
                });
    }

    @Override
    public void destroy() {
        view = null;
        RxUtils.unsubscribe(moviesListingSubscription);
        RxUtils.unsubscribe(searchResultsSubscription);
    }

    private boolean isViewAttached()
    {
        return view != null;
    }

    private void showLoading()
    {
        if (isViewAttached())
        {
            view.loadingStarted();
        }
    }

    private void finishLoading(){
        if (isViewAttached())
        {
            view.loadingFinished();
        }
    }

    private void onMovieFetchSuccess(List<Movie> movies)
    {
        if (isViewAttached())
        {
            view.passMovies(movies);
        }
    }

    private void onSearchResultFetchSuccess(List<Movie> movies)
    {
        if (isViewAttached())
        {
            view.passSearchResult(movies);
        }
    }

    private List<Movie> parseResponse(MovieListingResponse response) {
        LogUtils.printInfoLog(TAG, "search response size", String.valueOf(response.getResults().size()));
        List<Movie> movieList = new ArrayList<>();
        for(Movie movie : response.getResults()){
            movieList.add(movie);
        }
        finishLoading();
        return movieList;
    }

    private void onMovieFetchFailed(Throwable e)
    {
        view.loadingFailed(e.getMessage());
    }
}
