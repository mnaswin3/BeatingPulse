package co.aswin.elanic.activities.listing;

import javax.inject.Singleton;

import co.aswin.elanic.BaseApplicationClass;
import dagger.Component;

@Singleton
@Component(modules = {MoviesListingModule.class})
public interface MoviesListingComponent {

    void inject(HomeScreenActivity activity);

    final class Initializer {
        private Initializer() {
        }

        public static MoviesListingComponent init(BaseApplicationClass app) {
            return DaggerMoviesListingComponent.builder()
                    .moviesListingModule(new MoviesListingModule(app))
                    .build();
        }
    }
}
