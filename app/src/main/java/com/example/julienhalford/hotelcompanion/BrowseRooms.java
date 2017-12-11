package com.example.julienhalford.hotelcompanion;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.app.ActionBar.LayoutParams;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Space;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class BrowseRooms extends AppCompatActivity {
    String Search, Space, Cap, Rat, BrowseQuery, ip, db, un, pass, Pr, passedH, updateQuery;
    static Connection conn;
    int j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_rooms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ip = "sql7003.site4now.net";
        db = "DB_A2EB9D_471Hotels";
        un = "DB_A2EB9D_471Hotels_admin";
        pass = "hotelsdb1";
        conn = connectionclass(un, pass, db, ip);
        setSupportActionBar(toolbar);
        j = 0;
        Search = "Hotel Number: ";
        Space = "  ";
        Cap = "Capacity: ";
        Rat = "Rating: ";
        Pr = "| Price: ";
        Intent bundle = getIntent();
        passedH = bundle.getStringExtra("Hotel");
        String value = passedH;
        for(int i=0; i<10; i++) {
            if(Integer.parseInt(value)==0){
                passedH = "Best Western Plus";
            }else if(Integer.parseInt(value)==1){
                passedH = "Days Inn";
            }else if(Integer.parseInt(value)==2){
                passedH = "Delta";
            }
            else if(Integer.parseInt(value)==3){
                passedH = "Econolodge";
            }
            else if(Integer.parseInt(value)==4){
                passedH = "Holiday Inn";
            }
            else if(Integer.parseInt(value)==5){
                passedH = "Hotel Elan";
            }
            else if(Integer.parseInt(value)==6){
                passedH = "Hotel Alma";
            }
            else if(Integer.parseInt(value)==7){
                passedH = "Ramada Limited";
            }
            else if(Integer.parseInt(value)==8){
                passedH = "Sandman";
            }
            else if(Integer.parseInt(value)==9){
                passedH = "Sheraton";
            }
        }
        BrowseQuery = "SELECT * FROM DB_A2EB9D_471Hotels.dbo.Rooms WHERE H_Name = '"+passedH+"' AND Availability = 'Yes';";

        final LinearLayout lm = (LinearLayout) findViewById(R.id.linearMain);

        // create the layout params that will be used to define how your
        // button will be displayed
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                600, ViewGroup.LayoutParams.WRAP_CONTENT);
        //Create four
        try {
            Statement stmt = conn.createStatement();
            ResultSet reset = stmt.executeQuery(BrowseQuery);
            while(reset.next())
            {
                String H_Name = reset.getString(5);
                String Capacity = reset.getString(2);
                String Rating = reset.getString(1);
                String Price = reset.getString(3);
                String phoneNum = reset.getString(9);

                // Create LinearLayout
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);

                // Create TextView
                TextView name = new TextView(this);
                name.setText(H_Name);
                name.setTextAppearance(this, android.R.style.TextAppearance_Medium);
                ll.addView(name);

                TextView blank = new TextView(this);
                blank.setText(Space);
                ll.addView(blank);

                TextView blank1 = new TextView(this);
                blank1.setText(Space);

                TextView price = new TextView(this);
                price.setTextAppearance(this, android.R.style.TextAppearance_Medium);
                price.setText(Pr);
                ll.addView(price);

                TextView priceValue = new TextView(this);
                priceValue.setText(Price);
                ll.addView(priceValue);

                TextView blank2 = new TextView(this);
                blank2.setText(Space);
                ll.addView(blank2);

                TextView cap = new TextView(this);
                cap.setTextAppearance(this, android.R.style.TextAppearance_Medium);
                cap.setText(Cap);
                ll.addView(cap);

                // Create TextView
                TextView capacity = new TextView(this);
                capacity.setText(Capacity);
                ll.addView(capacity);
                ll.addView(blank1);

                TextView rat = new TextView(this);
                rat.setTextAppearance(this, android.R.style.TextAppearance_Medium);
                rat.setText(Rat);
                ll.addView(rat);

                TextView rating = new TextView(this);
                rating.setText(Rating);
                ll.addView(rating);

                LinearLayout llm = new LinearLayout(this);
                llm.setOrientation(LinearLayout.HORIZONTAL);

                // Create Button
                final Button btn = new Button(this);
                // Give button an ID
                btn.setId(j+1);
                btn.setText(Search + phoneNum);
                // set the layoutParams on the button
                btn.setLayoutParams(params);


                updateQuery = "UPDATE DB_A2EB9D_471Hotels.dbo.Rooms SET Availability = 'No' WHERE Phone = "+phoneNum+";";

                final int index = j;
                // Set click listener for button
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            Statement newStatement = conn.createStatement();
                            newStatement.executeUpdate(updateQuery);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        Intent goHome = new Intent(BrowseRooms.this, MainActivity.class);
                        startActivity(goHome);
                    }
                });

                //Add button to LinearLayout
                llm.addView(btn);
                //Add button to LinearLayout defined in XML
                lm.addView(ll);
                lm.addView(llm);
                j++;
            }
        } catch (SQLException e) {
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
