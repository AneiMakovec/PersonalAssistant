package com.sminarska.emp.personalassistant;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // expandable list
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    // navigation drawer
    List<String> drawerData;
    DrawerLayout drawerLayout;
    ListView listView;


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
    }

    /*
     * Preparing the expandable list data
     */
    private void prepareExpandableListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Plača");
        listDataHeader.add("Potni stroški");
        listDataHeader.add("Hrana");
        listDataHeader.add("Obleke");
        listDataHeader.add("Igrače");
        listDataHeader.add("Darila");
        listDataHeader.add("Prosti čas");
        listDataHeader.add("Muzika");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("Bruto");
        top250.add("Neto");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        List<String> t1 = new ArrayList<String>();
        t1.add("2 Guns");
        t1.add("The Smurfs 2");
        t1.add("The Spectacular Now");
        t1.add("The Canyons");
        t1.add("Europa Report");

        List<String> t2 = new ArrayList<String>();
        t2.add("2 Guns");
        t2.add("The Smurfs 2");
        t2.add("The Spectacular Now");
        t2.add("The Canyons");
        t2.add("Europa Report");

        List<String> t3 = new ArrayList<String>();
        t3.add("2 Guns");
        t3.add("The Smurfs 2");
        t3.add("The Spectacular Now");
        t3.add("The Canyons");
        t3.add("Europa Report");

        List<String> t4 = new ArrayList<String>();
        t4.add("2 Guns");
        t4.add("The Smurfs 2");
        t4.add("The Spectacular Now");
        t4.add("The Canyons");
        t4.add("Europa Report");

        List<String> t5 = new ArrayList<String>();
        t5.add("2 Guns");
        t5.add("The Smurfs 2");
        t5.add("The Spectacular Now");
        t5.add("The Canyons");
        t5.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
        listDataChild.put(listDataHeader.get(3), t1);
        listDataChild.put(listDataHeader.get(4), t2);
        listDataChild.put(listDataHeader.get(5), t3);
        listDataChild.put(listDataHeader.get(6), t4);
        listDataChild.put(listDataHeader.get(7), t5);
    }

    /*
        Preparing the navigation drawer data
     */
    private void prepareNavigationDrawerData() {
        drawerData = new ArrayList<String>();

        // TODO
    }
}
