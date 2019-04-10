package com.gefins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;


import Entities.Sort;
import Entities.User;

public class SortActivity extends ExitNavbarActivity {
    private Button sortCatBtn, sortLocBtn, submitBtn;
    private TextView chosenSort;
    public static final String ITEM_FILTERS_TXT = "WhichFilters";
    public static final String ITEM_FILTERS = "chosenItems";
    public ArrayList<String> Listi;
    private User currentuser;
    private Sort sort;

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

        currentuser = (User) getIntent().getSerializableExtra("user");
        sort = (Sort) getIntent().getSerializableExtra("sort");

        sortCatBtn = findViewById(R.id.sort_categories);
        sortLocBtn = findViewById(R.id.sort_loc);
        submitBtn = findViewById(R.id.sortItemsButton);
        chosenSort = findViewById(R.id.chosenSort);

        Listi = (ArrayList<String>) getIntent().getStringArrayListExtra("LISTI");


        final Bundle extras = getIntent().getExtras();

        if(!sort.isEmpty()) {
            chosenSort.append(sort.toString());
        }


        // Virknin á "Flokkar" takkanum
        sortCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Færir frá forsíðu yfir á ný auglýsing skjá
                Intent intent = new Intent(SortActivity.this, CategoryActivity.class);
                // intent.putExtra(CategoryActivity.FILTERS, (String) getIntent().getStringExtra(ITEM_FILTERS_TXT));
                intent.putExtra("sort", sort);
                intent.putExtra("user", currentuser);
                startActivity(intent);

            }
        });

        // Virknin á "Staðsetning" takkanum
        sortLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Færir frá forsíðu yfir á ný auglýsing skjá
                Intent intent = new Intent(SortActivity.this, LocationActivity.class);
                //intent.putExtra(LocationActivity.FILTERS, (String) getIntent().getStringExtra(ITEM_FILTERS_TXT));
                intent.putExtra("sort", sort);
                intent.putExtra("user", currentuser);
                startActivity(intent);
            }
        });

        // Virknin á "Leita" takkanum
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("REMOLAÐI", sort.getALL().toString());
                //Færir frá sort yfir á aðalskjá
                Intent intent = new Intent(SortActivity.this, MainActivity.class);
                // intent.putExtra(MainActivity.ITEM_REQUESTS, extras.getStringArrayList(ITEM_FILTERS));
                intent.putExtra("sort", sort);
                intent.putExtra("user", currentuser);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent( this, MainActivity.class);
        intent.putExtra("user", currentuser);
        SortActivity.this.startActivity(intent);
        return true;
    }
}