package com.gefins;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import Entities.User;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import Entities.User;
import Requests.UserRequest;

public class SettingsActivity extends NavbarActivity {

    private User currentUser;
    private Button settingsBtn;
    private EditText nameSettingsEdTxt, emailSettingsEdTxt, passSettingsEdTxt, confirmSettingsEdTxt;
    private TextView usernameContainer, emailContainer, passwordContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_settings, contentFrameLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.drawer_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.drawer_title);
        mTitle.setText(R.string.settings_title);

        // set currentUser
        currentUser = (User) getIntent().getSerializableExtra("user");

        nameSettingsEdTxt = findViewById(R.id.usernameEditText);
        emailSettingsEdTxt = findViewById(R.id.registerEmailEditText);
        passSettingsEdTxt = findViewById(R.id.registerPassEditText);
        confirmSettingsEdTxt = findViewById(R.id.registerPassConfEditText);
        settingsBtn = findViewById(R.id.registerButton);

        nameSettingsEdTxt.setText(currentUser.getUserName());
        emailSettingsEdTxt.setText(currentUser.getEmail());
        passSettingsEdTxt.setText(currentUser.getPassword());
        confirmSettingsEdTxt.setText(currentUser.getPassword());

        // Functionality if settings button
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                User user = new User(currentUser.getId(), nameSettingsEdTxt.getText().toString(),
                        passSettingsEdTxt.getText().toString(), emailSettingsEdTxt.getText().toString(),
                        "0", "0");

                String passwordConfirm = confirmSettingsEdTxt.getText().toString();
                String email = emailSettingsEdTxt.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                //Mitigation from server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            boolean success = jsonResponse.getBoolean("success");
                            if (!success) {
                                //If changing failed
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SettingsActivity.this);
                                builder.setMessage("Það mistókst að breyta notendaupplýsingum")
                                        .setNegativeButton("Reyna aftur",null)
                                        .create()
                                        .show();
                            } else {
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SettingsActivity.this);
                                builder.setMessage("Það tókst að breyta notendaupplýsingum");
                                builder.setPositiveButton("Loka",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                        android.app.AlertDialog dialog = builder.create();
                                        dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                if ((user.getPassword().equals(passwordConfirm)) && (email.matches(emailPattern))) {

                    //Connecting to server
                    UserRequest registerRequest = new UserRequest(user,"register", responseListener);
                    RequestQueue queue = Volley.newRequestQueue(SettingsActivity.this);
                    queue.add(registerRequest);
                } else if ((!user.getPassword().equals(passwordConfirm)) && (email.matches(emailPattern))){
                    //Warning if password is invalid
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                    builder.setMessage("Mismundandi lykilorð")
                            .setNegativeButton("Reyna Aftur", null)
                            .create()
                            .show();
                } else if ((user.getPassword().equals(passwordConfirm)) && (!email.matches(emailPattern))) {
                    //Warning if email is invalid
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                    builder.setMessage("Netfang er ekki gilt")
                            .setNegativeButton("Reyna Aftur", null)
                            .create()
                            .show();
                } else if ((!user.getPassword().equals(passwordConfirm)) && (!email.matches(emailPattern))) {
                    //Warning if both are invalid
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                    builder.setMessage("Mismundandi lykilorð og netfang er ekki gilt")
                            .setNegativeButton("Reyna Aftur", null)
                            .create()
                            .show();
                }
            }
        });

    }
}