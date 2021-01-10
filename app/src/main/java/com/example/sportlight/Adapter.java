package com.example.sportlight;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    private final List<SportEvent> sportList;

    public Adapter(List<SportEvent> sport) {
        this.sportList = sport;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        public TextView sport, time;
        public ImageView icon;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            sport = itemView.findViewById(R.id.sport);
            time = itemView.findViewById(R.id.time);
            icon = itemView.findViewById(R.id.icon);
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
        holder.time.setText(sport.getStartAt() + "\n" + sport.getFounder());
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
                Intent intent = new Intent();
                intent.setClass(v.getContext(), EventInfo.class);
                intent.putExtra("info", sportList.get(position));
                startActivityForResult(intent, 8081);
            }
        });
    }

    private void startActivityForResult(Intent intent, int i) {

    }

    @Override
    public int getItemCount() {
        return sportList.size();
    }

}
