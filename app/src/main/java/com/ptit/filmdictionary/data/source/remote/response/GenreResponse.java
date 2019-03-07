package com.ptit.filmdictionary.data.source.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ptit.filmdictionary.data.model.Genre;

import java.util.List;

public class GenreResponse {
    @SerializedName("genres")
    @Expose
    private List<Genre> mGenres;

    public GenreResponse() {
    }

    public List<Genre> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genre> genres) {
        mGenres = genres;
    }
}
