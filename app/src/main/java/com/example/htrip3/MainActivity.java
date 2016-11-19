package com.example.htrip3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;


public class MainActivity extends AppCompatActivity {

    String[] myItems = {"Event 1", "Event 2", "Event 3", "Event 4", "Event 5", "Event 6"
            , "Event 7", "Event 8", "Event 9", "Event 10", "Event 11", "Event 12", "Event 13", "Event 14", "Event 15"};


    private MobileServiceClient mClient;
    private ListView eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setLogo(R.drawable.ic_logo);

        eventList = (ListView) findViewById(R.id.lwList);
        eventList.setAdapter(new ArrayAdapter<String>(this, R.layout.da_item, myItems));




        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, JoinInActivity.class);
                startActivity(intent);
            }
        });
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
        if (id == R.id.about) {
            return true;
        }
        else if (id == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, CreateEventActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_log_out) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        else if (id == R.id.action_my_events) {
            Intent intent = new Intent(MainActivity.this, MyEventsActivity.class);
            startActivity(intent);
            return true;
        }

        else

        return super.onOptionsItemSelected(item);
    }
}
