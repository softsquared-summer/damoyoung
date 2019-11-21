package com.softsquared.damoyoung.src.popUpWordbookCopy.interfaces;

import com.softsquared.damoyoung.src.popUpWordbookCopy.models.PopupWordCopyResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;

public interface PopupWordCopyRetrofitInterface {


    @GET("/bookmark")
    Call<PopupWordCopyResponse> getPopupBookmark();

    @PATCH("/copiedWord")
    Call<PopupWordCopyResponse> patchCopyWord(@Body RequestBody params);

}
