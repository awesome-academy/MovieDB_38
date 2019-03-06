package com.ptit.filmdictionary.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {
    @SerializedName("id")
    @Expose
    private int mId;

    @SerializedName("backdrop_path")
    @Expose
    private String mBackdropPath;

    @SerializedName("genres")
    @Expose
    private List<Genre> mGenres;

    @SerializedName("title")
    @Expose
    private String mTitle;

    @SerializedName("overview")
    @Expose
    private String mOverview;

    @SerializedName("poster_path")
    @Expose
    private String mPosterPath;

    @SerializedName("production_companies")
    @Expose
    private List<Company> mProductionCompanies;

    @SerializedName("release_date")
    @Expose
    private String mReleaseDate;

    @SerializedName("runtime")
    @Expose
    private int mRuntime;

    @SerializedName("vote_average")
    @Expose
    private float mVoteAverage;

    @SerializedName("videos")
    @Expose
    private VideoResult mVideoResult;

    @SerializedName("credits")
    @Expose
    private CastResult mCastResult;

    private class CastResult {
        @SerializedName("cast")
        @Expose
        private List<Actor> mActors;

        public List<Actor> getActors() {
            return mActors;
        }

        public void setActors(List<Actor> actors) {
            mActors = actors;
        }
    }

    private class VideoResult {
        @SerializedName("results")
        @Expose
        private List<Video> mVideos;

        public List<Video> getVideos() {
            return mVideos;
        }

        public void setVideos(List<Video> videos) {
            mVideos = videos;
        }
    }
}
