package com.jecnaparlament.jecnak.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jecnaparlament.jecnak.R;
import com.jecnaparlament.jecnak.adapters.GradeAdapter;
import com.jecnaparlament.jecnak.databinding.FragmentGradesBinding;
import com.jecnaparlament.jecnak.models.Grade;
import com.jecnaparlament.jecnak.models.Subject;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity {
    private ArrayList<Grade> grades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        String subjectName = getIntent().getStringExtra("subject");
        setTitle(subjectName);
        ArrayList<Subject> subjects = MainActivity.controllers.getGradeController().getSubjects();

        for (Subject subject : subjects) {
            if (subject.getName().equals(subjectName)) {
                grades = subject.getGradesToArray();
            }
        }

        RecyclerView history = findViewById(R.id.recyclerViewSubjectGrades);
        history.setHasFixedSize(true);
        history.setLayoutManager(new LinearLayoutManager(this));
        history.setAdapter(new GradeAdapter(this, grades));
    }

}