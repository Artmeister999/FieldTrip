package com.example.htrip3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;
import com.example.htrip3.model.Event;
import com.example.htrip3.model.Joined;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyEventsActivity extends AppCompatActivity {

    public static final String EVENT_ID_EXTRA = "event_name";

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private MobileServiceTable<Event> eventTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        //Set up the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_events_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setLogo(R.drawable.ic_logo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        fetchEvents();
    }

    private void createEventsUi() {
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition,
                long id) {
                return false;
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                int childPosition, long id) {
                // TODO Auto-generated method stub

                if (childPosition == 6) {
                    String username = ((HtripApp) getApplicationContext()).getUsername();
                    final String eventName = listDataHeader.get(groupPosition);

                    final int childDataSize = listDataChild.get(eventName).size();
                    if (listDataChild.get(eventName).get(childDataSize - 1).contains(username)) {
                        Toast.makeText(getApplicationContext(), "CHAT", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MyEventsActivity.this, CHATActivity.class);
                        intent.putExtra(EVENT_ID_EXTRA, eventName);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                            "You are not signed up for this event.", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }

                return false;
            }
        });
    }

    private void fetchEvents() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        try {
            final MobileServiceClient client = new MobileServiceClient(Helpers.URL, this);
            eventTable = client.getTable(Event.class);

            eventTable.select().execute(new TableQueryCallback<Event>() {
                @Override
                public void onCompleted(List<Event> events, int count, Exception exception,
                    ServiceFilterResponse response) {
                    Log.d("TEST", "Query done");
                    final int[] i = { 0 };
                    for (Event event : events) {
                        listDataHeader.add(event.getTitle());

                        final String eventId = event.getId();

                        final List<String> eventData = new ArrayList<String>();
                        eventData.add(event.getTitle());
                        eventData.add(event.getDescription());

                        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                        eventData.add(sdf.format(event.getDate()) + " " + event.getTime());

                        eventData.add("Location: " + event.getLocation());
                        eventData.add("Instructions: " + event.getInstructions());
                        eventData.add("Spots Left: "
                            + event.getMax()); //TODO: put - already subscribed members
                        eventData.add("CHAT");

                        MobileServiceTable<Joined> joinedTable = client.getTable(Joined.class);
                        joinedTable.where()
                            .field("event_id")
                            .eq(eventId)
                            .execute(new TableQueryCallback<Joined>() {
                                @Override
                                public void onCompleted(List<Joined> joined, int count,
                                    Exception exception, ServiceFilterResponse response) {

                                    String joinedUsers = "";
                                    for (Joined j : joined) {
                                        joinedUsers += j.getUserId() + ", ";
                                    }
                                    eventData.add(joinedUsers);
                                    listDataChild.put(listDataHeader.get(i[0]), eventData);
                                    i[0]++;
                                }
                            });
                    }
                    createEventsUi();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
