package Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import Entities.User;

public class UserRequest extends StringRequest {

    //private static final String REGISTER_REQUEST_URL = "https://hugbo2.000webhostapp.com/connect/register.php";//linkur á Server
    private static final String REGISTER_REQUEST_URL = "https://gefins-server.herokuapp.com/";
    private Map<String, String> params;

    public UserRequest(User user, String request, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL+request, listener, null);
        params = new HashMap<>();

        params.put("id", user.getId());
        params.put("phone", user.getPhone());
        params.put("location", user.getLocation());
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
