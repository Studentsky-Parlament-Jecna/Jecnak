package eu.dotteex.jecnak.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import eu.dotteex.jecnak.R;
import eu.dotteex.jecnak.activities.MainActivity;
import eu.dotteex.jecnak.adapters.BoxAdapter;
import eu.dotteex.jecnak.adapters.GradeAdapter;
import eu.dotteex.jecnak.databinding.FragmentGradesBinding;
import eu.dotteex.jecnak.models.Grade;
import eu.dotteex.jecnak.models.Subject;

public class GradesFragment extends Fragment {

    private FragmentGradesBinding binding;
    private RecyclerView subjectsRecyclerView;
    private RecyclerView historyRecyclerView;
    private ArrayList<Subject> subjects;
    private ArrayList<Grade> grades;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Subject> subjectList = null;

        subjects = ((MainActivity) getActivity()).getControlers().getGradeController().getSubjects();
        grades = ((MainActivity) getActivity()).getControlers().getGradeController().getGrades();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = FragmentGradesBinding.inflate(inflater, container, false).getRoot();

        historyRecyclerView = view.findViewById(R.id.recyclerViewHistory);
        historyRecyclerView.setHasFixedSize(true);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        historyRecyclerView.setAdapter(new GradeAdapter(view.getContext(), grades));

        subjectsRecyclerView = view.findViewById(R.id.recyclerViewSubjects);
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

}