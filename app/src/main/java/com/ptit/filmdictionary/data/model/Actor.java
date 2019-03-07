package com.ptit.filmdictionary.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Actor {
    @SerializedName("id")
    @Expose
    private String mId;

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("profile_path")
    @Expose
    private String mProfilePath;

    @SerializedName("character")
    @Expose
    private String mCharater;

    @SerializedName("birthday")
    @Expose
    private String mBirthday;

    @SerializedName("biography")
    @Expose
    private String mBiography;

    @SerializedName("place_of_birth")
    @Expose
    private String mPlace;

    public Actor() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
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

    public String getCharater() {
        return mCharater;
    }

    public void setCharater(String charater) {
        mCharater = charater;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    public String getBiography() {
        return mBiography;
    }

    public void setBiography(String biography) {
        mBiography = biography;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }
}
