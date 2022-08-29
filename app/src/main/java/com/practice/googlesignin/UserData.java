package com.practice.googlesignin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class UserData implements Parcelable {

    private String name, email;
    private Uri displayPicture;

    UserData(String name, String email, Uri displayPicture) {
        this.name = name;
        this.email = email;
        this.displayPicture = displayPicture;
    }

    protected UserData(Parcel in) {
        name = in.readString();
        email = in.readString();
        displayPicture = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Uri getDisplayPicture() {
        return displayPicture;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", displayPicture=" + displayPicture +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeParcelable(displayPicture, flags);
    }
}
