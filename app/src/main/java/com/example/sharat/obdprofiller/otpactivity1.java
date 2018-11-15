package com.example.sharat.obdprofiller;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class otpactivity1 extends AppCompatActivity
{
    final String otpauthkey = "238639A9sBIi0YS5zl5ba37720";
    String otp;
    String otpexpiry;
    String driverph;
    int finaltimeh;
    int finaltimem;
    int starth;
    int startm;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity1);

        EditText driver = findViewById(R.id.drivername);
        EditText driverphone = (EditText)findViewById(R.id.driverphone);
        EditText randotp = (EditText)findViewById(R.id.genotp);
        final TextView timing = (TextView)findViewById(R.id.timing);
        final EditText otptime = (EditText)findViewById(R.id.selecttime);
        Button sendotpdriver = (Button)findViewById(R.id.sendotpdriver);
        final TimePicker startime = (TimePicker)findViewById(R.id.timePicker);
        Random r = new Random();
        otp = String.format("%04d", (Object) Integer.valueOf(r.nextInt(1001)));
        randotp.setText(otp);
        randotp.setEnabled(false);
        Log.d("OTP activity", "OTP generated" + otp);
        String[] details = new String[10];
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        otptime.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try
                {

                    starth = startime.getCurrentHour();
                    startm = startime.getCurrentMinute();
                    finaltimeh = starth + Integer.parseInt(otptime.getText().toString());
                    finaltimem = startm;
                    if (finaltimeh > 23) //Logic needs change for time exceeding 24 hours / midnight
                    {
                        finaltimeh = (Integer.parseInt(otptime.getText().toString()))-24;
                        timing.setText("Invalid Time");

                    }
                    timing.setText("Setting time: " + starth + ":" + startm + " to " + finaltimeh + ":" + finaltimem);
                    if(Integer.parseInt(otptime.getText().toString())>=10||finaltimeh<0)
                    {
                        timing.setText("Invalid Time");
                    }
                }
                catch (Exception e)
                {
                        timing.setText("Invalid Time");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        if(bundle != null)
        {
            details = (String[])bundle.get("driverdetails");
            Log.d("OTP", "Got details" + details[1] + details[0]);

        driver.setText(details[0]);
        driver.setEnabled(false);
        driverphone.setText(details[1]);
        driverphone.setEnabled(false);
        }
        sendotpdriver.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new Connection().execute();
            }
        });
    }
    private class Connection extends AsyncTask<EditText, Object, String>
    {
        public String getCurrentTimeUsingCalendar()
        {
            Calendar cal = Calendar.getInstance();
            Date date=cal.getTime();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String formattedDate=dateFormat.format(date);
            System.out.println("Current time of the day using Calendar - 24 hour format: "+ formattedDate);
            return formattedDate;
        }

        @Override
        protected String doInBackground(EditText... arg0)
        {
            final EditText drivername = (EditText)findViewById(R.id.drivername);
            final EditText driverphone = (EditText)findViewById(R.id.driverphone);
            final EditText otptime = (EditText)findViewById(R.id.selecttime);

            driverph = driverphone.getText().toString();
            otpexpiry = otptime.getText().toString();
            try
            {
                final String otpurl = "http://control.msg91.com/api/sendotp.php?authkey=" + otpauthkey + "&message=Your OTP is " + otp + " and is valid for " + otpexpiry + " minutes." + "&sender=OBDSMS&mobile=" + driverph + "&otp=" + otp + "&otp_expiry=" + otpexpiry;
                HttpResponse<String> response = Unirest.post(otpurl).asString();
                Log.d("OTP Activity", "OTPurl" + otpurl);
                Log.d("OTP Actvity", "Response " + response);
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        Toast.makeText(getApplicationContext(), "OTP successfully sent", Toast.LENGTH_LONG).show();
                        DBOpertion otpobject = new DBOpertion();
                        otpobject.setotp(drivername.getText().toString(), driverphone.getText().toString(), otp,getCurrentTimeUsingCalendar(), starth, startm, finaltimeh,finaltimem);

                    }
                });
            }
            catch (Exception e)
            {
                Log.d("OTP Activity", "Error sending otp" + e);
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        Toast.makeText(getApplicationContext(), "Unable to send OTP", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

    }
}
