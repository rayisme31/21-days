package org.ray.anshuman.blank21;

/**
 * Created by prayashm on 4/4/15.
 */

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmReceiver extends WakefulBroadcastReceiver {


    ArrayList<Integer> habitNos;
    ArrayList<String> habits;
    Integer habitNo;
    String habit;
    TinyDB tinyDB;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);

        tinyDB = new TinyDB(context);
        habitNos = tinyDB.getListInt("HabitNos");
        habits = tinyDB.getList("Habits");
        habitNo = intent.getIntExtra(AddHabitActivity.HABITINDEX, 0);
        Log.d("habitNo", habitNo + "");
        habit = habits.get(habitNos.indexOf(habitNo));
        // Notify User
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(habit)
                        .setContentText("Have you done it today?")
                        .setOngoing(true)
                        .setShowWhen(true)
                        .setAutoCancel(true);

        Intent resultIntent = new Intent(context, HabitProgressActivity.class);
        resultIntent.putExtra(AddHabitActivity.HABITINDEX,habitNo);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        habitNo,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        // Sets the notifications click behaviour
        mBuilder.setContentIntent(resultPendingIntent);

        int mNotificationId = habitNo;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(mNotificationId, mBuilder.build());
    }

    public void setAlarm(Context context, int paramHabitNo, int HH, int MM) {
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AddHabitActivity.HABITINDEX,paramHabitNo);

        alarmIntent = PendingIntent.getBroadcast(context, paramHabitNo, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // Set the alarm's trigger time to HH:MM
        calendar.set(Calendar.HOUR_OF_DAY, HH);
        calendar.set(Calendar.MINUTE, MM);

        // Set the alarm to fire at approximately 8:30 a.m., according to the device's
        // clock, and to repeat once a day.
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, alarmIntent);
        // Enable {@code SampleBootReceiver} to automatically restart the alarm when the
        // device is rebooted.
//        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
//        PackageManager pm = context.getPackageManager();
//
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);
    }
    // END_INCLUDE(set_alarm)

//    /**
//     * Cancels the alarm.
//     * @param context
//     */
    // BEGIN_INCLUDE(cancel_alarm)
    public void cancelAlarm(Context context, int paramHabitNo) {
        // If the alarm has been set, cancel it.
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AddHabitActivity.HABITINDEX, paramHabitNo);
        alarmIntent = PendingIntent.getBroadcast(context, paramHabitNo, intent, 0);

        if (alarmMgr != null) {
            alarmMgr.cancel(alarmIntent);
        }
    }

//        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the
//        // alarm when the device is rebooted.
//        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
//        PackageManager pm = context.getPackageManager();
//
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                PackageManager.DONT_KILL_APP);
//    }
}
