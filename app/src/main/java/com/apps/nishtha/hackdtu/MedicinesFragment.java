package com.apps.nishtha.hackdtu;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MedicinesFragment extends AppCompatActivity implements View.OnClickListener{
    FloatingActionButton fabAddMedicine;
    RecyclerView recViewMedicines;
    MedicinesAdapter medicinesAdapter;
    ArrayList<com.apps.nishtha.hackdtu.models.Medicine> medicineArrayList;
    com.apps.nishtha.hackdtu.database.AbstractDBAdapter abstractDBAdapter;
    Button btnPieChart;
    public MedicinesFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_medicines);

        fabAddMedicine = (FloatingActionButton) findViewById(R.id.fabAddMedicine);
        recViewMedicines= (RecyclerView) findViewById(R.id.recViewMedicines);
        btnPieChart= (Button) findViewById(R.id.btnPieChart);
        abstractDBAdapter=new com.apps.nishtha.hackdtu.database.AbstractDBAdapter(MedicinesFragment.this);

        medicineArrayList=new ArrayList<>();


        medicinesAdapter=new MedicinesAdapter(MedicinesFragment.this,medicineArrayList);
        recViewMedicines.setLayoutManager(new LinearLayoutManager(MedicinesFragment.this));
        recViewMedicines.setAdapter(medicinesAdapter);

        medicineArrayList.addAll(abstractDBAdapter.getAllMedicines());
        medicinesAdapter.notifyDataSetChanged();
        fabAddMedicine.setOnClickListener(this);
//        btnPieChart.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.fabAddMedicine){
            Intent intent=new Intent(MedicinesFragment.this,AddMedicineActivity.class);
            startActivity(intent);

        }
    }
}
