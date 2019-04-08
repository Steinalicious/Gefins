package com.gefins;

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
        mTitle.setText(R.string.settings);

        // set currentUser
        currentUser = (User) getIntent().getSerializableExtra("user");

        if(currentUser==null){
            Log.d("ble","USERINN ER HORFINN!!!!!!");
        }
        System.out.println(currentUser.getUserName());


        nameSettingsEdTxt = findViewById(R.id.usernameEditText);
        emailSettingsEdTxt = findViewById(R.id.registerEmailEditText);
        passSettingsEdTxt = findViewById(R.id.registerPassEditText);
        confirmSettingsEdTxt = findViewById(R.id.registerPassConfEditText);
        settingsBtn = findViewById(R.id.registerButton);

        nameSettingsEdTxt.setText(currentUser.getUserName());
        emailSettingsEdTxt.setText(currentUser.getEmail());
        passSettingsEdTxt.setText(currentUser.getPassword());
        confirmSettingsEdTxt.setText(currentUser.getPassword());

        // Virkni á RegisterTakkanum
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                User user=new User(currentUser.getId(), nameSettingsEdTxt.getText().toString(),
                        passSettingsEdTxt.getText().toString(), emailSettingsEdTxt.getText().toString(),
                        "0", "0");

                String passwordConfirm = confirmSettingsEdTxt.getText().toString();

                //Meðhöndlun á svari frá server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("JSONSETTINGS", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (!success) {

                                //Gefur upp glugga um að skráning mistókst
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SettingsActivity.this);
                                builder.setMessage("Það mistókst að breyta notendaupplýsingum")
                                        .setNegativeButton("Reyna aftur",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                if (user.getPassword().equals(passwordConfirm)) {

                    //Tengist server
                    UserRequest settingsRequest = new UserRequest(user, currentUser.getId(), "settings", responseListener);
                    RequestQueue queue = Volley.newRequestQueue(SettingsActivity.this);
                    queue.add(settingsRequest);
                } else {

                    //Varar við að Lykilorð eru mismunandi
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                    builder.setMessage("Mismundandi lykilorð")
                            .setNegativeButton("Reyna Aftur", null)
                            .create()
                            .show();
                }
            }
        });

    }
}