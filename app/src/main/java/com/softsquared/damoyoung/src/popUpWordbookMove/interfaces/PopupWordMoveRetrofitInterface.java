package com.softsquared.damoyoung.src.popUpWordbookMove.interfaces;

import com.softsquared.damoyoung.src.popUpWordbookMove.models.PopUpWordMoveData;
import com.softsquared.damoyoung.src.popUpWordbookMove.models.PopUpWordMoveResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;

public interface PopupWordMoveRetrofitInterface {


    @GET("/bookmark")
    Call<PopUpWordMoveResponse> getPopUpBookmark();

    @PATCH("/movedWord ")
    Call<PopUpWordMoveResponse> patchMovedWord(@Body PopUpWordMoveData data);

}
