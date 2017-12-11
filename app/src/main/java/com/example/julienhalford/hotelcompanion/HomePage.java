package com.example.julienhalford.hotelcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void onButtonClickBrowse(View v){
        Intent browse = new Intent(HomePage.this, Browse.class);
        startActivity(browse);
    }

    public void onButtonClickSearch(View v){
        Intent search = new Intent(HomePage.this, SearchHotels.class);
        startActivity(search);
    }
}
