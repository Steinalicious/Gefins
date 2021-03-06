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
        mTitle.setText(R.string.register_title);


        nameEdTxt = findViewById(R.id.usernameEditText);
        emailEdTxt = findViewById(R.id.registerEmailEditText);
        passEdTxt = findViewById(R.id.registerPassEditText);
        confirmEdTxt = findViewById(R.id.registerPassConfEditText);
        registerBtn = findViewById(R.id.registerButton);

        // Functionality of Register button
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                User user=new User("0", nameEdTxt.getText().toString(),
                        passEdTxt.getText().toString(), emailEdTxt.getText().toString(),
                        "0", "0");

                String passwordConfirm = confirmEdTxt.getText().toString();
                String email = emailEdTxt.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                //mitigate answer from server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                //Moves from register to login screen
                                Intent intent = new Intent(RegisterActivity.this, UserActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            } else {
                                //Wanring message if registration failed
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


                if ((user.getPassword().equals(passwordConfirm)) && (email.matches(emailPattern))) {
                    //cennecting to server
                    UserRequest registerRequest = new UserRequest(user,"register", responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                } else if ((!user.getPassword().equals(passwordConfirm)) && (email.matches(emailPattern))){
                    //Warning if password is invalid
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Mismundandi lykilorð")
                            .setNegativeButton("Reyna Aftur", null)
                            .create()
                            .show();
                } else if ((user.getPassword().equals(passwordConfirm)) && (!email.matches(emailPattern))) {
                    //Warning if email is not valid
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Netfang er ekki gilt")
                            .setNegativeButton("Reyna Aftur", null)
                            .create()
                            .show();
                } else if ((!user.getPassword().equals(passwordConfirm)) && (!email.matches(emailPattern))) {
                    //Warning if both ain't valid
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Mismundandi lykilorð og netfang er ekki gilt")
                            .setNegativeButton("Reyna Aftur", null)
                            .create()
                            .show();
                }
            }
        });

    }
}