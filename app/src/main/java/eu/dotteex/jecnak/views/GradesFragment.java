package eu.dotteex.jecnak.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import eu.dotteex.jecnak.MainActivity;
import eu.dotteex.jecnak.R;
import eu.dotteex.jecnak.adapters.BoxAdapter;
import eu.dotteex.jecnak.adapters.SubjectAdapter;
import eu.dotteex.jecnak.databinding.FragmentGradesBinding;
import eu.dotteex.jecnak.models.Subject;

public class GradesFragment extends Fragment {

    private FragmentGradesBinding binding;
    private RecyclerView subjectsRecyclerView;
    private RecyclerView historyRecyclerView;
    private Subject[] subjects;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Subject> subjectList = null;

        subjectList = ((MainActivity) getActivity()).getControlers().getGradeController().getSubjects();
        subjects = new Subject[subjectList.size()];
        int i = 0;
        for(Subject subject : subjectList) {
            subjects[i] = subject;
            i++;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = FragmentGradesBinding.inflate(inflater, container, false).getRoot();

        historyRecyclerView = view.findViewById(R.id.recyclerViewHistory);
        historyRecyclerView.setHasFixedSize(true);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        historyRecyclerView.setAdapter(new SubjectAdapter(view.getContext(), subjects));

        subjectsRecyclerView = view.findViewById(R.id.recyclerViewSubjects);
        subjectsRecyclerView.setHasFixedSize(true);
        subjectsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        subjectsRecyclerView.setAdapter(new BoxAdapter(view.getContext(), subjects));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}