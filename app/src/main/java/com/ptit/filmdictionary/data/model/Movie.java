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
    private double mVoteAverage;

    @SerializedName("videos")
    @Expose
    private VideoResult mVideoResult;

    @SerializedName("credits")
    @Expose
    private CastResult mCastResult;

    public Movie() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public List<Genre> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genre> genres) {
        mGenres = genres;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public List<Company> getProductionCompanies() {
        return mProductionCompanies;
    }

    public void setProductionCompanies(List<Company> productionCompanies) {
        mProductionCompanies = productionCompanies;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public int getRuntime() {
        return mRuntime;
    }

    public void setRuntime(int runtime) {
        mRuntime = runtime;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        mVoteAverage = voteAverage;
    }

    public VideoResult getVideoResult() {
        return mVideoResult;
    }

    public void setVideoResult(VideoResult videoResult) {
        mVideoResult = videoResult;
    }

    public CastResult getCastResult() {
        return mCastResult;
    }

    public void setCastResult(CastResult castResult) {
        mCastResult = castResult;
    }

    public class CastResult {
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

    public class VideoResult {
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
