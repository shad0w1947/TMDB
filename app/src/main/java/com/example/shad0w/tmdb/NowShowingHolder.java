package com.example.shad0w.tmdb;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NowShowingHolder extends RecyclerView.ViewHolder{
    View item;
    ImageView poster;
    TextView Name;
    TextView Rating;
    TextView Genre;
    ImageView likeButton;


    public NowShowingHolder(@NonNull View itemView) {
        super(itemView);
        poster=itemView.findViewById(R.id.movie_fragment_poster);
        Name=itemView.findViewById(R.id.movie_fragment_name);
        Rating=itemView.findViewById(R.id.movie_fragment_rating);
        Genre=itemView.findViewById(R.id.movie_fragment_genre);
        item=itemView;
        likeButton=itemView.findViewById(R.id.like_button);
    }
}
