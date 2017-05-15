package co.aswin.elanic.activities.listing;

import javax.inject.Singleton;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

import co.aswin.elanic.BaseApplicationClass;
import co.aswin.elanic.rest.RetroFitClient;
import co.aswin.elanic.rest.RetroFitClientImpl;
import co.aswin.elanic.rest.RetroFitService;
import co.aswin.elanic.rest.RetroFitServiceImpl;

@Module
public class MoviesListingModule {

    private BaseApplicationClass application;

    public MoviesListingModule(BaseApplicationClass application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    RetroFitClient provideRetroFitClientService(){
        return new RetroFitClientImpl();
    }

    @Provides
    RetroFitService provideRetroFitService(RetroFitClient retroFitClient){
        return new RetroFitServiceImpl(retroFitClient);
    }

    @Provides
    MoviesListingObtainer provideMovieListingObtainer(RetroFitService retroFitService)
    {
        return new MoviesListingObtainerImpl(retroFitService);
    }

    @Provides
    MoviesListingService provideListingService(MoviesListingObtainer interactor)
    {
        return new MoviesListingServiceImpl(interactor);
    }
}
