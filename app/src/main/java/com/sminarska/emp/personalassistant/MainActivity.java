package com.sminarska.emp.personalassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // indexes
    public static final int VSOTA = 0;

    public static final int PLACA = 0;
    public static final int PLACA_BRUTO = 1;
    public static final int PLACA_NETO = 2;

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

    // expandable list
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    // navigation drawer
    DrawerLayout drawerLayout;
    NavigationView drawerView;

    // control
    boolean displayWeek;
    boolean displayMonth;
    boolean displayYear;

    boolean setListView;
    boolean setGraphView;
    boolean setStatView;

    // data
    SparseArray<List<List<Integer>>> data;


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
                intent.putExtra("testOdliv", "to je test1");
                startActivity(intent);
            }
        });

        priliv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DodajActivity.class);
                intent.putExtra("testPriliv", "to je test2");
                startActivity(intent);
            }
        });



        // PREPARE THE EXPANDABLE LIST

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareExpandableListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                /*
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
                */
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                /*
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();
                */

            }
        });

        // TEGA NE BOVA RABLA
        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                /*
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                */
                return false;
            }
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

                                    updateDisplay();
                                }
                                break;
                            case R.id.drawer_first_month:
                                if (!displayMonth) {
                                    displayWeek = false;
                                    displayMonth = true;
                                    displayYear = false;

                                    Toast.makeText(getApplicationContext(), "Spreminjam prikaz: mesečno", Toast.LENGTH_SHORT).show();

                                    updateDisplay();
                                }
                                break;
                            case R.id.drawer_first_year:
                                if (!displayYear) {
                                    displayWeek = false;
                                    displayMonth = false;
                                    displayYear = true;

                                    Toast.makeText(getApplicationContext(), "Spreminjam prikaz: letno", Toast.LENGTH_SHORT).show();

                                    updateDisplay();
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



        // PREPARE THE DATA
        prepareDataStructure();
    }

    /*
     * Preparing the expandable list data
     */
    private void prepareExpandableListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding parent data
        listDataHeader.add("Plača");
        listDataHeader.add("Potni stroški");
        listDataHeader.add("Hrana");
        listDataHeader.add("Obleke");
        listDataHeader.add("Hišni stroški");
        listDataHeader.add("Igrače");
        listDataHeader.add("Darila");
        listDataHeader.add("Prosti čas");

        // Adding child data
        List<String> placa = new ArrayList<String>();
        placa.add("Bruto");
        placa.add("Neto");

        List<String> potStroski = new ArrayList<String>();
        potStroski.add("Gorivo");
        potStroski.add("Stroški za avto");

        List<String> hrana = new ArrayList<String>();
        hrana.add("Zelenjava");
        hrana.add("Mesni izdelki");
        hrana.add("Izdelki iz žit");
        hrana.add("Sadje");
        hrana.add("Pijača");
        hrana.add("Drugo");

        List<String> obleke = new ArrayList<String>();
        obleke.add("Zgornji del");
        obleke.add("Spodnji del");
        obleke.add("Obutev");
        obleke.add("Dodatki");

        List<String> hisniStroski = new ArrayList<String>();
        hisniStroski.add("Voda");
        hisniStroski.add("Elektrika");
        hisniStroski.add("Televizija/Internet");
        hisniStroski.add("Ogrevanje");
        hisniStroski.add("Drugo");

        List<String> igrace = new ArrayList<String>();
        igrace.add("Velike");
        igrace.add("Majhne");
        igrace.add("Drugo");

        List<String> darila = new ArrayList<String>();
        darila.add("Prazniki");
        darila.add("Rojstni dnevi");
        darila.add("Drugo");

        List<String> prostiCas = new ArrayList<String>();
        prostiCas.add("Hobiji");
        prostiCas.add("Šport");
        prostiCas.add("Glasbila");
        prostiCas.add("Drugo");

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
    }

    /*
     * Add a value to the data structure
     */
    private void addToDataStructure(int category, int subCategory, int value) {
        Calendar date = Calendar.getInstance();
        int key = date.get(Calendar.DAY_OF_YEAR);

        if (data.indexOfKey(key) >= 0) {
            data.get(key).get(category).set(subCategory, data.get(key).get(category).get(subCategory) + value);
            data.get(key).get(category).set(VSOTA, data.get(key).get(category).get(VSOTA) + value);
        } else {
            List<List<Integer>> catList = new ArrayList<>();

            List<Integer> placa = new ArrayList<>();
            placa.add(0);
            placa.add(0);
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
            catList.get(category).set(VSOTA, value);

            data.append(key, catList);
        }
    }

    /*
     * Update the display of values for chosen time interval
     */
    private void updateDisplay() {

    }

    /*
     * Update the view of the main activity to the chosen form
     */
    private void updateView() {
        Intent intent;

        if (setListView) {

        } else if (setGraphView) {

        } else if (setStatView) {
            Calendar date = Calendar.getInstance();
            int key = date.get(Calendar.DAY_OF_YEAR);

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
                values.clear();

                if (data.indexOfKey(key - i) >= 0) {
                    dailyValues = data.get(key - i);
                } else {
                    startDay = key - i + 1;
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
}
