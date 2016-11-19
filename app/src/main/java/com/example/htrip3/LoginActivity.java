package com.example.htrip3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;


import java.net.MalformedURLException;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;
import com.microsoft.windowsazure.mobileservices.table.query.Query;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOperations;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
import com.squareup.okhttp.OkHttpClient;

import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceAuthenticationProvider;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;
public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView tvSignUp;
    private TextView tvFotgotPassword;
    private EditText txtEmail;
    private EditText txtPassword;
    private TextView textLoginMessage;
    private Boolean UserTypedInCorrect=false;
    public List<Account> results=new ArrayList<Account>();
    private MobileServiceClient mClient;
    private String URL="http://demohunter.azurewebsites.net";
    /*
    private Button btnRegisterBack;
    private MobileServiceTable <Account> accTable;
    */
    private MobileServiceTable<Account> accTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {






        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Intent intent = new Intent(LoginActivity.this, CHATActivity.class);
        startActivity(intent);


        //Set up the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setLogo(R.drawable.ic_logo);

        try {
            mClient = new MobileServiceClient(URL,LoginActivity.this);

            accTable= mClient.getTable(Account.class);
            //results = accTable.execute().get();

            //CheckFromtable(txtEmail.getText().toString(),txtPassword.getText().toString());
        } catch (Exception exception) {
//	                createAndShowDialog(exception, "Error");
        }



        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        tvFotgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        textLoginMessage = (TextView) findViewById(R.id.textLoginMessage);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


                CheckFromtable(txtEmail.getText().toString(),txtPassword.getText().toString());




                /*
                else if ((!txtEmail.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(),
                            "Password empty", Toast.LENGTH_SHORT).show();
                } else if ((!txtPassword.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(),
                            "Email empty", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Email and Password field empty", Toast.LENGTH_SHORT).show();
                }
                */



            }
        });


    }


    public void CheckFromtable(final String email, final String password){
        // accTable.where().field("deleted").eq(val(false)).execute(new TableQueryCallback<Account>() {
        accTable.where().field("email").eq(val(email)).execute(new TableQueryCallback<Account>() {
            @Override
            public void onCompleted(List<Account> result, int count, Exception exception, ServiceFilterResponse response) {

                if(exception==null){

                    Log.e("Email:",result.toString());
                    if(result.size()!=0){
                        if(result.get(0).password.equals(password)){

                            Log.e("UserName","exisit");
                            Toast.makeText(getApplicationContext(),
                                    "Login success", Toast.LENGTH_LONG).show();

                            ((HtripApp) getApplicationContext()).setUsername(email);

                            startActivityMain();
                            LoginActivity.this.finish();

                        }
                        else{
                            Log.e("Notfound ", "User not found");

                        }


                    }else {

                        Toast.makeText(getApplicationContext(),
                                "Inccorect email or password", Toast.LENGTH_LONG).show();
                    }

                    //Log.e("AccountList",exception.getMessage());
                }


            }
        });




    }

    public void startActivitySignUp(View v) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }


    public void startActivityForgotPassword (View v) {
        Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
        startActivity(intent);
    }

    public void startActivityMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /*
    public void finishActivityA(View v) {
        ActivityA.this.finish();
    }
    */

}


