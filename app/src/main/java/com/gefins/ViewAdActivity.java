package com.gefins;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import Entities.OwnerInfo;
import Entities.QueueInfo;
import Entities.User;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Entities.Item;
import Requests.ItemRequest;
import Requests.MessageRequest;

/* Eftir að klára allt varðandi ViewAd */

public class ViewAdActivity extends ExitNavbarActivity {
    private Button inQueueButton,enterQueueBtn,acceptfromqueue, editAd, deleteAdBtn, messageBtn, cancelqueue;
    private ImageView adImg;
    private ImageView stars1,stars2, stars3, stars4, stars5;
    private User currentUser;
    private TextView categoryTxtView, zipTxtView, numberInQueueTxtView, descriptionTxtView, ownerInfoTxtView, adNameTxtView, numberQueueTxtView, firstQueueTxtView, ownerStarsTxtView, ownerAddressTxtView, ownerPhoneTxtView, ownerEmailTxtView, messageWindowTxtView;
    private EditText messageEdtText;
    private Item item;
    private String itemID, numInQue, firstInQue, option;
    private boolean isOwner;
    private int layout;
    ListView listView;
    ArrayList<String> messageList = new ArrayList<>();
    private LinearLayout acceptLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final Bundle extras = getIntent().getExtras();
        String itemOwner = extras.getString("itemOwner");
        String accepted = extras.getString("accepted");
        currentUser = (User) getIntent().getSerializableExtra("user");
        isOwner = currentUser.getUserName().equals(itemOwner);


        if (currentUser.getId().equals(accepted) || isOwner && !accepted.equals("0")){
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
          //  enterQueueBtn.setText("Fara í röð");
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.exit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.exit_title);
        mTitle.setText(R.string.viewad_title);

        listView = (ListView)findViewById(R.id.message_list);

        //set currentUser


        itemID = extras.getString("chosenItem");

        acceptfromqueue = findViewById(R.id.accept_from_queue);

        cancelqueue = findViewById(R.id.cancel_queue);

        adImg = findViewById(R.id.adImg);
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
        //messageWindowTxtView = findViewById(R.id.message_window);
        messageBtn = findViewById(R.id.message_button);
        deleteAdBtn = findViewById(R.id.deleteAd);
        stars1 = findViewById(R.id.star1);
        stars2 = findViewById(R.id.star2);
        stars3 = findViewById(R.id.star3);
        stars4 = findViewById(R.id.star4);
        stars5 = findViewById(R.id.star5);



        //ownerInfoTxtView.setMovementMethod(new ScrollingMovementMethod());

       // ownerInfoTxtView.setMovementMethod(new ScrollingMovementMethod());
       // descriptionTxtView.setMovementMethod(new ScrollingMovementMethod());


