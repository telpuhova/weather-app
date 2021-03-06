package com.boop442.weather442.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boop442 on 3/23/2018.
 */

@Parcel
public class Location {
    String index;
    String title;
    String woeid;
    List<Forecast> forecasts = new ArrayList<>();
    String pushId;

    public Location() {
    }

    public Location(String title, String woeid) {
        this.title = title;
        this.woeid = woeid;
        this.index = "not_specified";
    }

    public Location(String title, String woeid, List<Forecast> forecasts) {
        this.title = title;
        this.woeid = woeid;
        this.forecasts = forecasts;
        this.index = "not_specified";
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    public String getTitle() {
        return title;
    }

    public String getWoeid() {
        return woeid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
