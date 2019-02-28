package com.gefins;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

public class RegisterActivity extends AppCompatActivity {

    //Skilgreiningar
    private Button registerBtn;

    private EditText nameEdTxt, emailEdTxt, passEdTxt, confirmEdTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameEdTxt = findViewById(R.id.usernameEditText);
        emailEdTxt = findViewById(R.id.registerEmailEditText);
        passEdTxt = findViewById(R.id.registerPassEditText);
        confirmEdTxt = findViewById(R.id.registerPassConfEditText);

        registerBtn = findViewById(R.id.registerButton);

        // Virkni á RegisterTakkanum
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                // Nær í textan frá input field sem eru á register skjá
                // aka notandanafn, tölvupóstinn, lykillorðið og endurtekninguna
                String name = nameEdTxt.getText().toString();
                String email = emailEdTxt.getText().toString();
                String password = passEdTxt.getText().toString();
                String passwordConfirm = confirmEdTxt.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Intent intent = new Intent(RegisterActivity.this, UserActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            } else {
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                if (password.equals(passwordConfirm)) {
                    RegisterRequest registerRequest = new RegisterRequest(name, email, password, responseListener);
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