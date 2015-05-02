package applock.mindorks.com.applock.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.takwolf.android.lock9.Lock9View;

import applock.mindorks.com.applock.AppLockConstants;
import applock.mindorks.com.applock.Custom.FlatButton;
import applock.mindorks.com.applock.MainActivity;
import applock.mindorks.com.applock.R;
import applock.mindorks.com.applock.Utils.AppLockLogEvents;

/**
 * Created by amitshekhar on 30/04/15.
 */
public class PasswordFragment extends Fragment {
    Lock9View lock9View;
    FlatButton confirmButton, retryButton;
    TextView textView;
    boolean isEnteringFirstTime = true;
    boolean isEnteringSecondTime = false;
    String enteredPassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static PasswordFragment newInstance() {
        PasswordFragment f = new PasswordFragment();
        return (f);
    }

    public PasswordFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_password_set, container, false);

        lock9View = (Lock9View) v.findViewById(R.id.lock_9_view);
        confirmButton = (FlatButton) v.findViewById(R.id.confirmButton);
        retryButton = (FlatButton) v.findViewById(R.id.retryButton);
        textView = (TextView) v.findViewById(R.id.textView);
        confirmButton.setEnabled(false);
        retryButton.setEnabled(false);
        sharedPreferences = getActivity().getSharedPreferences(AppLockConstants.MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(AppLockConstants.PASSWORD, enteredPassword);
                editor.commit();

                editor.putBoolean(AppLockConstants.IS_PASSWORD_SET, true);
                editor.commit();

                Intent i = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(i);
                getActivity().finish();
                AppLockLogEvents.logEvents(AppLockConstants.MAIN_SCREEN, "Confirm Password", "confirm_password", "");

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
                AppLockLogEvents.logEvents(AppLockConstants.MAIN_SCREEN, "Retry Password", "retry_password", "");
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
                        Toast.makeText(getActivity(), "Both Pattern did not match - Try again", Toast.LENGTH_SHORT).show();
                        isEnteringFirstTime = true;
                        isEnteringSecondTime = false;
                        textView.setText("Draw Pattern");
                        retryButton.setEnabled(false);
                    }
                }
            }
        });
        return v;
    }

}
