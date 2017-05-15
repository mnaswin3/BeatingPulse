package co.aswin.elanic.rest;


import co.aswin.elanic.model.MovieListingResponse;
import rx.Observable;

public interface RetroFitService {

    Observable<MovieListingResponse> getMovieListingsResponse(int page);
    Observable<MovieListingResponse> getSearchResultMovieListingsResponse(String query);
}
