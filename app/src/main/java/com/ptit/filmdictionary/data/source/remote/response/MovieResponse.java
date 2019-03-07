package com.ptit.filmdictionary.data.source.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ptit.filmdictionary.data.model.Movie;

import java.util.List;

public class MovieResponse {
    @SerializedName("page")
    @Expose
    private int mPage;

    @SerializedName("total_page")
    @Expose
    private int mTotalPage;

    @SerializedName("total_results")
    @Expose
    private int mTotalResult;

    @SerializedName("results")
    @Expose
    private List<Movie> mResults;

    public MovieResponse() {
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public int getTotalPage() {
        return mTotalPage;
    }

    public void setTotalPage(int totalPage) {
        mTotalPage = totalPage;
    }

    public int getTotalResult() {
        return mTotalResult;
    }

    public void setTotalResult(int totalResult) {
        mTotalResult = totalResult;
    }

    public List<Movie> getResults() {
        return mResults;
    }

    public void setResults(List<Movie> results) {
        mResults = results;
    }
}
