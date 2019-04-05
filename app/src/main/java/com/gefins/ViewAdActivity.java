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
    private Button inQueueButton;
    private ImageView adImage;
    private TextView categoryTxtView, zipTxtView, numberInQueueTxtView, descriptionTxtView, ownerInfoTxtView, adNameTxtView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_viewad, contentFrameLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.back_title);
        mTitle.setText(R.string.viewad_title);
        adImage = findViewById(R.id.viewad_image);
        categoryTxtView = findViewById(R.id.item_category);
        zipTxtView = findViewById(R.id.item_zip);
        numberInQueueTxtView = findViewById(R.id.number_queue_container);
        descriptionTxtView = findViewById(R.id.description_container);
        ownerInfoTxtView = findViewById(R.id.ownerinfoContainer);
        adNameTxtView = findViewById(R.id.ad_name_container);
        // ownerInfoTxtView.setMovementMethod(new ScrollingMovementMethod());
        //  descriptionTxtView.setMovementMethod(new ScrollingMovementMethod());
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //debug
                    Log.d("JSONAD ", response);
                    JSONObject jsonResponse= new JSONObject(response);
                    Item item = new Item(jsonResponse.getJSONObject("item"));


                    System.out.println("Item:"+item.toString());

                    String name = item.getItemName();
                    adNameTxtView.setText(item.getItemName());
                    String description = item.getDescription();
                    descriptionTxtView.setText(description);
                    String category = item.getCategory();

                    new DownloadImg(adImage).execute("https://res.cloudinary.com/aso40/image/upload/v1554385218/avatars-000559149189-tawe7l-t500x500.jpg");
                    categoryTxtView.setText(category);
                    String zip = item.getZipcode();
                    zipTxtView.setText(zip);
                    System.out.println(name);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };
        final Bundle extras = getIntent().getExtras();
        String id = extras.getString("chosenItem");
        String request = "items/" + id;
        ItemRequest sortRequest = new ItemRequest(request, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ViewAdActivity.this);
        queue.add(sortRequest);
    }
}