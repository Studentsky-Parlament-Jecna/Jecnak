package com.jecnaparlament.jecnak.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.jecnaparlament.jecnak.R;
import com.jecnaparlament.jecnak.models.Attendance;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Attendance> attendances;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView date;
        private final TextView arrival;
        private final TextView exit;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.attendance_row_date);
            arrival = itemView.findViewById(R.id.attendance_row_arrival);
            exit = itemView.findViewById(R.id.attendance_row_departure);
        }
    }

    public AttendanceAdapter(Context context, ArrayList<Attendance> attendances) {
        this.context = context;
        this.attendances = attendances;
    }

    @NonNull
    @Override
    public AttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.attendance_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapter.ViewHolder holder, int position) {
        holder.date.setText(attendances.get(position).getDate());
        holder.arrival.setText(attendances.get(position).getArrival());
        holder.exit.setText(attendances.get(position).getExit());

        // TODO: 19.07.2022 - pozdni prichody
        if (attendances.get(position).getHmOfArrival() != 0 && attendances.get(position).getHmOfArrival() > +725){
            holder.arrival.setTextColor(context.getColor(R.color.grade_5));
            holder.arrival.setTypeface(null,Typeface.BOLD);
        }
    }

    @Override
    public int getItemCount() {
        return attendances.size();
    }


}
