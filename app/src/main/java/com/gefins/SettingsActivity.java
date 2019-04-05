package com.gefins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

        // set currentUser
        currentUser = (User) getIntent().getSerializableExtra("user");

        if(currentUser==null){
            Log.d("ble","USERINN ER HORFINN!!!!!!");}

        System.out.println("Curr id");


        nameSettingsEdTxt = findViewById(R.id.usernameEditText);
        emailSettingsEdTxt = findViewById(R.id.registerEmailEditText);
        passSettingsEdTxt = findViewById(R.id.registerPassEditText);
        confirmSettingsEdTxt = findViewById(R.id.registerPassConfEditText);
        settingsBtn = findViewById(R.id.registerButton);


        // Virkni á settingsBtn
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                /* Nær í textann frá input field sem eru á settings skjá
                   aka notandanafn, tölvupóstinn, lykilorðið og endurtekninguna
                */
                User user=new User("0", nameSettingsEdTxt.getText().toString(),
                        passSettingsEdTxt.getText().toString(), emailSettingsEdTxt.getText().toString(), "0", "0");

                String passwordConfirm = confirmSettingsEdTxt.getText().toString();

                //Meðhöndlun á svari frá server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("JSONREGITER", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                                //Fer frá register skjá á Login skjá
                                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                                SettingsActivity.this.startActivity(intent);
                            } else {

                                //Gefur upp glugga um að skráning mistókst
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SettingsActivity.this);
                                builder.setMessage("Ekki tókst að breyta stillingum")
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
                    UserRequest registerRequest = new UserRequest(user,"register", responseListener);
                    RequestQueue queue = Volley.newRequestQueue(SettingsActivity.this);
                    queue.add(registerRequest);
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