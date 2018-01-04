package com.sminarska.emp.personalassistant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;

/**
 * Created by anei on 04/01/2018.
 */

public class StatActivity extends AppCompatActivity {

    public LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        // get the chart view
        chart = (LineChart) findViewById(R.id.line_chart);
    }
}
