package com.gefins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import Entities.MultiSelectSpinner;
import Entities.User;
import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends BackNavbarActivity implements MultiSelectSpinner.OnMultipleItemsSelectedListener {
    private Button chooseLocBtn;
    private Intent sortIntent;
    private String chosenItems2;
    private ArrayList<String> chosenLocations;
    private String loc;
    public static final String FILTERS = "item_filter";

    private User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_location, contentFrameLayout);

        //set curentUser
        currentUser = (User) getIntent().getSerializableExtra("user");

        Toolbar toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.back_title);
        mTitle.setText(R.string.location);

        String[] arrayRvk = {"Hreinsa", "101 - Miðbær", "103 - Kringlan", "104 - Vogar", "105 - Austurbær",
                "107 - Vesturbær", "108 - Austurbær", "109 - Neðra Breiðholt", "110 - Árbær", "111 - Efra Breiðholt",
        "112 - Grafarvogur", "113 - Grafarholt", "116 - Kjalarnes", "162 - Dreifbýli"};
        String[] arrayKop = {"Hreinsa", "200 - Kópavogur", "201 - Kópavogur", "202 - Kópavogur", "203 - Kópavogur"};
        String[] arrayGrb = {"Hreinsa", "210 - Garðabær", "212 - Garðabær", "225 - Garðabær"};
        String[] arrayHfj = {"Hreinsa", "220 - Hafnarfjörður", "221 - Hafnarfjörður", "222 - Hafnarfjörður"};
        String[] arrayMos = {"Hreinsa", "270 - Mosfellsbær", "271 - Mosfellsbær", "276 - Mosfellsbær"};
        String[] arraySel = {"Hreinsa", "170 - Seltjarnarnes"};
        String[] arraySudnes = {"Hreinsa", "230 - Keflavík", "232 - Keflavík", "233 - Hafnir", "262 - Ásbrú",
        "235 - Ásbrú", "240 - Grindavík", "245 - Sandgerði", "250 - Garður", "260 - Njarðvík", "190 - Vogar"};
        String[] arrayNord = {"Hreinsa", "500 - Staður", "510 - Hólmavík", "520 - Drangsnes", "522 - Kjörvogur",
        "523 - Bær", "524 - Norðurfjörður", "530 - Hvammstangi", "531 - Hvammstangi", "540 - Blönduós",
        "541 - Blönduós", "545 - Skagaströnd", "550 - Sauðárkrókur", "551 - Sauðárkrókur", "560 - Varmahlíð",
        "565 - Hofsós", "566 - Hofsós", "570 - Fljót", "580 - Siglufjörður", "600 - Akureyri", "601 - Akureyri",
        "602 - Akureyri", "603 - Akureyri", "610 - Grenivík", "611 - Grímsey", "620 - Dalvík", "621 - Dalvík",
                "625 - Ólafsfjörður", "630 - Hrísey", "640 - Húsavík", "641 - Húsavík", "645 - Fosshóll",
        "650 - Laugar", "660 - Mývatn", "670 - Kópasker", "671 - Kópasker", "675 - Raufarhöfn", "680 - Þórshöfn",
        "681 - Þórshöfn"};
        String[] arrayVest = {"Hreinsa", "400 - Ísafjörður", "401 - Ísafjörður", "410 - Hnífsdalur", "415 - Bolungarvík",
        "420 - Súðavík", "425 - Flateyri", "430 - Suðureyri", "450 - Patreksfjörður", "451 - Patreksfjörður",
        "460 - Tálknafjörður", "465 - Bíldudalur", "470 - Þingeyri", "471 - Þingeyri"};
        String[] arrayVestland = {"Hreinsa", "300 - Akranes", "301 - Akranes", "302 - Akranes", "310 - Borgarnes",
        "311 - Borgarbyggð", "320 - Reykholt í Borgarfirði", "340 - Stykkishólmur", "345 - Flatey á Breiðafirði",
        "350 - Grundarfjörður", "355 - Snæfellsbær", "356 - Snæfellsbær", "360 - Snæfellsbær", "370 - Búðardalur",
        "371 - Búðardalur", "380 - Króksfjarðarnes"};
        String[] arrayAust = {"Hreinsa", "685 - Bakkafjörður", "690 - Vopnafjörður", "700 - Egilsstaðir", "701 - Egilsstaðir",
        "710 - Seyðisfjörður", "715 - Mjóifjörður", "720 - Borgarfjörður", "730 - Reyðarfjörður", "735 - Eskifjörður",
        "740 - Neskaupstaður", "750 - Fáskrúðsfjörður", "755 - Stöðvarfjörður", "760 - Breiðdalsvík",
        "765 - Djúpivogur", "780 - Höfn í Hornafirði", "781 - Höfn í Hornafirði", "785 - Fagurhólsmýri"};
        String[] arraySudland = {"Hreinsa", "800 - Selfoss", "801 - Selfoss", "802 - Selfoss", "810 - Hveragerði",
        "815 - Þorlákshöfn", "820 - Eyrarbakki", "825 - Stokkseyri", "840 - Laugarvatn", "845 - Flúðir",
        "850 - Hella", "851 - Hella", "860 - Hvolsvöllur", "861 - Hvolsvöllur", "870 - Vík", "871 - Vík",
        "880 - Kirkjubæjarklaustur", "900 - Vestmannaeyjar", "901 - Vestmannaeyjabær"};


        MultiSelectSpinner multiSelectSpinnerRvk = (MultiSelectSpinner) findViewById(R.id.spinnerRvk);
        MultiSelectSpinner multiSelectSpinnerKop = (MultiSelectSpinner) findViewById(R.id.spinnerKop);
        MultiSelectSpinner multiSelectSpinnerGrb = (MultiSelectSpinner) findViewById(R.id.spinnerGrb);
        MultiSelectSpinner multiSelectSpinnerHfj = (MultiSelectSpinner) findViewById(R.id.spinnerHfj);
        MultiSelectSpinner multiSelectSpinnerMos = (MultiSelectSpinner) findViewById(R.id.spinnerMos);
        MultiSelectSpinner multiSelectSpinnerSel = (MultiSelectSpinner) findViewById(R.id.spinnerSel);
        MultiSelectSpinner multiSelectSpinnerSudnes = (MultiSelectSpinner) findViewById(R.id.spinnerSudnes);
        MultiSelectSpinner multiSelectSpinnerNord = (MultiSelectSpinner) findViewById(R.id.spinnerNord);
        MultiSelectSpinner multiSelectSpinnerVest = (MultiSelectSpinner) findViewById(R.id.spinnerVest);
        MultiSelectSpinner multiSelectSpinnerVestland = (MultiSelectSpinner) findViewById(R.id.spinnerVestland);
        MultiSelectSpinner multiSelectSpinnerAust = (MultiSelectSpinner) findViewById(R.id.spinnerAust);
        MultiSelectSpinner multiSelectSpinnerSudland = (MultiSelectSpinner) findViewById(R.id.spinnerSudland);


        multiSelectSpinnerRvk.setItems(arrayRvk);
        multiSelectSpinnerKop.setItems(arrayKop);
        multiSelectSpinnerGrb.setItems(arrayGrb);
        multiSelectSpinnerHfj.setItems(arrayHfj);
        multiSelectSpinnerMos.setItems(arrayMos);
        multiSelectSpinnerSel.setItems(arraySel);
        multiSelectSpinnerSudnes.setItems(arraySudnes);
        multiSelectSpinnerNord.setItems(arrayNord);
        multiSelectSpinnerVest.setItems(arrayVest);
        multiSelectSpinnerVestland.setItems(arrayVestland);
        multiSelectSpinnerAust.setItems(arrayAust);
        multiSelectSpinnerSudland.setItems(arraySudland);


        multiSelectSpinnerRvk.hasNoneOption(true);
        multiSelectSpinnerKop.hasNoneOption(true);
        multiSelectSpinnerGrb.hasNoneOption(true);
        multiSelectSpinnerHfj.hasNoneOption(true);
        multiSelectSpinnerMos.hasNoneOption(true);
        multiSelectSpinnerSel.hasNoneOption(true);
        multiSelectSpinnerSudnes.hasNoneOption(true);
        multiSelectSpinnerNord.hasNoneOption(true);
        multiSelectSpinnerVest.hasNoneOption(true);
        multiSelectSpinnerVestland.hasNoneOption(true);
        multiSelectSpinnerAust.hasNoneOption(true);
        multiSelectSpinnerSudland.hasNoneOption(true);


        multiSelectSpinnerRvk.setSelection(new int[]{0});
        multiSelectSpinnerKop.setSelection(new int[]{0});
        multiSelectSpinnerGrb.setSelection(new int[]{0});
        multiSelectSpinnerHfj.setSelection(new int[]{0});
        multiSelectSpinnerMos.setSelection(new int[]{0});
        multiSelectSpinnerSel.setSelection(new int[]{0});
        multiSelectSpinnerSudnes.setSelection(new int[]{0});
        multiSelectSpinnerNord.setSelection(new int[]{0});
        multiSelectSpinnerVest.setSelection(new int[]{0});
        multiSelectSpinnerVestland.setSelection(new int[]{0});
        multiSelectSpinnerAust.setSelection(new int[]{0});
        multiSelectSpinnerSudland.setSelection(new int[]{0});


        multiSelectSpinnerRvk.setListener(this);
        multiSelectSpinnerKop.setListener(this);
        multiSelectSpinnerGrb.setListener(this);
        multiSelectSpinnerHfj.setListener(this);
        multiSelectSpinnerMos.setListener(this);
        multiSelectSpinnerSel.setListener(this);
        multiSelectSpinnerSudnes.setListener(this);
        multiSelectSpinnerNord.setListener(this);
        multiSelectSpinnerVest.setListener(this);
        multiSelectSpinnerVestland.setListener(this);
        multiSelectSpinnerAust.setListener(this);
        multiSelectSpinnerSudland.setListener(this);


        loc = "";
        chosenItems2 = getString(R.string.chosenItems);
        chosenLocations = new ArrayList<>();

        // Velja takki
        chooseLocBtn = findViewById(R.id.chooseLocBtn);
        sortIntent = new Intent(LocationActivity.this, SortActivity.class);

        // GET extras úr SortActivity og PUT-a þau aftur
        Bundle extras = getIntent().getExtras();
        Intent filterIntent = getIntent();

        final String allFilters = filterIntent.getStringExtra(FILTERS);
        if (allFilters != null) {
            chosenItems2 += allFilters + "\n" + "\n";
            //chosenLocations.add(allFilters);
        }


        // Virknin á "Velja" takkanum
        chooseLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Færir frá "Staðsetning" yfir á "Sort" skjá
                sortIntent = new Intent(LocationActivity.this, SortActivity.class);
                sortIntent.putExtra(SortActivity.ITEM_FILTERS_TXT, chosenItems2);
                sortIntent.putExtra(SortActivity.ITEM_FILTERS, chosenLocations);
                sortIntent.putExtra("user", currentUser);
                startActivity(sortIntent);
            }
        });
    }

    @Override
    public void selectedIndices(List<Integer> indices) {
    }

    @Override
    public void selectedStrings(List<String> strings) {
        chosenItems2 += "Valdar staðsetningar:";
        for(int i=0; i<strings.size(); i++) {
            loc += strings.get(i);
            chosenItems2 += "\n"+strings.get(i).substring(0, 3);
            chosenLocations.add(strings.get(i).substring(0,3));
        }
        Toast.makeText(this.getApplicationContext(),"Valdar staðsetningar " + strings,Toast.LENGTH_LONG).show();
    }
}
