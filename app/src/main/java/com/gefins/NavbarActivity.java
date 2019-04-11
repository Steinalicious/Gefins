package com.gefins;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.GravityCompat;
import android.support.design.widget.NavigationView;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import Entities.User;
import Entities.Item;


public class NavbarActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private User currentUser;
    private Button notifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);

        currentUser = (User) getIntent().getSerializableExtra("user");

        Toolbar toolbar = findViewById(R.id.drawer_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.baseline_menu_white2_18dp);

        drawerLayout = findViewById(R.id.drawer_layout);
        notifyBtn = findViewById(R.id.notifyBtn);

        notifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //We get a reference to the NotificationManager
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                String MyText = "Reminder";

                Notification mNotification = new Notification.Builder(getApplicationContext())
                        .setContentText("hallo")
                        .setSmallIcon(R.drawable.notifyicon)
                        .setWhen(System.currentTimeMillis())
                        .build();
                //The three parameters are: 1. an icon, 2. a title, 3. time when the notification appears

                String MyNotificationTitle = "Medicine!";
                String MyNotificationText  = "Don't forget to take your medicine!";

                Intent MyIntent = new Intent(Intent.ACTION_VIEW);
                PendingIntent StartIntent = PendingIntent.getActivity(getApplicationContext(),0,MyIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                //A PendingIntent will be fired when the notification is clicked. The FLAG_CANCEL_CURRENT flag cancels the pendingintent

                //mNotification.setLatestEventInfo(getApplicationContext(), MyNotificationTitle, MyNotificationText, StartIntent);

                int NOTIFICATION_ID = 1;
                notificationManager.notify(NOTIFICATION_ID , mNotification);
                //We are passing the notification to the NotificationManager with a unique id.
                Intent i = new Intent(NavbarActivity.this, MyspaceActivity.class);
                i.putExtra("user", currentUser);
                startActivity(i);
            }
        });

        Item item = new Item();
        item.getOwner();

        /* Virkni á menu items */
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);

                        int id = menuItem.getItemId();

                        if (id == R.id.nav_myspace) {
                            Intent intent = new Intent(NavbarActivity.this, MyspaceActivity.class);
                            intent.putExtra("user", currentUser);
                            startActivity(intent);
                        } else if (id == R.id.nav_settings) {
                            Intent intent = new Intent(NavbarActivity.this, SettingsActivity.class);
                            intent.putExtra("user", currentUser);
                            startActivity(intent);
                        } else if (id == R.id.nav_logout) {
                            Intent intent = new Intent(NavbarActivity.this, WelcomeActivity.class);
                            finish();
                            startActivity(intent);
                        }

                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }


                });

        View headerview = navigationView.getHeaderView(0);
        headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavbarActivity.this, MainActivity.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}




