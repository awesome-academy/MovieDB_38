package com.ptit.filmdictionary.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Actor implements Parcelable {
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
    private String mCharacter;

    @SerializedName("birthday")
    @Expose
    private String mBirthday;

    @SerializedName("biography")
    @Expose
    private String mBiography;

    @SerializedName("place_of_birth")
    @Expose
    private String mPlace;

    @SerializedName("popularity")
    @Expose
    private String mPopularity;

    @SerializedName("gender")
    @Expose
    private String mGender;

    @SerializedName("known_for_department")
    @Expose
    private String mDepartment;

    public Actor() {
    }

    private Actor(Parcel source) {
        mId = source.readString();
        mName = source.readString();
        mProfilePath = source.readString();
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

    public String getCharacter() {
        return mCharacter;
    }

    public void setCharacter(String character) {
        mCharacter = character;
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

    public String getPopularity() {
        return mPopularity;
    }

    public void setPopularity(String popularity) {
        mPopularity = popularity;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        mDepartment = department;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mProfilePath);
    }

    public static final Creator<Actor> CREATOR = new Creator<Actor>() {
        @Override
        public Actor createFromParcel(Parcel source) {
            return new Actor(source);
        }

        @Override
        public Actor[] newArray(int size) {
            return new Actor[size];
        }
    };
}
