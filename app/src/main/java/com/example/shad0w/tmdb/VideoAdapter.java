package com.example.shad0w.tmdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoHolder> {

    ArrayList<VideoResult> videoResultArrayList;
    LayoutInflater inflater;
    Clicklistener clicklistener;

    public VideoAdapter(Context context, ArrayList<VideoResult> videoResultArrayList, Clicklistener clicklistener) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.videoResultArrayList = videoResultArrayList;
        this.clicklistener = clicklistener;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View output = inflater.inflate(R.layout.video_view, parent, false);
        return new VideoHolder(output);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, final int position) {
        VideoResult result = videoResultArrayList.get(position);
        holder.Type.setText(result.getType());
        holder.Title.setText(result.getName());
        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicklistener.itemClick(v, position);
            }
        });
        Picasso.get().load(String.valueOf("http://img.youtube.com/vi/" + result.getKey() + "/maxresdefault.jpg\n"))
                .fit().placeholder(R.color.lightgray).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return videoResultArrayList.size();
    }
}
