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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.takwolf.android.lock9.Lock9View;

import applock.mindorks.com.applock.AppLockConstants;
import applock.mindorks.com.applock.MainActivity;
import applock.mindorks.com.applock.R;

/**
 * Created by amitshekhar on 30/04/15.
 */
public class PasswordFragment extends Fragment {
    private static final String KEY_TITLE = "title";
    Lock9View lock9View;
    Button confirmButton, retryButton;
    TextView textView;
    boolean isEnteringFirstTime = true;
    boolean isEnteringSecondTime = false;
    String enteredPassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static PasswordFragment newInstance(String title) {
        PasswordFragment f = new PasswordFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        f.setArguments(args);
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
        confirmButton = (Button) v.findViewById(R.id.confirmButton);
        retryButton = (Button) v.findViewById(R.id.retryButton);
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
