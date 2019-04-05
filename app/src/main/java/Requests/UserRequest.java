package Requests;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import Entities.User;

public class UserRequest extends StringRequest {

    //private static final String REGISTER_REQUEST_URL = "https://hugbo2.000webhostapp.com/connect/register.php";//linkur á Server
    private static final String REGISTER_REQUEST_URL = "https://gefins-server.herokuapp.com/";
    private Map<String, String> params;


    public UserRequest(User user,String userID, String request, Response.Listener<String> listener) {
        super(Method.PATCH, REGISTER_REQUEST_URL + request, listener, null);
        params = new HashMap<>();
        params.put("userID",userID);
        params.put("usernameUpdated", user.getUserName());
        params.put("passwordUpdated", user.getPassword());
        params.put("emailUpdated", user.getEmail());
    }


    public UserRequest(User user, String request, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL+request, listener, null);
        params = new HashMap<>();
        Log.d("ASD", "he");

        params.put("id", user.getId());
        params.put("mId", user.getmId());
        params.put("stars", user.getStars());
        params.put("starNumber", user.getStarsNumber());
        params.put("username", user.getUserName());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
