package com.example.htrip3;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.htrip3.model.Event;
import com.example.htrip3.model.Joined;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JoinInActivity extends AppCompatActivity {

    public static final String EVENT_EXTRA = "EVENT_EXTRA";

    private MobileServiceTable<Joined> joinedTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_in);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.join_in_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setLogo(R.drawable.ic_logo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final String username = ((HtripApp) getApplicationContext()).getUsername();
        final Event event = getIntent().getParcelableExtra(EVENT_EXTRA);

        final TextView eventTitle = (TextView) findViewById(R.id.tvEventTitle);
        eventTitle.setText(event.getTitle());

        final TextView eventDescription = (TextView) findViewById(R.id.tvEventDescription);
        eventDescription.setText(event.getDescription());

        final TextView eventType = (TextView) findViewById(R.id.tvEventType);
        eventType.setText(event.getType());

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String date = sdf.format(event.getDate()) + " " + event.getTime();
        final TextView eventDate = (TextView) findViewById(R.id.tvEventDate);
        eventDate.setText(date);

        final TextView eventLocation = (TextView) findViewById(R.id.tvEventLocation);
        eventLocation.setText(event.getLocation());

        final TextView eventInstructions = (TextView) findViewById(R.id.tvEventInstructions);
        eventInstructions.setText(event.getInstructions());

        try {
            MobileServiceClient client = new MobileServiceClient(Helpers.URL, this);
            joinedTable = client.getTable(Joined.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        final Button joinButton = (Button) findViewById(R.id.joinButton);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Joined joined = new Joined();
                joined.userId = username;
                joined.eventId = event.id;
                joined.createdAt = new Date();
                joined.updatedAt = new Date();
                joined.deleted = false;
                joinedTable.insert(joined, new TableOperationCallback<Joined>() {
                    @Override
                    public void onCompleted(Joined entity, Exception exception,
                        ServiceFilterResponse response) {
                        Log.d("TEST", "Joined inserted.");
                        Snackbar.make(findViewById(R.id.main_content), R.string.you_joined_event,
                            Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
