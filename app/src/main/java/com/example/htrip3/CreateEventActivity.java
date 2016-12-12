package com.example.htrip3;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import com.example.htrip3.model.Event;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;

public class CreateEventActivity extends AppCompatActivity {

    NumberPicker numberPicker = null;
    NumberPicker numberPicker2 = null;
    private MobileServiceTable<Event> eventTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        //Set up the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.create_event_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setLogo(R.drawable.ic_logo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        numberPicker = (NumberPicker) findViewById(R.id.nPick);
        numberPicker.setMaxValue(100);
        numberPicker.setMinValue(0);
        numberPicker.setWrapSelectorWheel(false);

        numberPicker2 = (NumberPicker) findViewById(R.id.nPick2);
        numberPicker2.setMaxValue(100);
        numberPicker2.setMinValue(0);
        numberPicker2.setWrapSelectorWheel(false);

        final EditText etEvTitleEditText = (EditText) findViewById(R.id.etEvTitle);

        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        final DatePicker datePicker2 = (DatePicker) findViewById(R.id.datePicker2);

        final EditText locationEditText = (EditText) findViewById(R.id.etLocation);
        final EditText instructionsEditText = (EditText) findViewById(R.id.etInstructions);

        final NumberPicker minNumberPicker = (NumberPicker) findViewById(R.id.nPick);
        final NumberPicker maxNumberPicker = (NumberPicker) findViewById(R.id.nPick2);

        final EditText etEvDescriptionEditText = (EditText) findViewById(R.id.etEvDescription);
        final EditText etEvTypeEditText = (EditText) findViewById(R.id.etEvType);

        try {
            MobileServiceClient client = new MobileServiceClient(Helpers.URL, this);
            eventTable = client.getTable(Event.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        final Button createButton = (Button) findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Event event = new Event();
                event.description = String.valueOf(etEvDescriptionEditText.getText());
                event.title = String.valueOf(etEvTitleEditText.getText());
                event.type = String.valueOf(etEvTypeEditText.getText());
                event.date = getDateFrom(datePicker2);
                event.time = getTimeFrom(timePicker);
                event.location = String.valueOf(locationEditText.getText());
                event.instructions = String.valueOf(instructionsEditText.getText());
                event.deleted = false;
                event.createdAt = new Date();
                event.min = minNumberPicker.getValue();
                event.max = maxNumberPicker.getValue();
                eventTable.insert(event, new TableOperationCallback<Event>() {
                    @Override
                    public void onCompleted(Event entity, Exception exception,
                        ServiceFilterResponse response) {
                        Log.d("TEST", " on event inserted");
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }
        });
    }

    private String getTimeFrom(TimePicker timePicker) {
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        return String.format("%02d", hour) + ":" + String.format("%02d", minute);
    }

    private Date getDateFrom(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();

        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day, 0, 0);

        return c.getTime();
    }
}
