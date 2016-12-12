package com.example.htrip3.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Joined {

    public @SerializedName("id") String id;

    public @SerializedName("user_id") String userId;

    public @SerializedName("event_id") String eventId;

    public @SerializedName("createdAt") Date createdAt;

    public @SerializedName("updatedAt") Date updatedAt;

    public @SerializedName("deleted") boolean deleted;

    public String getUserId() {
        return userId;
    }

    public String getEventId() {
        return eventId;
    }
}
