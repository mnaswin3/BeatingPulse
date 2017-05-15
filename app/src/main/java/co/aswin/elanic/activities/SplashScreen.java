package co.aswin.elanic.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.aswin.elanic.R;
import co.aswin.elanic.activities.listing.HomeScreenActivity;

/**
 * Starting Activity launched by the application.
 */
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        startHomeScreenActivity();
    }

    /**
     * Starts {@link HomeScreenActivity} after 500ms.
     */
    private void startHomeScreenActivity() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, HomeScreenActivity.class));
                finish();
            }
        };
        handler.postDelayed(runnable, 500);
    }
}
