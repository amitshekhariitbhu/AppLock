package applock.mindorks.com.applock;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by amitshekhar on 27/04/15.
 */
public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.abc_btn_rating_star_off_mtrl_alpha);

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

}
