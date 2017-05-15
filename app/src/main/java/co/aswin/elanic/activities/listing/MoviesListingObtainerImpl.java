package co.aswin.elanic.activities.listing;

import rx.Observable;

import co.aswin.elanic.model.MovieListingResponse;
import co.aswin.elanic.rest.RetroFitService;

class MoviesListingObtainerImpl implements MoviesListingObtainer {

    private RetroFitService retroFitService;

     MoviesListingObtainerImpl(RetroFitService retroFitService){
        this.retroFitService = retroFitService;
    }

    @Override
    public Observable<MovieListingResponse> fetchMovies(int page) {
        return retroFitService.getMovieListingsResponse(page);
    }

    @Override
    public Observable<MovieListingResponse> fetchSearchResultMovies(String query) {
        return retroFitService.getSearchResultMovieListingsResponse(query);
    }
}
