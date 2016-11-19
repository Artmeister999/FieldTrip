package com.example.htrip3;

import java.util.Date;

/**
 * Created by garvinchen on 10/24/16.
 */

public class Message {

    @com.google.gson.annotations.SerializedName("userinfo") String USERINFO;

    @com.google.gson.annotations.SerializedName("text") String TEXT;

    @com.google.gson.annotations.SerializedName("id") String id;

    @com.google.gson.annotations.SerializedName("updatedAt") Date updatedAt;
}
