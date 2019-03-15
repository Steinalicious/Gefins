package com.gefins;


import android.util.Log;
import android.view.textclassifier.TextLinks;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
   // private static final String LOGIN_REQUEST_URL = "https://hugbo2.000webhostapp.com/connect/login.php";//linkur á server
    private static final String LOGIN_REQUEST_URL = "https://gefins-server.herokuapp.com/login";
    private Map<String, String> params;

    public LoginRequest(String email, String password, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {return params;}

}
