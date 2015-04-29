package applock.mindorks.com.applock.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import applock.mindorks.com.applock.R;

/**
 * Created by amitshekhar on 30/04/15.
 */
public class SettingFragment extends Fragment {
    private static final String KEY_TITLE = "title";
    Button changePassword;

    public static SettingFragment newInstance(String title) {
        SettingFragment f = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        f.setArguments(args);
        return (f);
    }


    public SettingFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        changePassword = (Button) v.findViewById(R.id.changePassword);
       

        return v;
    }

}
