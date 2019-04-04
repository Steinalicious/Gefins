package com.gefins;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import Entities.User;

/* Eftir að klára virkni */

public class MyspaceActivity extends AppCompatActivity {

    private User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myspace);

        // set currentUser
        currentUser = (User) getIntent().getSerializableExtra("user");
    }
}
