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

import java.util.ArrayList;

import com.jecnaparlament.jecnak.R;
import com.jecnaparlament.jecnak.activities.MainActivity;
import com.jecnaparlament.jecnak.adapters.BoxAdapter;
import com.jecnaparlament.jecnak.adapters.GradeAdapter;
import com.jecnaparlament.jecnak.databinding.FragmentGradesBinding;
import com.jecnaparlament.jecnak.models.Grade;
import com.jecnaparlament.jecnak.models.Subject;

public class GradesFragment extends Fragment {

    private FragmentGradesBinding binding;
    private ArrayList<Subject> subjects;
    private ArrayList<Grade> grades;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Subject> subjectList = null;

        subjects = MainActivity.controllers.getGradeController().getSubjects();
        grades = MainActivity.controllers.getGradeController().getGrades();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = FragmentGradesBinding.inflate(inflater, container, false).getRoot();

        RecyclerView historyRecyclerView = view.findViewById(R.id.recyclerViewHistory);
        historyRecyclerView.setHasFixedSize(true);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        historyRecyclerView.setAdapter(new GradeAdapter(view.getContext(), grades));

        RecyclerView subjectsRecyclerView = view.findViewById(R.id.recyclerViewSubjects);
        subjectsRecyclerView.setHasFixedSize(true);
        subjectsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        subjectsRecyclerView.setAdapter(new BoxAdapter(view.getContext(), subjects.toArray(new Subject[subjects.size()])));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.default_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


}