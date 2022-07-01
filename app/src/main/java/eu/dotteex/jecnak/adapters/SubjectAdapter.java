package eu.dotteex.jecnak.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import eu.dotteex.jecnak.R;
import eu.dotteex.jecnak.models.Subject;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    Context context;
    private Subject[] data;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final RecyclerView recyclerView;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        }

    }

    public SubjectAdapter(Context context, Subject[] data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.subject_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.title.setText(data[position].getName()+" (průměr: "+data[position].getGradeAvg()+")");
        viewHolder.recyclerView.setHasFixedSize(true);
        viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        viewHolder.recyclerView.setAdapter(new GradeAdapter(context, data[position].getGradesToArray()));
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
