package com.softsquared.damoyoung.src.popUpBookmark.interfaces;

import com.softsquared.damoyoung.src.popUpBookmark.models.PopUpBookmarkResponse;
import com.softsquared.damoyoung.src.popUpBookmark.models.PopUpBookmarkGetResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PopUpBookmarkRetrofitInterface {
//    @GET("/test")

//
//    @DELETE("/bookmark/{bookmarkNo}")
//    Call<Pop>

    @POST("/bookmark")
    Call<PopUpBookmarkResponse> postPopUpBookmark(@Body RequestBody params);


    @GET("/bookmark")
    Call<PopUpBookmarkGetResponse> getPopUpBookmark();

    @POST("/bookmark/word")
    Call<PopUpBookmarkResponse> postSaveWord(@Body RequestBody params);

}
