package com.gefins;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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

public class InQueueActivity extends BackNavbarActivity {

    private User currentUser;
    ListView listViewInQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_inqueue, contentFrameLayout);

        // Title in header
        Toolbar toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.back_title);
        mTitle.setText(R.string.inQueueFor);

        listViewInQueue = (ListView)findViewById(R.id.listViewInQueue);

        // set currentUsers
        currentUser = (User) getIntent().getSerializableExtra("user");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    final JSONArray adArray = jsonResponse.getJSONArray("items");
                    Item item = new Item();

                    ArrayList<String> adList = new ArrayList<>();
                    String names[] = new String[adArray.length()];
                    String imgUrls[] = new String[adArray.length()];
                    String aUrls[] = new String[adArray.length()];
                    String queue[] = new String[adArray.length()];

                    for(int i = 0; i < adArray.length(); i++) {
                        item = new Item(adArray.getJSONObject(i));
                        names[i] = item.getItemName();
                        if (item.getAcceptedUser().equals(currentUser.getId())) {
                            names[i] += "  ✔ ";
                        }
                        queue[i] = item.getQueueInfo().getNumInQue();
                        imgUrls[i] = item.getImg();
                        aUrls[i] = imgUrls[i].replace("http", "https");
                        if (item == null) {
                            return;
                        } else {
                            adList.add(item.getItemName());
                            String accepter = item.getAcceptedUser();
                            // List of ads
                            ListViewAdapter listViewAdapter = new ListViewAdapter(getApplicationContext(),
                                    R.layout.list_item_layout, names, aUrls, queue);
                            listViewInQueue.setAdapter(listViewAdapter);

                            // When owner clicks on his ads
                            listViewInQueue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    try {
                                        Item item = new Item(adArray.getJSONObject(position));
                                        Intent i = new Intent(getApplicationContext(), ViewAdActivity.class);
                                        i.putExtra("chosenItem", item.getId());
                                        i.putExtra("accepted",item.getAcceptedUser());
                                        i.putExtra("user", currentUser);
                                        startActivity(i);
                                    } catch (Exception e) {
                                        e.printStackTrace();
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
        String request = "user/queued/" + userID;
        ItemRequest sortRequest = new ItemRequest(request, responseListener);
        RequestQueue queue = Volley.newRequestQueue(InQueueActivity.this);
        queue.add(sortRequest);

    }
}
