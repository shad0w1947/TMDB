package com.example.shad0w.tmdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersponDetail extends Activity {

    ImageView imageView;
    TextView name;
    TextView age;
    TextView biography;
    TextView birthplace;
    Long id;
    int forProfile = 0;
    int forTv = 1;
    int forMovie = 2;

    ArrayList<MovieResult> castMovieArray;
    ArrayList<TvResult> castTvArray;

    RecyclerView movieRecycler;
    RecyclerView tvRecyvler;

    LargeAdapter movieAdapter;
    LargeAdapter tvShowAdapter;

    Intent intent;

    Toolbar toolbar;

    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perspon_detail);
        this.imageView = findViewById(R.id.profile);
        this.name = findViewById(R.id.name);
        this.age = findViewById(R.id.age);
        this.biography = findViewById(R.id.biography);
        this.birthplace = findViewById(R.id.birthplace);
        this.toolbar=findViewById(R.id.toolbar);


        intent = getIntent();
        id = intent.getLongExtra(Contact.ID, 0);
        title=intent.getStringExtra(Contact.Name);

        toolbar.setTitle(title);
        name.setText(title);

        AppBarLayout appBarLayout=findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.i("off",verticalOffset+"");
                if(verticalOffset>=-230)
                    toolbar.setVisibility(View.GONE);
                else
                    toolbar.setVisibility(View.VISIBLE);
            }
        });

        castMovieArray = new ArrayList<>();
        movieRecycler = findViewById(R.id.MovieCastRecyclerview);
        Clicklistener clicklistener = new Clicklistener() {
            @Override
            public void itemClick(View view, int position) {
              MovieResult result=castMovieArray.get(position);
                Intent intent =new Intent(PersponDetail.this,DetailView.class);
                intent.putExtra(Contact.ID,result.getId());
                intent.putExtra(Contact.TYPE,Contact.Movie);
                intent.putExtra(Contact.Name,result.getTitle());
                intent.putExtra(Contact.BaseUrl,"https://api.themoviedb.org/3/movie/");
                startActivity(intent);
            }
        };
        movieAdapter = new LargeAdapter(this, castMovieArray, null, Contact.Smallview, Contact.Movie, clicklistener);
        movieRecycler.setAdapter(movieAdapter);
        movieRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        castTvArray = new ArrayList<>();
        tvRecyvler = findViewById(R.id.TvCastRecyclerview);
        Clicklistener clicklistener1 = new Clicklistener() {
            @Override
            public void itemClick(View view, int position) {
               TvResult result=castTvArray.get(position);
                Intent intent =new Intent(PersponDetail.this,DetailView.class);
                intent.putExtra(Contact.ID,result.getId());
                intent.putExtra(Contact.TYPE,Contact.Tvshow);
                intent.putExtra(Contact.Name,result.getName());
                intent.putExtra(Contact.BaseUrl,"https://api.themoviedb.org/3/tv/");
                startActivity(intent);
            }
        };
        tvShowAdapter = new LargeAdapter(this, null, castTvArray, Contact.Smallview, Contact.Tvshow, clicklistener1);
        tvRecyvler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        tvRecyvler.setAdapter(tvShowAdapter);


        connect(forProfile);
        connect(forTv);
        connect(forMovie);
    }

    private void connect(int choice) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/person/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        Retroservice retroservice = retrofit.create(Retroservice.class);

        if (choice == forProfile) {
            Call<PersonDetailPojo> call = retroservice.getPerson(this.id, API.getApi(),Contact.Language);
            call.enqueue(new Callback<PersonDetailPojo>() {
                @Override
                public void onResponse(Call<PersonDetailPojo> call, Response<PersonDetailPojo> response) {
                    PersonDetailPojo personDetailPojo = response.body();
                    name.setText(personDetailPojo.getName());
                    age.setText(personDetailPojo.getBirthday());
                    birthplace.setText(personDetailPojo.getPlaceOfBirth());
                    biography.setText(personDetailPojo.getBiography());
                    Picasso.get().load(String.valueOf("https://image.tmdb.org/t/p/w300" + personDetailPojo.getProfilePath()))
                            .fit().placeholder(R.color.lightgray).into(imageView);
                }

                @Override
                public void onFailure(Call<PersonDetailPojo> call, Throwable t) {

                }
            });
        }
        if (choice == forMovie) {
            Call<MoviePojo> call=retroservice.getMovieCast(id,API.getApi(),Contact.Language);
            call.enqueue(new Callback<MoviePojo>() {
                @Override
                public void onResponse(Call<MoviePojo> call, Response<MoviePojo> response) {
                    MoviePojo moviePojo=response.body();
                    castMovieArray.addAll(moviePojo.getCast());
                    movieAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<MoviePojo> call, Throwable t) {

                }
            });

        }
        if (choice == forTv) {
            Call<TvPojo> call=retroservice.getTvCast(id,API.getApi(),Contact.Language);
            call.enqueue(new Callback<TvPojo>() {
                @Override
                public void onResponse(Call<TvPojo> call, Response<TvPojo> response) {
                    TvPojo tvPojo=response.body();
                    castTvArray.addAll(tvPojo.getCast());
                    tvShowAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<TvPojo> call, Throwable t) {

                }
            });
        }

    }

    public void Expend(View view) {


    }
}