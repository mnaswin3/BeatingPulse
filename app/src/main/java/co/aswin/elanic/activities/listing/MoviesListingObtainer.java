package co.aswin.elanic.activities.listing;

import co.aswin.elanic.model.MovieListingResponse;
import rx.Observable;

public interface MoviesListingObtainer {

    Observable<MovieListingResponse> fetchMovies(int page);
    Observable<MovieListingResponse> fetchSearchResultMovies(String query);
}
