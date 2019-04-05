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

public class MyspaceActivity extends ExitNavbarActivity {

    private User currentUser;
    private Button myAdsButton, inQueueForButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_myspace, contentFrameLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.exit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.exit_title);
        mTitle.setText(R.string.myspace);

        // Takkar
        myAdsButton = findViewById(R.id.myAdsButton);
        inQueueForButton = findViewById(R.id.inQueueForButton);

        // set currentUser
        currentUser = (User) getIntent().getSerializableExtra("user");


        myAdsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Færir frá forsíðu yfir á ný auglýsing skjá
                Intent intent = new Intent(MyspaceActivity.this, MyAdsActivity.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent( this, MainActivity.class);
        intent.putExtra("user", currentUser);
        MyspaceActivity.this.startActivity(intent);
        return true;
    }
}
