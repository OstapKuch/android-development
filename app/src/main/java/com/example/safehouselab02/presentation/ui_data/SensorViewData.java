package com.example.safehouselab02.presentation.ui_data;

import android.os.Parcel;
import android.os.Parcelable;

public class SensorViewData implements Parcelable {
    private String name;
    private String time;
    private String state;
    private String photoUrl;

    public SensorViewData(String name, String time, String state, String photoUrl) {
        this.name = name;
        this.time = time;
        this.state = state;
        this.photoUrl = photoUrl;
    }

    public SensorViewData(Parcel parcel) {
        name = parcel.readString();
        time = parcel.readString();
        state = parcel.readString();
        photoUrl = parcel.readString();
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(time);
        parcel.writeString(state);
        parcel.writeString(photoUrl);
    }
    public static final Parcelable.Creator<SensorViewData> CREATOR
            = new Parcelable.Creator<SensorViewData>() {

        @Override
        public SensorViewData createFromParcel(Parcel in) {
            return new SensorViewData(in);
        }
        @Override
        public  SensorViewData[] newArray(int size) {
            return new SensorViewData[size];
        }

    };


}
