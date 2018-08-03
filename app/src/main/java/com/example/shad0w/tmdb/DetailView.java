package com.example.shad0w.tmdb;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailView extends Activity {

    Toolbar toolbar;
    ProgressBar progressBar;
    CoordinatorLayout coordinatorLayout;

    ArrayList<TvResult> TvResultArray;
    ArrayList<MovieResult> MovieResultArray;
    ArrayList<VideoResult> videoResultArray;
    ArrayList<CastResult> CastArray;

    Clicklistener clicklistener;

    int type;
    Long id;
    Intent intent;
    String baseUrl;
    ImageView wall;
    ImageView poster;
    TextView name;
    TextView genre;
    TextView rate;
    TextView overView;
    TextView similar;

    RecyclerView similarRecyclerView;
    RecyclerView CastRecyclerView;
    RecyclerView videoRecyclerView;

    CastAdapter castAdapter;
    VideoAdapter videoAdapter;
    LargeAdapter adapter;


    int forCast = 0;
    int forMovie = 1;
    int forTvShow = 2;
    int forPoster = 3;
    int forVideo = 4;

    int visibility = 0;

    String Title;

    GenreHas map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        toolbar=findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress);
        coordinatorLayout = findViewById(R.id.RootC);
        intent = getIntent();
        this.type = intent.getIntExtra(Contact.TYPE, 2);
        this.id = intent.getLongExtra(Contact.ID, 0);
        this.Title=intent.getStringExtra(Contact.Name);
        this.baseUrl = intent.getStringExtra(Contact.BaseUrl);

        toolbar.setTitle(this.Title);
        AppBarLayout appBarLayout=findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if(verticalOffset>=-420)
                {
                    toolbar.setVisibility(View.GONE);
                }
                else {
                    toolbar.setVisibility(View.VISIBLE);
                }
            }
        });

        this.wall = findViewById(R.id.wall);
        this.poster = findViewById(R.id.poster);
        this.name = findViewById(R.id.name);
        name.setText(Title);
        this.genre = findViewById(R.id.genre);
        this.rate = findViewById(R.id.rating);
        this.overView = findViewById(R.id.overview);
        this.similar = findViewById(R.id.similar);

        map=new GenreHas();


        this.videoRecyclerView = findViewById(R.id.videoRecycler);
        videoResultArray = new ArrayList<>();
        clicklistener = new Clicklistener() {
            @Override
            public void itemClick(View view, int position) {
                VideoResult result = videoResultArray.get(position);
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + result.getKey()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + result.getKey()));
                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    startActivity(webIntent);
                }


            }
        };
        videoAdapter = new VideoAdapter(this, videoResultArray, clicklistener);
        videoRecyclerView.setAdapter(videoAdapter);
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        this.CastRecyclerView = findViewById(R.id.CastRecycler);
        CastArray = new ArrayList<>();
        clicklistener = new Clicklistener() {
            @Override
            public void itemClick(View view, int position) {
                CastResult result = CastArray.get(position);
                Intent intent = new Intent(DetailView.this, PersponDetail.class);
                intent.putExtra(Contact.ID, result.getId());
                intent.putExtra(Contact.Name,result.getName());
                startActivity(intent);
            }
        };
        this.castAdapter = new CastAdapter(this, CastArray, clicklistener);
        CastRecyclerView.setAdapter(castAdapter);
        CastRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));



        this.similarRecyclerView = findViewById(R.id.similarRecycler);
        if (type == Contact.Movie) {
            similar.setText("Similar Movie");
            MovieResultArray = new ArrayList<>();
            clicklistener = new Clicklistener() {
                @Override
                public void itemClick(View view, int position) {
                    MovieResult result = MovieResultArray.get(position);
                    detialActivity(result.getId(),result.getTitle());

                }
            };
            adapter = new LargeAdapter(this, MovieResultArray, null, Contact.Smallview, Contact.Movie, clicklistener);
            connect(forMovie);
        } else if (type == Contact.Tvshow) {
            similar.setText("Similar Tv Show");
            TvResultArray = new ArrayList<>();
            clicklistener = new Clicklistener() {
                @Override
                public void itemClick(View view, int position) {
                    TvResult result = TvResultArray.get(position);
                    detialActivity(result.getId(),result.getName());
                }
            };
            adapter = new LargeAdapter(this, null, TvResultArray, Contact.Smallview, Contact.Tvshow, clicklistener);
            connect(forTvShow);
        }
        similarRecyclerView.setAdapter(adapter);
        similarRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));




        connect(forPoster);
        connect(forCast);
        connect(forVideo);


    }

    private void detialActivity(Long id,String name) {
        Intent intent = new Intent(this, DetailView.class);
        intent.putExtra(Contact.ID, id);
        intent.putExtra(Contact.TYPE, type);
        intent.putExtra(Contact.Name,name);
        intent.putExtra(Contact.BaseUrl, baseUrl);
        startActivity(intent);
    }

    private void connect(int choice) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        Retroservice retroservice = retrofit.create(Retroservice.class);


        if (choice == forPoster) {
            Call<DetailPojo> call = retroservice.getDetail(this.id, API.getApi(),Contact.Language);
            call.enqueue(new Callback<DetailPojo>() {
                @Override
                public void onResponse(Call<DetailPojo> call, Response<DetailPojo> response) {
                    DetailPojo detailPojo = response.body();
                    Picasso.get().load(String.valueOf("https://image.tmdb.org/t/p/w300" + detailPojo.getBackdropPath()))
                            .fit().placeholder(R.color.lightgray).into(wall);
                    Picasso.get().load(String.valueOf("https://image.tmdb.org/t/p/w300" + detailPojo.getPosterPath()))
                            .fit().placeholder(R.color.lightgray).into(poster);
                    if (type == Contact.Movie)
                        name.setText(detailPojo.getmTitle());
                    else
                        name.setText(detailPojo.getName());
                    String genreString = "";
                    for (int j = 0; j < detailPojo.getGenres().size(); j++) {
                        if (j == detailPojo.getGenres().size() - 1)
                            genreString = genreString + map.getString(detailPojo.getGenres().get(j).getId());
                        else
                            genreString = genreString + map.getString(detailPojo.getGenres().get(j).getId()) + ",";
                    }
                    genre.setText(genreString);
                    rate.setText(detailPojo.getVoteAverage() + "");
                    overView.setText(detailPojo.getOverview());
                    visibility++;
                    Visibilityfunction();

                }

                @Override
                public void onFailure(Call<DetailPojo> call, Throwable t) {

                }
            });
        }


        if (choice == forCast) {
            Call<CastPojo> call = retroservice.getCast(this.id, API.getApi(),Contact.Language);
            call.enqueue(new Callback<CastPojo>() {
                @Override
                public void onResponse(Call<CastPojo> call, Response<CastPojo> response) {
                    CastPojo castPojo = response.body();
                    CastArray.addAll(castPojo.getCast());
                    castAdapter.notifyDataSetChanged();
                    visibility++;
                    Visibilityfunction();
                }

                @Override
                public void onFailure(Call<CastPojo> call, Throwable t) {

                }
            });

        }
        if (choice == forMovie) {
            Call<MoviePojo> call = retroservice.getSimilarMovie(this.id, API.getApi(),Contact.Language);
            call.enqueue(new Callback<MoviePojo>() {
                @Override
                public void onResponse(Call<MoviePojo> call, Response<MoviePojo> response) {
                    MoviePojo moviePojo = response.body();
                    MovieResultArray.addAll(moviePojo.getResults());
                    adapter.notifyDataSetChanged();
                    visibility++;
                    Visibilityfunction();
                }

                @Override
                public void onFailure(Call<MoviePojo> call, Throwable t) {

                }
            });

        }
        if (choice == forTvShow) {
            Call<TvPojo> call = retroservice.getSimilarTv(this.id, API.getApi(),Contact.Language);
            call.enqueue(new Callback<TvPojo>() {
                @Override
                public void onResponse(Call<TvPojo> call, Response<TvPojo> response) {
                    TvPojo tvPojo = response.body();
                    TvResultArray.addAll(tvPojo.getResults());
                    adapter.notifyDataSetChanged();
                    visibility++;
                    Visibilityfunction();

                }

                @Override
                public void onFailure(Call<TvPojo> call, Throwable t) {

                }
            });
        }

        if (choice == forVideo) {
            Call<VideosPojo> call = retroservice.getVideothumbnail(this.id, API.getApi(),Contact.Language);
            call.enqueue(new Callback<VideosPojo>() {
                @Override
                public void onResponse(Call<VideosPojo> call, Response<VideosPojo> response) {
                    VideosPojo videosPojo = response.body();
                    videoResultArray.addAll(videosPojo.getResults());
                    videoAdapter.notifyDataSetChanged();
                    Visibilityfunction();
                }

                @Override
                public void onFailure(Call<VideosPojo> call, Throwable t) {

                }
            });
        }

    }

    private void Visibilityfunction() {
        if (visibility >= 0) {
            progressBar.setVisibility(View.GONE);
            coordinatorLayout.setVisibility(View.VISIBLE);
        }
    }


    public void largeText(View view) {
    }
}
