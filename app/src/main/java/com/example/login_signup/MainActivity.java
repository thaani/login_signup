package com.example.login_signup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1=(Button)findViewById(R.id.button1);
        btn2=(Button)findViewById(R.id.button2);
        btn1.setOnClickListener(this);
       btn2.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button1)
        {
            Toast.makeText(MainActivity.this, "Button 1", Toast.LENGTH_SHORT).show();

        }
        else if(v.getId()==R.id.button2)
            Toast.makeText(MainActivity.this, "Button 2 ", Toast.LENGTH_SHORT).show();


    }
}
