package com.gefins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Entities.Item;
import Entities.User;
import Requests.ItemRequest;

public class MyAdsActivity extends BackNavbarActivity {

    private User currentUser;
    ListView listView;
    private String numInQue;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_myads, contentFrameLayout);

        // Titill í header
        Toolbar toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.back_title);
        mTitle.setText(R.string.myAds);

        listView = (ListView)findViewById(R.id.listView);

        // set currentUsers
        currentUser = (User) getIntent().getSerializableExtra("user");
        Log.d("userID", currentUser.getId());

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //debug
                    Log.d("MYADS", response);
                    JSONObject jsonResponse = new JSONObject(response);
                    final JSONArray adArray = jsonResponse.getJSONArray("items");

                    Item item = new Item();

                    ArrayList<String> adList = new ArrayList<>();
                    ArrayList<String> queueCount = new ArrayList<>(); // fjoldi i rod

                    for(int i = 0; i < adArray.length(); i++) {
                        item = new Item(adArray.getJSONObject(i));
                        if (item == null) {
                            return;
                        } else {
                            adList.add(item.getItemName());
                            //queueCount.add(item.getQueueInfo().getNumInQue());
                            Log.d("queueCount", item.getQueueInfo().getNumInQue());

                            // Listi af auglýsingum
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MyAdsActivity.this,
                                    R.layout.list_item_layout, R.id.textView_adTitle, adList);
                            listView.setAdapter(arrayAdapter);

                            // Þegar owner klikkar á auglýsingarnar sínar
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    try {
                                        Item item = new Item(adArray.getJSONObject(position));
                                        Intent i = new Intent(getApplicationContext(), ViewAdActivity.class);
                                        i.putExtra("chosenItem", item.getId());
                                        i.putExtra("itemOwner", item.getOwner());
                                        i.putExtra("user", currentUser);
                                        startActivity(i);
                                    } catch (Exception e) {
                                        //  Intent intent = new Intent(MainActivity.this, ViewAdActivity.class);
                                        //  startActivity(intent);
                                    }
                                }
                            });
                        }
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        };

        String userID = currentUser.getId();
        String request = "items/owner/" + userID;
        ItemRequest sortRequest = new ItemRequest(request, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MyAdsActivity.this);
        queue.add(sortRequest);

    }
}
