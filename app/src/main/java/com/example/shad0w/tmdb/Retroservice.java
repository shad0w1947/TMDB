package com.example.shad0w.tmdb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Retroservice {


    //it is used to get nowshowing movie
    @GET("now_playing/")
    Call<MoviePojo> getNowShowingMovie(@Query("api_key") String api,@Query("page") int page);

    //it is used to get torated  movie
    @GET("top_rated/")
    Call<MoviePojo> getTopratedMovie(@Query("api_key") String api,@Query("page") int page);


    //it is used to get upcoming movie
    @GET("upcoming/")
    Call<MoviePojo> getUpcomingMovie(@Query("api_key") String api,@Query("page") int page);


    //it is used to get poupular Movie
    @GET("popular/")
    Call<MoviePojo> getPopularMovie(@Query("api_key") String api,@Query("page") int page);


    //it is used to get  poupular tv show
    @GET("popular/")
    Call<TvPojo> getPopularTv(@Query("api_key") String api,@Query("page") int page);


    //it is used to get Onair tv show
    @GET("on_the_air/")
     Call<TvPojo> getOnairTv(@Query("api_key") String api,@Query("page") int page);


    //it used to get latest tv show
    @GET("airing_today/")
    Call<TvPojo> getAiringTodayTv(@Query("api_key") String api,@Query("page") int page);

    //it is used to get torated tv show
    @GET("top_rated/")
    Call<TvPojo> getTopratedTv(@Query("api_key") String api,@Query("page") int page);

    //It is used to get detail of both movie and tv show
    @GET("{id}")
    Call<DetailPojo> getDetail(@Path("id") Long id,@Query("api_key") String api);

    //It is used to get cast  both in movie and Tv show
    @GET("{id}/credits")
    Call<CastPojo> getCast(@Path("id") Long id,@Query("api_key") String api);

    //It is used to get similar movie
    @GET("{id}/similar")
    Call<MoviePojo> getSimilarMovie(@Path("id") Long id,@Query("api_key") String api);

    //It is used to get similar Tv show
    @GET("{id}/similar")
    Call<TvPojo> getSimilarTv(@Path("id") Long id,@Query("api_key") String api);

    //it is used to person detail
    @GET("{id}")
    Call<PersonDetailPojo> getPerson(@Path("id") Long id,@Query("api_key") String api);

    //it is used get cast of a person  tv Show
    @GET("{id}/tv_credits")
    Call<TvPojo> getTvCast(@Path("id") Long id,@Query("api_key") String api);

    //it is used get cast of a person in movie
    @GET("{id}/movie_credits")
    Call<MoviePojo> getMovieCast(@Path("id") Long id,@Query("api_key") String api);

    //it is used to get VideoThumNail
    @GET("{id}/videos")
    Call<VideosPojo> getVideothumbnail(@Path("id") Long id,@Query("api_key") String api);

    @GET("{type}")
    Call<TvPojo> getSearchTv(@Path("type") String type,@Query("api_key") String api,@Query("language") String language,@Query("query") String query,@Query("page") int page);

    @GET("{type}")
    Call<MoviePojo> getSearchMovie(@Path("type") String type,@Query("api_key") String api,@Query("language") String language,@Query("query") String query,@Query("page") int page);
}