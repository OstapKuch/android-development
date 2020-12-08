package com.example.safehouselab02.presentation.ui_data;

public class SensorViewData {
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
}
