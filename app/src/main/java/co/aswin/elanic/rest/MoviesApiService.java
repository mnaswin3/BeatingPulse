package co.aswin.elanic.rest;

import co.aswin.elanic.model.MovieListingResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

interface MoviesApiService {

    @GET("discover/movie?api_key=ed75ba81d3a8253393684108406b8e26&language=en-US&sort_by=popularity.desc&&page=2&include_adult=false&include_video=false")
    Observable<MovieListingResponse> getMovieListings(@Query("page") int page);

    @GET("search/movie?api_key=ed75ba81d3a8253393684108406b8e26&language=en-US&query=fight&page=1&include_adult=false")
    Observable<MovieListingResponse> getSearchResultMovieListings(@Query("query") String query);
}
