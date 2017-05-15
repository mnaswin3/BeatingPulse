package co.aswin.elanic;

import android.app.Application;

import co.aswin.elanic.activities.listing.MoviesListingComponent;

public class BaseApplicationClass extends Application{

    private static MoviesListingComponent graph;
    private static BaseApplicationClass instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        buildComponentGraph();
    }

    public static MoviesListingComponent component() {
        return graph;
    }

    public static void buildComponentGraph() {
        graph = MoviesListingComponent.Initializer.init(instance);
    }
}
