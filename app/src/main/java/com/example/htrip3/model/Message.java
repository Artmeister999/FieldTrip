package com.example.htrip3.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;


public class Message {

    public @SerializedName("userinfo") String USERINFO;

    public @SerializedName("text") String TEXT;

    public @SerializedName("id") String id;

    public @SerializedName("updatedAt") Date updatedAt;

    public @SerializedName("eventId") String eventId;
}
