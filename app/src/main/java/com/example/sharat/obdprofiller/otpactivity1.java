package com.example.sharat.obdprofiller;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import org.json.JSONObject;

import java.util.Random;

public class otpactivity1 extends AppCompatActivity {
    final String otpauthkey = "238639A9sBIi0YS5zl5ba37720";
    String otp;
    String otpexpiry;
    String driverph;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity1);

        EditText driver = findViewById(R.id.drivername);
        EditText driverphone = (EditText)findViewById(R.id.driverphone);
        EditText randotp = (EditText)findViewById(R.id.genotp);
        Button sendotpdriver = (Button)findViewById(R.id.sendotpdriver);

        Random r = new Random();
        otp = String.format("%04d", (Object) Integer.valueOf(r.nextInt(1001)));

        randotp.setText(otp);
        Log.d("OTP activity", "OTP generated" + otp);

        String[] details = new String[10];

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            details = (String[])bundle.get("driverdetails");
            Log.d("OTP", "Got details" + details[1] + details[0]);

        driver.setText(details[0]);
        driverphone.setText(details[1]);
        }
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
}
