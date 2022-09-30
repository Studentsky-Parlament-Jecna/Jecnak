package com.jecnaparlament.jecnak.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jecnaparlament.jecnak.R;
import com.jecnaparlament.jecnak.activities.MainActivity;
import com.jecnaparlament.jecnak.activities.SubjectActivity;
import com.jecnaparlament.jecnak.models.Subject;

public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.ViewHolder>{
    private final Context context;
    private final Subject[] data;

    public BoxAdapter(Context context, Subject[] data) {
        this.context = context;
        this.data = data;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView grade;
        private final TextView subject;
        private final CardView card;

        public ViewHolder(View view) {
            super(view);
            subject = (TextView) view.findViewById(R.id.box_subject);
            grade = (TextView) view.findViewById(R.id.box_grade);
            card = (CardView) view.findViewById(R.id.box_sub_grade);
        }

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.subject_grade_box, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.grade.setText(String.valueOf(data[position].getGradeAvg()));
        viewHolder.subject.setText(data[position].getAbbr());


        switch(String.valueOf(Math.round(data[position].getGradeAvg()))){
            case "1":
                viewHolder.card.setCardBackgroundColor(context.getColor(R.color.grade_1));
                break;
            case "2":
                viewHolder.card.setCardBackgroundColor(context.getColor(R.color.grade_2));
                break;
            case "3":
                viewHolder.card.setCardBackgroundColor(context.getColor(R.color.grade_3));
                break;
            case "4":
                viewHolder.card.setCardBackgroundColor(context.getColor(R.color.grade_4));
                break;
            case "5":
                viewHolder.card.setCardBackgroundColor(context.getColor(R.color.grade_5));
                break;
            default:
                viewHolder.card.setCardBackgroundColor(context.getColor(R.color.white));
                break;
        }
        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SubjectActivity.class);
                intent.putExtra("subject", data[viewHolder.getBindingAdapterPosition()].getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

}
