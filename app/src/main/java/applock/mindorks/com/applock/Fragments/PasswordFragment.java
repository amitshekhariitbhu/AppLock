package applock.mindorks.com.applock.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.takwolf.android.lock9.Lock9View;

import applock.mindorks.com.applock.R;

/**
 * Created by amitshekhar on 30/04/15.
 */
public class PasswordFragment extends Fragment {
    private static final String KEY_TITLE = "title";
    Lock9View lock9View;

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

        View v = inflater.inflate(R.layout.fragment_password, container, false);
        lock9View = (Lock9View) v.findViewById(R.id.lock_9_view);
        lock9View.setCallBack(new Lock9View.CallBack() {
            @Override
            public void onFinish(String password) {
                Toast.makeText(getActivity(), password, Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}
