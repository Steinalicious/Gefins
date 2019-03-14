package com.gefins;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AdActivity extends AppCompatActivity {
    private Spinner spinner1;
    private Button button, submitBtn;
    private EditText titleEdTxt, descEdTxt, zipEdTxt, locEdTxt, phoneEdTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

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

                String title = titleEdTxt.getText().toString();
                String desc = descEdTxt.getText().toString();
                String zip = zipEdTxt.getText().toString();
                String location = locEdTxt.getText().toString();
                String phone = phoneEdTxt.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //debug
                            Log.d("JSONADMAKER", response);
                            JSONObject jsonResponse= new JSONObject(response);
                            boolean connected = jsonResponse.getBoolean("success");

                            if(connected) {

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
                AdMakerRequest adMakerRequest = new AdMakerRequest(title, desc, zip, location, phone, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AdActivity.this);
                queue.add(adMakerRequest);
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
        // TODO Auto-generated method stub

        switch (requestCode) {
            case 7:
                if (resultCode == RESULT_OK) {
                    String PathHolder = data.getData().getPath();
                    Toast.makeText(AdActivity.this, PathHolder, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}

