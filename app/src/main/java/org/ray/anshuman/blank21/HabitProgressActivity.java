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


public class HabitProgressActivity extends ActionBarActivity {

    int HabitIndex,DaysLeft;
    String habit;
    TextView tvhabit,tvdaysleft;
    TinyDB tinyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_progress);
        tinyDB = new TinyDB(getApplicationContext());
        tvhabit = (TextView) findViewById(R.id.textViewhabit);
        tvdaysleft = (TextView) findViewById(R.id.textViewdaysleft);
        Intent intent = getIntent();
        HabitIndex = intent.getIntExtra(AddHabitActivity.HABITINDEX, 0);
        habit = tinyDB.getString(HabitIndex + "Habit");
        DaysLeft = tinyDB.getInt(HabitIndex+"DaysLeft");
        tvhabit.setText(habit);
        tvdaysleft.setText(DaysLeft+"");
    }

    public void yep(View view){
        DaysLeft--;
        tvdaysleft.setText(DaysLeft+"");
        tinyDB.putInt(HabitIndex+"DaysLeft",DaysLeft);
        Toast("Awesome! Good going.");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void nope(View view){
        DaysLeft++;
        tvdaysleft.setText(DaysLeft+"");
        tinyDB.putInt(HabitIndex+"DaysLeft",DaysLeft);
        Toast("Urgh, That's bad.");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
            tinyDB.putString(HabitIndex+"Habit","Deleted habit");
            tinyDB.putInt(HabitIndex+"DaysLeft",-1);
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
}
