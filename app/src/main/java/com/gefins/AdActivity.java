package com.gefins;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import Entities.Item;
import Requests.ItemRequest;

public class AdActivity extends ExitNavbarActivity {
    private Spinner spinner1;
    private Button button, submitBtn;
    private EditText titleEdTxt, descEdTxt, zipEdTxt, locEdTxt, phoneEdTxt,category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_ad, contentFrameLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.exit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.exit_title);
        mTitle.setText(R.string.new_ad);


        spinner1 = findViewById(R.id.spinner);
        spinner1.setOnItemSelectedListener(new ItemSelectedListener());
        button = findViewById(R.id.btn_picker);
        submitBtn = findViewById(R.id.submitAdButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 7);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleEdTxt = findViewById(R.id.title_input);
                descEdTxt = findViewById(R.id.description_input);
                zipEdTxt = findViewById(R.id.zip_input);
                locEdTxt = findViewById(R.id.itemLoc_input);
                phoneEdTxt = findViewById(R.id.phone_input);

                Item item =new Item("maggi",descEdTxt.getText().toString(),locEdTxt.getText().toString(),
                        phoneEdTxt.getText().toString(),titleEdTxt.getText().toString(),
                        "maggi@hi.is",zipEdTxt.getText().toString(),spinner1.getSelectedItem().toString());


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //debug
                            Log.d("JSONADMAKER", response);
                            JSONObject jsonResponse= new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {
                                // Færir frá Login skjá á forsíðu
                                Intent intent = new Intent( AdActivity.this, MainActivity.class);
                                AdActivity.this.startActivity(intent);
                            } else{
                                // Lætur vita ef innskráning mistókst
                                AlertDialog.Builder builder = new AlertDialog.Builder( AdActivity.this);
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
                ItemRequest itemRequest = new ItemRequest(item, "admaker", responseListener);
                RequestQueue queue = Volley.newRequestQueue(AdActivity.this);
                queue.add(itemRequest);
            }
        });
    }

    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(spinner1.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (firstItem.equals(String.valueOf(spinner1.getSelectedItem()))) {
                // ToDo when first item is selected
            } else {
                Toast.makeText(parent.getContext(),
                        "You have selected : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();
                // Todo when item is selected by the user
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
                    Toast.makeText(AdActivity.this, PathHolder, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}

