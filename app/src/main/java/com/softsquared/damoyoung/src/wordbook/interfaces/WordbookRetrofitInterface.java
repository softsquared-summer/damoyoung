package com.softsquared.damoyoung.src.wordbook.interfaces;

import com.softsquared.damoyoung.src.splash.models.SplashResponse;
import com.softsquared.damoyoung.src.wordbook.models.WordbookResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WordbookRetrofitInterface {

    @POST("/bookmark/{bookmarkNo}/word")
    Call<WordbookResponse> getWordbook(@Path("bookmarkNo") int bookmarkNo, @Body RequestBody params);

    @HTTP(method = "DELETE" ,path = "/bookmark/{bookmarkNo}/word",hasBody = true)
    Call<WordbookResponse> deleteWord(@Path("bookmarkNo") int bookmarkNo, @Body RequestBody params);

    @DELETE("/bookmark/{bookmarkNo}/sentence/{sentenceNo}")
    Call<WordbookResponse> deleteSentence(@Path("bookmarkNo") int bookmarkNo, @Path("sentenceNo") int sentenceNo);

    @PATCH("/bookmark/{bookmarkNo}/word/{wordNo}")
    Call<WordbookResponse> patchWordMemorized(@Path("bookmarkNo") int bookmarkNo, @Path("wordNo") int wordNo,@Body RequestBody params);





}
