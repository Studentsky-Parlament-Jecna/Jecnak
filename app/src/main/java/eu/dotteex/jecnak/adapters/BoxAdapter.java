package eu.dotteex.jecnak.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import eu.dotteex.jecnak.R;
import eu.dotteex.jecnak.models.Subject;

public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.ViewHolder>{
    private Context context;
    private Subject[] data;

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
            grade = (TextView) view.findViewById(R.id.box_grade);
            subject = (TextView) view.findViewById(R.id.box_subject);
            card = (CardView) view.findViewById(R.id.box_sub_grade);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.subject_grade_box, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.grade.setText(data[position].getFinalGrade());
        viewHolder.subject.setText(data[position].getName());

/*        switch (Integer.getInteger(data[position].getFinalGrade())){
            case 1:
                holder.card.setCardBackgroundColor(context.getColor(R.color.grade_1));
                break;
            case 2:
                holder.card.setCardBackgroundColor(context.getColor(R.color.grade_2));
                break;
            case 3:
                holder.card.setCardBackgroundColor(context.getColor(R.color.grade_3));
                break;
            case 4:
                holder.card.setCardBackgroundColor(context.getColor(R.color.grade_4));
                break;
            case 5:
                holder.card.setCardBackgroundColor(context.getColor(R.color.grade_5));
                break;
        }*/
        switch(data[position].getFinalGrade()){
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
            case "N":
                viewHolder.card.setCardBackgroundColor(context.getColor(R.color.gray));
                break;
            case "U":
                viewHolder.card.setCardBackgroundColor(context.getColor(R.color.gray));
                break;
        }


    }

    @Override
    public int getItemCount() {
        return data.length;
    }

}
