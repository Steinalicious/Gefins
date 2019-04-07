package com.gefins;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import Entities.User;
import Requests.UserRequest;

public class RegisterActivity extends BackNavbarActivity {

    //Skilgreiningar
    private Button registerBtn;
    private Intent userIntent;
    private EditText nameEdTxt, emailEdTxt, passEdTxt, confirmEdTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_register, contentFrameLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.back_title);
        mTitle.setText(R.string.register);


        nameEdTxt = findViewById(R.id.usernameEditText);
        emailEdTxt = findViewById(R.id.registerEmailEditText);
        passEdTxt = findViewById(R.id.registerPassEditText);
        confirmEdTxt = findViewById(R.id.registerPassConfEditText);
        registerBtn = findViewById(R.id.registerButton);

        // Virkni á RegisterTakkanum
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                User user=new User("0", nameEdTxt.getText().toString(),
                        passEdTxt.getText().toString(), emailEdTxt.getText().toString(),
                        "0", "0");


                String passwordConfirm = confirmEdTxt.getText().toString();

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
                                Intent intent = new Intent(RegisterActivity.this, UserActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            } else {

                                //Gefur upp glugga um að skráning mistókst
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Skráning mistókst")
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
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                } else {

                    //Varar við að Lykilorð eru mismunandi
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Mismundandi lykilorð")
                            .setNegativeButton("Reyna Aftur", null)
                            .create()
                            .show();
                }
            }
        });

    }
}