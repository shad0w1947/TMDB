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
public class MovieFragment extends Fragment implements View.OnClickListener {

    boolean isNowShowing, isUpcoming, isToprated, isPopular;

    boolean[] booleans;

    boolean smallview = true;
    boolean largeview = false;

    LinearLayout root;
    ProgressBar progressBar;

    TextView ViewallNowShowing;
    ArrayList<MovieResult> arrayListNowShowing;
    RecyclerView recyclerViewNowShowing;
    LargeAdapter largeAdapterNowShowing;
    Clicklistener clicklistenerNowShowing;

    TextView ViewallUpcoming;
    ArrayList<MovieResult> arrayListUpcoming;
    RecyclerView recyclerViewUpcoming;
    LargeAdapter largeAdapterUpcoming;
    Clicklistener clicklistenerUpcoming;

    TextView ViewallToprated;
    ArrayList<MovieResult> arrayListToprated;
    RecyclerView recyclerViewToprated;
    LargeAdapter largeAdapterToprated;
    Clicklistener clicklistenerToprated;

    TextView ViewallPopular;
    ArrayList<MovieResult> arrayListPopular;
    RecyclerView recyclerViewPopular;
    LargeAdapter largeAdapterPopular;
    Clicklistener clicklistenerPopular;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View output = inflater.inflate(R.layout.fragment_movie, container, false);

        booleans = new boolean[4];

        root = output.findViewById(R.id.root);

        progressBar = output.findViewById(R.id.progress_bar);


        ViewallNowShowing = output.findViewById(R.id.movie_fragment_NowShowing_viewAll);
        ViewallNowShowing.setOnClickListener(this);
        arrayListNowShowing = new ArrayList<>();
        recyclerViewNowShowing = output.findViewById(R.id.movie_fragment_NowShowing);
        clicklistenerNowShowing = new Clicklistener() {
            @Override
            public void itemClick(View view, int position) {
                MovieResult result = arrayListNowShowing.get(position);
                detialActivity(result.getId(),result.getTitle());
            }
        };
        largeAdapterNowShowing = new LargeAdapter(getContext(), arrayListNowShowing, null, Contact.Largeview, Contact.Movie, clicklistenerNowShowing);
        recyclerViewNowShowing.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewNowShowing.setAdapter(largeAdapterNowShowing);


        ViewallPopular = output.findViewById(R.id.movie_fragment_Popular_viewAll);
        ViewallPopular.setOnClickListener(this);
        arrayListPopular = new ArrayList<>();
        recyclerViewPopular = output.findViewById(R.id.movie_fragment_Popular);
        clicklistenerPopular = new Clicklistener() {
            @Override
            public void itemClick(View view, int position) {
                MovieResult result = arrayListPopular.get(position);
                detialActivity(result.getId(),result.getTitle());
            }
        };
        largeAdapterPopular = new LargeAdapter(getContext(), arrayListPopular, null, smallview, Contact.Movie, clicklistenerPopular);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewPopular.setAdapter(largeAdapterPopular);


        ViewallUpcoming = output.findViewById(R.id.movie_fragment_Upcoming_viewAll);
        ViewallUpcoming.setOnClickListener(this);
        arrayListUpcoming = new ArrayList<>();
        recyclerViewUpcoming = output.findViewById(R.id.movie_fragment_Upcoming);
        clicklistenerUpcoming = new Clicklistener() {
            @Override
            public void itemClick(View view, int position) {
                MovieResult result = arrayListUpcoming.get(position);
                detialActivity(result.getId(),result.getTitle());
            }
        };
        largeAdapterUpcoming = new LargeAdapter(getContext(), arrayListUpcoming, null, largeview, Contact.Movie, clicklistenerUpcoming);
        recyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewUpcoming.setAdapter(largeAdapterUpcoming);


