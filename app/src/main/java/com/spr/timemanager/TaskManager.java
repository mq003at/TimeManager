package com.spr.timemanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

public class TaskManager {
    private final Context context;
    private final Intent intent;
    private AlarmManager alarmManager;
    private PendingIntent myPendingIntent;
    private BroadcastReceiver myBroadcastReceiver;


    public TaskManager(Context context) {
        this.context = context;
        intent = new Intent("com.spr.timmanager");
    }

    public void logout_schedule(long timeInMillisecond) {
        myBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Logout employee
                Toast.makeText(context, "Schedule employee check: complete!", Toast.LENGTH_SHORT).show();
                Log.e("Alarm", String.valueOf(timeInMillisecond));

            }
        };
        context.registerReceiver(myBroadcastReceiver, new IntentFilter("com.spr.timmanager"));
        myPendingIntent = PendingIntent.getBroadcast(context, 9, intent, 0);
        alarmManager = (AlarmManager) (context.getSystemService(Context.ALARM_SERVICE));
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillisecond, 20000,myPendingIntent);
    }

    public void unregister_schedule() {
        alarmManager.cancel(myPendingIntent);
        context.unregisterReceiver(myBroadcastReceiver);
    }

}