package com.example.sportlight;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    private final List<SportEvent> sportList;

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener itemClickListener = null;

    public Adapter(List<SportEvent> sport) {
        this.sportList = sport;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        public TextView sport, time;
        public ImageView icon;
        public TextView location;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            sport = itemView.findViewById(R.id.sport);
            time = itemView.findViewById(R.id.time);
            icon = itemView.findViewById(R.id.icon);
            location = itemView.findViewById(R.id.location);
        }
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item,parent,false);

        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        SportEvent sport = sportList.get(position);
        holder.sport.setText(sport.getSport());
        holder.time.setText(sport.getStartAt());
        holder.location.setText(sport.getPosition());
        switch(sport.getSport()) {
            case "游泳":
                holder.icon.setImageResource(R.drawable.swimming);
                break;
            case "健走":
                holder.icon.setImageResource(R.drawable.walking);
                break;
            case "爬山":
                holder.icon.setImageResource(R.drawable.mountain);
                break;
            case "騎腳踏車":
                holder.icon.setImageResource(R.drawable.biking);
                break;
            case "跑步":
                holder.icon.setImageResource(R.drawable.running);
                break;
            case "羽球":
                holder.icon.setImageResource(R.drawable.badminton);
                break;
            case "桌球":
                holder.icon.setImageResource(R.drawable.tabletennis);
                break;
            case "太極拳":
                holder.icon.setImageResource(R.drawable.taichichuan);
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(holder.itemView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sportList.size();
    }

}
