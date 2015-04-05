package org.ray.anshuman.blank21;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    TinyDB tinyDB;
    ArrayList<String> habits;
    ArrayList<Integer> habitNos;
    ListView lvhabits;
    AlarmReceiver alarm = new AlarmReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tinyDB = new TinyDB(getApplicationContext());
        lvhabits = (ListView) findViewById(R.id.listViewhabits);
        habits = tinyDB.getList("Habits");
        habitNos = tinyDB.getListInt("HabitNos");

        if(habits.size() == 0) Toast("No habits saved. Add a new one!");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, habits);
        lvhabits.setAdapter(arrayAdapter);
        if(habits.size() > 0){
            lvhabits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    String selectedHabit = indexList.get(position)+"";
                    Intent intent = new Intent(MainActivity.this, HabitProgressActivity.class);
                    intent.putExtra(AddHabitActivity.HABITINDEX, habitNos.get(position));
                    startActivity(intent);
                }
            });
        }
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
        if (id == R.id.action_addhabit) {
            goToAddHabit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToAddHabit() {
        Intent intent = new Intent(this, AddHabitActivity.class);
        startActivity(intent);
    }


    public void Toast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
