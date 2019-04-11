package Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MessageRequest extends StringRequest {
    private static final String ADMAKER_REQUEST_URL = "https://gefins-server.herokuapp.com/";
    private Map<String, String> params;

    public MessageRequest(String request, String itemId, String person, String message, Response.Listener<String>listener){
        super(Method.POST, ADMAKER_REQUEST_URL+request, listener, null);
        params = new HashMap<>();
        params.put("itemId", itemId);
        params.put("person", person);
        params.put("message", message);
    }
}
