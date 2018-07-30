package com.example.shad0w.tmdb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoHolder extends RecyclerView.ViewHolder {

   View item;
   ImageView poster;
   TextView Title;
   TextView Type;

    public VideoHolder(View itemView) {
        super(itemView);
        this.item=itemView;
        this.poster=itemView.findViewById(R.id.posterVideo);
        this.Title=itemView.findViewById(R.id.title);
        this.Type=itemView.findViewById(R.id.type);
    }
}
