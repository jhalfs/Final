package com.example.julienhalford.hotelcompanion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Register extends AppCompatActivity {

    EditText name, password, email, address;
    String Name, Password, Email, Address, insertQuery;
    Button regButton;

    static Connection conn;
    String un;
    String pass;
    static String db;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ip = "sql7003.site4now.net";
        db = "DB_A2EB9D_471Hotels";
        un = "DB_A2EB9D_471Hotels_admin";
        pass = "hotelsdb1";

        conn = connectionclass(un, pass, db, ip);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (EditText) findViewById(R.id.etName);
        password = (EditText) findViewById(R.id.etPassword);
        email = (EditText) findViewById(R.id.etEmail);
        address = (EditText) findViewById(R.id.etAddress);
        regButton = (Button) findViewById(R.id.bCompleteReg);

    }

    public void onButtonClickConnect(View v){
        Name = name.getText().toString();
        Password = password.getText().toString();
        Email = email.getText().toString();
        Address = address.getText().toString();

        insertQuery = "INSERT INTO UserInfo " + "VALUES ('"+Name+"', '"+Password+"', '"+Email+"', '"+Address+"')";
        //insertQuery = "INSERT INTO UserInfo " + "VALUES ('name1','password1','email1','address1')";

        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(insertQuery);

            conn.close();

            Intent ret = new Intent(Register.this, MainActivity.class);
            startActivity(ret);
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
