package com.jecnaparlament.jecnak.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.jecnaparlament.jecnak.R;
import com.jecnaparlament.jecnak.activities.MainActivity;
import com.jecnaparlament.jecnak.adapters.AttendanceAdapter;
import com.jecnaparlament.jecnak.databinding.FragmentAttendanceBinding;
import com.jecnaparlament.jecnak.models.Attendance;

public class AttendanceFragment extends Fragment {

    private FragmentAttendanceBinding binding;
    private RecyclerView recyclerView;
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
        attendanceArrayList = ((MainActivity) getActivity()).getControlers().getAbsenceController().getAttendace();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = FragmentAttendanceBinding.inflate(inflater, container, false).getRoot();


        recyclerView = view.findViewById(R.id.recyclerViewAttendance);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new AttendanceAdapter(view.getContext(), attendanceArrayList));
        return view;
    }
}