package com.gefins;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import Entities.Item;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

import Requests.ItemRequest;
import Services.ItemService;


public class MainActivity extends NavbarActivity {
    private ItemService itemservice = new ItemService();
    private Button inQueueButton;
    private ImageView adImage;
    private TextView categoryTxtView, zipTxtView, numberInQueueTxtView, descriptionTxtView, ownerInfoTxtView, adNameTxtView;
    private Button createAdBtn, filterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.drawer_title);
        mTitle.setText(R.string.app_name);

        createAdBtn = findViewById(R.id.createAdButton);
        filterBtn = findViewById(R.id.filterButton);
        adImage = findViewById(R.id.viewad_image);
        categoryTxtView = findViewById(R.id.category_container);
        zipTxtView = findViewById(R.id.zip_container);
        numberInQueueTxtView = findViewById(R.id.number_queue_container);
        descriptionTxtView = findViewById(R.id.description_container);
        ownerInfoTxtView = findViewById(R.id.ownerinfoContainer);
        adNameTxtView = findViewById(R.id.ad_name_container);

      //  ownerInfoTxtView.setMovementMethod(new ScrollingMovementMethod());
      //  descriptionTxtView.setMovementMethod(new ScrollingMovementMethod());



        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //debug
                    Log.d("JSONADLIST ", response);
                    JSONObject jsonResponse= new JSONObject(response);
                    final JSONArray items =  jsonResponse.getJSONArray("items");
                    String names[] = new String[items.length()];
                    String imgs[] = new String[items.length()];

                    for(int i = 0; i < items.length(); i++){
                        JSONObject item = items.getJSONObject(i);
                        names[i] = item.getString("name");
                        imgs[i] = "https://res.cloudinary.com/aso40/image/upload/v1554385218/avatars-000559149189-tawe7l-t500x500.jpg";
                        Log.d("NAME[]", names[i]);
                        Log.d("IMG[]", imgs[i]);
                    }
                    GridView gridView = findViewById(R.id.gridView);
                    CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(MainActivity.this, names, imgs);
                    gridView.setAdapter(adapterViewAndroid);

/*
                    ImageView image = findViewById(R.id.Img_item);
                    JSONObject item = items.getJSONObject(31);
                    Log.d("IMG", item.getString("img"));
                    String url = item.getString("img");
                    new DownloadImg(image).execute(url);
*/
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, names);
                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.d("GRIDVIEW", String.valueOf(position) );
                            try {
                                Item item = new Item(items.getJSONObject(position));
                                System.out.print("TEST: ");
                                System.out.println(items.getJSONObject(position));

                                Intent viewIntent = new Intent(MainActivity.this, ViewAdActivity.class);
                                viewIntent.putExtra("chosenItem", item.getId());
                                startActivity(viewIntent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        };


        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String request = ArrayStringListToRequest(extras.getStringArrayList("chosenCategories"));
            //String request2 = ArrayStringListToRequest2(extras.getStringArrayList("chosenLocations"));

            Log.d("REMOLAÐI", request);
            //Log.d("REMOLAÐI2", request2);


            ItemRequest sortRequest = new ItemRequest(request, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(sortRequest);
            /*
            ItemRequest sortRequest2 = new ItemRequest(request2, responseListener);
            RequestQueue queue2 = Volley.newRequestQueue(MainActivity.this);
            queue2.add(sortRequest2);
            */

        } else {
            ItemRequest adListRequest = new ItemRequest("items", responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(adListRequest);
        }



        // Virknin á "skrá auglýsingu" takkanum
        createAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Færir frá forsíðu yfir á ný auglýsing skjá
                Intent intent = new Intent(MainActivity.this, AdActivity.class);
                startActivity(intent);
            }
        });

        // Virknin á "Sía" takkanum
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Færir frá forsíðu yfir á síu skjá
                Intent intent = new Intent(MainActivity.this, SortActivity.class);
                startActivity(intent);
            }
        });

        GradientDrawable gd1 = new GradientDrawable();
        gd1.setCornerRadius(5);

    }

    public String ArrayStringListToRequest(ArrayList<String> list) {
        String request = "items?";
        for(int i = 0; i < list.size(); i++){
            request += "category=" + list.get(i);
            if(i+1 != list.size()){
                request += "&";
            }
        }

        return request;
    }
    public String ArrayStringListToRequest2(ArrayList<String> list) {
        String request = "items?";
        for(int i = 0; i < list.size(); i++){
            request += "zip=" + list.get(i);
            if(i+1 != list.size()){
                request += "&";
            }
        }

        return request;
    }
}
