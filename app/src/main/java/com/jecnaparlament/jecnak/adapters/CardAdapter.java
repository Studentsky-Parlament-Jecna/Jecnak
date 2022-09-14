package com.jecnaparlament.jecnak.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jecnaparlament.jecnak.enums.CardType;
import com.jecnaparlament.jecnak.R;
import com.jecnaparlament.jecnak.models.Card;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    Context context;
    private final Card[] data;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView subtitle;
        private final TextView title;
        private final TextView content;
        private final TextView footer;
        private final LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            subtitle = (TextView) view.findViewById(R.id.subtitle);
            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.content);
            footer = (TextView) view.findViewById(R.id.footer);
            linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        }

    }

    public CardAdapter(Context context, Card[] data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if(data[position].getType() == CardType.GRADE) {
            String grade = data[position].getContent();
            switch(grade) {
                case "1":
                    viewHolder.linearLayout.setBackgroundColor(context.getColor(R.color.grade_1));
                    break;
                case "2":
                    viewHolder.linearLayout.setBackgroundColor(context.getColor(R.color.grade_2));
                    break;
                case "3":
                    viewHolder.linearLayout.setBackgroundColor(context.getColor(R.color.grade_3));
                    break;
                case "4":
                    viewHolder.linearLayout.setBackgroundColor(context.getColor(R.color.grade_4));
                    break;
                case "5":
                    viewHolder.linearLayout.setBackgroundColor(context.getColor(R.color.grade_5));
                    break;
                case "N":
                    viewHolder.linearLayout.setBackgroundColor(context.getColor(R.color.gray));
                    break;
                case "U":
                    viewHolder.linearLayout.setBackgroundColor(context.getColor(R.color.gray));
                    break;
            }

            viewHolder.subtitle.setText(R.string.new_grade);
            viewHolder.title.setText(data[position].getTitle());
            viewHolder.content.setTextSize(40);
            viewHolder.content.setTypeface(viewHolder.content.getTypeface(), Typeface.BOLD);
            viewHolder.content.setText(data[position].getContent());
            if(data[position].getFooter().equals("")) {
                viewHolder.footer.setVisibility(View.GONE);
            }else {
                viewHolder.footer.setText(data[position].getFooter());
            }

        }else if(data[position].getType() == CardType.NEWS) {

            viewHolder.linearLayout.setBackgroundColor(context.getColor(R.color.light));
            viewHolder.subtitle.setText(R.string.new_news);
            viewHolder.subtitle.setTextColor(context.getColor(R.color.darkGray));
            viewHolder.title.setText(data[position].getTitle());
            viewHolder.title.setTextColor(context.getColor(R.color.black));
            viewHolder.content.setText(data[position].getContent());
            viewHolder.content.setTextColor(context.getColor(R.color.darkGray));
            viewHolder.content.setTextSize(18);
            viewHolder.content.setTypeface(viewHolder.content.getTypeface(), Typeface.NORMAL);
            viewHolder.footer.setText(data[position].getFooter());
            viewHolder.footer.setTextColor(context.getColor(R.color.gray));

        }else if(data[position].getType() == CardType.RECORD) {

            viewHolder.linearLayout.setBackgroundColor(context.getColor(R.color.light));
            viewHolder.subtitle.setVisibility(View.GONE);
            viewHolder.title.setText(R.string.new_parents_news);
            viewHolder.title.setTextColor(context.getColor(R.color.black));
            viewHolder.content.setText(data[position].getContent());
            viewHolder.content.setTextColor(context.getColor(R.color.darkGray));
            viewHolder.content.setTextSize(20);
            viewHolder.content.setTypeface(viewHolder.content.getTypeface(), Typeface.NORMAL);
            viewHolder.footer.setText(data[position].getFooter());
            viewHolder.footer.setTextColor(context.getColor(R.color.gray));
        }
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
