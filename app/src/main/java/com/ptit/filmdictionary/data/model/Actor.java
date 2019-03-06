package com.ptit.filmdictionary.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Actor {
    @SerializedName("cast_id")
    @Expose
    private int mCastId;
    @SerializedName("credit_id")
    @Expose
    private String mCreditId;
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("profile_path")
    @Expose
    private String mProfilePath;

    public Actor() {
    }

    public int getCastId() {
        return mCastId;
    }

    public void setCastId(int castId) {
        mCastId = castId;
    }

    public String getCreditId() {
        return mCreditId;
    }

    public void setCreditId(String creditId) {
        mCreditId = creditId;
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

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        mProfilePath = profilePath;
    }
}
