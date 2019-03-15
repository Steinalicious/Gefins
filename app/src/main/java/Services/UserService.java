package Services;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.gefins.RegisterActivity;
import com.gefins.UserActivity;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import Entities.User;
import Requests.UserRequest;

public class UserService {
    public UserService() {

    }
    /*
    public void updateUser(User user){



    }

    public Boolean register(User user) {
        //Meðhöndlun á svari frá server
        final boolean[] success = new boolean[1];
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("JSONREGITER", response);
                    JSONObject jsonResponse = new JSONObject(response);
                    success[0] = jsonResponse.getBoolean("success");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

            //Tengist server

            UserRequest userRequest = new UserRequest(user,"register", responseListener);
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(userRequest);
            return success[0];




    }

    public void delete(User user) {
        //Todo function to delete user
    }

    public List<User> findByUsers(List<String> users) {
        return null;
    }

    public User findByuserName(String userName) {
        return null;
    }

    public User unJson(JSONObject json){
        return null;
    }

    public List<User> unJsonList(JSONObject json){
        return null;
    }
    */
}