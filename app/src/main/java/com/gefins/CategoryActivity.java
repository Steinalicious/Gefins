package com.gefins;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CategoryActivity extends BackNavbarActivity {
    private Button choose_button;
    private Intent sortIntent;
    CheckBox furniture, clothing, kids, electronics, tools, commute, food, animals;
    private static final int REQUEST_CODE_FILTER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_category, contentFrameLayout);

        // Titill í header
        Toolbar toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.back_title);
        mTitle.setText(R.string.categories);

        // Sækja checkbox flokka
        furniture = (CheckBox)findViewById(R.id.checkbox_furniture);
        clothing = (CheckBox)findViewById(R.id.checkbox_clothing);
        kids = (CheckBox)findViewById(R.id.checkbox_kids);
        electronics = (CheckBox)findViewById(R.id.checkbox_electronics);
        tools = (CheckBox)findViewById(R.id.checkbox_tools);
        commute = (CheckBox)findViewById(R.id.checkbox_commute);
        food = (CheckBox)findViewById(R.id.checkbox_food);
        animals = (CheckBox)findViewById(R.id.checkbox_animals);

        // Velja takki
        choose_button = findViewById(R.id.choose_button);

        sortIntent = new Intent(CategoryActivity.this, SortActivity.class);

        // Virknin á "Velja" takkanum
        choose_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chosenItems = getString(R.string.chosenItems);
                ArrayList<String> chosenCategories = new ArrayList<>();
                chosenItems += "Valdir flokkar:";
                if(furniture.isChecked()) {
                    chosenItems += "\nHúsgögn";
                    chosenCategories.add("Húsgögn");
                }
                if(clothing.isChecked()) {
                    chosenItems += "\nFatnaður";
                    chosenCategories.add("Fatnaður");
                }
                if(kids.isChecked()) {
                    chosenItems += "\nBarnavörur";
                    chosenCategories.add("Barnavörur");
                }
                if(electronics.isChecked()) {
                    chosenItems += "\nRaftæki";
                    chosenCategories.add("Raftæki");
                }
                if(tools.isChecked()) {
                    chosenItems += "\nVerkfæri";
                    chosenCategories.add("Verkfæri");
                }
                if(commute.isChecked()) {
                    chosenItems += "\nFarartæki";
                    chosenCategories.add("Farartæki");
                }
                if(food.isChecked()) {
                    chosenItems += "\nMatur";
                    chosenCategories.add("Matur");
                }
                if(animals.isChecked()) {
                    chosenItems +="\nDýr";
                    chosenCategories.add("Dýr");
                }

                //Færir frá "Flokkar" yfir á "Sort" skjá
                sortIntent = new Intent(CategoryActivity.this, SortActivity.class);
                sortIntent.putExtra(SortActivity.ITEM_FILTERS, chosenItems);
                sortIntent.putExtra(SortActivity.CHOSEN_ITEMS, chosenCategories);
                startActivity(sortIntent);
            }
        });
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        String str = "";

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_furniture:
                /*
                if (checked) {
                    sortIntent = new Intent(CategoryActivity.this, SortActivity.class);
                    sortIntent.putExtra("chosen_items", "Húsgögn");
                }
                else {
                    break;
                }
                */

            case R.id.checkbox_clothing:
                /*
                if (checked) {
                    sortIntent = new Intent(CategoryActivity.this, SortActivity.class);
                    sortIntent.putExtra("chosen_items", "Fatnaður");
                }
                else {
                // I'm lactose intoleran
                    break;
                }
                */
        }
    }
}
