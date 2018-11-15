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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ListUserforOTP extends AppCompatActivity {
    // public static ArrayList<String> list = new ArrayList<String>();

    List userlist;
    DBOpertion listobject = new DBOpertion();
    public static String ph;
    public static String drivername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_userfor_otp);
        final String[] driverinfo = new String[5];
        userlist = listobject.getuserlist();
        ListView lv = (ListView) findViewById(R.id.otplv);
        Log.d("OTPList", "Users list " + userlist);

        ArrayAdapter<String> test = new ArrayAdapter<String>(this, R.layout.driverslist, R.id.driversview, userlist);
        lv.setAdapter(test);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // fetch from SQL
                drivername = parent.getItemAtPosition(position).toString();
                Log.d("OTPList", "Name clicked" + parent.getItemAtPosition(position));
                driverinfo[0] = drivername;
                String driverph = getphone(drivername);
                Log.d("OTPList", "Driver phone:" + driverph);
                driverinfo[1] = driverph;

                Intent i = new Intent(getApplicationContext(), otpactivity1.class);
                i.putExtra("driverdetails", driverinfo);
                startActivity(i);

            }
        });

    }

    public String getphone(final String drivername) {
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        Future<List> future = executorService1.submit(new Callable(){
            public Object call() throws Exception {
                System.out.println("Asynchronous Callable");
                Log.w("DBOperation", "in thread");
                String connectionUrl = "jdbc:jtds:sqlserver://boschsql.database.windows.net:1433/boschdb;"
                        + "database=boschdb;"
                        + "user=bosch;"
                        + "password=Asd12345****;"
                        + "encrypt=false;"
                        + "trustServerCertificate=false;"
                        + "hostNameInCertificate=*.database.windows.net;"
                        + "loginTimeout=30;";
                String fetchsql = "select phone from OBDuser where userid = '"+drivername+"';";

                ResultSet resultSet = null;
                try (Connection connection = DriverManager.getConnection(connectionUrl))
                {
                    java.sql.Statement statement;
                    java.sql.ResultSet res;
                    Log.w("DBOperation", "Trying to execute");
                    statement = connection.createStatement();
                    res = statement.executeQuery(fetchsql);

                    while (res.next())
                    {
                        ph = res.getString(1);
                        System.out.println("Generated: " + res.getString(1));
                        Log.d("OTPList", "phone" + ph);

                    }

                    connection.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
                Log.d("OTPList", "Got list within call: " + ph);

                return ph;
            }
        });

        try {
            System.out.println("future.get() = " + future.get());
            Log.d("OTPList", "Got list above: " + future.get());
        }catch (Exception e){
            Log.d("OTPList", "Could not retrieve future" + e );
        }
        Log.d("OTPList", "Phone value" + ph);
        return ph ;

    }
}
