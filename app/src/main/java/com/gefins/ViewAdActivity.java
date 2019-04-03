package com.gefins;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

/* Eftir að klára allt varðandi ViewAd */

public class ViewAdActivity extends BackNavbarActivity {
    private Button inQueueButton;
    private ImageView adImage;
    private TextView categoryTxtView, zipTxtView, numberInQueueTxtView, descriptionTxtView, ownerInfoTxtView, adNameTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_viewadowner, contentFrameLayout);

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



        final Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String newText = extras.getString("chosenItem");
            System.out.println("NewText" + newText);
            if(newText != null) {
            }
        }


    }
}