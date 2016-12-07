package com.example.htrip3.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Event {

    public @SerializedName("id") String id;

    public @SerializedName("title") String title;

    public @SerializedName("description") String description;

    public @SerializedName("type") String type;

    public @SerializedName("date") Date date;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }
}
