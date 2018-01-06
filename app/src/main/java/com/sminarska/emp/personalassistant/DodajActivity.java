package com.sminarska.emp.personalassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DodajActivity extends AppCompatActivity {

    public static final int RESULT_REQUEST_CATEGORY = 1;

    public static final String RESULT_CATEGORY_INDEX = "com.seminarska.emp.personalassistant.CATEGORYINDEX";
    public static final String RESULT_SUBCATEGORY_INDEX = "com.seminarska.emp.personalassistant.SUBCATEGORYINDEX";
    public static final String RESULT_ADDED_AMOUNT = "com.seminarska.emp.personalassistant.ADDEDAMOUNT";

    public boolean operatorPressed = false;
    public double st1;
    public double st2;
    public String OP;

    public boolean categoryChosen = false;
    public int chosenCategory;

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

        // naredi intent, kjer boš shranil in poslal rezultat
        final Intent intent_result = new Intent();

        // poženemo nov aktivity za izbiro kategorije in pričakujemo rezultat
        izberiKat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(DodajActivity.this, KategorijeActivity.class);
                startActivityForResult(intent2, RESULT_REQUEST_CATEGORY);
            }
        });

        // listenerji za vnos zneska
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
                if(!operatorPressed) {
                    operatorPressed = true;
                    OP = "×";
                    rezultat.setText(rezultat.getText() + "×");
                }
            }
        });
        deljeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!operatorPressed) {
                    operatorPressed = true;
                    OP = "/";
                    rezultat.setText(rezultat.getText() + "/");
                }
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!operatorPressed) {
                    operatorPressed = true;
                    OP = "\\+";
                    rezultat.setText(rezultat.getText() + "+");
                }
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!operatorPressed) {
                    operatorPressed = true;
                    OP = "-";
                    rezultat.setText(rezultat.getText() + "-");
                }
            }
        });
        enako.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operatorPressed = false;
                double rez = 0.0;
                String temp = (String)rezultat.getText();
                String[] st = temp.split(OP);
                st1 = Double.parseDouble(st[0]);
                st2 = Double.parseDouble(st[1]);
                switch(OP) {
                    case "\\+":
                        rez = st1 + st2;
                        break;
                    case "-":
                        rez = st1 - st2;
                        break;
                    case "×":
                        rez = st1 * st2;
                        break;
                    case "/":
                        rez = st1 / st2;
                        break;
                }

                rezultat.setText(String.format( "%.0f", rez));

            }
        });
        brisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rezultat.setText("");
            }
        });

        // če kliknemo na znesek, potrdimo da ga hočemo dodati oziroma odšteti
        rezultat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // preveri, če je uporabnik izbral želeno kategorijo
                if (categoryChosen) {
                    // preveri, če je uporabnik vnesel znesek
                    if (!rezultat.getText().equals("")) {
                        // preveri, če je vnesen znesek v pravilnem formatu
                        if (!rezultat.getText().toString().contains("+") || !rezultat.getText().toString().contains("-") || !rezultat.getText().toString().contains("*") || !rezultat.getText().toString().contains("/")) {
                            int catIndex;
                            int subcatIndex;

                            switch (chosenCategory) {
                                //----------PLAČA-------------//
                                case 0:
                                    catIndex = MainActivity.PLACA;
                                    subcatIndex = MainActivity.VSOTA;
                                    break;
                                //-----POTNI_STROŠKI----------//
                                case 1:
                                    catIndex = MainActivity.POTNI_STROSKI;
                                    subcatIndex = MainActivity.POTNI_STROSKI_GORIVO;
                                    break;
                                case 2:
                                    catIndex = MainActivity.POTNI_STROSKI;
                                    subcatIndex = MainActivity.POTNI_STROSKI_AVTO;
                                    break;
                                //-----------HRANA------------//
                                case 3:
                                    catIndex = MainActivity.HRANA;
                                    subcatIndex = MainActivity.HRANA_ZELENJAVA;
                                    break;
                                case 4:
                                    catIndex = MainActivity.HRANA;
                                    subcatIndex = MainActivity.HRANA_MESO;
                                    break;
                                case 5:
                                    catIndex = MainActivity.HRANA;
                                    subcatIndex = MainActivity.HRANA_ZITA;
                                    break;
                                case 6:
                                    catIndex = MainActivity.HRANA;
                                    subcatIndex = MainActivity.HRANA_SADJE;
                                    break;
                                case 7:
                                    catIndex = MainActivity.HRANA;
                                    subcatIndex = MainActivity.HRANA_PIJACA;
                                    break;
                                case 8:
                                    catIndex = MainActivity.HRANA;
                                    subcatIndex = MainActivity.HRANA_DRUGO;
                                    break;
                                //---------OBLEKE-------------//
                                case 9:
                                    catIndex = MainActivity.OBLEKE;
                                    subcatIndex = MainActivity.OBLEKE_ZGORNJI_DEL;
                                    break;
                                case 10:
                                    catIndex = MainActivity.OBLEKE;
                                    subcatIndex = MainActivity.OBLEKE_SPODNJI_DEL;
                                    break;
                                case 11:
                                    catIndex = MainActivity.OBLEKE;
                                    subcatIndex = MainActivity.OBLEKE_OBUTEV;
                                    break;
                                case 12:
                                    catIndex = MainActivity.OBLEKE;
                                    subcatIndex = MainActivity.OBLEKE_DODATKI;
                                    break;
                                //------HIŠNI_STROŠKI---------//
                                case 13:
                                    catIndex = MainActivity.HISNI_STROSKI;
                                    subcatIndex = MainActivity.HISNI_STROSKI_VODA;
                                    break;
                                case 14:
                                    catIndex = MainActivity.HISNI_STROSKI;
                                    subcatIndex = MainActivity.HISNI_STROSKI_ELEKTRIKA;
                                    break;
                                case 15:
                                    catIndex = MainActivity.HISNI_STROSKI;
                                    subcatIndex = MainActivity.HISNI_STROSKI_TV_INT;
                                    break;
                                case 16:
                                    catIndex = MainActivity.HISNI_STROSKI;
                                    subcatIndex = MainActivity.HISNI_STROSKI_OGRAVANJE;
                                    break;
                                case 17:
                                    catIndex = MainActivity.HISNI_STROSKI;
                                    subcatIndex = MainActivity.HISNI_STROSKI_DRUGO;
                                    break;
                                //---------IGRAČE-------------//
                                case 18:
                                    catIndex = MainActivity.IGRACE;
                                    subcatIndex = MainActivity.IGRACE_VELIKE;
                                    break;
                                case 19:
                                    catIndex = MainActivity.IGRACE;
                                    subcatIndex = MainActivity.IGRACE_MAJHNE;
                                    break;
                                case 20:
                                    catIndex = MainActivity.IGRACE;
                                    subcatIndex = MainActivity.IGRACE_DRUGO;
                                    break;
                                //---------DARILA-------------//
                                case 21:
                                    catIndex = MainActivity.DARILA;
                                    subcatIndex = MainActivity.DARILA_PRAZNIKI;
                                    break;
                                case 22:
                                    catIndex = MainActivity.DARILA;
                                    subcatIndex = MainActivity.DARILA_ROJSTNI_DNEVI;
                                    break;
                                case 23:
                                    catIndex = MainActivity.DARILA;
                                    subcatIndex = MainActivity.DARILA_DRUGO;
                                    break;
                                //--------PROSTI_ČAS----------//
                                case 24:
                                    catIndex = MainActivity.PROSTI_CAS;
                                    subcatIndex = MainActivity.PROSTI_CAS_HOBIJI;
                                    break;
                                case 25:
                                    catIndex = MainActivity.PROSTI_CAS;
                                    subcatIndex = MainActivity.PROSTI_CAS_SPORT;
                                    break;
                                case 26:
                                    catIndex = MainActivity.PROSTI_CAS;
                                    subcatIndex = MainActivity.PROSTI_CAS_GLASBILA;
                                    break;
                                case 27:
                                    catIndex = MainActivity.PROSTI_CAS;
                                    subcatIndex = MainActivity.PROSTI_CAS_DRUGO;
                                    break;
                                //---------DEFAULT------------//
                                default:
                                    return;
                            }

                            intent_result.putExtra(RESULT_CATEGORY_INDEX, catIndex);
                            intent_result.putExtra(RESULT_SUBCATEGORY_INDEX, subcatIndex);
                            intent_result.putExtra(RESULT_ADDED_AMOUNT, Integer.parseInt(rezultat.getText().toString()));
                            setResult(Activity.RESULT_OK, intent_result);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Znesek ne sme vsebovati znakov +, -, * ali /.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Vnesti morate znesek.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Najprej morate izbrati kategorijo..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // pridobimo rezultat izbire kategorije
        switch (requestCode) {
            case RESULT_REQUEST_CATEGORY:
                if (resultCode == Activity.RESULT_OK) {
                    chosenCategory = data.getIntExtra(KategorijeActivity.RESULT_CATEGORY_KEY, 0);

                    if (!categoryChosen)
                        categoryChosen = true;
                }
                break;
            default:
                break;
        }
    }
}
