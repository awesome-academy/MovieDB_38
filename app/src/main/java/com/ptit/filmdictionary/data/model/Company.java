package com.ptit.filmdictionary.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company {
    @SerializedName("id")
    @Expose
    private int mId;

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("logo_path")
    @Expose
    private String mLogoPath;

    @SerializedName("origin_country")
    @Expose
    private String mCountry;

    public Company() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLogoPath() {
        return mLogoPath;
    }

    public void setLogoPath(String logoPath) {
        mLogoPath = logoPath;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }
}
