package com.example.shad0w.tmdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastHolder> {
    ArrayList<CastResult> arrayList;
    LayoutInflater inflater;
    Clicklistener clicklistener;

   public CastAdapter(Context context,ArrayList<CastResult> arrayList,Clicklistener clicklistener){
       inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
       this.arrayList=arrayList;
       this.clicklistener=clicklistener;
   }

    @NonNull
    @Override
    public CastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View output=inflater.inflate(R.layout.cast_view,parent,false);
        return new CastHolder(output);
    }

    @Override
    public void onBindViewHolder(@NonNull CastHolder holder, final int position) {

        CastResult cast=arrayList.get(position);

       holder.name.setText(cast.getName());
       holder.role.setText(cast.getCharacter());
        Picasso.get().load(String.valueOf("https://image.tmdb.org/t/p/w300" + cast.getProfilePath()))
                .fit().placeholder(R.color.lightgray).into(holder.imageView);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicklistener.itemClick(v,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
