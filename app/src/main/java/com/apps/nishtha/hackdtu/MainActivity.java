package com.apps.nishtha.hackdtu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnGetSchedule, btnSetSchedule,btnGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetSchedule= (Button) findViewById(R.id.btnGetSchedule);
        btnSetSchedule= (Button) findViewById(R.id.btnSetSchecule);
        btnGraph=(Button) findViewById(R.id.btnGraph);
        btnSetSchedule.setOnClickListener(this);
        btnGetSchedule.setOnClickListener(this);
        btnGraph.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnGetSchedule){


        }
        if(view.getId()==R.id.btnSetSchecule){
            Intent i=new Intent(MainActivity.this,AddMedicineActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        if(view.getId()==R.id.btnGraph){
            Intent intent=new Intent(MainActivity.this, GraphActivity.class);
            startActivity(intent);
        }
    }
}
