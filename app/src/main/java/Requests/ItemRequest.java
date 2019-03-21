package Requests;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import Entities.Item;


public class ItemRequest extends StringRequest {
//admaker
    private static final String ADMAKER_REQUEST_URL = "https://gefins-server.herokuapp.com/";
    private Map<String, String> params;

    public ItemRequest(String request, Response.Listener<String> listener){
        super(Method.GET, ADMAKER_REQUEST_URL+request, listener, null);
    }

    public ItemRequest(Item item, String request, Response.Listener<String> listener){
        super(Method.POST, ADMAKER_REQUEST_URL+request, listener, null);
        params = new HashMap<>();
        params.put("name", item.getItemName());
        params.put("category", item.getCategory());
        params.put("generalLocation", item.getGeneralLocation());
        params.put("id", item.getId());
        params.put("accepteduser", item.getAcceptedUser());
        params.put("rated", item.getRated());
        params.put("messenger", item.getMessenger());
        params.put("owner", item.getOwner());
        params.put("email", item.getEmail());
        params.put("img", item.getImg());
        params.put("users", item.getUsers().toString());
        params.put("description", item.getDescription());
        params.put("zip", item.getZipcode());
        params.put("location", item.getLocation());
        params.put("phone", item.getPhone());
    }


    @Override
    public Map<String, String> getParams(){ return params; }

}
