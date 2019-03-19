package com.example.login_signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private ProgressDialog pDialog;
    Button login;
    EditText email,password;
    TextView create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=(Button)findViewById(R.id.btn_login);
        email=(EditText)findViewById(R.id.input_email);
        password=(EditText)findViewById(R.id.input_password);
        create=(TextView)findViewById(R.id.link_signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), signup.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
            }
        });
    }

    private void login() {

        Log.d(TAG, "Login");

        if (validate() == false) {
            onLoginFailed();
            return;
        }

        //_loginButton.setEnabled(false);

        loginByServer();
    }

    private void loginByServer() {

        pDialog = new ProgressDialog(login.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);

        showpDialog();

        String semail = email.getText().toString();
        String spassword = password.getText().toString();


        APIService service = ApiClient.getClient().create(APIService.class);

        Call<MSG> userCall = service.userLogIn(semail,spassword);
 userCall.enqueue(new Callback<MSG>() {
     @Override
     public void onResponse(Call<MSG> call, Response<MSG> response) {
         hidepDialog();
         //onSignupSuccess();
         Log.d("onResponse", "" + response.body().getMessage());


         if(response.body().getSuccess() == 1) {
             startActivity(new Intent(login.this, MainActivity.class));

             finish();
         }else {
             Toast.makeText(login.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
         }
     }

     @Override
     public void onFailure(Call<MSG> call, Throwable t) {
         hidepDialog();
         Log.d("onFailure", t.toString());

     }
 });


    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }

    }
    public void onLoginSuccess() {
        login.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        login.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String lemail = email.getText().toString();
        String lpassword = password.getText().toString();

        if (lemail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(lemail).matches()) {
            email.setError("enter a valid email address");
           // requestFocus(password);
            valid = false;
        } else {
            email.setError(null);
        }

        if (lpassword.isEmpty()) {
            password.setError("Password is empty");
            //requestFocus(password);
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }

  //  private void requestFocus(View view) {
     //   if (view.requestFocus()) {
     //       getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
     //   }
  //  }
}
