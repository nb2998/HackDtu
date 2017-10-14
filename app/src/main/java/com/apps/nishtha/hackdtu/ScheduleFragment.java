package com.apps.nishtha.hackdtu;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.apps.nishtha.hackdtu.database.AbstractDBAdapter;
import com.apps.nishtha.hackdtu.models.Medicine;
import com.apps.nishtha.hackdtu.models.Medicine.MedicineSchedule;
import com.apps.nishtha.hackdtu.models.Medicine.Time;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends AppCompatActivity implements View.OnClickListener {

    TextView tvDate,tvMedLeft,tvTotalMed;
    RecyclerView recViewSchedule;
    ArrayList<MedicineSchedule> medicineScheduleArrayList;
    ArrayList<Time> completedTimes;
    AbstractDBAdapter abstractDBAdapter;

//    public ScheduleFragment() {
//        // Required empty public constructor
//    }


    public ScheduleFragment() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_schedule);
        tvDate= (TextView) findViewById(R.id.tvDate);
        recViewSchedule= (RecyclerView) findViewById(R.id.recViewSchedule);
        abstractDBAdapter=new AbstractDBAdapter(this);
        Medicine medicine = (Medicine) getIntent().getSerializableExtra("data");

        recViewSchedule.setLayoutManager(new LinearLayoutManager(this));

       // medicineScheduleArrayList = abstractDBAdapter.getScheduleOfMedicine()


        Calendar c=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd+MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        medicineScheduleArrayList = abstractDBAdapter.getScheduleOfMedicine(medicine.getId(),formattedDate);
        completedTimes = abstractDBAdapter.getTimesByMedicine(medicine.getId());

//        recViewSchedule.setAdapter(new ScheduleAdapter(medicineScheduleArrayList,completedTimes,getBaseContext());
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnPieChart){
            Intent intent=new Intent(this,PieChart.class);
            intent.putExtra("a", medicineScheduleArrayList.size());
            intent.putExtra("b", completedTimes.size());
            startActivity(intent);
        }
    }
}
