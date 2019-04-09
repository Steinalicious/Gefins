package com.gefins;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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
    //ATHUGA
    private String imgUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle extras = getIntent().getExtras();
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_editad, contentFrameLayout);

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
        submitAdBtn = findViewById(R.id.submitAdBtn);
        btn_picker = findViewById(R.id.btn_picker);

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
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.putExtra("user", currentUser);
                startActivityForResult(intent, 7);
            }
        });

        submitAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        try {
                            //debug
                            Log.d("JSONADMAKER", response);
                            JSONObject jsonResponse= new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {
                                // Færir frá EditAd skjá á forsíðu
                                Intent intent = new Intent( EditAdActivity.this, MainActivity.class);
                                intent.putExtra("user", currentUser);
                                EditAdActivity.this.startActivity(intent);
                            } else{
                                // Lætur vita ef innskráning mistókst
                                AlertDialog.Builder builder = new AlertDialog.Builder( EditAdActivity.this);
                                builder.setMessage("Breyting á auglýsingu mistókst")
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 7:
                if (resultCode == Activity.RESULT_OK) {
                    String PathHolder = data.getData().getPath();
                    Toast.makeText(EditAdActivity.this, PathHolder, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
