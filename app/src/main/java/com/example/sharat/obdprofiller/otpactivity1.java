package com.example.sharat.obdprofiller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;
public class otpactivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity1);

        TextView driverph = (TextView) findViewById(R.id.driverphone);
        TextView drivername = (TextView) findViewById(R.id.drivername);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            String details = (String) bundle.get("driverdetails");
            Log.d("OTP", "Got details" + details);
            try {

                JSONObject drdetails = new JSONObject(details);
                String drphone = drdetails.getJSONObject("Phone").toString();
                String drname = drdetails.getJSONObject("Name").toString();
                Log.d("OTP", drphone);
                driverph.setText(drphone);
                drivername.setText(drname);
            }catch (Exception e){
                Log.d("OTP", "Could not get json" + e);
            }

        //drivername.setText(details);
        }

    }
}
