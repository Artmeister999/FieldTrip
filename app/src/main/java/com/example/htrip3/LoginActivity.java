package com.example.htrip3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public List<Account> results = new ArrayList<Account>();
    private Button btnLogin;
    private TextView tvSignUp;
    private TextView tvFotgotPassword;
    private EditText txtEmail;
    private EditText txtPassword;
    private TextView textLoginMessage;
    private Boolean UserTypedInCorrect = false;
    private MobileServiceClient mClient;
    private String URL = "http://demohunter.azurewebsites.net";
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
            mClient = new MobileServiceClient(URL, LoginActivity.this);

            accTable = mClient.getTable(Account.class);
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

                CheckFromtable(txtEmail.getText().toString(),
                    txtPassword.getText().toString()); //crash SO here
            }
        });
    }

    public void CheckFromtable(final String email, final String password) {
        accTable.where()
            .field("email")
            .eq(email)
            .execute(new TableQueryCallback<Account>() { //val(email)
                @Override
                public void onCompleted(List<Account> result, int count, Exception exception,
                    ServiceFilterResponse response) {

                    if (exception == null) {

                        Log.e("Email:", result.toString());
                        if (result.size() != 0) {
                            if (result.get(0).password.equals(password)) {

                                Log.e("UserName", "exisit");
                                Toast.makeText(getApplicationContext(), "Login success",
                                    Toast.LENGTH_LONG).show();

                                ((HtripApp) getApplicationContext()).setUsername(email);

                                startActivityMain();
                                LoginActivity.this.finish();
                            } else {
                                Log.e("Notfound ", "User not found");
                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "Inccorect email or password",
                                Toast.LENGTH_LONG).show();
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

    public void startActivityForgotPassword(View v) {
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


