package applock.mindorks.com.applock.services;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import applock.mindorks.com.applock.AppLockConstants;
import applock.mindorks.com.applock.R;
import applock.mindorks.com.applock.Utils.AppLockLogEvents;
import applock.mindorks.com.applock.Utils.SharedPreference;

/**
 * Created by amitshekhar on 01/05/15.
 */
public class NewAppInstalledReceiver extends BroadcastReceiver {

    SharedPreference sharedPreference;

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, AppCheckServices.class));
        sharedPreference = new SharedPreference();

        if (!intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED) && intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)) {
            return;
        }

        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String[] a = intent.getDataString().split(":");
            String packageName = a[a.length - 1];
            if (sharedPreference != null) {
                if (!sharedPreference.getPassword(context).isEmpty()) {
                    showDialogToAskForNewAppInstalled(context, appName(context, packageName), packageName);
                }
            }
        }

        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String[] a = intent.getDataString().split(":");
            String packageName = a[a.length - 1];
            sharedPreference.removeLocked(context, packageName);
        }
    }

    public String appName(Context context, String packageName) {
        try {
            ApplicationInfo app = context.getPackageManager().getApplicationInfo(packageName, 0);
            return context.getPackageManager().getApplicationLabel(app).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        return "";
    }

    public void showDialogToAskForNewAppInstalled(final Context context, String appName, final String packageName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
// Add the buttons

        builder.setTitle("App Lock")
                .setIcon(context.getResources().getDrawable(R.mipmap.ic_launcher))
                .setMessage("Do you want to lock " + appName + "?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        sharedPreference.addLocked(context, packageName);
                        AppLockLogEvents.logEvents(AppLockConstants.NEW_APP_INSTALL_DIALOG_BOX, "Lock Clicked", "lock_clicked", packageName);
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                AppLockLogEvents.logEvents(AppLockConstants.NEW_APP_INSTALL_DIALOG_BOX, "Cancel Clicked", "cancel_clicked", "");

            }
        });

// Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);

        dialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == KeyEvent.ACTION_UP) {
                    dialog.cancel();
                    AppLockLogEvents.logEvents(AppLockConstants.NEW_APP_INSTALL_DIALOG_BOX, "Cancel Clicked", "cancel_clicked", "");
                }
                return true;
            }
        });
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        dialog.show();
        AppLockLogEvents.logEvents(AppLockConstants.NEW_APP_INSTALL_DIALOG_BOX, "New App Installed Dialog Shown", "new_app_installed_dialog_shown", packageName);
    }


}
