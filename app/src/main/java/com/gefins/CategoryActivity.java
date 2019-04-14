package com.gefins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;

import Entities.Sort;
import Entities.User;

public class CategoryActivity extends BackNavbarActivity {
    private Button choose_button;
    private Intent sortIntent;
    CheckBox furniture, clothing, kids, electronics, tools, commute, food, animals;
    private User currentUser;
    private Sort sort;
    public static final String FILTERS = "item_filter";
    private String chosenItems;
    private ArrayList<String> chosenCategories;
    private ArrayList<String> LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_category, contentFrameLayout);

        // Title in header
        Toolbar toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.back_title);
        mTitle.setText(R.string.categories);

        currentUser = (User) getIntent().getSerializableExtra("user");
        sort = (Sort) getIntent().getSerializableExtra("sort");

        // Fetch checkbox categories
        furniture = (CheckBox)findViewById(R.id.checkbox_furniture);
        clothing = (CheckBox)findViewById(R.id.checkbox_clothing);
        kids = (CheckBox)findViewById(R.id.checkbox_kids);
        electronics = (CheckBox)findViewById(R.id.checkbox_electronics);
        tools = (CheckBox)findViewById(R.id.checkbox_tools);
        commute = (CheckBox)findViewById(R.id.checkbox_commute);
        food = (CheckBox)findViewById(R.id.checkbox_food);
        animals = (CheckBox)findViewById(R.id.checkbox_animals);

        // chosen button
        choose_button = findViewById(R.id.choose_button);

        sortIntent = new Intent(CategoryActivity.this, SortActivity.class);

        chosenItems = getString(R.string.chosenItems);
        chosenCategories = new ArrayList<>();
        LIST =  new ArrayList<>();

        // GET extras from SortActivity and PUT the back
        Bundle extras = getIntent().getExtras();
        Intent filterIntent = getIntent();

        final String allFilters = filterIntent.getStringExtra(FILTERS);
        if (allFilters != null) {
            chosenItems += allFilters + "\n" + "\n";
        }

        // Functionality of the "Velja" button
        choose_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenItems += "Valdir flokkar:";
                if(furniture.isChecked()) {
                    chosenCategories.add("Húsgögn");
                }
                if(clothing.isChecked()) {
                    chosenCategories.add("Fatnaður");
                }
                if(kids.isChecked()) {
                    chosenCategories.add("Barnavörur");
                }
                if(electronics.isChecked()) {
                    chosenCategories.add("Raftæki");
                }
                if(tools.isChecked()) {
                    chosenCategories.add("Verkfæri");
                }
                if(commute.isChecked()) {
                    chosenCategories.add("Farartæki");
                }
                if(food.isChecked()) {
                    chosenCategories.add("Matur");
                }
                if(animals.isChecked()) {
                    chosenCategories.add("Dýr");
                }

                //Moves from "Flokkar" to "Sort" screen
                sort.setCategory(chosenCategories);
                finish();
                sortIntent = new Intent(CategoryActivity.this, SortActivity.class);
                sortIntent.putExtra("sort", sort);
                sortIntent.putExtra("user", currentUser);
                startActivity(sortIntent);
            }
        });
    }

    public void onCheckboxClicked(View view) {
        // Is the view checked
        boolean checked = ((CheckBox) view).isChecked();
        String str = "";
    }
}
