package com.jecnaparlament.jecnak.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.android.material.tabs.TabLayout;
import com.jecnaparlament.jecnak.R;
import com.jecnaparlament.jecnak.activities.MainActivity;
import com.jecnaparlament.jecnak.adapters.AttendanceAdapter;
import com.jecnaparlament.jecnak.databinding.FragmentAttendanceBinding;
import com.jecnaparlament.jecnak.models.Attendance;

public class AttendanceFragment extends Fragment {

    private FragmentAttendanceBinding binding;
    private ArrayList<Attendance> attendanceArrayList;


    public AttendanceFragment() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attendanceArrayList = MainActivity.controllers.getAbsenceController().getAttendace();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = FragmentAttendanceBinding.inflate(inflater, container, false).getRoot();


        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewAttendance);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new AttendanceAdapter(view.getContext(), attendanceArrayList));

        DateFormat dateFormat = new SimpleDateFormat("MM");
        TabLayout tabLayout = view.findViewById(R.id.tabLayout_attendance);

        Date date = new Date();
        int month = Integer.parseInt(dateFormat.format(date));
        // -1 because indexes starts at 0 not 1 like months
        tabLayout.getTabAt(Math.abs(month-7-1)).select();


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.default_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
