package com.softsquared.damoyoung.src.bookmark.interfaces;

import com.softsquared.damoyoung.src.bookmark.models.BookmarkResponse;
import com.softsquared.damoyoung.src.bookmark.models.BookmarkGetResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookmarkRetrofitInterface {
//    @GET("/test")


    @POST("/bookmark")
    Call<BookmarkResponse> postBookmark(@Body RequestBody params);


    @GET("/bookmark")
    Call<BookmarkGetResponse> getBookmark();

    @DELETE("/bookmark/{bookmarkNo}")
    Call<BookmarkResponse> deleteBookmark(@Path("bookmarkNo") int bookmarkNo);
}
