package com.example.shad0w.tmdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfiniteLoding extends AppCompatActivity {


    Toolbar toolbar;
    ArrayList<MovieResult> arrayListMovie;
    ArrayList<TvResult> arrayListTv;
    LargeAdapter adapter;
    RecyclerView recyclerView;
    int page = 1;
    Intent intent;
    String baseUrl;
    int type;
    int viewItem, totalItem, pastVisibleItems;
    boolean isScroll;
    Clicklistener clicklistener;
    String Category;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infinite_loding);
        toolbar=findViewById(R.id.toolbar);



        intent = getIntent();
        baseUrl = intent.getStringExtra("baseurl");
        name=intent.getStringExtra(Contact.Name);
        toolbar.setTitle(name);
        type = intent.getIntExtra(Contact.TYPE, 2);
        Category = intent.getStringExtra(Contact.Category);
        recyclerView = findViewById(R.id.infiniteLoding_recycler);
        arrayListMovie = new ArrayList<>();
        arrayListTv = new ArrayList<>();
        setSupportActionBar(toolbar);
        if (type == Contact.Movie) {
            clicklistener = new Clicklistener() {
                @Override
                public void itemClick(View view, int position) {
                    MovieResult result = arrayListMovie.get(position);
                    detailActivity(result.getId(),result.getTitle());
                }
            };
            adapter = new LargeAdapter(this, arrayListMovie, arrayListTv, Contact.Smallview, type, clicklistener);
        } else {
            clicklistener = new Clicklistener() {
                @Override
                public void itemClick(View view, int position) {
                    TvResult result = arrayListTv.get(position);
                    detailActivity(result.getId(),result.getName());
                }
            };
            adapter = new LargeAdapter(this, arrayListMovie, arrayListTv, Contact.Smallview, type, clicklistener);

        }

        recyclerView.setAdapter(adapter);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, 1);
        recyclerView.setLayoutManager(layoutManager);


        connect(baseUrl, Category);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScroll = true;
                Log.d("tag1", "LOAD NEXT ITEM2");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                viewItem = layoutManager.getChildCount();
                int[] firstVisibleItems = null;
                firstVisibleItems = layoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                totalItem = layoutManager.getItemCount();
                if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                    pastVisibleItems = firstVisibleItems[0];
                }

                if (isScroll) {
                    if ((viewItem + pastVisibleItems) >= totalItem) {
                        isScroll = false;
                        page++;
                        //  progressBar.setVisibility(View.VISIBLE);
                        connect(baseUrl, Category);
                        Log.d("tag1", "LOAD NEXT ITEM111");
                    }
                }
            }
        });
    }

    private void detailActivity(Long id,String name) {
        Intent intent = new Intent(this, DetailView.class);
        intent.putExtra("id", id);
        intent.putExtra(Contact.BaseUrl, baseUrl);
        intent.putExtra(Contact.Name,name);
        intent.putExtra(Contact.TYPE,type);
        startActivity(intent);
    }

    private void connect(String baseUrl, String Catagory) {

        if (type == Contact.Movie) {

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();
            Retroservice retroservice = retrofit.create(Retroservice.class);
            Call<MoviePojo> call;

            if (Catagory.equals(Contact.NowShowingMovie))
                call = retroservice.getNowShowingMovie(API.getApi(), Contact.Language,page);
            else if (Catagory.equals(Contact.PopularMovie))
                call = retroservice.getPopularMovie(API.getApi(),Contact.Language, page);
            else if (Catagory.equals(Contact.PopularMovie))
                call = retroservice.getPopularMovie(API.getApi(),Contact.Language, page);
            else
                call = retroservice.getUpcomingMovie(API.getApi(),Contact.Language, page);

            call.enqueue(new Callback<MoviePojo>() {
                @Override
                public void onResponse(Call<MoviePojo> call, Response<MoviePojo> response) {
                    MoviePojo nowShowingMovie = response.body();
                    arrayListMovie.addAll(nowShowingMovie.getResults());
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<MoviePojo> call, Throwable t) {

                }
            });
        } else {

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();
            Retroservice retroservice = retrofit.create(Retroservice.class);
            Call<TvPojo> call;

            if (Catagory.equals(Contact.AiringTodayTvshow))
                call = retroservice.getAiringTodayTv(API.getApi(), page);
            else if (Catagory.equals(Contact.PopularMovie))
                call = retroservice.getPopularTv(API.getApi(),Contact.Language, page);
            else if (Catagory.equals(Contact.TopratedTvShow))
                call = retroservice.getTopratedTv(API.getApi(),Contact.Language, page);
            else
                call = retroservice.getOnairTv(API.getApi(),Contact.Language, page);

            call.enqueue(new Callback<TvPojo>() {
                @Override
                public void onResponse(Call<TvPojo> call, Response<TvPojo> response) {
                    TvPojo nowShowingMovie = response.body();
                    arrayListTv.addAll(nowShowingMovie.getResults());
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<TvPojo> call, Throwable t) {

                }
            });

        }

    }
}
