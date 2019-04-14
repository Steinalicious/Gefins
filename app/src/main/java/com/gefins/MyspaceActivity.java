package com.gefins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import Entities.User;

public class MyspaceActivity extends NavbarActivity {

    private User currentUser;
    private Button myAdsButton, inQueueForButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_myspace, contentFrameLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.drawer_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.drawer_title);
        mTitle.setText(R.string.myspace_title);

        // Buttons
        myAdsButton = findViewById(R.id.myAdsButton);
        inQueueForButton = findViewById(R.id.inQueueForButton);

        // set currentUser
        currentUser = (User) getIntent().getSerializableExtra("user");

        myAdsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Moves from myspace to my ads screen
                Intent intent = new Intent(MyspaceActivity.this, MyAdsActivity.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
        });

        inQueueForButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Moves from myspace to queue screen
                Intent intent = new Intent(MyspaceActivity.this, InQueueActivity.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
        });
    }
}
