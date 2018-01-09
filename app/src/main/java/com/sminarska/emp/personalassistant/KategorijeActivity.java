package com.sminarska.emp.personalassistant;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class KategorijeActivity extends AppCompatActivity {

    public static final String RESULT_CATEGORY_KEY = "com.seminarska.emp.personalassistant.CATEGORY";

    public String items[] = new String[]{"Plača",
                                         "Potni stroški : Gorivo", "Potni stroški : Avto",
                                         "Hrana : Zelenjava", "Hrana : Mesni izdelki", "Hrana : Izdelki iz žit", "Hrana : Sadje", "Hrana : Pijača", "Hrana : Drugo",
                                         "Obleke : Zgornji del", "Obleke : Spodnji del", "Obleke : Obutev", "Obleke : Dodatki",
                                         "Hišni stroški : Voda", "Hišni stroški : Elektrika", "Hišni stroški : Televizija / Internet", "Hišni stroški : Ogrevanje", "Hišni stroški : Drugo",
                                         "Igrače : Velike", "Igrače : Majhne", "Igrače : Drugo",
                                         "Darila : Prazniki", "Darila : Rojstni dnevi", "Darila : Drugo",
                                         "Prosti čas : Hobiji", "Prosti čas : Šport", "Prosti čas : Glasbila", "Prosti čas : Drugo"};

    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategorije);

        // intent s končnim rezultatom
        final Intent intent_result = new Intent();

        final ListView kategorije = (ListView)findViewById(R.id.seznam);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(KategorijeActivity.this, android.R.layout.simple_list_item_1, items);

        Button dodajKategorijo = (Button) findViewById(R.id.dodajKat);

        // ko uporabnik izbere kategorijo si jo zapomnimo in ga o tem obvestimo
        kategorije.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), kategorije.getItemAtPosition(i).toString() + " izbrana!", Toast.LENGTH_SHORT).show();
                result = i;
            }
        });
        kategorije.setAdapter(adapter);

        // ko uporabnik pritisne na gumb "dodaj kategorijo", pošljemo izbrano kategorijo kot rezultat
        dodajKategorijo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent_result.putExtra(RESULT_CATEGORY_KEY, result);
                setResult(Activity.RESULT_OK, intent_result);
                finish();
            }
        });
    }
}
