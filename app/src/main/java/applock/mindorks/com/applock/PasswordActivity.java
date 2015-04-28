package applock.mindorks.com.applock;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.widget.Toast;

import com.takwolf.android.lock9.Lock9View;

/**
 * Created by amitshekhar on 28/04/15.
 */
public class PasswordActivity extends ActionBarActivity {

    Lock9View lock9View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setIcon(R.drawable.abc_btn_rating_star_off_mtrl_alpha);
        actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>Password change</font>"));

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);

        lock9View = (Lock9View) findViewById(R.id.lock_9_view);
        lock9View.setCallBack(new Lock9View.CallBack() {
            @Override
            public void onFinish(String password) {
                Toast.makeText(PasswordActivity.this, password, Toast.LENGTH_SHORT).show();
            }
        });

    }

}

