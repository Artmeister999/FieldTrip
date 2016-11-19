package com.example.htrip3;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.NumberPicker;

public class CreateEventActivity extends AppCompatActivity {

    NumberPicker numberPicker = null;
    NumberPicker numberPicker2 = null;
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

    }
}
