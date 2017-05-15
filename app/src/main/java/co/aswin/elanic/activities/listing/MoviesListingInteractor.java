package co.aswin.elanic.activities.listing;

import java.util.List;

import co.aswin.elanic.model.Movie;

interface MoviesListingInteractor {

    void passMovies(List<Movie> movies);
    void passSearchResult(List<Movie> movies);
    void loadingStarted();
    void loadingFailed(String errorMessage);
    void loadingFinished();
}
