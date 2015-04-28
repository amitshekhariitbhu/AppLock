package applock.mindorks.com.applock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;

/**
 * Created by amitshekhar on 27/04/15.
 */
public class SettingsActivity extends ActionBarActivity {


    Button changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setIcon(R.drawable.abc_btn_rating_star_off_mtrl_alpha);
        actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>Settings</font>"));
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);

        changePassword = (Button) findViewById(R.id.changePassword);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, PasswordActivity.class);
                startActivity(i);
            }
        });

    }

}
