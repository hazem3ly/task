package com.example.hazem.task.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hazem.task.Data.DataBaseAdapter;
import com.example.hazem.task.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    TextView newUser;
    Intent mainActivity;
    Button loginBTN;
    EditText input_email;
    EditText input_password;
    DataBaseAdapter dataBaseAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dataBaseAdapter = new DataBaseAdapter(this);

        initializeViews();

        createNewUserBTN();

        loginUserBTN();





    }

    private void initializeViews() {
        loginBTN = (Button) findViewById(R.id.loginBTN);
        newUser = (TextView) findViewById(R.id.link_signup);
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
    }

    private void loginUserBTN() {
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginBTN.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,R.style.AppTheme);
        progressDialog.setMessage("Logging In");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

        boolean isSuccess = dataBaseAdapter.checkUser(email,password);

        if (isSuccess){
            progressDialog.dismiss();
            mainActivity = new Intent(getApplicationContext(),MainActivity.class);
            mainActivity.putExtra("email",email);
            mainActivity.putExtra("password",password);
            startActivity(mainActivity);
            finish();
        }else {
            progressDialog.dismiss();
            onLoginFailed();
        }

    }



    private boolean validate() {

        boolean valid = true;

        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.setError("enter a valid email address");
            valid = false;
        } else {
            input_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            input_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            input_password.setError(null);
        }

        return valid;
    }

    private void createNewUserBTN() {
        // listen to when User click on create new account
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(mainActivity);
            }
        });
    }

    public void onLoginSuccess() {
        loginBTN.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        loginBTN.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

}
