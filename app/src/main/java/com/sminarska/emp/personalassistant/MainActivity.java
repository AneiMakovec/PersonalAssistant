package com.sminarska.emp.personalassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // expandable list
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    // navigation drawer
    DrawerLayout drawerLayout;
    NavigationView drawerView;


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
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // TEGA NE BOVA RABLA
        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
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
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

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
}
