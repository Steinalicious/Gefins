package com.gefins;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AdMakerRequest extends StringRequest {

    private static final String ADMAKER_REQUEST_URL = "https://gefins-server.herokuapp.com/admaker";
    private Map<String, String> params;

    public AdMakerRequest(String name, String description, String zip, String location, String phone, Response.Listener<String> listener){
        super(Method.POST, ADMAKER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("description", description);
        params.put("zip", zip);
        params.put("location", location);
        params.put("phone", phone);
    }

    @Override
    public Map<String, String> getParams(){ return params; }
}
