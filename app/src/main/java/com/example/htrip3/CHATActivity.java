package com.example.htrip3;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.htrip3.model.Message;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.htrip3.MyEventsActivity.EVENT_ID_EXTRA;
import static com.example.htrip3.MyEventsActivity.EVENT_SIGNUP_USERS_EXTRA;

public class CHATActivity extends AppCompatActivity {

    private MobileServiceTable<Message> accTable;
    private MobileServiceClient mClient;

    private RecyclerView chatroomView;
    private String text123;
    private EditText text5;
    private Button button5;
    private TextView tvFotgotPassword;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ChatAdapter chatAdapter;
    private String username;
    private String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        try {
            MobileServiceClient mClient = new MobileServiceClient(Helpers.URL, CHATActivity.this);

            accTable = mClient.getTable(Message.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        button5 = (Button) findViewById(R.id.button5);
        chatroomView = (RecyclerView) findViewById(R.id.chatroom_list);
        chatroomView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter();
        chatroomView.setAdapter(chatAdapter);

        username = ((HtripApp) getApplicationContext()).getUsername();

        displayChat();

        button5.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Message myAcc = new Message();

                text5 = (EditText) findViewById(R.id.text5);

                myAcc.TEXT = text5.getText().toString();
                myAcc.USERINFO = username;
                myAcc.eventId = eventId;
                accTable.insert(myAcc);

                text123 = myAcc.TEXT;

                displayChat();
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

    private void displayChat() {
        accTable.where()
            .field("eventId")
            .eq(eventId)
            .execute(new TableQueryCallback<Message>() {
                @Override
                public void onCompleted(List<Message> result, int count,
                    Exception exception, ServiceFilterResponse response) {
                    chatAdapter.setMessages(result);
                    chatAdapter.notifyDataSetChanged();
                }
            });
    }

    class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatItemViewHolder> {

        private List<Message> messages;

        ChatAdapter() {
            messages = new ArrayList<>();
        }

        public void setMessages(List<Message> messages) {
            this.messages.clear();
            if (messages != null) {
                this.messages.addAll(messages);
            }
        }

        @Override
        public ChatItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item_layout, parent, false);
            return new ChatItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ChatItemViewHolder holder, int position) {
            holder.userInfo.setText(messages.get(position).USERINFO);
            holder.text.setText(" : " + messages.get(position).TEXT);
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        public class ChatItemViewHolder extends RecyclerView.ViewHolder {
            TextView userInfo;
            TextView text;

            public ChatItemViewHolder(View v) {
                super(v);
                this.userInfo = (TextView) v.findViewById(R.id.user_info_tv);
                this.text = (TextView) v.findViewById(R.id.text_tv);
            }
        }

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object =
            new Thing.Builder().setName("CHAT Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]")).build();
        return new Action.Builder(Action.TYPE_VIEW).setObject(object)
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
