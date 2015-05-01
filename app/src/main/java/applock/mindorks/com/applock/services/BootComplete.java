package applock.mindorks.com.applock.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by amitshekhar on 28/04/15.
 */
public class BootComplete extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("football ", "BootComplete");
        context.startService(new Intent(context, AppCheckServices.class));

          /*-------alarm setting after boot again--------*/
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 999, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int interval = (86400 * 1000) / 4;
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
    }
}
