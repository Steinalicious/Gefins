package com.gefins;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
<<<<<<< HEAD
import android.util.Log;
=======
import android.support.v7.widget.Toolbar;
>>>>>>> 86636967111baa50f86586e024a2c3eef46cf530
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
<<<<<<< HEAD
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
=======
import android.widget.TextView;
>>>>>>> 86636967111baa50f86586e024a2c3eef46cf530

public class MainActivity extends NavbarActivity {

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

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //debug
                    Log.d("JSONADLIST ", response);
                    JSONObject jsonResponse= new JSONObject(response);
                    JSONArray items =  jsonResponse.getJSONArray("items");
                    String names[] = new String[items.length()];

                    for(int i = 0; i < items.length(); i++){
                        JSONObject item = items.getJSONObject(i);
                        names[i] = item.getString("name");
                        Log.d("NAME", names[i]);
                    }
                    ListView listView = findViewById(R.id.itemsList);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, names);
                    listView.setAdapter(adapter);


                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        AdListRequest adListRequest = new AdListRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(adListRequest);


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
}