        ViewallToprated = output.findViewById(R.id.movie_fragment_Toprated_viewAll);
        ViewallToprated.setOnClickListener(this);
        arrayListToprated = new ArrayList<>();
        recyclerViewToprated = output.findViewById(R.id.movie_fragment_Toprated);
        clicklistenerToprated = new Clicklistener() {
            @Override
            public void itemClick(View view, int position) {
                MovieResult result = arrayListToprated.get(position);
                detialActivity(result.getId(),result.getTitle());
            }
        };
        largeAdapterToprated = new LargeAdapter(getContext(), arrayListToprated, null, smallview, Contact.Movie, clicklistenerToprated);
        recyclerViewToprated.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewToprated.setAdapter(largeAdapterToprated);


        connect(arrayListNowShowing, recyclerViewNowShowing, largeAdapterNowShowing, 0);
        connect(arrayListPopular, recyclerViewPopular, largeAdapterPopular, 1);
        connect(arrayListToprated, recyclerViewToprated, largeAdapterToprated, 2);
        connect(arrayListUpcoming, recyclerViewUpcoming, largeAdapterUpcoming, 3);

        return output;
    }

    private void detialActivity(Long id,String name) {
        Intent intent = new Intent(getContext(), DetailView.class);
        intent.putExtra(Contact.ID, id);
        intent.putExtra(Contact.TYPE, Contact.Movie);
        intent.putExtra(Contact.Name,name);
        intent.putExtra(Contact.BaseUrl, "https://api.themoviedb.org/3/movie/");
        startActivity(intent);
    }

    private void connect(final ArrayList<MovieResult> arrayList, RecyclerView recyclerView, final LargeAdapter adapter, final int i) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        Retroservice retroservice = retrofit.create(Retroservice.class);
        Call<MoviePojo> call;

        if (recyclerView == recyclerViewPopular) {
            call = retroservice.getPopularMovie(API.getApi(), 1);
            //isPopular=true;
        } else if (recyclerView == recyclerViewToprated) {
            call = retroservice.getTopratedMovie(API.getApi(), 1);
            //isToprated=true;
        } else if (recyclerView == recyclerViewUpcoming) {
            call = retroservice.getUpcomingMovie(API.getApi(), 1);
            //isUpcoming=true;
        } else {
            call = retroservice.getNowShowingMovie(API.getApi(), 1);
            //isNowShowing=true;
        }
        call.enqueue(new Callback<MoviePojo>() {
            @Override
            public void onResponse(Call<MoviePojo> call, Response<MoviePojo> response) {
                MoviePojo nowShowingMovie = response.body();
                arrayList.addAll(nowShowingMovie.getResults());
                adapter.notifyDataSetChanged();
                booleans[i] = true;
                alldatafetch();

            }

            @Override
            public void onFailure(Call<MoviePojo> call, Throwable t) {

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
        if (view.getId() == R.id.movie_fragment_NowShowing_viewAll) {
            intent.putExtra(Contact.Category, Contact.NowShowingMovie);
            intent.putExtra(Contact.Name, "Now Showing Movies");
        }
        if (view.getId() == R.id.movie_fragment_Popular_viewAll) {
            intent.putExtra(Contact.Category, Contact.PopularMovie);
            intent.putExtra(Contact.Name, "Popular Movies");
        }
        if (view.getId() == R.id.movie_fragment_Toprated_viewAll) {
            intent.putExtra(Contact.Category, Contact.TopratedMovie);
            intent.putExtra(Contact.Name, "Top Rated Movies");
        }
        if (view.getId() == R.id.movie_fragment_Upcoming_viewAll) {
            intent.putExtra(Contact.Category, Contact.UpcomingMovie);
            intent.putExtra(Contact.Name, "Upcoming Movie");
        }
        intent.putExtra("baseurl", "https://api.themoviedb.org/3/movie/");

        intent.putExtra(Contact.TYPE, Contact.Movie);

        startActivity(intent);
    }
}
