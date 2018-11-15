package com.example.sharat.obdprofiller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class maxvalues extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maxvalues);
        final TextView speedvalue = findViewById(R.id.editText);
        Button setspeed = findViewById(R.id.button3);

        setspeed.setOnClickListener(new View.OnClickListener()
        {
            DBOpertion threshobj = new DBOpertion();
            @Override
            public void onClick(View view)
            {
                String speed = speedvalue.getText().toString();
                threshobj.setmaxspeed(speed);
            }
        });

    }
}
