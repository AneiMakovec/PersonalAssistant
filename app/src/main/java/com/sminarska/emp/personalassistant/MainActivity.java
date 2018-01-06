package com.sminarska.emp.personalassistant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    // indexes
    public static final int VSOTA = 0;

    public static final int PLACA = 0;

    public static final int POTNI_STROSKI = 1;
    public static final int POTNI_STROSKI_GORIVO = 1;
    public static final int POTNI_STROSKI_AVTO = 2;

    public static final int HRANA = 2;
    public static final int HRANA_ZELENJAVA = 1;
    public static final int HRANA_MESO = 2;
    public static final int HRANA_ZITA = 3;
    public static final int HRANA_SADJE = 4;
    public static final int HRANA_PIJACA = 5;
    public static final int HRANA_DRUGO = 6;

    public static final int OBLEKE = 3;
    public static final int OBLEKE_ZGORNJI_DEL = 1;
    public static final int OBLEKE_SPODNJI_DEL = 2;
    public static final int OBLEKE_OBUTEV = 3;
    public static final int OBLEKE_DODATKI = 4;

    public static final int HISNI_STROSKI = 4;
    public static final int HISNI_STROSKI_VODA = 1;
    public static final int HISNI_STROSKI_ELEKTRIKA = 2;
    public static final int HISNI_STROSKI_TV_INT = 3;
    public static final int HISNI_STROSKI_OGRAVANJE = 4;
    public static final int HISNI_STROSKI_DRUGO = 5;

    public static final int IGRACE = 5;
    public static final int IGRACE_VELIKE = 1;
    public static final int IGRACE_MAJHNE = 2;
    public static final int IGRACE_DRUGO = 3;

    public static final int DARILA = 6;
    public static final int DARILA_PRAZNIKI = 1;
    public static final int DARILA_ROJSTNI_DNEVI = 2;
    public static final int DARILA_DRUGO = 3;

    public static final int PROSTI_CAS = 7;
    public static final int PROSTI_CAS_HOBIJI = 1;
    public static final int PROSTI_CAS_SPORT = 2;
    public static final int PROSTI_CAS_GLASBILA = 3;
    public static final int PROSTI_CAS_DRUGO = 4;

    // intent string
    public static final String EXTRA_START_DAY = "com.seminarska.emp.personalassistant.STARTDAY";
    public static final String EXTRA_END_DAY = "com.seminarska.emp.personalassistant.ENDDAY";
    public static final String EXTRA_DATA_PER_DAY = "com.seminarska.emp.personalassistant.DATA";

    public static final String STORAGE_FILE_NAME = "personalassistant.data";
    public static final String PREFERENCES_FILE_NAME = "personalassistantprefs";

    public static final int RESULT_REQUEST_ADD = 1;
    public static final int RESULT_REQUEST_SUB = 2;

    // expandable list
    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    // navigation drawer
    private DrawerLayout drawerLayout;
    private NavigationView drawerView;

    // control
    private boolean displayWeek = true;
    private boolean displayMonth = false;
    private boolean displayYear = false;

    private boolean setListView = true;
    private boolean setGraphView = false;
    private boolean setStatView = false;

    private boolean updatedDataStructure = false;
    private boolean restoredDataStructure = false;

    // key value
    private int key;

    // data
    private SparseArray<List<List<Integer>>> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button odliv = (Button)findViewById(R.id.button7);
        Button priliv = (Button)findViewById(R.id.button8);

        odliv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DodajActivity.class);
                startActivityForResult(intent, RESULT_REQUEST_SUB);
            }
        });

        priliv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DodajActivity.class);
                startActivityForResult(intent, RESULT_REQUEST_ADD);
            }
        });

        /*
        // check if the data structure was created in earlier usages of the program
        SharedPreferences settings = getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        createdDataStructure = settings.getBoolean("dataCreated", false);
        */




        // PREPARE THE DATA
        prepareDataStructure();



        // PREPARE THE EXPANDABLE LIST

        // preparing list data
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);


        if (restoredDataStructure) {
            refreshDisplay();
        } else {
            prepareExpandableListData();

            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

            // setting list adapter
            expListView.setAdapter(listAdapter);
        }


        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {}
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {}
        });



        // PREPARE THE NAVIGATION DRAWER

        // get the view
        drawerView = (NavigationView) findViewById(R.id.drawer_view);

        // get the layout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // add item activated listener
        drawerView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);

                        switch (menuItem.getItemId()) {
                            case R.id.drawer_first_week:
                                if (!displayWeek) {
                                    displayWeek = true;
                                    displayMonth = false;
                                    displayYear = false;

                                    Toast.makeText(getApplicationContext(), "Spreminjam prikaz: tedensko", Toast.LENGTH_SHORT).show();

                                    refreshDisplay();
                                }
                                break;
                            case R.id.drawer_first_month:
                                if (!displayMonth) {
                                    displayWeek = false;
                                    displayMonth = true;
                                    displayYear = false;

                                    Toast.makeText(getApplicationContext(), "Spreminjam prikaz: mesečno", Toast.LENGTH_SHORT).show();

                                    refreshDisplay();
                                }
                                break;
                            case R.id.drawer_first_year:
                                if (!displayYear) {
                                    displayWeek = false;
                                    displayMonth = false;
                                    displayYear = true;

                                    Toast.makeText(getApplicationContext(), "Spreminjam prikaz: letno", Toast.LENGTH_SHORT).show();

                                    refreshDisplay();
                                }
                                break;
                            case R.id.drawer_second_list:
                                if (!setListView) {
                                    setListView = true;
                                    setGraphView = false;
                                    setStatView = false;

                                    Toast.makeText(getApplicationContext(), "Spreminjam pogled: seznam", Toast.LENGTH_SHORT).show();

                                    updateView();
                                }
                                break;
                            case R.id.drawer_second_graph:
                                if (!setGraphView) {
                                    setListView = false;
                                    setGraphView = true;
                                    setStatView = false;

                                    Toast.makeText(getApplicationContext(), "Spreminjam pogled: slika", Toast.LENGTH_SHORT).show();

                                    updateView();
                                }
                                break;
                            case R.id.drawer_second_stat:
                                if (!setStatView) {
                                    setListView = false;
                                    setGraphView = false;
                                    setStatView = true;

                                    Toast.makeText(getApplicationContext(), "Spreminjam pogled: statistika", Toast.LENGTH_SHORT).show();

                                    updateView();
                                }
                                break;
                            default:
                                break;
                        }

                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int amount = 0;
        int catIndex = 0;
        int subcatIndex = 0;

        switch (requestCode) {
            case RESULT_REQUEST_ADD:
                if (resultCode == Activity.RESULT_OK) {
                    amount = data.getIntExtra(DodajActivity.RESULT_ADDED_AMOUNT, 0);
                    catIndex = data.getIntExtra(DodajActivity.RESULT_CATEGORY_INDEX, 0);
                    subcatIndex = data.getIntExtra(DodajActivity.RESULT_SUBCATEGORY_INDEX, 0);
                }
                break;
            case RESULT_REQUEST_SUB:
                if (resultCode == Activity.RESULT_OK) {
                    amount = - data.getIntExtra(DodajActivity.RESULT_ADDED_AMOUNT, 0);
                    catIndex = data.getIntExtra(DodajActivity.RESULT_CATEGORY_INDEX, 0);
                    subcatIndex = data.getIntExtra(DodajActivity.RESULT_SUBCATEGORY_INDEX, 0);
                }
                break;
            default:
                return;
        }

        addToDataStructure(catIndex, subcatIndex, amount);
        refreshDisplay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        /*
        SharedPreferences settings = getSharedPreferences(PREFERENCES_FILE_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("dataCreated", createdDataStructure);
        editor.commit();
        */

        if (updatedDataStructure)
            saveData();
    }

    /*
     * Preparing the expandable list data
     */
    private void prepareExpandableListData() {
        listDataHeader.clear();
        listDataChild.clear();

        int size = 40;

        // Adding parent data
        listDataHeader.add("Plača");
        listDataHeader.add(resizeString("Potni stroški:", 0, size));
        listDataHeader.add(resizeString("Hrana:", 0, size));
        listDataHeader.add(resizeString("Obleke:", 0, size));
        listDataHeader.add(resizeString("Hišni stroški:", 0, size));
        listDataHeader.add(resizeString("Igrače:", 0, size));
        listDataHeader.add(resizeString("Darila:", 0, size));
        listDataHeader.add(resizeString("Prosti čas:", 0, size));

        // Adding child data
        List<String> placa = new ArrayList<String>();
        placa.add(resizeString("Skupaj:", 0, size));

        List<String> potStroski = new ArrayList<String>();
        potStroski.add(resizeString("Gorivo:", 0, size));
        potStroski.add(resizeString("Stroški za avto:", 0, size));

        List<String> hrana = new ArrayList<String>();
        hrana.add(resizeString("Zelenjava:", 0, size));
        hrana.add(resizeString("Mesni izdelki:", 0, size));
        hrana.add(resizeString("Izdelki iz žit:", 0, size));
        hrana.add(resizeString("Sadje:", 0, size));
        hrana.add(resizeString("Pijača:", 0, size));
        hrana.add(resizeString("Drugo:", 0, size));

        List<String> obleke = new ArrayList<String>();
        obleke.add(resizeString("Zgornji del:", 0, size));
        obleke.add(resizeString("Spodnji del:", 0, size));
        obleke.add(resizeString("Obutev:",0, size));
        obleke.add(resizeString("Dodatki:", 0, size));

        List<String> hisniStroski = new ArrayList<String>();
        hisniStroski.add(resizeString("Voda:",0, size));
        hisniStroski.add(resizeString("Elektrika:", 0, size));
        hisniStroski.add(resizeString("Televizija/Internet:", 0, size));
        hisniStroski.add(resizeString("Ogrevanje:", 0, size));
        hisniStroski.add(resizeString("Drugo:", 0, size));

        List<String> igrace = new ArrayList<String>();
        igrace.add(resizeString("Velike:", 0, size));
        igrace.add(resizeString("Majhne:", 0, size));
        igrace.add(resizeString("Drugo:", 0, size));

        List<String> darila = new ArrayList<String>();
        darila.add(resizeString("Prazniki:", 0, size));
        darila.add(resizeString("Rojstni dnevi:", 0, size));
        darila.add(resizeString("Drugo:", 0, size));

        List<String> prostiCas = new ArrayList<String>();
        prostiCas.add(resizeString("Hobiji:", 0, size));
        prostiCas.add(resizeString("Šport:", 0, size));
        prostiCas.add(resizeString("Glasbila:", 0, size));
        prostiCas.add(resizeString("Drugo:", 0, size));

        listDataChild.put(listDataHeader.get(0), placa); // Header, Child data
        listDataChild.put(listDataHeader.get(1), potStroski);
        listDataChild.put(listDataHeader.get(2), hrana);
        listDataChild.put(listDataHeader.get(3), obleke);
        listDataChild.put(listDataHeader.get(4), hisniStroski);
        listDataChild.put(listDataHeader.get(5), igrace);
        listDataChild.put(listDataHeader.get(6), darila);
        listDataChild.put(listDataHeader.get(7), prostiCas);
    }

    /*
     * Prapare the data structure
     */
    private void prepareDataStructure() {
        data = new SparseArray<List<List<Integer>>>();

        restoredDataStructure = restoreData();
    }

    /*
     * Add a value to the data structure
     */
    private void addToDataStructure(int category, int subCategory, int value) {
        Calendar date = Calendar.getInstance();
        key = date.get(Calendar.DAY_OF_YEAR);

        if (!updatedDataStructure)
            updatedDataStructure = true;

        if (data.indexOfKey(key) >= 0) {
            data.get(key).get(category).set(subCategory, data.get(key).get(category).get(subCategory) + value);

            if (category != PLACA)
                data.get(key).get(category).set(VSOTA, data.get(key).get(category).get(VSOTA) + value);
        } else {
            List<List<Integer>> catList = new ArrayList<>();

            List<Integer> placa = new ArrayList<>();
            placa.add(0);

            List<Integer> potStroski = new ArrayList<>();
            potStroski.add(0);
            potStroski.add(0);
            potStroski.add(0);

            List<Integer> hrana = new ArrayList<>();
            hrana.add(0);
            hrana.add(0);
            hrana.add(0);
            hrana.add(0);
            hrana.add(0);
            hrana.add(0);
            hrana.add(0);

            List<Integer> obleke = new ArrayList<>();
            obleke.add(0);
            obleke.add(0);
            obleke.add(0);
            obleke.add(0);
            obleke.add(0);

            List<Integer> hisniStroski = new ArrayList<>();
            hisniStroski.add(0);
            hisniStroski.add(0);
            hisniStroski.add(0);
            hisniStroski.add(0);
            hisniStroski.add(0);
            hisniStroski.add(0);

            List<Integer> igrace = new ArrayList<>();
            igrace.add(0);
            igrace.add(0);
            igrace.add(0);
            igrace.add(0);

            List<Integer> darila = new ArrayList<>();
            darila.add(0);
            darila.add(0);
            darila.add(0);
            darila.add(0);

            List<Integer> prostiCas = new ArrayList<>();
            prostiCas.add(0);
            prostiCas.add(0);
            prostiCas.add(0);
            prostiCas.add(0);
            prostiCas.add(0);

            catList.add(placa);
            catList.add(potStroski);
            catList.add(hrana);
            catList.add(obleke);
            catList.add(hisniStroski);
            catList.add(igrace);
            catList.add(darila);
            catList.add(prostiCas);

            catList.get(category).set(subCategory, value);

            if (category != PLACA)
                catList.get(category).set(VSOTA, value);

            data.append(key, catList);
        }
    }

    /*
     * Update the display of values for chosen time interval
     */
    private void refreshDisplay() {
        Calendar date = Calendar.getInstance();
        int balance = 0;

        if (data.indexOfKey(key) >= 0) {
            // število predhodnih dni
            int day;

            // seznam vsot podatkov
            List<List<Integer>> values = new ArrayList<>();

            List<Integer> placa = new ArrayList<>();
            placa.add(0);

            List<Integer> potStroski = new ArrayList<>();
            potStroski.add(0);
            potStroski.add(0);
            potStroski.add(0);

            List<Integer> hrana = new ArrayList<>();
            hrana.add(0);
            hrana.add(0);
            hrana.add(0);
            hrana.add(0);
            hrana.add(0);
            hrana.add(0);
            hrana.add(0);

            List<Integer> obleke = new ArrayList<>();
            obleke.add(0);
            obleke.add(0);
            obleke.add(0);
            obleke.add(0);
            obleke.add(0);

            List<Integer> hisniStroski = new ArrayList<>();
            hisniStroski.add(0);
            hisniStroski.add(0);
            hisniStroski.add(0);
            hisniStroski.add(0);
            hisniStroski.add(0);
            hisniStroski.add(0);

            List<Integer> igrace = new ArrayList<>();
            igrace.add(0);
            igrace.add(0);
            igrace.add(0);
            igrace.add(0);

            List<Integer> darila = new ArrayList<>();
            darila.add(0);
            darila.add(0);
            darila.add(0);
            darila.add(0);

            List<Integer> prostiCas = new ArrayList<>();
            prostiCas.add(0);
            prostiCas.add(0);
            prostiCas.add(0);
            prostiCas.add(0);
            prostiCas.add(0);

            values.add(placa);
            values.add(potStroski);
            values.add(hrana);
            values.add(obleke);
            values.add(hisniStroski);
            values.add(igrace);
            values.add(darila);
            values.add(prostiCas);


            // seznam podatkov za trenuten dan
            List<List<Integer>> dailyValues;

            // prikaz na teden
            if (displayWeek) {
                day = date.get(Calendar.DAY_OF_WEEK);
                // prikaz na mesec
            } else if (displayMonth) {
                day = date.get(Calendar.DAY_OF_MONTH);
                // prikaz na leto
            } else if (displayYear) {
                day = date.get(Calendar.DAY_OF_YEAR);
            } else {
                day = 0;
                return;
            }

            // pomikamo se po dnevih nazaj in si beležimo vsote podatkov za vsako kategorijo v posameznem dnevu
            for (int i = 0; i < day; i++) {
                if (key - i == 0)
                    key = 365 + i;

                if (data.indexOfKey(key - i) >= 0) {
                    dailyValues = data.get(key - i);
                } else {
                    continue;
                }

                balance += values.get(PLACA).get(VSOTA) + dailyValues.get(PLACA).get(VSOTA);
                values.get(PLACA).set(VSOTA, values.get(PLACA).get(VSOTA) + dailyValues.get(PLACA).get(VSOTA));

                balance -= values.get(POTNI_STROSKI).get(VSOTA) + dailyValues.get(POTNI_STROSKI).get(VSOTA);
                values.get(POTNI_STROSKI).set(VSOTA, values.get(POTNI_STROSKI).get(VSOTA) + dailyValues.get(POTNI_STROSKI).get(VSOTA));
                values.get(POTNI_STROSKI).set(POTNI_STROSKI_GORIVO, values.get(POTNI_STROSKI).get(POTNI_STROSKI_GORIVO) + dailyValues.get(POTNI_STROSKI).get(POTNI_STROSKI_GORIVO));
                values.get(POTNI_STROSKI).set(POTNI_STROSKI_AVTO, values.get(POTNI_STROSKI).get(POTNI_STROSKI_AVTO) + dailyValues.get(POTNI_STROSKI).get(POTNI_STROSKI_AVTO));

                balance -= values.get(HRANA).get(VSOTA) + dailyValues.get(HRANA).get(VSOTA);
                values.get(HRANA).set(VSOTA, values.get(HRANA).get(VSOTA) + dailyValues.get(HRANA).get(VSOTA));
                values.get(HRANA).set(HRANA_ZELENJAVA, values.get(HRANA).get(HRANA_ZELENJAVA) + dailyValues.get(HRANA).get(HRANA_ZELENJAVA));
                values.get(HRANA).set(HRANA_MESO, values.get(HRANA).get(HRANA_MESO) + dailyValues.get(HRANA).get(HRANA_MESO));
                values.get(HRANA).set(HRANA_ZITA, values.get(HRANA).get(HRANA_ZITA) + dailyValues.get(HRANA).get(HRANA_ZITA));
                values.get(HRANA).set(HRANA_SADJE, values.get(HRANA).get(HRANA_SADJE) + dailyValues.get(HRANA).get(HRANA_SADJE));
                values.get(HRANA).set(HRANA_PIJACA, values.get(HRANA).get(HRANA_PIJACA) + dailyValues.get(HRANA).get(HRANA_PIJACA));
                values.get(HRANA).set(HRANA_DRUGO, values.get(HRANA).get(HRANA_DRUGO) + dailyValues.get(HRANA).get(HRANA_DRUGO));

                balance -= values.get(OBLEKE).get(VSOTA) + dailyValues.get(OBLEKE).get(VSOTA);
                values.get(OBLEKE).set(VSOTA, values.get(OBLEKE).get(VSOTA) + dailyValues.get(OBLEKE).get(VSOTA));
                values.get(OBLEKE).set(OBLEKE_ZGORNJI_DEL, values.get(OBLEKE).get(OBLEKE_ZGORNJI_DEL) + dailyValues.get(OBLEKE).get(OBLEKE_ZGORNJI_DEL));
                values.get(OBLEKE).set(OBLEKE_SPODNJI_DEL, values.get(OBLEKE).get(OBLEKE_SPODNJI_DEL) + dailyValues.get(OBLEKE).get(OBLEKE_SPODNJI_DEL));
                values.get(OBLEKE).set(OBLEKE_OBUTEV, values.get(OBLEKE).get(OBLEKE_OBUTEV) + dailyValues.get(OBLEKE).get(OBLEKE_OBUTEV));
                values.get(OBLEKE).set(OBLEKE_DODATKI, values.get(OBLEKE).get(OBLEKE_DODATKI) + dailyValues.get(OBLEKE).get(OBLEKE_DODATKI));

                balance -= values.get(HISNI_STROSKI).get(VSOTA) + dailyValues.get(HISNI_STROSKI).get(VSOTA);
                values.get(HISNI_STROSKI).set(VSOTA, values.get(HISNI_STROSKI).get(VSOTA) + dailyValues.get(HISNI_STROSKI).get(VSOTA));
                values.get(HISNI_STROSKI).set(HISNI_STROSKI_VODA, values.get(HISNI_STROSKI).get(HISNI_STROSKI_VODA) + dailyValues.get(HISNI_STROSKI).get(HISNI_STROSKI_VODA));
                values.get(HISNI_STROSKI).set(HISNI_STROSKI_ELEKTRIKA, values.get(HISNI_STROSKI).get(HISNI_STROSKI_ELEKTRIKA) + dailyValues.get(HISNI_STROSKI).get(HISNI_STROSKI_ELEKTRIKA));
                values.get(HISNI_STROSKI).set(HISNI_STROSKI_TV_INT, values.get(HISNI_STROSKI).get(HISNI_STROSKI_TV_INT) + dailyValues.get(HISNI_STROSKI).get(HISNI_STROSKI_TV_INT));
                values.get(HISNI_STROSKI).set(HISNI_STROSKI_OGRAVANJE, values.get(HISNI_STROSKI).get(HISNI_STROSKI_OGRAVANJE) + dailyValues.get(HISNI_STROSKI).get(HISNI_STROSKI_OGRAVANJE));
                values.get(HISNI_STROSKI).set(HISNI_STROSKI_DRUGO, values.get(HISNI_STROSKI).get(HISNI_STROSKI_DRUGO) + dailyValues.get(HISNI_STROSKI).get(HISNI_STROSKI_DRUGO));

                balance -= values.get(IGRACE).get(VSOTA) + dailyValues.get(IGRACE).get(VSOTA);
                values.get(IGRACE).set(VSOTA, values.get(IGRACE).get(VSOTA) + dailyValues.get(IGRACE).get(VSOTA));
                values.get(IGRACE).set(IGRACE_VELIKE, values.get(IGRACE).get(IGRACE_VELIKE) + dailyValues.get(IGRACE).get(IGRACE_VELIKE));
                values.get(IGRACE).set(IGRACE_MAJHNE, values.get(IGRACE).get(IGRACE_MAJHNE) + dailyValues.get(IGRACE).get(IGRACE_MAJHNE));
                values.get(IGRACE).set(IGRACE_DRUGO, values.get(IGRACE).get(IGRACE_DRUGO) + dailyValues.get(IGRACE).get(IGRACE_DRUGO));

                balance -= values.get(DARILA).get(VSOTA) + dailyValues.get(DARILA).get(VSOTA);
                values.get(DARILA).set(VSOTA, values.get(DARILA).get(VSOTA) + dailyValues.get(DARILA).get(VSOTA));
                values.get(DARILA).set(DARILA_PRAZNIKI, values.get(DARILA).get(DARILA_PRAZNIKI) + dailyValues.get(DARILA).get(DARILA_PRAZNIKI));
                values.get(DARILA).set(DARILA_ROJSTNI_DNEVI, values.get(DARILA).get(DARILA_ROJSTNI_DNEVI) + dailyValues.get(DARILA).get(DARILA_ROJSTNI_DNEVI));
                values.get(DARILA).set(DARILA_DRUGO, values.get(DARILA).get(DARILA_DRUGO) + dailyValues.get(DARILA).get(DARILA_DRUGO));

                balance -= values.get(PROSTI_CAS).get(VSOTA) + dailyValues.get(PROSTI_CAS).get(VSOTA);
                values.get(PROSTI_CAS).set(VSOTA, values.get(PROSTI_CAS).get(VSOTA) + dailyValues.get(PROSTI_CAS).get(VSOTA));
                values.get(PROSTI_CAS).set(PROSTI_CAS_HOBIJI, values.get(PROSTI_CAS).get(PROSTI_CAS_HOBIJI) + dailyValues.get(PROSTI_CAS).get(PROSTI_CAS_HOBIJI));
                values.get(PROSTI_CAS).set(PROSTI_CAS_SPORT, values.get(PROSTI_CAS).get(PROSTI_CAS_SPORT) + dailyValues.get(PROSTI_CAS).get(PROSTI_CAS_SPORT));
                values.get(PROSTI_CAS).set(PROSTI_CAS_GLASBILA, values.get(PROSTI_CAS).get(PROSTI_CAS_GLASBILA) + dailyValues.get(PROSTI_CAS).get(PROSTI_CAS_GLASBILA));
                values.get(PROSTI_CAS).set(PROSTI_CAS_DRUGO, values.get(PROSTI_CAS).get(PROSTI_CAS_DRUGO) + dailyValues.get(PROSTI_CAS).get(PROSTI_CAS_DRUGO));
            }

            updateExpandableListData(values, balance);
        }
    }

    /*
     * Updates the values in the expandable list
     */
    private void updateExpandableListData(List<List<Integer>> listWithValues, int balance) {
        listDataHeader.clear();
        listDataChild.clear();

        int size = 40;

        // Adding parent data
        listDataHeader.add("Plača");
        listDataHeader.add(resizeString("Potni stroški:", listWithValues.get(POTNI_STROSKI).get(VSOTA), size));
        listDataHeader.add(resizeString("Hrana:", listWithValues.get(HRANA).get(VSOTA), size));
        listDataHeader.add(resizeString("Obleke:", listWithValues.get(OBLEKE).get(VSOTA), size));
        listDataHeader.add(resizeString("Hišni stroški:", listWithValues.get(HISNI_STROSKI).get(VSOTA), size));
        listDataHeader.add(resizeString("Igrače:", listWithValues.get(IGRACE).get(VSOTA), size));
        listDataHeader.add(resizeString("Darila:", listWithValues.get(DARILA).get(VSOTA), size));
        listDataHeader.add(resizeString("Prosti čas:", listWithValues.get(PROSTI_CAS).get(VSOTA), size));

        // Adding child data
        List<String> placa = new ArrayList<String>();
        placa.add(resizeString("Skupaj:", listWithValues.get(PLACA).get(VSOTA), size));

        List<String> potStroski = new ArrayList<String>();
        potStroski.add(resizeString("Gorivo:", listWithValues.get(POTNI_STROSKI).get(POTNI_STROSKI_GORIVO), size));
        potStroski.add(resizeString("Stroški za avto:", listWithValues.get(POTNI_STROSKI).get(POTNI_STROSKI_AVTO), size));

        List<String> hrana = new ArrayList<String>();
        hrana.add(resizeString("Zelenjava:", listWithValues.get(HRANA).get(HRANA_ZELENJAVA), size));
        hrana.add(resizeString("Mesni izdelki:", listWithValues.get(HRANA).get(HRANA_MESO), size));
        hrana.add(resizeString("Izdelki iz žit:", listWithValues.get(HRANA).get(HRANA_ZITA), size));
        hrana.add(resizeString("Sadje:", listWithValues.get(HRANA).get(HRANA_SADJE), size));
        hrana.add(resizeString("Pijača:", listWithValues.get(HRANA).get(HRANA_PIJACA), size));
        hrana.add(resizeString("Drugo:", listWithValues.get(HRANA).get(HRANA_DRUGO), size));

        List<String> obleke = new ArrayList<String>();
        obleke.add(resizeString("Zgornji del:", listWithValues.get(OBLEKE).get(OBLEKE_ZGORNJI_DEL), size));
        obleke.add(resizeString("Spodnji del:", listWithValues.get(OBLEKE).get(OBLEKE_SPODNJI_DEL), size));
        obleke.add(resizeString("Obutev:", listWithValues.get(OBLEKE).get(OBLEKE_OBUTEV), size));
        obleke.add(resizeString("Dodatki:", listWithValues.get(OBLEKE).get(OBLEKE_DODATKI), size));

        List<String> hisniStroski = new ArrayList<String>();
        hisniStroski.add(resizeString("Voda:", listWithValues.get(HISNI_STROSKI).get(HISNI_STROSKI_VODA), size));
        hisniStroski.add(resizeString("Elektrika:", listWithValues.get(HISNI_STROSKI).get(HISNI_STROSKI_ELEKTRIKA), size));
        hisniStroski.add(resizeString("Televizija/Internet:", listWithValues.get(HISNI_STROSKI).get(HISNI_STROSKI_TV_INT), size));
        hisniStroski.add(resizeString("Ogrevanje:", listWithValues.get(HISNI_STROSKI).get(HISNI_STROSKI_OGRAVANJE), size));
        hisniStroski.add(resizeString("Drugo:", listWithValues.get(HISNI_STROSKI).get(HISNI_STROSKI_DRUGO), size));

        List<String> igrace = new ArrayList<String>();
        igrace.add(resizeString("Velike:", listWithValues.get(IGRACE).get(IGRACE_VELIKE), size));
        igrace.add(resizeString("Majhne:", listWithValues.get(IGRACE).get(IGRACE_MAJHNE), size));
        igrace.add(resizeString("Drugo:", listWithValues.get(IGRACE).get(IGRACE_DRUGO), size));

        List<String> darila = new ArrayList<String>();
        darila.add(resizeString("Prazniki:", listWithValues.get(DARILA).get(DARILA_PRAZNIKI), size));
        darila.add(resizeString("Rojstni dnevi:", listWithValues.get(DARILA).get(DARILA_ROJSTNI_DNEVI), size));
        darila.add(resizeString("Drugo:", listWithValues.get(DARILA).get(DARILA_DRUGO), size));

        List<String> prostiCas = new ArrayList<String>();
        prostiCas.add(resizeString("Hobiji:", listWithValues.get(PROSTI_CAS).get(PROSTI_CAS_HOBIJI), size));
        prostiCas.add(resizeString("Šport:", listWithValues.get(PROSTI_CAS).get(PROSTI_CAS_SPORT), size));
        prostiCas.add(resizeString("Glasbila:", listWithValues.get(PROSTI_CAS).get(PROSTI_CAS_GLASBILA), size));
        prostiCas.add(resizeString("Drugo:", listWithValues.get(PROSTI_CAS).get(PROSTI_CAS_DRUGO), size));

        listDataChild.put(listDataHeader.get(0), placa);
        listDataChild.put(listDataHeader.get(1), potStroski);
        listDataChild.put(listDataHeader.get(2), hrana);
        listDataChild.put(listDataHeader.get(3), obleke);
        listDataChild.put(listDataHeader.get(4), hisniStroski);
        listDataChild.put(listDataHeader.get(5), igrace);
        listDataChild.put(listDataHeader.get(6), darila);
        listDataChild.put(listDataHeader.get(7), prostiCas);

        // get list adapter
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        //set balance
        TextView balanceValue = (TextView) findViewById(R.id.balance_main_value);
        String balanceString = Integer.toString(balance) + "€";
        balanceValue.setText(balanceString);
    }

    /*
     * Update the view of the main activity to the chosen form
     */
    // TODO: updateView method for graph view and list view
    // TODO: popravi updateView, da bo upoštevala popravljene vrednosti
    private void updateView() {
        Intent intent;

        if (setListView) {

        } else if (setGraphView) {

        } else if (setStatView) {
            Calendar date = Calendar.getInstance();

            // začetni in končni datum uporabljen pri izrisu grafa
            int startDay, endDay;

            // število predhodnih dni
            int day;

            // začasen seznam podatkov po kategorijah
            List<Integer> values = new ArrayList<>();

            // seznam podatkov, ki jih pošljemo aktivnosti s statistiko
            List<List<Integer>> graphValues = new ArrayList<>();

            // seznam podatkov za trenuten dan
            List<List<Integer>> dailyValues;

            // prikaz na teden
            if (displayWeek) {
                day = date.get(Calendar.DAY_OF_WEEK);
            // prikaz na mesec
            } else if (displayMonth) {
                day = date.get(Calendar.DAY_OF_MONTH);
            // prikaz na leto
            } else if (displayYear) {
                day = date.get(Calendar.DAY_OF_YEAR);
            } else {
                day = 0;
                return;
            }

            startDay = key;
            endDay = key;

            // pomikamo se po dnevih nazaj in si beležimo vsote podatkov za vsako kategorijo v posameznem dnevu
            for (int i = 0; i < day; i++) {
                if (key - i == 0)
                    key = 365 + i;

                values.clear();

                if (data.indexOfKey(key - i) >= 0) {
                    dailyValues = data.get(key - i);
                } else {
                    startDay = key - i;
                    break;
                }

                for (int j = 0; j < 8; j++) {
                    values.add(dailyValues.get(j).get(VSOTA));
                }

                graphValues.add(values);
            }

            intent = new Intent(this, StatActivity.class);
            intent.putExtra(EXTRA_START_DAY, startDay);
            intent.putExtra(EXTRA_END_DAY, endDay);
            intent.putExtra(EXTRA_DATA_PER_DAY, (Serializable) graphValues);
            startActivity(intent);
        }
    }

    /*
     * Format a string to have a length of 20
     */
    private String resizeString(String left, int right, int size) {
        StringBuilder sb = new StringBuilder();

        sb.append(left);

        while (sb.length() + left.length() + 1 < size) {
            sb.append(" ");
        }

        sb.append(right);
        sb.append("€");

        return sb.toString();
    }

    /*
     * Restore the data structure from internal memory
     */
    private boolean restoreData() {
        try {
            Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(openFileInput(STORAGE_FILE_NAME))));

            if (sc.hasNext()) {
                List<List<Integer>> tempList = new ArrayList<>();
                int key;

                while (sc.hasNext()) {
                    key = Integer.parseInt(sc.nextLine());

                    tempList.get(PLACA).add(VSOTA, Integer.parseInt(sc.nextLine()));

                    tempList.get(POTNI_STROSKI).add(VSOTA, Integer.parseInt(sc.nextLine()));
                    tempList.get(POTNI_STROSKI).add(POTNI_STROSKI_GORIVO, Integer.parseInt(sc.nextLine()));
                    tempList.get(POTNI_STROSKI).add(POTNI_STROSKI_AVTO, Integer.parseInt(sc.nextLine()));

                    tempList.get(HRANA).add(VSOTA, Integer.parseInt(sc.nextLine()));
                    tempList.get(HRANA).add(HRANA_ZELENJAVA, Integer.parseInt(sc.nextLine()));
                    tempList.get(HRANA).add(HRANA_MESO, Integer.parseInt(sc.nextLine()));
                    tempList.get(HRANA).add(HRANA_ZITA, Integer.parseInt(sc.nextLine()));
                    tempList.get(HRANA).add(HRANA_SADJE, Integer.parseInt(sc.nextLine()));
                    tempList.get(HRANA).add(HRANA_PIJACA, Integer.parseInt(sc.nextLine()));
                    tempList.get(HRANA).add(HRANA_DRUGO, Integer.parseInt(sc.nextLine()));

                    tempList.get(OBLEKE).add(VSOTA, Integer.parseInt(sc.nextLine()));
                    tempList.get(OBLEKE).add(OBLEKE_ZGORNJI_DEL, Integer.parseInt(sc.nextLine()));
                    tempList.get(OBLEKE).add(OBLEKE_SPODNJI_DEL, Integer.parseInt(sc.nextLine()));
                    tempList.get(OBLEKE).add(OBLEKE_OBUTEV, Integer.parseInt(sc.nextLine()));
                    tempList.get(OBLEKE).add(OBLEKE_DODATKI, Integer.parseInt(sc.nextLine()));

                    tempList.get(HISNI_STROSKI).add(VSOTA, Integer.parseInt(sc.nextLine()));
                    tempList.get(HISNI_STROSKI).add(HISNI_STROSKI_VODA, Integer.parseInt(sc.nextLine()));
                    tempList.get(HISNI_STROSKI).add(HISNI_STROSKI_ELEKTRIKA, Integer.parseInt(sc.nextLine()));
                    tempList.get(HISNI_STROSKI).add(HISNI_STROSKI_TV_INT, Integer.parseInt(sc.nextLine()));
                    tempList.get(HISNI_STROSKI).add(HISNI_STROSKI_OGRAVANJE, Integer.parseInt(sc.nextLine()));
                    tempList.get(HISNI_STROSKI).add(HISNI_STROSKI_DRUGO, Integer.parseInt(sc.nextLine()));

                    tempList.get(IGRACE).add(VSOTA, Integer.parseInt(sc.nextLine()));
                    tempList.get(IGRACE).add(IGRACE_VELIKE, Integer.parseInt(sc.nextLine()));
                    tempList.get(IGRACE).add(IGRACE_MAJHNE, Integer.parseInt(sc.nextLine()));
                    tempList.get(IGRACE).add(IGRACE_DRUGO, Integer.parseInt(sc.nextLine()));

                    tempList.get(DARILA).add(VSOTA, Integer.parseInt(sc.nextLine()));
                    tempList.get(DARILA).add(DARILA_PRAZNIKI, Integer.parseInt(sc.nextLine()));
                    tempList.get(DARILA).add(DARILA_ROJSTNI_DNEVI, Integer.parseInt(sc.nextLine()));
                    tempList.get(DARILA).add(DARILA_DRUGO, Integer.parseInt(sc.nextLine()));

                    tempList.get(PROSTI_CAS).add(VSOTA, Integer.parseInt(sc.nextLine()));
                    tempList.get(PROSTI_CAS).add(PROSTI_CAS_HOBIJI, Integer.parseInt(sc.nextLine()));
                    tempList.get(PROSTI_CAS).add(PROSTI_CAS_SPORT, Integer.parseInt(sc.nextLine()));
                    tempList.get(PROSTI_CAS).add(PROSTI_CAS_GLASBILA, Integer.parseInt(sc.nextLine()));
                    tempList.get(PROSTI_CAS).add(PROSTI_CAS_DRUGO, Integer.parseInt(sc.nextLine()));

                    data.append(key, tempList);

                    tempList.clear();
                }

                sc.close();

                return true;
            } else {
                return false;
            }
        } catch (FileNotFoundException e) {
            //Log.d("File Not Found", "Ne najdem datoteke " + STORAGE_FILE_NAME + ".");
            return false;
        }
    }

    /*
     * Save the data structure to internal memory
     */
    private boolean saveData() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(STORAGE_FILE_NAME, Context.MODE_PRIVATE)));
            int key;

            for (int i = 0; i < data.size(); i++) {
                key = data.keyAt(i);

                bw.write(Integer.toString(key));
                bw.newLine();

                bw.write(Integer.toString(data.get(key).get(PLACA).get(VSOTA)));
                bw.newLine();

                bw.write(Integer.toString(data.get(key).get(POTNI_STROSKI).get(VSOTA)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(POTNI_STROSKI).get(POTNI_STROSKI_GORIVO)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(POTNI_STROSKI).get(POTNI_STROSKI_AVTO)));
                bw.newLine();

                bw.write(Integer.toString(data.get(key).get(HRANA).get(VSOTA)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(HRANA).get(HRANA_ZELENJAVA)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(HRANA).get(HRANA_MESO)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(HRANA).get(HRANA_ZITA)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(HRANA).get(HRANA_SADJE)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(HRANA).get(HRANA_PIJACA)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(HRANA).get(HRANA_DRUGO)));
                bw.newLine();

                bw.write(Integer.toString(data.get(key).get(OBLEKE).get(VSOTA)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(OBLEKE).get(OBLEKE_ZGORNJI_DEL)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(OBLEKE).get(OBLEKE_SPODNJI_DEL)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(OBLEKE).get(OBLEKE_OBUTEV)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(OBLEKE).get(OBLEKE_DODATKI)));
                bw.newLine();

                bw.write(Integer.toString(data.get(key).get(HISNI_STROSKI).get(VSOTA)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(HISNI_STROSKI).get(HISNI_STROSKI_VODA)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(HISNI_STROSKI).get(HISNI_STROSKI_ELEKTRIKA)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(HISNI_STROSKI).get(HISNI_STROSKI_TV_INT)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(HISNI_STROSKI).get(HISNI_STROSKI_OGRAVANJE)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(HISNI_STROSKI).get(HISNI_STROSKI_DRUGO)));
                bw.newLine();

                bw.write(Integer.toString(data.get(key).get(IGRACE).get(VSOTA)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(IGRACE).get(IGRACE_VELIKE)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(IGRACE).get(IGRACE_MAJHNE)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(IGRACE).get(IGRACE_DRUGO)));
                bw.newLine();

                bw.write(Integer.toString(data.get(key).get(DARILA).get(VSOTA)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(DARILA).get(DARILA_PRAZNIKI)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(DARILA).get(DARILA_ROJSTNI_DNEVI)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(DARILA).get(DARILA_DRUGO)));
                bw.newLine();

                bw.write(Integer.toString(data.get(key).get(PROSTI_CAS).get(VSOTA)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(PROSTI_CAS).get(PROSTI_CAS_HOBIJI)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(PROSTI_CAS).get(PROSTI_CAS_SPORT)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(PROSTI_CAS).get(PROSTI_CAS_GLASBILA)));
                bw.newLine();
                bw.write(Integer.toString(data.get(key).get(PROSTI_CAS).get(PROSTI_CAS_DRUGO)));
            }

            bw.flush();
            bw.close();

            return true;
        } catch (IOException e) {
            //Log.d("IO Error", "Napaka pri shranjevanju podatkov v notranji pomnilnik.");
            //e.printStackTrace();
            return false;
        }
    }
}
