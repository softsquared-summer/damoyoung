package com.softsquared.damoyoung.src.splash.Interfaces;


import com.softsquared.damoyoung.src.splash.models.SplashResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SplashRetrofitInterface {


    @POST("/user")
    Call<SplashResponse> postUserCheck(@Body RequestBody params);

}
