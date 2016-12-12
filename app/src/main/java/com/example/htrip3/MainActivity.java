package com.example.htrip3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.htrip3.model.Event;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CREATE_EVENT_REQUEST_CODE = 200;
    List<Event> myEvents = new ArrayList<>();

    private ListView eventList;
    private MobileServiceTable<Event> eventTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setLogo(R.drawable.ic_logo);

        try {
            MobileServiceClient client = new MobileServiceClient(Helpers.URL, this);
            eventTable = client.getTable(Event.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        eventTable.select().execute(new TableQueryCallback<Event>() {
            @Override
            public void onCompleted(List<Event> events, int count, Exception exception,
                ServiceFilterResponse response) {
                myEvents.addAll(events);
                displayEventsList();
            }
        });
    }

    private void displayEventsList() {
        eventList = (ListView) findViewById(R.id.lwList);
        eventList.setAdapter(new EventAdapter(this, myEvents));

        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, JoinInActivity.class);
                intent.putExtra(JoinInActivity.EVENT_EXTRA, myEvents.get(position));
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
        } else if (id == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, CreateEventActivity.class);
            startActivityForResult(intent, CREATE_EVENT_REQUEST_CODE);
            return true;
        } else if (id == R.id.action_log_out) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.action_my_events) {
            Intent intent = new Intent(MainActivity.this, MyEventsActivity.class);
            startActivity(intent);
            return true;
        } else

        {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    static class EventAdapter extends BaseAdapter {

        private final List<Event> events;
        private LayoutInflater inflater;

        public EventAdapter(@NonNull Context context, List<Event> events) {
            inflater = LayoutInflater.from(context);
            this.events = events;
        }

        @Override
        public int getCount() {
            return events.size();
        }

        @Override
        public Object getItem(int i) {
            return events.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.da_item, null);
                holder = new ViewHolder();
                holder.eventTitle = (TextView) convertView.findViewById(R.id.evtTitle);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Event event = this.events.get(position);

            holder.eventTitle.setText(event.getTitle());
            return convertView;
        }

        static class ViewHolder {
            private TextView eventTitle;
        }
    }
}
