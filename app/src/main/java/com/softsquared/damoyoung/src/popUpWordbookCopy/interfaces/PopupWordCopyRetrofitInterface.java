package com.softsquared.damoyoung.src.popUpWordbookCopy.interfaces;

import com.softsquared.damoyoung.src.popUpWordbookCopy.models.PopUpWordMoveResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;

public interface PopupWordCopyRetrofitInterface {


    @GET("/bookmark")
    Call<PopUpWordMoveResponse> getPopUpBookmark();

    @PATCH("/copyWord ")
    Call<PopUpWordMoveResponse> patchCopyWord(@Body RequestBody params);

}
