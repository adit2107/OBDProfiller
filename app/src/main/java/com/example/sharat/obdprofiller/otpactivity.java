package com.example.sharat.obdprofiller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask;
import android.os.Bundle;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import java.io.IOException;
import java.util.Random;

import local.org.apache.http.client.ClientProtocolException;

public class otpactivity extends AppCompatActivity {
    final String otpauthkey = "238639A9sBIi0YS5zl5ba37720";
    String otp;
    String otpexpiry;
    String driverph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText driver = findViewById(R.id.drivername);
       // EditText driverphone = (EditText)findViewById(R.id.driverphone);
        EditText randotp = findViewById(R.id.genotp);
//        EditText otptime = (EditText)findViewById(R.id.selecttime);
        Button sendotpdriver = findViewById(R.id.sendotpdriver);

        Random r = new Random();
        otp = String.format("%04d", (Object) Integer.valueOf(r.nextInt(1001)));

        //otpexpiry = otptime.getText().toString();
       // driverph = driverphone.getText().toString();

        randotp.setText(otp);
        Log.d("OTP activity", "OTP generated" + otp);
        sendotpdriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Connection().execute();

            }
        });

    }
    private class Connection extends AsyncTask<EditText, Object, String> {

        @Override
        protected String doInBackground(EditText... arg0) {
            EditText otptime = (EditText)findViewById(R.id.selecttime);
            EditText driverphone = (EditText)findViewById(R.id.driverphone);

            driverph = driverphone.getText().toString();
            otpexpiry = otptime.getText().toString();
            try {
                final String otpurl = "http://control.msg91.com/api/sendotp.php?authkey=" + otpauthkey + "&message=Your OTP is " + otp + " and is valid for " + otpexpiry + " minutes." + "&sender=OBDSMS&mobile=" + driverph + "&otp=" + otp + "&otp_expiry=" + otpexpiry;
                HttpResponse<String> response = Unirest.post(otpurl).asString();
                Log.d("OTP Activity", "OTPurl" + otpurl);
                Log.d("OTP Actvity", "Response " + response);
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "OTP successfully sent", Toast.LENGTH_LONG).show();
                    }
                });
            }catch (Exception e){
                Log.d("OTP Activity", "Error sending otp" + e);
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Unable to send OTP", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

    }

     public void sendotp() {
//                        try {
//                            final String otpurl = "http://control.msg91.com/api/sendotp.php?authkey=" + otpauthkey + "&message=Your OTP is " + otp + " and is valid for " + otpexpiry + " minutes." + "&sender=OBDSMS&mobile=" + driverph + "&otp=" + otp + "&otp_expiry=" + otpexpiry;
//                    System.out.println(otpurl);
//                    HttpResponse<String> response = Unirest.post(otpurl).asString();
//                    Log.d("OTP Actvity", "Response " + response);
//                            runOnUiThread(new Runnable() {
//                                public void run() {
//                                    Toast.makeText(getApplicationContext(), "OTP successfully sent", Toast.LENGTH_LONG).show();
//                                }
//                            });
//                }catch (Exception e){
//                    Log.d("OTP Activity", "Error sending otp" + e);
//                            runOnUiThread(new Runnable() {
//                                public void run() {
//                                    Toast.makeText(getApplicationContext(), "Unable to send OTP", Toast.LENGTH_LONG).show();
//                                }
//                            });
//                }
    }
}


