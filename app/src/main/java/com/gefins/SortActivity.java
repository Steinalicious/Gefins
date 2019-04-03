package com.gefins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SortActivity extends ExitNavbarActivity {
    private Button sortCatBtn, sortLocBtn, submitBtn;
    private TextView chosenSort;
    public static final String ITEM_FILTERS = "WhichFilters";
    public static final String CHOSEN_ITEMS = "chosenItems";

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
        submitBtn = findViewById(R.id.sortItemsButton);
        chosenSort = findViewById(R.id.chosenSort);


        final Bundle extras = getIntent().getExtras();

        if(extras != null) {
            String filters = (String) getIntent().getStringExtra(ITEM_FILTERS);
            if(filters != null) {
                chosenSort.append(filters);
            }
            Log.d("activity_from", filters);
        }


        // Virknin á "Flokkar" takkanum
        sortCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Færir frá forsíðu yfir á ný auglýsing skjá
                Intent intent = new Intent(SortActivity.this, CategoryActivity.class);
                startActivityForResult(intent, 5);
            }
        });

        // Virknin á "Staðsetning" takkanum
        sortLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Færir frá forsíðu yfir á ný auglýsing skjá
                Intent intent = new Intent(SortActivity.this, LocationActivity.class);
                startActivityForResult(intent, 5);
            }
        });

        // Virknin á "Leita" takkanum
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Færir frá sort yfir á aðalskjá
                Intent intent = new Intent(SortActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.ITEM_REQUESTS, extras.getStringArrayList(CHOSEN_ITEMS));
                startActivity(intent);
            }
        });
    }
}
