package com.sminarska.emp.personalassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anei on 04/01/2018.
 */

public class StatActivity extends AppCompatActivity {

    public LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        // get intent
        Intent intent = getIntent();

        // get extra data
        int start = intent.getIntExtra(MainActivity.EXTRA_START_DAY, 0);
        int end = intent.getIntExtra(MainActivity.EXTRA_END_DAY, 0);
        List<List<Integer>> data = (List<List<Integer>>) intent.getSerializableExtra(MainActivity.EXTRA_DATA_PER_DAY);

        int temp = start;

        // get the chart view
        chart = (LineChart) findViewById(R.id.line_chart);

        // create the arrays of chart entries
        List<Entry> entryPlaca = new ArrayList<Entry>();
        List<Entry> entryPot = new ArrayList<Entry>();
        List<Entry> entryHrana = new ArrayList<Entry>();
        List<Entry> entryObleke = new ArrayList<Entry>();
        List<Entry> entryHisa = new ArrayList<Entry>();
        List<Entry> entryIgrace = new ArrayList<Entry>();
        List<Entry> entryDarila = new ArrayList<Entry>();
        List<Entry> entryProstCas = new ArrayList<Entry>();

        int income = 0;
        int outcome = 0;

        // merge the data for each category in its own entry array
        for (int i = data.size() - 1; i >= 0; i--) {
            entryPlaca.add(new Entry(temp, data.get(i).get(0)));
            income += data.get(i).get(0);
            entryPot.add(new Entry(temp, data.get(i).get(1)));
            outcome += data.get(i).get(1);
            entryHrana.add(new Entry(temp, data.get(i).get(2)));
            outcome += data.get(i).get(2);
            entryObleke.add(new Entry(temp, data.get(i).get(3)));
            outcome += data.get(i).get(3);
            entryHisa.add(new Entry(temp, data.get(i).get(4)));
            outcome += data.get(i).get(4);
            entryIgrace.add(new Entry(temp, data.get(i).get(5)));
            outcome += data.get(i).get(5);
            entryDarila.add(new Entry(temp, data.get(i).get(6)));
            outcome += data.get(i).get(6);
            entryProstCas.add(new Entry(temp, data.get(i).get(7)));
            outcome += data.get(i).get(7);

            temp++;
        }

        // calculate and display the balance
        int balance = income - outcome;

        TextView incomeValue = (TextView) findViewById(R.id.stat_income);
        TextView outcomeValue = (TextView) findViewById(R.id.stat_outcome);
        TextView ballanceValue = (TextView) findViewById(R.id.stat_balance);

        String incomeString = "Prihodki: " + Integer.toString(income);
        String outcomeString = "Odhodki: " + Integer.toString(outcome);
        String balanceString = "Ostanek: " + Integer.toString(balance);

        incomeValue.setText(incomeString);
        outcomeValue.setText(outcomeString);
        ballanceValue.setText(balanceString);

        // create the data sets for each category
        LineDataSet placaSet = new LineDataSet(entryPlaca, "Plača");
        LineDataSet potSet = new LineDataSet(entryPot, "Potni stroški");
        LineDataSet hranaSet = new LineDataSet(entryHrana, "Hrana");
        LineDataSet oblekeSet = new LineDataSet(entryObleke, "Oblačila");
        LineDataSet hisaSet = new LineDataSet(entryHisa, "Hišni stroški");
        LineDataSet igraceSet = new LineDataSet(entryIgrace, "Igrače");
        LineDataSet darilaSet = new LineDataSet(entryDarila, "Darila");
        LineDataSet prostCasSet = new LineDataSet(entryProstCas, "Prosti čas");

        // set colors
        placaSet.setColor(getResources().getColor(R.color.statColorOne));
        potSet.setColor(getResources().getColor(R.color.statColorTwo));
        hranaSet.setColor(getResources().getColor(R.color.statColorThree));
        oblekeSet.setColor(getResources().getColor(R.color.statColorFour));
        hisaSet.setColor(getResources().getColor(R.color.statColorFive));
        igraceSet.setColor(getResources().getColor(R.color.statColorSix));
        darilaSet.setColor(getResources().getColor(R.color.statColorSeven));
        prostCasSet.setColor(getResources().getColor(R.color.statColorEight));

        placaSet.setCircleColor(getResources().getColor(R.color.statColorOne));
        potSet.setCircleColor(getResources().getColor(R.color.statColorTwo));
        hranaSet.setCircleColor(getResources().getColor(R.color.statColorThree));
        oblekeSet.setCircleColor(getResources().getColor(R.color.statColorFour));
        hisaSet.setCircleColor(getResources().getColor(R.color.statColorFive));
        igraceSet.setCircleColor(getResources().getColor(R.color.statColorSix));
        darilaSet.setCircleColor(getResources().getColor(R.color.statColorSeven));
        prostCasSet.setCircleColor(getResources().getColor(R.color.statColorEight));

        placaSet.setCircleColorHole(getResources().getColor(R.color.statColorOne));
        potSet.setCircleColorHole(getResources().getColor(R.color.statColorTwo));
        hranaSet.setCircleColorHole(getResources().getColor(R.color.statColorThree));
        oblekeSet.setCircleColorHole(getResources().getColor(R.color.statColorFour));
        hisaSet.setCircleColorHole(getResources().getColor(R.color.statColorFive));
        igraceSet.setCircleColorHole(getResources().getColor(R.color.statColorSix));
        darilaSet.setCircleColorHole(getResources().getColor(R.color.statColorSeven));
        prostCasSet.setCircleColorHole(getResources().getColor(R.color.statColorEight));

        // add the data sets to an array of data sets
        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(placaSet);
        dataSets.add(potSet);
        dataSets.add(hranaSet);
        dataSets.add(oblekeSet);
        dataSets.add(hisaSet);
        dataSets.add(igraceSet);
        dataSets.add(darilaSet);
        dataSets.add(prostCasSet);

        // format the data sets into a single line data
        LineData graphData = new LineData(dataSets);

        // set the legend to not clip outside of screen
        Legend legend = chart.getLegend();
        legend.setWordWrapEnabled(true);

        // set the description text
        Description desc = new Description();
        desc.setText("Prihodki in odhodki po kategorijah.");
        chart.setDescription(desc);

        // add the data to the chart
        chart.setData(graphData);
        chart.setVisibleXRange(start, end);
        // display the data on the chart
        chart.invalidate();

        // set the return listener on the button
        Button returnButton = (Button) findViewById(R.id.stat_return_button);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
