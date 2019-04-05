package com.gefins;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import Entities.User;

public class SettingsActivity extends NavbarActivity {

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_settings, contentFrameLayout);

        // set currentUser
        currentUser = (User) getIntent().getSerializableExtra("user");

        if(currentUser==null){
            Log.d("ble","USERINN ER HORFINN!!!!!!");}
    }
}
