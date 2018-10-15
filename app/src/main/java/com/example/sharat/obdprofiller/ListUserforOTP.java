package com.example.sharat.obdprofiller;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUserforOTP extends AppCompatActivity
{
   // public static ArrayList<String> list = new ArrayList<String>();

    List userlist;
    DBOpertion listobject = new DBOpertion();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_userfor_otp);
        userlist = listobject.getuserlist();
        String[] userslist1 = {"Driver1", "Driver2", "Driver3", "Driver4"};
        ListView lv = (ListView)findViewById(R.id.otplv);
        Log.d("OTPList", "Users list " + userlist);
        Object[] objectArray = userlist.toArray();
        String[] stringArray = Arrays.copyOf(objectArray, objectArray.length, String[].class);

//        int i=0;
//        while(i<userlist.size())
//        {
//
//        }

        ArrayAdapter<String> test = new ArrayAdapter<String>(this, R.layout.driverslist, R.id.driversview, userlist);
        lv.setAdapter(test);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // fetch from SQL
                JSONObject driversjson = new JSONObject();
                try {
                    driversjson.put("Phone", "919482871995");
                    driversjson.put("Name", "Adit");
                } catch (Exception e){
                    Log.d("DriverJson", "Could not add json" + e);
                }

                Intent i = new Intent(getApplicationContext(), otpactivity1.class);
                i.putExtra("driverdetails", driversjson.toString());
                startActivity(i);

            }
        });

    }
}
