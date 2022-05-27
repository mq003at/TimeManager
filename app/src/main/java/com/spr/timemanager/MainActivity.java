package com.spr.timemanager;

import static android.app.PendingIntent.FLAG_NO_CREATE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    Button button;
    long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        Intent intent = new Intent(this, AlarmReceiver.class);
        button.setOnClickListener(v -> {

            // Initialize time to setup
//            boolean isDaylightSavingNow = TimeZone.getTimeZone("Europe/Helsinki").inDaylightTime(new Date());
//            Log.e("Daylight now", String.valueOf(isDaylightSavingNow));

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 17);
            calendar.set(Calendar.MINUTE, 49);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                calendar.add(Calendar.DATE, 1);
            }
            time = calendar.getTimeInMillis();

            pendingIntent = PendingIntent.getBroadcast(this, (int) time, intent, PendingIntent.FLAG_ONE_SHOT);
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            Log.e("time", String.valueOf(time));
            Log.e("System", String.valueOf(System.currentTimeMillis()));

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    time, pendingIntent);

        });

    }
}

