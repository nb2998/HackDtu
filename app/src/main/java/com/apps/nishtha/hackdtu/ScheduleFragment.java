package com.apps.nishtha.hackdtu;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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
    Button button;

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
        button= (Button) findViewById(R.id.btnPieChart);
        abstractDBAdapter=new AbstractDBAdapter(this);
        Medicine medicine = (Medicine) getIntent().getSerializableExtra("data");
;
        recViewSchedule.setLayoutManager(new LinearLayoutManager(this));

       // medicineScheduleArrayList = abstractDBAdapter.getScheduleOfMedicine()

        button.setOnClickListener(this);

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

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View v= inflater.inflate(R.layout.fragment_schedule, container, false);
//        tvDate=v.findViewById(R.id.tvDate);
//        recViewSchedule=v.findViewById(R.id.recViewSchedule);
//        abstractDBAdapter=new AbstractDBAdapter(getContext());
//
//        recViewSchedule.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        Calendar c=Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
//        String formattedDate = df.format(c.getTime());
////        medicineScheduleArrayList.addAll(abstractDBAdapter.getScheduleOfMedicine(1,formattedDate));
//
//
//        recViewSchedule.setAdapter(new ScheduleAdapter(medicineScheduleArrayList,completedTimes,getContext()));
//        return v;
//    }

}
