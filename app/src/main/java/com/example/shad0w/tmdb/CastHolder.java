package com.example.shad0w.tmdb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CastHolder extends RecyclerView.ViewHolder {
    View item;
    ImageView imageView;
    TextView name;
    TextView role;

    public CastHolder(View itemView) {
        super(itemView);
        this.item=itemView;
        this.imageView=itemView.findViewById(R.id.image);
        this.name=itemView.findViewById(R.id.name);
        this.role=itemView.findViewById(R.id.character);
    }


}
