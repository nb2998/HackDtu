package com.apps.nishtha.hackdtu;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainScheduleFragment extends AppCompatActivity {

    RecyclerView recViewMainSchedule;


    public MainScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recViewMainSchedule= (RecyclerView) findViewById(R.id.recViewMainSchedule);
        recViewMainSchedule.setLayoutManager(new LinearLayoutManager(MainScheduleFragment.this));
    }
}
