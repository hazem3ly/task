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

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    TextView newUser;
    Intent signupActivity;
    EditText newName;
    EditText newEmail;
    EditText newPassword;
    Button createNewBTN;
    DataBaseAdapter dataBaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dataBaseAdapter = new DataBaseAdapter(this);

        initializeViews();

        createNewAccountBTN();

        loginUserBTN();

    }

    private void createNewAccountBTN() {
        createNewBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        createNewBTN.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = newName.getText().toString();
        String email = newEmail.getText().toString();
        String password = newPassword.getText().toString();

        if (dataBaseAdapter.checkEmail(email)){
            progressDialog.dismiss();
            createNewBTN.setEnabled(true);
            Toast.makeText(this,"This Email Already Registered",Toast.LENGTH_LONG).show();
        }else {
            long id = dataBaseAdapter.insertUser(name,email,password);

            if (id < 0){
                onSignupFailed();
                progressDialog.dismiss();
            }else {
                onSignupSuccess();
                progressDialog.dismiss();
                signupActivity = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(signupActivity);
            }
        }



        // TODO: Implement your own signup logic here.

//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onSignupSuccess or onSignupFailed
//                        // depending on success
//                        onSignupSuccess();
//                        // onSignupFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);

    }

    private void initializeViews() {
        newUser = (TextView) findViewById(R.id.link_signup);
        newName = (EditText) findViewById(R.id.newName);
        newEmail = (EditText) findViewById(R.id.newE_mail);
        newPassword = (EditText) findViewById(R.id.newPassword);
        createNewBTN = (Button) findViewById(R.id.create_accountBTN);
    }

    private void loginUserBTN() {
        // listen to when User click on create new account
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signupActivity = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(signupActivity);
            }
        });

    }

    public void onSignupSuccess() {
        Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_LONG).show();

        createNewBTN.setEnabled(true);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Account Create Failed", Toast.LENGTH_LONG).show();

        createNewBTN.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = newName.getText().toString();
        String email = newEmail.getText().toString();
        String password = newPassword.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            newName.setError("at least 3 characters");
            valid = false;
        } else {
            newName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            newEmail.setError("enter a valid email address");
            valid = false;
        } else {
            newEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            newPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            newPassword.setError(null);
        }

        return valid;
    }

}
