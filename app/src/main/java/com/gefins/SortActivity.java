package com.gefins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class SortActivity extends ExitNavbarActivity {
    private Button sortCatBtn, sortLocBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_sort, contentFrameLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.exit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.exit_title);
        mTitle.setText(R.string.filter);

        sortCatBtn = findViewById(R.id.sort_categories);
        sortLocBtn = findViewById(R.id.sort_loc);


        // Virknin á "Flokkar" takkanum
        sortCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Færir frá forsíðu yfir á ný auglýsing skjá
                Intent intent = new Intent(SortActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        // Virknin á "Staðsetning" takkanum
        sortLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Færir frá forsíðu yfir á ný auglýsing skjá
                Intent intent = new Intent(SortActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });

    }
}
