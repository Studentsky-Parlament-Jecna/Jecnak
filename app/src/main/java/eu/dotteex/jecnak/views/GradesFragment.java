package eu.dotteex.jecnak.views;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import eu.dotteex.jecnak.LoginActivity;
import eu.dotteex.jecnak.R;
import eu.dotteex.jecnak.adapters.SubjectAdapter;
import eu.dotteex.jecnak.controllers.GradeController;
import eu.dotteex.jecnak.databinding.FragmentGradesBinding;
import eu.dotteex.jecnak.models.Connect;
import eu.dotteex.jecnak.models.Subject;

public class GradesFragment extends Fragment {

    private FragmentGradesBinding binding;
    private RecyclerView recyclerView;
    private Subject[] subjects;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Subject> subjectList = null;
        try {
            subjectList = new GetGrades().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        subjects = new Subject[subjectList.size()];
        int i = 0;
        for(Subject subject : subjectList) {
            subjects[i] = subject;
            i++;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = FragmentGradesBinding.inflate(inflater, container, false).getRoot();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new SubjectAdapter(view.getContext(), subjects));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class GetGrades extends AsyncTask<Void, Void, ArrayList<Subject>> {
        @Override
        protected ArrayList<Subject> doInBackground(Void... arg0) {
            SharedPreferences sh = getActivity().getSharedPreferences("login", MODE_PRIVATE);
            String user = sh.getString("user", "");
            String password = sh.getString("pass", "");
            Connect connect = null;
            try {
                connect = new Connect(user, password);
            } catch (Exception e) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user", null);
                editor.putString("pass", null);
                editor.commit();

                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
            GradeController gc = new GradeController(connect);

            return gc.getSubjects();
        }
    }
}