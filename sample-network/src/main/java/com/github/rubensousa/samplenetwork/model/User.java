package com.github.rubensousa.samplenetwork.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("html_url")
    public String url;

    @SerializedName("avatar_url")
    public String avatarUrl;

    @SerializedName("login")
    public String username;

    public User() {

    }

    protected User(Parcel in) {
        url = in.readString();
        avatarUrl = in.readString();
        username = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(avatarUrl);
        dest.writeString(username);
    }
}
