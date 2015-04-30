package applock.mindorks.com.applock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.takwolf.android.lock9.Lock9View;

/**
 * Created by amitshekhar on 30/04/15.
 */
public class PasswordSetActivity extends AppCompatActivity {
    Lock9View lock9View;
    Button confirmButton, retryButton;
    TextView textView;
    boolean isEnteringFirstTime = true;
    boolean isEnteringSecondTime = false;
    String enteredPassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_set);
        lock9View = (Lock9View) findViewById(R.id.lock_9_view);
        confirmButton = (Button) findViewById(R.id.confirmButton);
        retryButton = (Button) findViewById(R.id.retryButton);
        textView = (TextView) findViewById(R.id.textView);
        confirmButton.setEnabled(false);
        retryButton.setEnabled(false);
        sharedPreferences = getSharedPreferences(AppLockConstants.MyPREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(AppLockConstants.PASSWORD, enteredPassword);
                editor.commit();

                editor.putBoolean(AppLockConstants.IS_PASSWORD_SET, true);
                editor.commit();

                Intent i = new Intent(PasswordSetActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEnteringFirstTime = true;
                isEnteringSecondTime = false;
                textView.setText("Draw Pattern");
                confirmButton.setEnabled(false);
                retryButton.setEnabled(false);
            }
        });

        lock9View.setCallBack(new Lock9View.CallBack() {
            @Override
            public void onFinish(String password) {
                retryButton.setEnabled(true);
                if (isEnteringFirstTime) {
                    enteredPassword = password;
                    isEnteringFirstTime = false;
                    isEnteringSecondTime = true;
                    textView.setText("Re-Draw Pattern");
                } else if (isEnteringSecondTime) {
                    if (enteredPassword.matches(password)) {
                        confirmButton.setEnabled(true);
                    } else {
                        Toast.makeText(getApplicationContext(), "Both Pattern did not match - Try again", Toast.LENGTH_SHORT).show();
                        isEnteringFirstTime = true;
                        isEnteringSecondTime = false;
                        textView.setText("Draw Pattern");
                        retryButton.setEnabled(false);
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
