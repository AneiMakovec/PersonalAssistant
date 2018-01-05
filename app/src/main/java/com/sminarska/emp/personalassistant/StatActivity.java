package com.sminarska.emp.personalassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
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

        int balance = income - outcome;

        TextView incomeValue = (TextView) findViewById(R.id.income_value);
        TextView outcomeValue = (TextView) findViewById(R.id.outcome_value);
        TextView ballanceValue = (TextView) findViewById(R.id.balance_value);

        incomeValue.setText(income);
        outcomeValue.setText(outcome);
        ballanceValue.setText(balance);

        LineDataSet placaSet = new LineDataSet(entryPlaca, "Plača");
        placaSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineDataSet potSet = new LineDataSet(entryPot, "Potni stroški");
        potSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineDataSet hranaSet = new LineDataSet(entryHrana, "Hrana");
        hranaSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineDataSet oblekeSet = new LineDataSet(entryObleke, "Oblačila");
        oblekeSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineDataSet hisaSet = new LineDataSet(entryHisa, "Hišni stroški");
        hisaSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineDataSet igraceSet = new LineDataSet(entryIgrace, "Igrače");
        igraceSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineDataSet darilaSet = new LineDataSet(entryDarila, "Darila");
        darilaSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineDataSet prostCasSet = new LineDataSet(entryProstCas, "Prosti čas");
        prostCasSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        // TODO: colors

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(placaSet);
        dataSets.add(potSet);
        dataSets.add(hranaSet);
        dataSets.add(oblekeSet);
        dataSets.add(hisaSet);
        dataSets.add(igraceSet);
        dataSets.add(darilaSet);
        dataSets.add(prostCasSet);

        LineData graphData = new LineData(dataSets);

        chart.setData(graphData);
        chart.invalidate();
    }
}
