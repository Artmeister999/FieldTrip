package com.example.htrip3;

import android.app.Application;

public class HtripApp extends Application {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}