        final Response.Listener<String> responseListener6 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse= new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("msg");
                    if(success) {
                        enterQueueBtn.setText("Fara úr röð");
                    } else {
                        enterQueueBtn.setText("Fara í röð");
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        };
        isUserInQueue(accepted,responseListener6);


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
                    firstInQue = jsonResponse.getJSONObject("item").getJSONObject("queueInfo").getString("firstInQue");
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
                        enterQueueBtn.setText("Fara í röð");
                        Log.d("ENTERQUE", itemID);
                        ItemRequest sortRequest = new ItemRequest("Items/queue", "2", itemID, currentUser.getId(), responseListener2);
                        RequestQueue queue = Volley.newRequestQueue(ViewAdActivity.this);
                        queue.add(sortRequest);
                    } else {
                        enterQueueBtn.setText("Fara úr röð");
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
                        reload();
                    } else {
                        Log.d("j2","responseListener4");
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        };


        if(isOwner && accepted.equals("0")) {
            //acceptar user í röð
            acceptfromqueue.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    item.setAcceptedUser(firstInQue);
                    ItemRequest inQueueRequest = new ItemRequest("items/queue", "4", itemID, currentUser.getId(), responseListener4);
                    RequestQueue queue1 = Volley.newRequestQueue(ViewAdActivity.this);
                    queue1.add(inQueueRequest);
                }
            });
        }
        isUserInQueue(accepted,responseListener6);

        // setti if því hann getur ekki set listener á takka sem er ekki til
        if (!isOwner && !accepted.equals(currentUser.getId())) {
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


        if (layout == 3){
            // add message takki
            cancelqueue.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    item.setAcceptedUser("0");
                    ItemRequest inQueueRequest = new ItemRequest("items/queue","3",itemID,"0", responseListener4);
                    RequestQueue queue1 = Volley.newRequestQueue(ViewAdActivity.this);
                    queue1.add(inQueueRequest);
                }

            });
        }


        if (layout == 3){
            // add message takki
            messageBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String message = messageEdtText.getText().toString();
                    //String a = currentUser.getUserName();
                    //a += "\n" + messageEdtText.getText().toString();

                    MessageRequest messageRequest = new MessageRequest("/sendMessage",
                            itemID, currentUser.getId(), message , responseListener4);
                    RequestQueue queue1 = Volley.newRequestQueue(ViewAdActivity.this);
                    queue1.add(messageRequest);
                }

            });
        }


        // setti if því hann getur ekki set listener á takka sem er ekki til
        if (isOwner && accepted.equals("0")) {
            deleteAdBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemRequest inQueueRequest = new ItemRequest ("Items/delete", itemID,77, responseListener5);
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
        String aUrl = item.getImg().replace("http", "https");
        Picasso.with(getApplicationContext())
                .load(Uri.parse(aUrl))
                .placeholder(R.drawable.earth_color)
                .into(adImg);
        String rating  = item.getOwnerInfo().getStars();
        initStars(rating);
    }

    public void viewadOwner(String numQueue, String firstQueue) {

        adNameTxtView.setText(item.getItemName());
        descriptionTxtView.setText(item.getDescription());
        categoryTxtView.setText(item.getCategory());
        zipTxtView.setText(item.getZipcode());
        numberQueueTxtView.setText(numQueue);
        firstQueueTxtView.setText(firstQueue);

        String aUrl = item.getImg().replace("http", "https");
        Picasso.with(getApplicationContext())
                .load(Uri.parse(aUrl))
                .placeholder(R.drawable.earth_color)
                .into(adImg);
    }

    public void viewadAccepted() {
        /*
        Log.d("vA",item.toString());
        messageWindowTxtView.setText(item.getMessenger());
        messageList.add(item.getMessenger());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ViewAdActivity.this,
                R.layout.message_item_layout, R.id.message, messageList);
        listView.setAdapter(arrayAdapter);

        Log.d("vA",messageWindowTxtView.getText().toString());
        */

        ////////////////////////////////////////////////////////item.getMessageInfo().getUserName();

        String[] usernames = {"anna", "ragnar", "petur", "palina", "halli", "tussa"};
        String[] messages = {"hallo", "flott", "jajajjaja", "akldjglkajdslkjgal", "jalkdsjglka", "ladsjglkajg"};

        // Listi af mínum auglýsingum
        //MessageListAdapter messageListAdapter = new MessageListAdapter(getApplicationContext(),
         //       R.layout.message_item_layout, usernames, messages);
        //listView.setAdapter(messageListAdapter);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ViewAdActivity.this,
                R.layout.message_item_layout, R.id.message_username, usernames);
        listView.setAdapter(arrayAdapter);

        adNameTxtView.setText(item.getItemName());
