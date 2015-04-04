package org.ray.anshuman.blank21;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class HabitProgressActivity extends ActionBarActivity {

    int habitNo,DaysLeft;
    String habit;
    ArrayList<String> habits;
    ArrayList<Integer> habitNos;
    ArrayList<Integer> habitDays;
    TextView tvhabit,tvdaysleft;
    TinyDB tinyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_progress);

        tinyDB = new TinyDB(getApplicationContext());
        habits = tinyDB.getList("Habits");
        habitNos = tinyDB.getListInt("HabitNos");
        habitDays = tinyDB.getListInt("HabitDays");

        tvhabit = (TextView) findViewById(R.id.textViewhabit);
        tvdaysleft = (TextView) findViewById(R.id.textViewdaysleft);

        Intent intent = getIntent();
        habitNo = intent.getIntExtra(AddHabitActivity.HABITINDEX, 0);
        tvhabit.setText(habits.get(habitNos.indexOf(habitNo)));
        DaysLeft = habitDays.get(habitNos.indexOf(habitNo));
        tvdaysleft.setText(DaysLeft+"");
    }

    public void yep(View view){
        DaysLeft--;
        tvdaysleft.setText(DaysLeft+"");
        habitDays.set(habitNos.indexOf(habitNo),DaysLeft);
        saveState();
        Toast("Awesome! Good going.");
    }

    public void nope(View view){
        DaysLeft++;
        tvdaysleft.setText(DaysLeft+"");
        habitDays.set(habitNos.indexOf(habitNo),DaysLeft);
        saveState();
        Toast("Urgh, That's bad.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_habit_progress, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_deletehabit) {
//            tinyDB.remove(HabitIndex + "Habit");
//            tinyDB.remove(HabitIndex + "DaysLeft");
            int temp = habitNos.indexOf(habitNo);
            habitNos.remove(temp);
            habits.remove(temp);
            habitDays.remove(temp);
            saveState();
            Toast("Habit deleted");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void Toast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    private void saveState(){
        tinyDB.putListInt("HabitNos",habitNos);
        tinyDB.putList("Habits",habits);
        tinyDB.putListInt("HabitDays",habitDays);
    }
}
