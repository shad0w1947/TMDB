package com.example.shad0w.tmdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LargeAdapter extends RecyclerView.Adapter<NowShowingHolder> {

    ArrayList<MovieResult> MovieResultArrayList;
    ArrayList<TvResult> TvResultArrayList;
    LayoutInflater inflater;
    Clicklistener clicklistener;
    boolean size;
    int type;
    GenreHas map;


    public LargeAdapter(Context context, ArrayList<MovieResult> MovieResults, ArrayList<TvResult> TvResults, boolean size, int type, Clicklistener clicklistener) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.MovieResultArrayList = MovieResults;
        this.TvResultArrayList = TvResults;
        this.type = type;

        this.clicklistener = clicklistener;
        this.size = size;
    }

    @NonNull
    @Override
    public NowShowingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        map = new GenreHas();
        View output;
        if (size) {
            if (type == Contact.Person)
                output = inflater.inflate(R.layout.person_view, viewGroup, false);
            else
                output = inflater.inflate(R.layout.small_view, viewGroup, false);
        } else
            output = inflater.inflate(R.layout.large_view, viewGroup, false);
        return new NowShowingHolder(output);
    }

    @Override
    public void onBindViewHolder(@NonNull NowShowingHolder nowShowingHolder, final int i) {
        Log.i("adapter", "called");
        if (type == Contact.Movie) {
            MovieResult result = MovieResultArrayList.get(i);
            String genre = "";
            for (int j = 0; j < result.getGenreIds().size(); j++) {
                if (j == result.getGenreIds().size() - 1)
                    genre = genre + map.getString(result.getGenreIds().get(j));
                else
                    genre = genre + map.getString(result.getGenreIds().get(j)) + ",";
            }
            nowShowingHolder.Genre.setText(genre);
            nowShowingHolder.Name.setText(result.getOriginalTitle());
            nowShowingHolder.Rating.setText(result.getVoteAverage() + "");
            Picasso.get().load(String.valueOf("https://image.tmdb.org/t/p/w500" + result.getBackdropPath()))
                    .fit().placeholder(R.color.lightgray).into(nowShowingHolder.poster);
            nowShowingHolder.poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clicklistener.itemClick(view, i);
                }
            });
        } else {
            TvResult result = TvResultArrayList.get(i);

            if (type != Contact.Person) {
                String genre = "";
                for (int j = 0; j < result.getGenreIds().size(); j++) {
                    if (j == result.getGenreIds().size() - 1)
                        genre = genre + map.getString(result.getGenreIds().get(j));
                    else
                        genre = genre + map.getString(result.getGenreIds().get(j)) + ",";
                }
                nowShowingHolder.Genre.setText(genre);
                Picasso.get().load(String.valueOf("https://image.tmdb.org/t/p/w300" + result.getBackdropPath()))
                        .fit().placeholder(R.color.lightgray).into(nowShowingHolder.poster);
            } else {
                Picasso.get().load(String.valueOf("https://image.tmdb.org/t/p/w300" + result.getProfile_path()))
                        .fit().placeholder(R.color.lightgray).into(nowShowingHolder.poster);

            }
            nowShowingHolder.Name.setText(result.getName());
            nowShowingHolder.Rating.setText(result.getVoteAverage() + "");
            nowShowingHolder.poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clicklistener.itemClick(view, i);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        int length = 0;
        if (TvResultArrayList != null && TvResultArrayList.size() > 0)
            length = TvResultArrayList.size();
        if (MovieResultArrayList != null && MovieResultArrayList.size() > 0)
            length = MovieResultArrayList.size();
        return length;
    }
}
