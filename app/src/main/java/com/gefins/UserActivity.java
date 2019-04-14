package com.gefins;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import Entities.User;
import Requests.UserRequest;
import Services.UserService;

public class UserActivity extends BackNavbarActivity {

    // Skilgreiningar
    private Button loginBtn;
    private EditText emailEdTxt, passEdTxt;
    private UserService userService=new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_user, contentFrameLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.back_title);
        mTitle.setText(R.string.login_title);

        loginBtn = findViewById(R.id.loginButton);
        emailEdTxt = findViewById(R.id.loginEmail);
        passEdTxt = findViewById(R.id.loginPass);

        //Functionality of Login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user=new User();
                user.setEmail(emailEdTxt.getText().toString());
                user.setPassword(passEdTxt.getText().toString());

                //Handling of response from server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse= new JSONObject(response);

                            if(!jsonResponse.isNull("user")) {
                                User currentuser = new User(jsonResponse.getJSONObject("user"));

                                // Moves from login to mainn
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("user", currentuser);
                                startActivity(intent);

                            } else{
                                // Warning if login failed
                                AlertDialog.Builder builder = new AlertDialog.Builder( UserActivity.this);
                                builder.setMessage("Innskráning mistókst")
                                        .setNegativeButton("Reyna aftur", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };

                // Connection to server
                UserRequest loginRequest = new UserRequest(user, "login", responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserActivity.this);
                queue.add(loginRequest);

            }
        });

    }

}