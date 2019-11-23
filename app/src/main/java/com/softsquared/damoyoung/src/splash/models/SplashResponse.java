package com.softsquared.damoyoung.src.splash.models;

import com.google.gson.annotations.SerializedName;

public class SplashResponse {

    @SerializedName("result")
    Result result;

    public class Result {

        @SerializedName("jwt")
        private String jwt;

        public String getJwt() {
            return jwt;
        }
    }

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("isSuccess")
    private boolean isSuccess;


    public Result getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }
}
