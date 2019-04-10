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
import android.widget.EditText;
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
    private Button inQueueButton,enterQueueBtn,acceptfromqueue, editAd, deleteAdBtn;
    private ImageView adImage;
    private User currentUser;
    private TextView categoryTxtView, zipTxtView, numberInQueueTxtView, descriptionTxtView, ownerInfoTxtView, adNameTxtView, numberQueueTxtView, firstQueueTxtView, ownerStarsTxtView, ownerAddressTxtView, ownerPhoneTxtView, ownerEmailTxtView, messageWindowTxtView;
    private EditText messageEdtText;
    private Item item;
    private String itemID, numInQue, firstInQue, option;
    private boolean isOwner;
    private int layout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final Bundle extras = getIntent().getExtras();
        String itemOwner = extras.getString("itemOwner");
        String accepted = extras.getString("accepted");
        currentUser = (User) getIntent().getSerializableExtra("user");
        isOwner = currentUser.getUserName().equals(itemOwner);


        if (currentUser.getUserName().equals(accepted) || isOwner && !accepted.equals("0")){
            layout =3;
            FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
            getLayoutInflater().inflate(R.layout.activity_viewadaccepted, contentFrameLayout);
        }
        else if (isOwner ) {
            layout =2;
            Log.d("Fyrsta", itemOwner );
            FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
            getLayoutInflater().inflate(R.layout.activity_viewadowner, contentFrameLayout);
        } else {
            layout =1;
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

        acceptfromqueue = findViewById(R.id.accept_from_queue);

        adImage = findViewById(R.id.viewad_image);
        categoryTxtView = findViewById(R.id.category_container);
        zipTxtView = findViewById(R.id.zip_container);
        numberInQueueTxtView = findViewById(R.id.number_queue_container);
        descriptionTxtView = findViewById(R.id.description_container);
        ownerInfoTxtView = findViewById(R.id.owner_container);
        ownerStarsTxtView = findViewById(R.id.userStars_container);
        adNameTxtView = findViewById(R.id.ad_name_container);
        numberQueueTxtView = findViewById(R.id.number_queue_container);
        //descriptionTxtView.setMovementMethod(new ScrollingMovementMethod());
        enterQueueBtn = findViewById(R.id.enter_queue);
        firstQueueTxtView = findViewById(R.id.first_in_queue_container);
        ownerAddressTxtView = findViewById(R.id.owner_address_container);
        ownerPhoneTxtView = findViewById(R.id.owner_phone_container);
        ownerEmailTxtView = findViewById(R.id.owner_email_container);
        messageEdtText = findViewById(R.id.message_editor);
        messageWindowTxtView = findViewById(R.id.message_window);
        deleteAdBtn = findViewById(R.id.deleteAd);

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
                    String test = item.getQueueInfo().getNumInQue();
                    Log.d("HAHAHAH", item.getQueueInfo().getNumInQue());
                    firstInQue = jsonResponse.getJSONObject("item").getJSONObject("queueInfo").getString("firstInQue");
                    Log.d("j5",item.getAcceptedUser());
                    Log.d("JSONRESPONSE ", firstInQue);
                    Log.d("j3",""+layout);
                    if(layout==3) {
                        viewadAccepted();
                    }
                    else if (layout==2) {
                        Log.d("j4",""+layout);
                        System.out.println("innri loopa");
                        viewadOwner(numInQue, firstInQue);
                        editAd = findViewById(R.id.editAd);
                        editAd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ViewAdActivity.this, EditAdActivity.class);
                                intent.putExtra("chosenItem", item.getId());
                                intent.putExtra("user", currentUser);
                                startActivity(intent);
                            }
                        });

                    } else {
                        viewad(numInQue);
                      //  viewadAccepted();
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

        final Response.Listener<String> responseListener3 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //debug
                    Log.d("JSONQUEUE ", response);
                    JSONObject jsonResponse= new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("msg");
                    if (success) {
                        enterQueueBtn.setText("Fara úr röð");
                        Log.d("ENTERQUE", itemID);
                        ItemRequest sortRequest = new ItemRequest("Items/queue", "2", itemID, currentUser.getId(), responseListener2);
                        RequestQueue queue = Volley.newRequestQueue(ViewAdActivity.this);
                        queue.add(sortRequest);
                    } else {
                        enterQueueBtn.setText("Fara í röð");
                        Log.d("LEAVEQUE", itemID);
                        ItemRequest sortRequest = new ItemRequest("Items/queue", "1", itemID, currentUser.getId(), responseListener2);
                        RequestQueue queue = Volley.newRequestQueue(ViewAdActivity.this);
                        queue.add(sortRequest);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        };


        final Response.Listener<String> responseListener4 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //debug
                    Log.d("JSONQUEUE ", response);
                    JSONObject jsonResponse= new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Log.d("j1","225");
                        layout=3;
                        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
                        getLayoutInflater().inflate(R.layout.activity_viewadaccepted, contentFrameLayout);
                        getitem(responseListener);
                    } else {
                        Log.d("j2","responseListener4");
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        };

        if(isOwner && accepted.equals("0")) {
            acceptfromqueue.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.d("j7","listener");
                    ItemRequest inQueueRequest = new ItemRequest("items/queue", "4", itemID, currentUser.getId(), responseListener4);
                    RequestQueue queue1 = Volley.newRequestQueue(ViewAdActivity.this);
                    queue1.add(inQueueRequest);
                }
            });
        }

        // setti if því hann getur ekki set listener á takka sem er ekki til
        if (!isOwner && !accepted.equals(currentUser.getUserName())) {
            enterQueueBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemRequest inQueueRequest = new ItemRequest ("Items/queue","exists", itemID, currentUser.getId(), responseListener3);
                    RequestQueue queue1 = Volley.newRequestQueue(ViewAdActivity.this);
                    queue1.add(inQueueRequest);
                }

            });
        }

        final Response.Listener<String> responseListener5 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //debug
                    Log.d("JSONDELETE ", response);
                    JSONObject jsonResponse= new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success) {
                        finish();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        };


        // setti if því hann getur ekki set listener á takka sem er ekki til
        if (isOwner && accepted.equals("0")) {
            deleteAdBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemRequest inQueueRequest = new ItemRequest ("Items/delete", itemID,77, responseListener4);
                    RequestQueue queue1 = Volley.newRequestQueue(ViewAdActivity.this);
                    queue1.add(inQueueRequest);
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
      //  ownerStarsTxtView.setText(stars);
    }

    public void viewadOwner(String numQueue, String firstQueue) {

        adNameTxtView.setText(item.getItemName());
        descriptionTxtView.setText(item.getDescription());
        categoryTxtView.setText(item.getCategory());
        zipTxtView.setText(item.getZipcode());
        numberQueueTxtView.setText(numQueue);
        firstQueueTxtView.setText(firstQueue);
    }

    public void viewadAccepted() {
        Log.d("j8",item.toString());
        adNameTxtView.setText(item.getItemName());
//        descriptionTxtView.setText(item.getDescription());
        categoryTxtView.setText(item.getCategory());
        zipTxtView.setText(item.getZipcode());
       // ownerInfoTxtView.setText(item.getOwner());
        //String stars = String.valueOf();
       // ownerStarsTxtView.setText(stars);
      //  ownerAddressTxtView.setText();
      //  ownerPhoneTxtView.setText();
      //  ownerEmailTxtView.setText();
    }

    public void getitem(Response.Listener<String> responseListener) {
        Log.d("ITEMID", itemID);
        String request = "items/" + itemID;
        ItemRequest sortRequest = new ItemRequest(request, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ViewAdActivity.this);
        queue.add(sortRequest);
    }
}