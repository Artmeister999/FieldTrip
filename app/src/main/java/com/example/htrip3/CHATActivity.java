package com.example.htrip3;

import android.accounts.*;
import android.accounts.Account;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceJsonTable;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutionException;


public class CHATActivity extends AppCompatActivity {


    private String URL = "http://demohunter.azurewebsites.net";
    private MobileServiceTable<Message> accTable;
    private MobileServiceClient mClient;

    private TextView chatroom;
    private String text123;
    private EditText text5;
    private Button button5;
    private TextView tvFotgotPassword;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        try {
            MobileServiceClient mClient = new MobileServiceClient(URL, CHATActivity.this);

            accTable = mClient.getTable(Message.class);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }



        button5 = (Button) findViewById(R.id.button5);


        button5.setOnClickListener(new View.OnClickListener() {

                                       public void onClick(View view) {


                                           Message myAcc = new Message();


                                           text5 = (EditText) findViewById(R.id.text5);
                                           chatroom = (TextView) findViewById(R.id.chatroom);

                                           myAcc.TEXT = text5.getText().toString();
                                           myAcc.USERINFO = "Arti Yagushenko";
                                           accTable.insert(myAcc);

                                           text123 = myAcc.TEXT;

                                           // Date xx= myAcc.updatedAt;


                                           System.out.println("Requesting");

                                           System.out.println("Sending request");
                                           List<Message> messages; //.get();
                                           accTable.where().field("USERINFO").eq("Arti Yagushenko").execute(new TableQueryCallback<Message>() {
                                               @Override
                                               public void onCompleted(List<Message> result, int count, Exception exception, ServiceFilterResponse response) {
                                                   Log.d("TEST", "onCompleted: " + result.size());
                                               }
                                           });

                                       }
                                   });


                //Set up the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setLogo(R.drawable.ic_logo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("CHAT Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
