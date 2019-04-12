package com.gefins;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import Entities.Item;
import Entities.User;
import Requests.ItemRequest;

public class EditAdActivity extends BackNavbarActivity {

    private User currentUser;
    private Item currentItem;
    private Button submitAdBtn, btn_picker;
    private EditText title_input, description_input, zip_input, itemLoc_input, phone_input;
    private Spinner spinner1;
    private Item item;
    private String itemID;
    private ImageView editAdImg;
    private Uri selectedImg;
    private int PICK_FILE_REQUEST = 1;
    //ATHUGA
    private String imgUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle extras = getIntent().getExtras();
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_editad, contentFrameLayout);

        try{ MediaManager.get();}

        catch (Exception e){
            MediaManager.init(this);
        }

        // Titill í header
        Toolbar toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.back_title);
        mTitle.setText(R.string.editAd);

        // set currentUser
        currentUser = (User) getIntent().getSerializableExtra("user");

        title_input = findViewById(R.id.title_input);
        description_input = findViewById(R.id.description_input);
        zip_input = findViewById(R.id.zip_input);
        itemLoc_input = findViewById(R.id.itemLoc_input);
        phone_input = findViewById(R.id.phone_input);

        spinner1 = findViewById(R.id.spinner);
        spinner1.setOnItemSelectedListener(new EditAdActivity.ItemSelectedListener());
        submitAdBtn = findViewById(R.id.submitAdButton);
        btn_picker = findViewById(R.id.btn_picker);
        editAdImg = findViewById(R.id.editAdImg);

        itemID = extras.getString("chosenItem");

        // Sækja viðeigandi item
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //debug
                    Log.d("JSONADLIST ", response);
                    JSONObject jsonResponse= new JSONObject(response);
                    item = new Item(jsonResponse.getJSONObject("item"));

                    // Setja gömlu gildin í reitina
                    title_input.setText(item.getItemName());
                    description_input.setText(item.getDescription());
                    zip_input.setText(item.getZipcode());
                    itemLoc_input.setText(item.getLocation());
                    phone_input.setText(item.getPhone());
                    String cat = item.getCategory();
                    spinner1.setSelection(getIndex(spinner1, cat));

                    String aUrl = item.getImg().replace("http", "https");
                    Picasso.with(getApplicationContext()).load(Uri.parse(aUrl)).into(editAdImg);

                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        };
        String request = "items/" + itemID;
        ItemRequest sortRequest = new ItemRequest(request, responseListener);
        RequestQueue queue = Volley.newRequestQueue(EditAdActivity.this);
        queue.add(sortRequest);


        btn_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(Intent.createChooser(intent,"Choose image"), PICK_FILE_REQUEST);
            }
        });

        submitAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requestId = MediaManager.get()
                        .upload(selectedImg)
                        .unsigned("utmqe54f")
                        .callback(new UploadCallback() {
                            @Override
                            public void onStart(String requestId) {
                                Toast.makeText(EditAdActivity.this, "Upload has started...",
                                        Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onProgress(String requestId, long bytes, long totalBytes) {

                            }

                            @Override
                            public void onSuccess(String requestId, Map resultData) {
                                Toast.makeText(EditAdActivity.this,
                                        "Uploaded Succesfully", Toast.LENGTH_SHORT).show();
                                String publicId = resultData.get("public_id").toString();
                                imgUrl = MediaManager.get().url().generate(publicId+".jpg");

                                Log.d("selected", imgUrl);


                                title_input = findViewById(R.id.title_input);
                                description_input = findViewById(R.id.description_input);
                                zip_input = findViewById(R.id.zip_input);
                                itemLoc_input = findViewById(R.id.itemLoc_input);
                                phone_input = findViewById(R.id.phone_input);


                                Item item = new Item(currentUser.getUserName(),currentUser.getId(),
                                        description_input.getText().toString(),itemLoc_input.getText().toString(),
                                        phone_input.getText().toString(),title_input.getText().toString(),
                                        currentUser.getEmail(),zip_input.getText().toString(),
                                        spinner1.getSelectedItem().toString(), imgUrl);


                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("JSONADMAKER", "hei");
                                        try {
                                            //debug
                                            Log.d("JSONADMAKER", response);
                                            JSONObject jsonResponse= new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");

                                            if(success) {
                                                // Færir frá Login skjá á forsíðu
                                                finish();
                                                Intent intent = new Intent( EditAdActivity.this, MainActivity.class);
                                                intent.putExtra("user", currentUser);
                                                EditAdActivity.this.startActivity(intent);
                                            } else{
                                                // Lætur vita ef innskráning mistókst
                                                AlertDialog.Builder builder = new AlertDialog.Builder( EditAdActivity.this);
                                                builder.setMessage("Auglýsingaskráning mistókst")
                                                        .setNegativeButton("Reyna aftur", null)
                                                        .create()
                                                        .show();
                                            }
                                        } catch (JSONException e){
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                ItemRequest itemRequest = new ItemRequest(item, "editad", itemID, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(EditAdActivity.this);
                                queue.add(itemRequest);
                            }

                            @Override
                            public void onError(String requestId, ErrorInfo error) {
                                Log.d("selected", requestId);
                                Toast.makeText(EditAdActivity.this,
                                        "Upload Error", Toast.LENGTH_SHORT).show();
                                Log.v("ERROR!!", error.getDescription());
                            }

                            @Override
                            public void onReschedule(String requestId, ErrorInfo error) {

                            }
                        }).dispatch();
            }
        });
    }

    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(spinner1.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (firstItem.equals(String.valueOf(spinner1.getSelectedItem()))) {
            } else {
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if(requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK) {
            selectedImg = data.getData();
            Picasso.with(this).load(selectedImg).into(editAdImg);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        Intent intent = new Intent( this, MainActivity.class);
        intent.putExtra("user", currentUser);
        EditAdActivity.this.startActivity(intent);
        return true;
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

}
