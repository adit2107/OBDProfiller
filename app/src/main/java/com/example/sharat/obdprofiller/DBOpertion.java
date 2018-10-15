package com.example.sharat.obdprofiller;

/**
 * Created by Sharat on 11-10-2018.
 */

import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import static java.lang.Thread.sleep;

public class DBOpertion
{

    public static String type;
    public static ArrayList<String> list = new ArrayList<String>();
    String DBOpertion_getID(final String uid, final String pass)
    {
        Log.w("DBOperation", "Constructing DB Object for | " + uid + pass);
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                    Log.w("DBOperation", "in thread");
                    String connectionUrl = "jdbc:jtds:sqlserver://boschsql.database.windows.net:1433/boschdb;"
                                            + "database=boschdb;"
                                            + "user=bosch;"
                                            + "password=Asd12345****;"
                                            + "encrypt=false;"
                                            + "trustServerCertificate=false;"
                                            + "hostNameInCertificate=*.database.windows.net;"
                                            + "loginTimeout=30;";
                    String fetchsql = "select logintype from OBDuser where userid = '"+uid+"' and pass = '" +pass+"';";
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
                          type = res.getString(1);
                          System.out.println("Generated: " + res.getString(1) + type);
                        }
                        connection.close();
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
            }
        };
        thread.start();
        return type;
    }

    public List getuserlist()
    {
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
                String fetchsql = "select userid from OBDuser;";
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
                        list.add(res.getString(1));
                        System.out.println("Generated: " + res.getString(1));
                        Log.d("DBOperation", "Adding list" + list);

                    }
                    connection.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
                Log.d("DBOperation", "Got list within call: " + list);

                return list;
            }
        });

        try {
            System.out.println("future.get() = " + future.get());
            Log.d("DBOperation", "Got list above: " + future.get());
        }catch (Exception e){
            Log.d("DBOperation", "Could not retrieve future" + e );
        }
        Log.d("DBOperation", "List value" + list);
        return list ;
    }
}
