package applock.mindorks.com.applock;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import applock.mindorks.com.applock.Adapter.ApplicationListAdapter;
import applock.mindorks.com.applock.Data.AppInfo;
import applock.mindorks.com.applock.services.AppCheckServices;


public class MainActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.abc_btn_rating_star_off_mtrl_alpha);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new ApplicationListAdapter(getListOfInstalledApp(getApplicationContext()), getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);


        startService(new Intent(MainActivity.this, AppCheckServices.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * get the list of all installed applications in the device
     *
     * @return ArrayList of installed applications or null
     */
    private static List<AppInfo> getListOfInstalledApp(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<AppInfo> installedApps = new ArrayList();
        List<PackageInfo> apps = packageManager.getInstalledPackages(PackageManager.SIGNATURE_MATCH);
        if (apps != null && !apps.isEmpty()) {

            for (int i = 0; i < apps.size(); i++) {
                PackageInfo p = apps.get(i);
                ApplicationInfo appInfo = null;
                try {
                    appInfo = packageManager.getApplicationInfo(p.packageName, 0);
                    AppInfo app = new AppInfo();
                    app.setName(p.applicationInfo.loadLabel(packageManager).toString());
                    app.setPackageName(p.packageName);
                    app.setVersionName(p.versionName);
                    app.setVersionCode(p.versionCode);
                    app.setIcon(p.applicationInfo.loadIcon(packageManager));

                    //check if the application is not an application system
//                    Intent launchIntent = app.getLaunchIntent(context);
//                    if (launchIntent != null && (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
                    installedApps.add(app);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }

            //sort the list of applications alphabetically
//            if (installedApps.size() > 0) {
//                Collections.sort(installedApps, new Comparator() {
//
//                    @Override
//                    public int compare(final AppInfo app1, final AppInfo app2) {
//                        return app1.getName().toLowerCase(Locale.getDefault()).compareTo(app2.getName().toLowerCase(Locale.getDefault()));
//                    }
//                });
//            }
            return installedApps;
        }
        return null;
    }


}
