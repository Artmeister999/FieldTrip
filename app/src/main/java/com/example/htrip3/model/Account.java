package com.example.htrip3.model;

public class Account {

    @com.google.gson.annotations.SerializedName("id") public String mId;

    @com.google.gson.annotations.SerializedName("First_Login") public int first_login;

    @com.google.gson.annotations.SerializedName("verCode") public int verCode;

    @com.google.gson.annotations.SerializedName("firstname") public String firstname;
    @com.google.gson.annotations.SerializedName("lastname") public String lastname;
    @com.google.gson.annotations.SerializedName("verStatus") public Boolean verStatus;
    @com.google.gson.annotations.SerializedName("password") public String password;
    @com.google.gson.annotations.SerializedName("email") public String email;
}
