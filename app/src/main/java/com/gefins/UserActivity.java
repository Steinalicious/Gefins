package com.gefins;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import Entities.User;
import Requests.UserRequest;
import Services.UserService;

public class UserActivity extends AppCompatActivity {

    // Skilgreiningar
    private Button loginBtn;
    private EditText emailEdTxt, passEdTxt;
    private UserService userService=new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        loginBtn = findViewById(R.id.loginButton);
        emailEdTxt = findViewById(R.id.loginEmail);
        passEdTxt = findViewById(R.id.loginPass);

        //Virknin á Login takkanum
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user=new User();
                user.setEmail(emailEdTxt.getText().toString());
                user.setPassword(passEdTxt.getText().toString());

                //Meðhöndlun á svari frá server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //debug
                            Log.d("JSONLOGIN", response);
                            JSONObject jsonResponse= new JSONObject(response);
                            //boolean success = jsonResponse.getBoolean("success");

                            if(!jsonResponse.isNull("user")) {
                            //if(success) {
                                User currentuser = new User(jsonResponse.getJSONObject("user"));
                                Log.d("user1",currentuser.toString());

                                // Færir frá Login skjá á forsíðu
                                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                                intent.putExtra("user", currentuser);
                                startActivity(intent);

                            } else{
                                // Lætur vita ef innskráning mistókst
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

                // Tengist server
                UserRequest loginRequest = new UserRequest(user, "login", responseListener);

                RequestQueue queue = Volley.newRequestQueue(UserActivity.this);
                queue.add(loginRequest);

            }
        });

    }

}