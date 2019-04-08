package Requests;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Entities.Item;


public class ItemRequest extends StringRequest {
//admaker
    private static final String ADMAKER_REQUEST_URL = "https://gefins-server.herokuapp.com/";
    private Map<String, String> params;


    // 1 skrá sig í rod
    // 2 skrá sig úr rod
    // 3 eida efsta
    // 4 velja efsta
    // 5 rate þigjanda
    // 6 rate owner
    public ItemRequest(String request,String option,String itemID,String userID, Response.Listener<String> listener){
        super(Method.PATCH, ADMAKER_REQUEST_URL+request, listener, null);
        params = new HashMap<>();
        params.put("option",option);
        params.put("itemID",itemID);
        params.put("userID",userID);

    }

    public ItemRequest(String request, Response.Listener<String> listener){
        super(Method.GET, ADMAKER_REQUEST_URL+request, listener, null);
    }

    public ItemRequest(String request, boolean bull, Response.Listener<String> listener){
        super(Method.DELETE, ADMAKER_REQUEST_URL+request, listener, null);
    }

    //request = "/user/queued"
    public ItemRequest(String request,String userID, Response.Listener<String> listener){
        super(Method.GET, ADMAKER_REQUEST_URL+request, listener, null);
        params= new HashMap<>();
        params.put("userID",userID);
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
        params.put("ownerID", item.getOwnerID());
        params.put("owner", item.getOwner());
        params.put("email", item.getEmail());
        params.put("img", item.getImg());
        params.put("users", item.getUsers());
        params.put("description", item.getDescription());
        params.put("zip", item.getZipcode());
        params.put("location", item.getLocation());
        params.put("phone", item.getPhone());
    }

    public ItemRequest(Item item, String request, String itemID, Response.Listener<String> listener){
        super(Method.PATCH, ADMAKER_REQUEST_URL+request, listener, null);
        params = new HashMap<>();
        params.put("name", item.getItemName());
        params.put("category", item.getCategory());
        params.put("img", item.getImg());
        params.put("description", item.getDescription());
        params.put("zip", item.getZipcode());
        params.put("location", item.getLocation());
        params.put("phone", item.getPhone());
        params.put("itemID", itemID);
    }


    @Override
    public Map<String, String> getParams(){ return params; }

}
