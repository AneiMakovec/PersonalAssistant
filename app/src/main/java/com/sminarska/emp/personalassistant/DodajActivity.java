package com.sminarska.emp.personalassistant;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DodajActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj);

        Button ena = (Button)findViewById(R.id.buttonEna);
        Button dva = (Button)findViewById(R.id.buttonDva);
        Button tri = (Button)findViewById(R.id.buttonTri);
        Button stiri = (Button)findViewById(R.id.buttonStiri);
        Button pet = (Button)findViewById(R.id.buttonPet);
        Button sest = (Button)findViewById(R.id.buttonSest);
        Button sedem = (Button)findViewById(R.id.buttonSedem);
        Button osem = (Button)findViewById(R.id.buttonOsem);
        Button devet = (Button)findViewById(R.id.buttonDevet);
        Button nic = (Button)findViewById(R.id.buttonNic);
        Button pika = (Button)findViewById(R.id.buttonPika);
        Button enako = (Button)findViewById(R.id.buttonEnako);
        Button krat = (Button)findViewById(R.id.buttonKrat);
        Button deljeno = (Button)findViewById(R.id.buttonDeljeno);
        Button plus = (Button)findViewById(R.id.buttonPlus);
        Button minus = (Button)findViewById(R.id.buttonMinus);
        Button brisi = (Button)findViewById(R.id.brisi);
        Button izberiKat = (Button)findViewById(R.id.izberiKat);
        final TextView rezultat = (TextView)findViewById(R.id.rezultat);

        ena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rezultat.setText(rezultat.getText() + "1");
            }
        });
        dva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rezultat.setText(rezultat.getText() + "2");
            }
        });
        tri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rezultat.setText(rezultat.getText() + "3");
            }
        });
        stiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rezultat.setText(rezultat.getText() + "4");
            }
        });
        pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rezultat.setText(rezultat.getText() + "5");
            }
        });
        sest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rezultat.setText(rezultat.getText() + "6");
            }
        });
        sedem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rezultat.setText(rezultat.getText() + "7");
            }
        });
        osem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rezultat.setText(rezultat.getText() + "8");
            }
        });
        devet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rezultat.setText(rezultat.getText() + "9");
            }
        });
        nic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rezultat.setText(rezultat.getText() + "0");
            }
        });
        pika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rezultat.setText(rezultat.getText() + ".");
            }
        });
        krat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rezultat.setText(rezultat.getText() + "*");
            }
        });
        deljeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rezultat.setText(rezultat.getText() + "/");
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rezultat.setText(rezultat.getText() + "+");
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rezultat.setText(rezultat.getText() + "-");
            }
        });

    }
}
