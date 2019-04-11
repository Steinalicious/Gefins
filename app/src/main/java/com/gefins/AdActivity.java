package com.gefins;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
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


public class AdActivity extends ExitNavbarActivity {
    private static final String CURRENT_USER = null;
    private Spinner spinner1;
    private Button button, submitBtn;
    private EditText titleEdTxt, descEdTxt, zipEdTxt, locEdTxt, phoneEdTxt;
    private User currentUser;
    private Uri selectedImg;
    private int PICK_FILE_REQUEST = 1;
    private ImageView imageView3;
    private String imgUrl;

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

        currentUser = (User) getIntent().getSerializableExtra("user");

        imageView3 = (ImageView) findViewById(R.id.imageView3);

        spinner1 = findViewById(R.id.spinner);
        spinner1.setOnItemSelectedListener(new ItemSelectedListener());
        submitBtn = findViewById(R.id.submitAdButton);
        button = findViewById(R.id.btn_picker);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(Intent.createChooser(intent,"Choose image"), PICK_FILE_REQUEST);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaManager.init(getApplicationContext());

                String requestId = MediaManager.get()
                        .upload(selectedImg)
                        .unsigned("utmqe54f")
                        .callback(new UploadCallback() {
                            @Override
                            public void onStart(String requestId) {
                                Toast.makeText(AdActivity.this, "Upload has started...",
                                        Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onProgress(String requestId, long bytes, long totalBytes) {

                            }

                            @Override
                            public void onSuccess(String requestId, Map resultData) {
                                Toast.makeText(AdActivity.this,
                                        "Uploaded Succesfully", Toast.LENGTH_SHORT).show();
                                String publicId = resultData.get("public_id").toString();
                                imgUrl = MediaManager.get().url().generate(publicId+".jpg");

                                Log.d("selected", imgUrl);


                                titleEdTxt = findViewById(R.id.title_input);
                                descEdTxt = findViewById(R.id.description_input);
                                zipEdTxt = findViewById(R.id.zip_input);
                                locEdTxt = findViewById(R.id.itemLoc_input);
                                phoneEdTxt = findViewById(R.id.phone_input);


                                Item item = new Item(currentUser.getUserName(), currentUser.getId(),
                                        descEdTxt.getText().toString(), locEdTxt.getText().toString(),
                                        phoneEdTxt.getText().toString(),titleEdTxt.getText().toString(),
                                        currentUser.getEmail(), zipEdTxt.getText().toString(),
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
                                                Intent intent = new Intent( AdActivity.this, MainActivity.class);
                                                intent.putExtra("user", currentUser);
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
                                Log.d("JSONADMAKER", "HEI");
                                ItemRequest itemRequest = new ItemRequest(item, "admaker", responseListener);
                                RequestQueue queue = Volley.newRequestQueue(AdActivity.this);
                                queue.add(itemRequest);
                            }

                            @Override
                            public void onError(String requestId, ErrorInfo error) {
                                Log.d("selected", requestId);
                                Toast.makeText(AdActivity.this,
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
                // ToDo when first item is selected
            } else {
                // Todo when item is selected by the user
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
            Picasso.with(this).load(selectedImg).into(imageView3);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent( this, MainActivity.class);
        intent.putExtra("user", currentUser);
        AdActivity.this.startActivity(intent);
        return true;
    }
}
