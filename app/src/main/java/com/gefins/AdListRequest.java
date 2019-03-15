package com.gefins;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdListRequest extends StringRequest {
    private static final String ADLIST_REQUEST_URL = "http://gefins-server.herokuapp.com/items";

    public AdListRequest(Response.Listener<String> listener){
        super(Method.GET, ADLIST_REQUEST_URL, listener, null);
    }

}
