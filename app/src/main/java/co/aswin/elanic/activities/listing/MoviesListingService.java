package co.aswin.elanic.activities.listing;

interface MoviesListingService {

    void setView(MoviesListingInteractor view);
    void obtainMovies(int page);
    void obtainMovies(String query);
    void destroy();
}
