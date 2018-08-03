package com.example.shad0w.tmdb;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements View.OnClickListener {


    public TvShowFragment() {
        // Required empty public constructor
    }

    boolean isOnTheAir, isLatest, isToprated, isPopular;

    boolean[] booleans;


    LinearLayout root;
    ProgressBar progressBar;

    TextView ViewallLatest;
    ArrayList<TvResult> arrayListLatest;
    RecyclerView recyclerViewLatest;
    LargeAdapter largeAdapterLatest;
    Clicklistener clicklistenerLatest;

    TextView ViewallOnTheAir;
    ArrayList<TvResult> arrayListOnTheAir;
    RecyclerView recyclerViewOnTheAir;
    LargeAdapter largeAdapterOnTheAir;
    Clicklistener clicklistenerOnTheAir;

    TextView ViewallToprated;
    ArrayList<TvResult> arrayListToprated;
    RecyclerView recyclerViewToprated;
    LargeAdapter largeAdapterToprated;
    Clicklistener clicklistenerToprated;

    TextView ViewallPopular;
    ArrayList<TvResult> arrayListPopular;
    RecyclerView recyclerViewPopular;
    LargeAdapter largeAdapterPopular;
    Clicklistener clicklistenerPopular;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View output = inflater.inflate(R.layout.fragment_tv_show, container, false);

        booleans = new boolean[4];

        root = output.findViewById(R.id.root);

        progressBar = output.findViewById(R.id.progress_bar);


        ViewallLatest = output.findViewById(R.id.tv_fragment_Latest_viewAll);
        ViewallLatest.setOnClickListener(this);
        arrayListLatest = new ArrayList<>();
        recyclerViewLatest = output.findViewById(R.id.tv_fragment_Latest);
        clicklistenerLatest = new Clicklistener() {
            @Override
            public void itemClick(View view, int position) {
                TvResult result = arrayListLatest.get(position);
                detialActivity(result.getId(),result.getName());
            }
        };
        largeAdapterLatest = new LargeAdapter(getContext(), null, arrayListLatest, Contact.Largeview, Contact.Tvshow, clicklistenerLatest);
        recyclerViewLatest.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewLatest.setAdapter(largeAdapterLatest);


        ViewallPopular = output.findViewById(R.id.tv_fragment_Popular_viewAll);
        ViewallPopular.setOnClickListener(this);
        arrayListPopular = new ArrayList<>();
        recyclerViewPopular = output.findViewById(R.id.tv_fragment_Popular);
        clicklistenerPopular = new Clicklistener() {
            @Override
            public void itemClick(View view, int position) {
                TvResult result = arrayListPopular.get(position);
                detialActivity(result.getId(),result.getName());
            }
        };
        largeAdapterPopular = new LargeAdapter(getContext(), null, arrayListPopular, Contact.Largeview, Contact.Tvshow, clicklistenerPopular);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewPopular.setAdapter(largeAdapterPopular);


        ViewallOnTheAir = output.findViewById(R.id.tv_fragment_OnAir_viewAll);
        ViewallOnTheAir.setOnClickListener(this);
        arrayListOnTheAir = new ArrayList<>();
        recyclerViewOnTheAir = output.findViewById(R.id.tv_fragment_OnAir);
        clicklistenerOnTheAir = new Clicklistener() {
            @Override
            public void itemClick(View view, int position) {
                TvResult result = arrayListOnTheAir.get(position);
                detialActivity(result.getId(),result.getName());
            }
        };
        largeAdapterOnTheAir = new LargeAdapter(getContext(), null, arrayListOnTheAir, Contact.Smallview, Contact.Tvshow, clicklistenerOnTheAir);
        recyclerViewOnTheAir.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewOnTheAir.setAdapter(largeAdapterOnTheAir);


        ViewallToprated = output.findViewById(R.id.tv_fragment_Toprated_viewAll);
        ViewallToprated.setOnClickListener(this);
        arrayListToprated = new ArrayList<>();
        recyclerViewToprated = output.findViewById(R.id.tv_fragment_Toprated);
        clicklistenerToprated = new Clicklistener() {
            @Override
            public void itemClick(View view, int position) {
                TvResult result = arrayListToprated.get(position);
                detialActivity(result.getId(),result.getName());
            }
        };
        largeAdapterToprated = new LargeAdapter(getContext(), null, arrayListToprated, Contact.Smallview, Contact.Tvshow, clicklistenerToprated);
        recyclerViewToprated.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewToprated.setAdapter(largeAdapterToprated);


        connect(arrayListLatest, recyclerViewLatest, largeAdapterLatest, 0);
        connect(arrayListPopular, recyclerViewPopular, largeAdapterPopular, 1);
        connect(arrayListToprated, recyclerViewToprated, largeAdapterToprated, 2);
        connect(arrayListOnTheAir, recyclerViewOnTheAir, largeAdapterOnTheAir, 3);

        return output;
    }

    private void detialActivity(Long id,String name) {
        Intent intent = new Intent(getContext(), DetailView.class);
        intent.putExtra(Contact.ID, id);
        intent.putExtra(Contact.Name,name);
        intent.putExtra(Contact.TYPE, Contact.Tvshow);
        intent.putExtra(Contact.BaseUrl, "https://api.themoviedb.org/3/tv/");
        startActivity(intent);
    }

    private void connect(final ArrayList<TvResult> arrayList, RecyclerView recyclerView, final LargeAdapter adapter, final int i) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/tv/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        Retroservice retroservice = retrofit.create(Retroservice.class);
        Call<TvPojo> call;

        if (recyclerView == recyclerViewPopular) {
            call = retroservice.getPopularTv(API.getApi(), Contact.Language,1);
            isPopular = true;
        } else if (recyclerView == recyclerViewToprated) {
            call = retroservice.getTopratedTv(API.getApi(),Contact.Language, 1);
            isToprated = true;
        } else if (recyclerView == recyclerViewOnTheAir) {
            call = retroservice.getOnairTv(API.getApi(),Contact.Language, 1);
            isOnTheAir = true;
        } else {
            call = retroservice.getAiringTodayTv(API.getApi(), 1);
            isLatest = true;
        }
        call.enqueue(new Callback<TvPojo>() {
            @Override
            public void onResponse(Call<TvPojo> call, Response<TvPojo> response) {
                TvPojo nowShowingMovie = response.body();
                arrayList.addAll(nowShowingMovie.getResults());
                adapter.notifyDataSetChanged();
                booleans[i] = true;
                alldatafetch();

            }

            @Override
            public void onFailure(Call<TvPojo> call, Throwable t) {

            }
        });
    }

    private void alldatafetch() {
        if (booleans[0] & booleans[1] & booleans[2] & booleans[3]) {
            progressBar.setVisibility(View.GONE);
            root.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(getContext(), InfiniteLoding.class);
        if (view.getId() == R.id.tv_fragment_Latest_viewAll) {
            intent.putExtra(Contact.Category, Contact.OntheAirTvshow);
            intent.putExtra(Contact.Name, "On The Air Tv Shows");
        }
        if (view.getId() == R.id.tv_fragment_Popular_viewAll) {
            intent.putExtra(Contact.Name, "Popular Tv Shows");
            intent.putExtra(Contact.Category, Contact.PopularTvShow);
        }
        if (view.getId() == R.id.tv_fragment_Toprated_viewAll) {
            intent.putExtra(Contact.Name, "Top Rated Tv Shows");
            intent.putExtra(Contact.Category, Contact.TopratedTvShow);
        }
        if (view.getId() == R.id.tv_fragment_OnAir_viewAll) {
            intent.putExtra(Contact.Name, "Airing Today Tv Shows");
            intent.putExtra(Contact.Category, Contact.AiringTodayTvshow);
        }
        intent.putExtra(Contact.TYPE, Contact.Tvshow);
        intent.putExtra("baseurl", "https://api.themoviedb.org/3/tv/");

        startActivity(intent);
    }
}
