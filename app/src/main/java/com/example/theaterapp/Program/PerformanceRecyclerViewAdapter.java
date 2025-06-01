package com.example.theaterapp.Program;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.theaterapp.R;
import com.example.theaterapp.dao.PerformanceDAO;
import com.example.theaterapp.domain.Performance;
import com.example.theaterapp.memoryDAO.PerformanceDAOmemory;

import java.util.List;


public class PerformanceRecyclerViewAdapter extends RecyclerView.Adapter<PerformanceRecyclerViewAdapter.ViewHolder>{

    private final List<Performance> performances;

    private final PerformanceDAO performanceDAO = new PerformanceDAOmemory();
    private final PerformanceListener listener;

    public PerformanceRecyclerViewAdapter(List<Performance> performances, PerformanceListener listener) {
        this.performances = performances;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_program_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Performance performance = performances.get(position);

        String name = performance.getName();
        holder.name.setText(name);

        String result = String.join(", ", performance.getPerformanceDays());
        holder.days.setText(result);

        String time = "18:00, 21:00";
        holder.time.setText(time);

        String duration = performance.getPerformanceTime();
        holder.duration.setText(duration);

        String room = "Room " + performance.getRoomID();
        holder.room.setText(room);

        String type = performance.getPerformanceType();
        holder.type.setText(type);

        if (position == 1) {

            holder.image.setImageResource(R.drawable.jurors);
        }
    }

    @Override
    public int getItemCount() {
        return performances.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView name;
        public final TextView days;

        public final TextView time;
        public final TextView duration;

        public final TextView room;
        public final TextView type;
//
        public final ImageView image;
        public ViewHolder(View v) {
            super(v);
            this.name = (TextView) v.findViewById(R.id.PerformanceName);
            this.days = (TextView) v.findViewById(R.id.Days);
            this.time = (TextView) v.findViewById(R.id.Time);
            this.duration = (TextView) v.findViewById(R.id.Duration);
            this.room = (TextView) v.findViewById(R.id.TheaterRoom);
            this.type = (TextView) v.findViewById(R.id.PerformanceType);
            this.image= (ImageView) v.findViewById(R.id.PerPhoto);
        }
    }

    public interface PerformanceListener {

    }
}

