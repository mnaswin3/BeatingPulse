package co.aswin.elanic.rest;

import co.aswin.elanic.model.MovieListingResponse;
import rx.Observable;

public class RetroFitServiceImpl implements RetroFitService {

    private RetroFitClient retroFitClient;

    public RetroFitServiceImpl(RetroFitClient retroFitClient){
        this.retroFitClient = retroFitClient;
    }

    @Override
    public Observable<MovieListingResponse> getMovieListingsResponse(int page) {
        MoviesApiService service = retroFitClient.createClient();
        return service.getMovieListings(page);
    }

    @Override
    public Observable<MovieListingResponse> getSearchResultMovieListingsResponse(String query) {
        MoviesApiService service = retroFitClient.createClient();
        return service.getSearchResultMovieListings(query);
    }
}
