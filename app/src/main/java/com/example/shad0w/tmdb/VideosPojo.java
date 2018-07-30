
package com.example.shad0w.tmdb;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class VideosPojo {

    @SerializedName("id")
    private Long mId;
    @SerializedName("results")
    private List<VideoResult> mResults;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public List<VideoResult> getResults() {
        return mResults;
    }

    public void setResults(List<VideoResult> results) {
        mResults = results;
    }

}
