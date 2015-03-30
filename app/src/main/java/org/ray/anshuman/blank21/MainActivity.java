package org.ray.anshuman.blank21;

import android.content.Intent;
import android.os.Bundle;
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
    String habit;
    ListView lvhabits;
    int HabitIndex;
    ArrayList<String> habitsList;
    ArrayList<Integer> indexList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tinyDB = new TinyDB(getApplicationContext());
        HabitIndex = tinyDB.getInt("MaxHabitIndex");
//        Toast(HabitIndex+"");
        lvhabits = (ListView) findViewById(R.id.listViewhabits);

        habitsList = new ArrayList<String>();
        indexList = new ArrayList<Integer>();
        getHabitList();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, habitsList);
        lvhabits.setAdapter(arrayAdapter);
        if(HabitIndex > 0){
            lvhabits.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedHabit = indexList.get(position)+"";
                    Toast(position+"|"+selectedHabit);
                    Intent intent = new Intent(MainActivity.this, HabitProgressActivity.class);
                    intent.putExtra(AddHabitActivity.HABITINDEX, selectedHabit);
                    startActivity(intent);
                }
            });
        }


        // Fetch HABIT from tinyDB
        habit = tinyDB.getString(HabitIndex+"Habit");
        if (habit == "") habit = "No saved habits.";
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

    void getHabitList(){
        String temp;
        int j = 0;
        if (HabitIndex > 0)
            for (int i = 1; i<=HabitIndex; i++) {
                if (tinyDB.getInt(i + "DaysLeft") != -1) {
//                    Toast(tinyDB.getInt(i+"DaysLeft")+"");
                    habitsList.add(++j+". "+tinyDB.getString(i+"Habit"));
                    indexList.add(i);
                }
            }
        else
            habitsList.add("No habits added yet.");
    }

    public void Toast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
