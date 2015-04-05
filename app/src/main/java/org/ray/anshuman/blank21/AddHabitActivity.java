package org.ray.anshuman.blank21;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcel;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class AddHabitActivity extends ActionBarActivity {

    public static final String HABITINDEX = "org.ray.anshuman.blank21.AddHabitActivity.HABITINDEX";
    EditText editTexthabit;
    TimePicker timePicker;
    String habit;
    ArrayList<String> habits;
    ArrayList<Integer> habitNos;
    ArrayList<Integer> habitDays;
    ArrayList<Integer> habitTimes;
    int habitNo = -1;
    Long time;
    TinyDB tinyDB;
    AlarmReceiver alarm = new AlarmReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        tinyDB = new TinyDB(getApplicationContext());
        habits = tinyDB.getList("Habits");
        habitNos = tinyDB.getListInt("HabitNos");
        habitDays = tinyDB.getListInt("HabitDays");
        habitTimes = tinyDB.getListInt("HabitTimes");
        if (habitNos.size() != 0) habitNo = habitNos.get(habitNos.size()-1);

        editTexthabit = (EditText) findViewById(R.id.editTexthabit);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_habit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_savehabit) {
            saveHabit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void saveHabit(){
        habit = editTexthabit.getText().toString();
        int HH = timePicker.getCurrentHour();
        int MM = timePicker.getCurrentMinute();

        habitNo++;
        habitNos.add(habitNo);
        habits.add(habit);
        habitDays.add(21);
        habitTimes.add(HH*100+MM);
        tinyDB.putListInt("HabitNos", habitNos);
        tinyDB.putList("Habits",habits);
        tinyDB.putListInt("HabitDays",habitDays);
        tinyDB.putListInt("HabitTimes",habitTimes);
        alarm.setAlarm(this,habitNo,HH,MM);
        goToMain();
    }

    public void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Toast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
