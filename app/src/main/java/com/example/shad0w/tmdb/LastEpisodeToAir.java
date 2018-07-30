
package com.example.shad0w.tmdb;


import com.google.gson.annotations.SerializedName;


public class LastEpisodeToAir {

    @SerializedName("air_date")
    private String mAirDate;
    @SerializedName("episode_number")
    private Long mEpisodeNumber;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("production_code")
    private Object mProductionCode;
    @SerializedName("season_number")
    private Long mSeasonNumber;
    @SerializedName("show_id")
    private Long mShowId;
    @SerializedName("still_path")
    private String mStillPath;
    @SerializedName("vote_average")
    private Long mVoteAverage;
    @SerializedName("vote_count")
    private Long mVoteCount;

    public String getAirDate() {
        return mAirDate;
    }

    public void setAirDate(String airDate) {
        mAirDate = airDate;
    }

    public Long getEpisodeNumber() {
        return mEpisodeNumber;
    }

    public void setEpisodeNumber(Long episodeNumber) {
        mEpisodeNumber = episodeNumber;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public Object getProductionCode() {
        return mProductionCode;
    }

    public void setProductionCode(Object productionCode) {
        mProductionCode = productionCode;
    }

    public Long getSeasonNumber() {
        return mSeasonNumber;
    }

    public void setSeasonNumber(Long seasonNumber) {
        mSeasonNumber = seasonNumber;
    }

    public Long getShowId() {
        return mShowId;
    }

    public void setShowId(Long showId) {
        mShowId = showId;
    }

    public String getStillPath() {
        return mStillPath;
    }

    public void setStillPath(String stillPath) {
        mStillPath = stillPath;
    }

    public Long getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(Long voteAverage) {
        mVoteAverage = voteAverage;
    }

    public Long getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(Long voteCount) {
        mVoteCount = voteCount;
    }

}
