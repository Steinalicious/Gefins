package com.gefins;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import Entities.Item;

import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.SearchView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

import Entities.Sort;
import Entities.User;
import Requests.ItemRequest;
import Services.ItemService;


public class MainActivity extends NavbarActivity {
    private ItemService itemservice = new ItemService();
    private Button inQueueButton;
    private ImageView adImage, daemiImage;
    private VideoView daemiVideo;
    private ImageView img_ad, imageView4;
    private TextView categoryTxtView, zipTxtView, numberInQueueTxtView, descriptionTxtView, ownerInfoTxtView, adNameTxtView;
    private Button createAdBtn, filterBtn;
    private User currentUser;
    private SearchView searchView;
    private Sort sort;
    public static final String ITEM_REQUESTS = "item_req";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.drawer_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.drawer_title);
        mTitle.setText(R.string.appname);

        currentUser = (User) getIntent().getSerializableExtra("user");

        //image = (ImageView) findViewById(R.id.image_demo);
        //new DownloadImg(daemiImage).execute("https://res.cloudinary.com/aso40/image/upload/v1554740993/32042_1285425382323_82382_n.jpg");
        secretOfTheDay();

        if(currentUser==null){
            Log.d("ble","USERINN ER HORFINN!!!!!!");}

        sort = (Sort) getIntent().getSerializableExtra("sort");


        //daemiImage = (ImageView) findViewById(R.id.image_demo);
        //new DownloadImg(daemiImage).execute("https://res.cloudinary.com/aso40/image/upload/v1554740993/32042_1285425382323_82382_n.jpg");



        createAdBtn = findViewById(R.id.createAdButton);
        filterBtn = findViewById(R.id.filterButton);
        categoryTxtView = findViewById(R.id.category_container);
        zipTxtView = findViewById(R.id.zip_container);
        numberInQueueTxtView = findViewById(R.id.number_queue_container);
        descriptionTxtView = findViewById(R.id.description_container);
        ownerInfoTxtView = findViewById(R.id.owner_container);
        adNameTxtView = findViewById(R.id.ad_name_container);
        searchView = findViewById((R.id.search));

      //  ownerInfoTxtView.setMovementMethod(new ScrollingMovementMethod());
      //  descriptionTxtView.setMovementMethod(new ScrollingMovementMethod());



        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //debug
                    Log.d("JSONADLIST ", response);
                    JSONObject jsonResponse= new JSONObject(response);
                    final JSONArray items =  jsonResponse.getJSONArray("items");
                    String names[] = new String[items.length()];
                    String imgUrls[] = new String[items.length()];
                    String aUrls[] = new String[items.length()];

                    for(int i = 0; i < items.length(); i++){
                        JSONObject item = items.getJSONObject(i);
                        names[i] = item.getString("name");
                        imgUrls[i] = item.getString("img");
                        aUrls[i] = imgUrls[i].replace("http", "https");
                    }

                    /*
                    String aUrl = img.replace("http", "https");
                    //String imgUri = "\""+img+"\"";
                    img_ad = findViewById(R.id.img_ad);
                    //Picasso.with(this).load(imgUri).into(imageView4);
                    new DownloadImg(imageView4).execute(aUrl);
                    Picasso.with(context).load(Uri.parse(imageUrls[])).into((ImageView) v);
                    */


                    GridView gridView = findViewById(R.id.gridView);
                    GridViewAdapter gridViewAdapter = new GridViewAdapter(getApplicationContext(),
                            R.layout.grid_item_layout, names, aUrls);
                    gridView.setAdapter(gridViewAdapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.d("GRIDVIEW", String.valueOf(position) );
                            try {
                                Item item = new Item(items.getJSONObject(position));
                                Intent i = new Intent(getApplicationContext(),ViewAdActivity.class);
                                i.putExtra("chosenItem", item.getId());
                                i.putExtra("itemOwner", item.getOwner());
                                i.putExtra("accepted",item.getAcceptedUser());
                                i.putExtra("user", currentUser);
                                startActivity(i);
                            } catch (Exception e) {
                                //Intent intent = new Intent(MainActivity.this, ViewAdActivity.class);
                                //startActivity(intent);

                            }

                        }
                    });


                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        };


        if(sort != null) {
            String request = arrayStringListToRequest(sort.getALL());

            Log.d("REMOLAÐI", request);

            ItemRequest sortRequest = new ItemRequest(request, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(sortRequest);

        } else {
            ItemRequest adListRequest = new ItemRequest("items", responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(adListRequest);
        }

        final Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //debug
                    Log.d("JSONSEARCH ", response);
                    JSONObject jsonResponse= new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        };

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Do whatever you need. This will be fired only when submitting.
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                return true;
            }

            // Do whatever you need when text changes.
            // This will be fired every time you input any character.
            @Override
            public boolean onQueryTextChange(String newText) {
//              if (searchView.isExpanded() && TextUtils.isEmpty(newText)) {
                callSearch(newText);
//              }
                return true;
            }


            public void callSearch(String query) {
                //Do searching
                String request = "items/?search=" + query;
                ItemRequest sortRequest = new ItemRequest(request, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(sortRequest);
            }

        });


        // Virknin á "skrá auglýsingu" takkanum
        createAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Færir frá forsíðu yfir á ný auglýsing skjá
                Intent intent = new Intent(MainActivity.this, AdActivity.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
        });

        // Virknin á "Sía" takkanum
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sort==null)
                    sort=new Sort();
                //Færir frá forsíðu yfir á síu skjá
                Intent intent = new Intent(MainActivity.this, SortActivity.class);
                intent.putExtra("user", currentUser);
                intent.putExtra("sort", sort);
                startActivity(intent);
            }
        });

        GradientDrawable gd1 = new GradientDrawable();
        gd1.setCornerRadius(5);

    }
    public String arrayStringListToRequest(ArrayList<String> list) {
        String request = "items?";
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).matches(".*[0123456789].*")) {
                request += "zip=" + list.get(i);
                if(i+1 != list.size()){
                    request += "&";
                }
            } else if(!(list.get(i).matches(".*[0123456789].*"))) {
                request += "category=" + list.get(i);
                if(i+1 != list.size()){
                    request += "&";
                }
            }
        }

        return request;
    }

    public void secretOfTheDay(){
       // daemiImage = (ImageView) findViewById(R.id.image_demo);
        daemiVideo = (VideoView) findViewById(R.id.video_demo);


        String videoPath = "android.resource://"+getPackageName()+"/"+R.raw.ultra_dream_dragon1080p;
        Uri heh = Uri.parse(videoPath);
        daemiVideo.setVideoURI(heh);
        daemiVideo.start();
        daemiVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
        //new DownloadImg(daemiImage).execute("https://res.cloudinary.com/aso40/image/upload/c_scale,h_500/v1554837966/5afee48f1e000043008e5ecd.jpg");

    }
}
