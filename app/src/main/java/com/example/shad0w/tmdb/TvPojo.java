
package com.example.shad0w.tmdb;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TvPojo {

    @SerializedName("page")
    private Long mPage;
    @SerializedName("results")
    private List<TvResult> mResults;
    @SerializedName("total_pages")
    private Long mTotalPages;
    @SerializedName("total_results")
    private Long mTotalResults;
    @SerializedName("cast")
    private List<TvResult> mCast;
    @SerializedName("crew")
    private List<Crew> mCrew;
    @SerializedName("id")
    private Long mId;

    public List<TvResult> getCast() {
        return mCast;
    }

    public void setCast(List<TvResult> cast) {
        mCast = cast;
    }

    public List<Crew> getCrew() {
        return mCrew;
    }

    public void setCrew(List<Crew> crew) {
        mCrew = crew;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Long getPage() {
        return mPage;
    }

    public void setPage(Long page) {
        mPage = page;
    }

    public List<TvResult> getResults() {
        return mResults;
    }

    public void setResults(List<TvResult> results) {
        mResults = results;
    }

    public Long getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(Long totalPages) {
        mTotalPages = totalPages;
    }

    public Long getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(Long totalResults) {
        mTotalResults = totalResults;
    }

}
