package com.example.julienhalford.hotelcompanion;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MainActivity extends AppCompatActivity {

    EditText password, email;
    String Password, Email, selectQuery, loginName;
    Button regButton;
    Context context = this;

    static Connection conn;
    String un;
    String pass;
    static String db;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ip = "sql7003.site4now.net";
        db = "DB_A2EB9D_471Hotels";
        un = "DB_A2EB9D_471Hotels_admin";
        pass = "hotelsdb1";

        conn = connectionclass(un, pass, db, ip);

        password = (EditText) findViewById(R.id.etPassword);
        email = (EditText) findViewById(R.id.etEmail);


    }

    public void onButtonRegister(View v){
        Intent reg = new Intent(MainActivity.this, Register.class);
        startActivity(reg);
    }

    public void onButtonSignIn(View v){



        Password = password.getText().toString();
        Email = email.getText().toString();

        selectQuery = "SELECT * FROM DB_A2EB9D_471Hotels.dbo.UserInfo WHERE Email = '"+Email+"' AND Password = '"+Password+"'";
       // selectQuery = "SELECT * FROM DB_A2EB9D_471Hotels.dbo.UserInfo WHERE Email = '"+Email+"' AND";

        try{


            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(selectQuery);



            if(resultSet.next()){
                loginName = resultSet.getString(1);
                Intent signIn = new Intent(MainActivity.this, HomePage.class);
                signIn.putExtra("usersName", loginName);
                startActivity(signIn);
            } else {
                Intent signIn = new Intent(MainActivity.this, MainActivity.class);
                startActivity(signIn);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }




    }

    @SuppressLint("NewApi")
    public Connection connectionclass (String user, String pass, String db, String server){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;

        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://" + server + ";" + "databseName=" + db + ";user=" + user + ";password=" + pass + ";";
            connection = DriverManager.getConnection(connectionURL);

        }catch(Exception e){
            Log.e("Error: ", e.getMessage());
        }

        return connection;
    }
}