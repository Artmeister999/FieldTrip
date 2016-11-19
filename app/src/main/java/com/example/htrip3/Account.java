package com.example.htrip3;

/**
 * Created by garvinchen on 10/24/16.
 */

public class Account
{



       @com.google.gson.annotations.SerializedName("id")
        public String mId;

    @com.google.gson.annotations.SerializedName("First_Login")
    public int first_login;

        @com.google.gson.annotations.SerializedName("verCode")
        public int verCode;


    @com.google.gson.annotations.SerializedName("firstname")
    public String firstname;
    @com.google.gson.annotations.SerializedName("lastname")
    public String lastname;
    @com.google.gson.annotations.SerializedName("password")
    String password;
    @com.google.gson.annotations.SerializedName("email")
    String email;

    @com.google.gson.annotations.SerializedName("verStatus")
    public Boolean verStatus;


}
