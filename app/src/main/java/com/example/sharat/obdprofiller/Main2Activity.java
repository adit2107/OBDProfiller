package com.example.sharat.obdprofiller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button generateotp = findViewById(R.id.button2);
        Button thresh = findViewById(R.id.thresh);

        generateotp.setOnClickListener(new View.OnClickListener()
        {            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(),ListUserforOTP.class);
                startActivity(i);
            }
        });

        thresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(),maxvalues.class);
                startActivity(i);
            }
        });
    }
}
