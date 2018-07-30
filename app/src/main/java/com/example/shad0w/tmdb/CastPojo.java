
package com.example.shad0w.tmdb;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CastPojo {

    @SerializedName("cast")
    private List<CastResult> mCast;
    @SerializedName("crew")
    private List<CrewCastPojoResult> mCrew;
    @SerializedName("id")
    private Long mId;

    public List<CastResult> getCast() {
        return mCast;
    }

    public void setCast(List<CastResult> cast) {
        mCast = cast;
    }

    public List<CrewCastPojoResult> getCrew() {
        return mCrew;
    }

    public void setCrew(List<CrewCastPojoResult> crew) {
        mCrew = crew;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

}
