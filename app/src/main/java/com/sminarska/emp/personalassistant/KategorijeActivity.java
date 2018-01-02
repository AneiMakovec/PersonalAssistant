package com.sminarska.emp.personalassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class KategorijeActivity extends AppCompatActivity {
    public String items[] = new String[]{"Plača", "Hrana", "Avto", "Prosti čas", "Računi", "Obleke", "Darila", "Igrače", "Muzika", "Potni stroški"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategorije);

        final ListView kategorije = (ListView)findViewById(R.id.seznam);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(KategorijeActivity.this, android.R.layout.simple_list_item_1, items);


        kategorije.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), kategorije.getItemAtPosition(i).toString()+" pressed!", Toast.LENGTH_LONG).show();
            }
        });
        kategorije.setAdapter(adapter);
    }
}
