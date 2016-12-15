package com.example.htrip3.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Joined {

    public @SerializedName("deleted") boolean deleted;

    public @SerializedName("id") String id;

    public @SerializedName("createdAt") Date createdAt;

    public @SerializedName("event_id") String eventId;

    public @SerializedName("updatedAt") Date updatedAt;

    public @SerializedName("user_id") String userId;

    public String getUserId() {
        return userId;
    }

    public String getEventId() {
        return eventId;
    }

}
