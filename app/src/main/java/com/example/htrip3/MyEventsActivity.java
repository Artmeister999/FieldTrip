package com.example.htrip3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyEventsActivity extends AppCompatActivity {

    public static final String EVENT_ID_EXTRA = "event_name";
    public static final String EVENT_SIGNUP_USERS_EXTRA = "event_signed_up_users";

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private Map<String, List<String>> signedUpUsersList;

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

        // preparing list data
        prepareListData();
        //fetchEvents();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition,
                long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                    listDataHeader.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                    listDataHeader.get(groupPosition) + " Collapsed", Toast.LENGTH_SHORT).show();
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

                    if (!signedUpUsersList.get(eventName).contains(username)) {
                        Toast.makeText(getApplicationContext(), "You are not signed up for this event.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "CHAT", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MyEventsActivity.this, CHATActivity.class);
                        intent.putExtra(EVENT_ID_EXTRA, eventName);
                        intent.putStringArrayListExtra(EVENT_SIGNUP_USERS_EXTRA,
                            (ArrayList<String>) signedUpUsersList.get(eventName));
                        startActivity(intent);
                    }
                    return false;
                }

                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        signedUpUsersList = new HashMap<>();

        // Adding child data
        listDataHeader.add("Event 1");
        listDataHeader.add("Event 2");
        listDataHeader.add("Event 3");

        // Adding child data
        List<String> event_1 = new ArrayList<String>();
        event_1.add("Event Name");
        event_1.add("Event Title");
        event_1.add("mm/dd/yy 08:08pm");
        event_1.add("Event Location");
        event_1.add("Instrucions:");
        event_1.add("Spots Left: 3");
        event_1.add("CHAT");

        //this is just dummy data , once we have a table in the database with subscribed usersSignedUpToEvent1
        // we can use that
        List<String> usersSignedUpToEvent1 = new ArrayList<>();
        usersSignedUpToEvent1.add("jane@myhunter.cuny.edu");
        usersSignedUpToEvent1.add("bob@myhunter.cuny.edu");
        signedUpUsersList.put(listDataHeader.get(0), usersSignedUpToEvent1);

        String displaySignedUpUsersEvent1 = "Signed up users: ";
        for (String user : usersSignedUpToEvent1) {
            displaySignedUpUsersEvent1 += ", " + user;
        }
        event_1.add(displaySignedUpUsersEvent1);

        List<String> event_2 = new ArrayList<String>();
        event_2.add("Event Name");
        event_2.add("Event Title");
        event_2.add("mm/dd/yy 08:08pm");
        event_2.add("Event Location");
        event_2.add("Instrucions:");
        event_2.add("Spots Left: 4");
        event_2.add("CHAT");

        List<String> usersSignedUpToEvent2 = new ArrayList<>();
        signedUpUsersList.put(listDataHeader.get(1), usersSignedUpToEvent2);
        String displaySignedUpUsersEvent2 = "Signed up users: ";
        for (String user : usersSignedUpToEvent2) {
            displaySignedUpUsersEvent2 += ", " + user;
        }
        event_2.add(displaySignedUpUsersEvent2);

        List<String> event_3 = new ArrayList<String>();
        event_3.add("Event Name");
        event_3.add("Event Title");
        event_3.add("mm/dd/yy 08:08pm");
        event_3.add("Event Location");
        event_3.add("Instrucions:");
        event_3.add("Spots Left: 5");
        event_3.add("CHAT");

        List<String> usersSignedUpToEvent3 = new ArrayList<>();
        signedUpUsersList.put(listDataHeader.get(2), usersSignedUpToEvent3);
        String displaySignedUpUsersEvent3 = "Signed up users: ";
        for (String user : usersSignedUpToEvent3) {
            displaySignedUpUsersEvent3 += ", "+ user;
        }
        event_3.add(displaySignedUpUsersEvent3);

        listDataChild.put(listDataHeader.get(0), event_1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), event_2);
        listDataChild.put(listDataHeader.get(2), event_3);
    }

    //TODO: use this when events table is ok
    /*private void fetchEvents() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        try {
            MobileServiceClient mClient =
                new MobileServiceClient(Helpers.URL, MyEventsActivity.this);
            MobileServiceTable<Event> eventsTable = mClient.getTable(Event.class);

            eventsTable.execute(new TableQueryCallback<Event>() {
                @Override
                public void onCompleted(List<Event> events, int count, Exception exception,
                    ServiceFilterResponse response) {
                    int i = 0;
                    for (Event event : events) {
                        listDataHeader.add(event.getTitle());
                        //this just dummy data for now -> TODO: use whats on the server later
                        List<String> event_dummy_date = new ArrayList<String>();
                        event_dummy_date.add("Event Name");
                        event_dummy_date.add("Event Title");
                        event_dummy_date.add("mm/dd/yy 08:08pm");
                        event_dummy_date.add("Event Location");
                        event_dummy_date.add("Instrucions:");
                        event_dummy_date.add("Spots Left: 5");
                        event_dummy_date.add("CHAT");
                        listDataChild.put(listDataHeader.get(i), event_dummy_date);
                    }
                }
            });
        } catch (MobileServiceException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }*/
}
