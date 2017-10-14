package com.apps.nishtha.hackdtu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphActivity extends AppCompatActivity {

    GraphView graph1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        graph1 = (GraphView) findViewById(R.id.graph);
        DataPoint dp[];
        dp=new DataPoint[4];
        dp[0]=new DataPoint(1,0);
        dp[1]=new DataPoint(2,0);
        dp[2]=new DataPoint(3,2);
        dp[3]=new DataPoint(4,1);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(6);
        graph1.addSeries(series);
    }
}
