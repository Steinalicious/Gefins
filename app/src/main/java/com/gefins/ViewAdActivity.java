package com.gefins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import Entities.OwnerInfo;
import Entities.QueueInfo;
import Entities.User;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Entities.Item;
import Requests.ItemRequest;

/* Eftir að klára allt varðandi ViewAd */

public class ViewAdActivity extends BackNavbarActivity {
    private Button inQueueButton,enterQueueBtn;
    private ImageView adImage;
    private User currentUser;
    private TextView categoryTxtView, zipTxtView, numberInQueueTxtView, descriptionTxtView, ownerInfoTxtView, adNameTxtView, numberQueueTxtView, firstQueueTxtView, userStarsTxtView;
    private Item item;
    private String itemID, numInQue, firstInQue, option;
    private boolean isOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final Bundle extras = getIntent().getExtras();
        String itemOwner = extras.getString("itemOwner");

        currentUser = (User) getIntent().getSerializableExtra("user");
        isOwner = currentUser.getUserName().equals(itemOwner);
        if (isOwner) {
            Log.d("Fyrsta", itemOwner );
            FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
            getLayoutInflater().inflate(R.layout.activity_viewadowner, contentFrameLayout);
        } else {
            FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
            getLayoutInflater().inflate(R.layout.activity_viewad, contentFrameLayout);
            enterQueueBtn = findViewById(R.id.enter_queue);
            enterQueueBtn.setText("Fara í röð");
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.back_title);
        mTitle.setText(R.string.viewad_title);

        //set currentUser


        itemID = extras.getString("chosenItem");

        adImage = findViewById(R.id.viewad_image);
        categoryTxtView = findViewById(R.id.category_container);
        zipTxtView = findViewById(R.id.zip_container);
        numberInQueueTxtView = findViewById(R.id.number_queue_container);
        descriptionTxtView = findViewById(R.id.description_container);
        ownerInfoTxtView = findViewById(R.id.owner_container);
        userStarsTxtView = findViewById(R.id.userStars_container);
        adNameTxtView = findViewById(R.id.ad_name_container);
        numberQueueTxtView = findViewById(R.id.number_queue_container);
        descriptionTxtView.setMovementMethod(new ScrollingMovementMethod());
        enterQueueBtn = findViewById(R.id.enter_queue);
        firstQueueTxtView = findViewById(R.id.first_in_queue_container);

        //ownerInfoTxtView.setMovementMethod(new ScrollingMovementMethod());

       // ownerInfoTxtView.setMovementMethod(new ScrollingMovementMethod());
       // descriptionTxtView.setMovementMethod(new ScrollingMovementMethod());




        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //debug
                    Log.d("JSONAD ", response);
                    JSONObject jsonResponse= new JSONObject(response);
                    item = new Item(jsonResponse.getJSONObject("item"));
                    numInQue = jsonResponse.getJSONObject("item").getJSONObject("queueInfo").getString("numInQue");
                    firstInQue = jsonResponse.getJSONObject("item").getJSONObject("queueInfo").getString("firstInQue");

                    Log.d("JSONRESPONSE ", firstInQue);

                    if (currentUser.getUserName().equals(item.getOwner())) {
                        System.out.println("innri loopa");
                        viewadOwner(numInQue, firstInQue);
                    } else {
                        viewad(numInQue);
                    }


                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        };


        getitem(responseListener);



        final Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //debug
                    Log.d("JSONAD ", response);
                    JSONObject jsonResponse= new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success)
                        getitem(responseListener);

                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        };

        // setti if því hann getur ekki set listener á takka sem er ekki til
        if (!isOwner) {
            enterQueueBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (enterQueueBtn.getText().equals("Fara í röð")) {
                        enterQueueBtn.setText("Fara úr röð");
                        Log.d("ENTERQUE", itemID);
                        ItemRequest sortRequest = new ItemRequest("Items/queue", "1", itemID, currentUser.getId(), responseListener2);
                        RequestQueue queue = Volley.newRequestQueue(ViewAdActivity.this);
                        queue.add(sortRequest);
                    } else {
                        enterQueueBtn.setText("Fara í röð");
                        Log.d("LEAVEQUE", itemID);
                        ItemRequest sortRequest = new ItemRequest("Items/queue", "2", itemID, currentUser.getId(), responseListener2);
                        RequestQueue queue = Volley.newRequestQueue(ViewAdActivity.this);
                        queue.add(sortRequest);

                    }

                }

            });
        }
    }

    public void viewad(String numQueue) {
        adNameTxtView.setText(item.getItemName());
        descriptionTxtView.setText(item.getDescription());
        categoryTxtView.setText(item.getCategory());
        zipTxtView.setText(item.getZipcode());
        ownerInfoTxtView.setText(item.getOwner());
        numberQueueTxtView.setText(numQueue);
      //  String stars = String.valueOf();
      //  userStarsTxtView.setText(stars);
    }

    public void viewadOwner(String numQueue, String firstQueue) {
        adNameTxtView.setText(item.getItemName());
        descriptionTxtView.setText(item.getDescription());
        categoryTxtView.setText(item.getCategory());
        zipTxtView.setText(item.getZipcode());
        numberQueueTxtView.setText(numQueue);
        firstQueueTxtView.setText(firstQueue);
    }
/*
    public void viewadAccepted() {
        adNameTxtView.setText(item.getItemName());
        descriptionTxtView.setText(item.getDescription());
        categoryTxtView.setText(item.getCategory());
        zipTxtView.setText(item.getZipcode());
        ownerInfoTxtView.setText(item.getOwner());
    }*/

    public void getitem(Response.Listener<String> responseListener) {
        Log.d("ITEMID", itemID);
        String request = "items/" + itemID;
        ItemRequest sortRequest = new ItemRequest(request, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ViewAdActivity.this);
        queue.add(sortRequest);
    }
}