//        descriptionTxtView.setText(item.getDescription());
        categoryTxtView.setText(item.getCategory());
        zipTxtView.setText(item.getZipcode());
        String aUrl = item.getImg().replace("http", "https");
        Picasso.with(getApplicationContext())
                .load(Uri.parse(aUrl))
                .placeholder(R.drawable.earth_color)
                .into(adImg);


        acceptLayout=(LinearLayout)this.findViewById(R.id.accepted_layout);

        if(isOwner) {
            acceptLayout.setVisibility(LinearLayout.GONE);
        } else {
            acceptLayout.setVisibility(LinearLayout.VISIBLE);
            ownerInfoTxtView.setText(item.getOwner());
            //String stars = String.valueOf();
            // ownerStarsTxtView.setText(stars);
            ownerAddressTxtView.setText(item.getOwnerInfo().getLocation());
            ownerPhoneTxtView.setText(item.getOwnerInfo().getPhone());
            ownerEmailTxtView.setText(item.getOwnerInfo().getEmail());
        }

        //}
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

    public void isUserInQueue(String accepted, Response.Listener<String> responseListener){
        if (!isOwner && !accepted.equals(currentUser.getId())) {
            ItemRequest inQueueRequest = new ItemRequest ("Items/queue","exists", itemID, currentUser.getId(), responseListener);
            RequestQueue queue1 = Volley.newRequestQueue(ViewAdActivity.this);
            queue1.add(inQueueRequest);
        }
    }
    private  void reload(){
        finish();
        Intent i = new Intent(getApplicationContext(),ViewAdActivity.class);
        i.putExtra("chosenItem", item.getId());
        i.putExtra("itemOwner", item.getOwner());
        i.putExtra("accepted",item.getAcceptedUser());
        i.putExtra("user", currentUser);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent( this, MainActivity.class);
        intent.putExtra("user", currentUser);
        this.startActivity(intent);
        return true;
    }


    public void initStars(String rating){
        switch (rating){
            case "0":
                stars1.setImageResource(R.drawable.earth_rating_empty);
                stars2.setImageResource(R.drawable.earth_rating_empty);
                stars3.setImageResource(R.drawable.earth_rating_empty);
                stars4.setImageResource(R.drawable.earth_rating_empty);
                stars5.setImageResource(R.drawable.earth_rating_empty);

                break;
            case "0.5":
                stars1.setImageResource(R.drawable.earth_rating_empty);
                stars2.setImageResource(R.drawable.earth_rating_empty);
                stars3.setImageResource(R.drawable.earth_rating_empty);
                stars4.setImageResource(R.drawable.earth_rating_empty);
                stars5.setImageResource(R.drawable.earth_rating_half);
                break;
            case "1":
                stars1.setImageResource(R.drawable.earth_rating_empty);
                stars2.setImageResource(R.drawable.earth_rating_empty);
                stars3.setImageResource(R.drawable.earth_rating_empty);
                stars4.setImageResource(R.drawable.earth_rating_empty);
                stars5.setImageResource(R.drawable.earth_rating_full);
                break;
            case "1.5":
                stars1.setImageResource(R.drawable.earth_rating_empty);
                stars2.setImageResource(R.drawable.earth_rating_empty);
                stars3.setImageResource(R.drawable.earth_rating_empty);
                stars4.setImageResource(R.drawable.earth_rating_half);
                stars5.setImageResource(R.drawable.earth_rating_full);
                break;
            case "2":
                stars1.setImageResource(R.drawable.earth_rating_empty);
                stars2.setImageResource(R.drawable.earth_rating_empty);
                stars3.setImageResource(R.drawable.earth_rating_empty);
                stars4.setImageResource(R.drawable.earth_rating_full);
                stars5.setImageResource(R.drawable.earth_rating_full);
                break;
            case "2.5":
                stars1.setImageResource(R.drawable.earth_rating_empty);
                stars2.setImageResource(R.drawable.earth_rating_empty);
                stars3.setImageResource(R.drawable.earth_rating_half);
                stars4.setImageResource(R.drawable.earth_rating_full);
                stars5.setImageResource(R.drawable.earth_rating_full);
                break;
            case "3":
                stars1.setImageResource(R.drawable.earth_rating_empty);
                stars2.setImageResource(R.drawable.earth_rating_empty);
                stars3.setImageResource(R.drawable.earth_rating_full);
                stars4.setImageResource(R.drawable.earth_rating_full);
                stars5.setImageResource(R.drawable.earth_rating_full);
                break;
            case "3.5":
                stars1.setImageResource(R.drawable.earth_rating_empty);
                stars2.setImageResource(R.drawable.earth_rating_half);
                stars3.setImageResource(R.drawable.earth_rating_full);
                stars4.setImageResource(R.drawable.earth_rating_full);
                stars5.setImageResource(R.drawable.earth_rating_full);
                break;
            case "4":
                stars1.setImageResource(R.drawable.earth_rating_empty);
                stars2.setImageResource(R.drawable.earth_rating_full);
                stars3.setImageResource(R.drawable.earth_rating_full);
                stars4.setImageResource(R.drawable.earth_rating_full);
                stars5.setImageResource(R.drawable.earth_rating_full);
                break;
            case "4.5":
                stars1.setImageResource(R.drawable.earth_rating_half);
                stars2.setImageResource(R.drawable.earth_rating_full);
                stars3.setImageResource(R.drawable.earth_rating_full);
                stars4.setImageResource(R.drawable.earth_rating_full);
                stars5.setImageResource(R.drawable.earth_rating_full);
                break;
            case "5":
                stars1.setImageResource(R.drawable.earth_rating_full);
                stars2.setImageResource(R.drawable.earth_rating_full);
                stars3.setImageResource(R.drawable.earth_rating_full);
                stars4.setImageResource(R.drawable.earth_rating_full);
                stars5.setImageResource(R.drawable.earth_rating_full);
                break;

                default:

      }

    }

}
