package com.example.login_signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup extends AppCompatActivity {
    EditText name, email, password, repassword;
    Button signup;
    TextView loginlink;
    private static final String TAG = "SignupActivity";
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = (EditText) findViewById(R.id.input_name);
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        repassword = (EditText) findViewById(R.id.input_reEnterPassword);
        signup = (Button) findViewById(R.id.btn_signup);
        loginlink = (TextView) findViewById(R.id.link_login);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (validate() == false) {
            onSignupFailed();
            return;
        }

        saveToServerDB();

    }

    public void onSignupSuccess() {
        signup.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String sname = name.getText().toString();
        String semail = email.getText().toString();
        String spassword = password.getText().toString();
        String srepassword = repassword.getText().toString();

        if (sname.isEmpty() || name.length() < 3) {
            name.setError("at least 3 characters");
            valid = false;
        } else {
            name.setError(null);
        }


        if (semail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(semail).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }


        if (spassword.isEmpty() || password.length() < 4 || password.length() > 10) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        if (srepassword.isEmpty() || srepassword.length() < 4 || srepassword.length() > 10 || !(srepassword.equals(spassword))) {
         repassword.setError("Password Do not match");
          valid = false;
         } else {
             repassword.setError(null);
         }

        return valid;
    }

    private void saveToServerDB() {
        pDialog = new ProgressDialog(signup.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);

        showpDialog();

        String lname = name.getText().toString();
        String lemail = email.getText().toString();
        String lpassword = password.getText().toString();


        APIService service = ApiClient.getClient().create(APIService.class);
        //User user = new User(name, email, password);


        Call<MSG> userCall = service.userSignUp(lname, lemail, lpassword);
        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();
                //onSignupSuccess();
                Log.d("onResponse", "" + response.body().getMessage());


                if (response.body().getSuccess() == 1) {
                    startActivity(new Intent(signup.this, MainActivity.class));

                    finish();
                } else {
                    Toast.makeText(signup.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                hidepDialog();
                Log.d("onFailure", t.toString());


            }
        });

    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
