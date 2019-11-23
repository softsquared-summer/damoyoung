package com.softsquared.damoyoung.src.popUpWordbookCopy;

import android.util.Log;

import com.softsquared.damoyoung.src.popUpWordbookCopy.interfaces.PopupWordCopyActivityView;
import com.softsquared.damoyoung.src.popUpWordbookCopy.interfaces.PopupWordCopyRetrofitInterface;
import com.softsquared.damoyoung.src.popUpWordbookCopy.models.PopupWordCopyResponse;
import com.softsquared.damoyoung.src.popUpWordbookMove.interfaces.PopupWordMoveActivityView;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softsquared.damoyoung.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.softsquared.damoyoung.src.ApplicationClass.getRetrofit;

public class PopupWordCopyService {

    private final PopupWordCopyActivityView mPopupWordCopyActivityView;

    public PopupWordCopyService(final PopupWordCopyActivityView popupWordCopyActivityView) {
        this.mPopupWordCopyActivityView = popupWordCopyActivityView;
    }


    void getPopUpBookmark() {

        final PopupWordCopyRetrofitInterface popupWordCopyRetrofitInterface = getRetrofit().create(PopupWordCopyRetrofitInterface.class);
        popupWordCopyRetrofitInterface.getPopupBookmark().enqueue(new Callback<PopupWordCopyResponse>() {
            @Override
            public void onResponse(Call<PopupWordCopyResponse> call, Response<PopupWordCopyResponse> response) {
                if (response == null) {
                    mPopupWordCopyActivityView.validateGetFailure("북마크 조회에 실패하였습니다.");
                    return;
                }
                final PopupWordCopyResponse popUpWordCopyResponse = response.body();

                if (popUpWordCopyResponse == null) {
                    mPopupWordCopyActivityView.validateGetFailure(null);
                    return;
                } else if (popUpWordCopyResponse.getCode() == 100) {
                    //북마크 조회 성공//code 100
                    mPopupWordCopyActivityView.validateGetSuccess(popUpWordCopyResponse.getMessage(), popUpWordCopyResponse.getBookmarkListItems());

                } else {
                    //유효하지 않는 토큰         a
                    mPopupWordCopyActivityView.validateGetFailure(popUpWordCopyResponse.getMessage());
                }

            }

            @Override
            public void onFailure(Call<PopupWordCopyResponse> call, Throwable t) {
                mPopupWordCopyActivityView.validateGetFailure(null);
            }
        });
    }

    void patchWordCopy(JSONObject params) {

        final PopupWordCopyRetrofitInterface popUpWordCopyRetrofitInterface = getRetrofit().create(PopupWordCopyRetrofitInterface.class);
        popUpWordCopyRetrofitInterface.patchCopyWord(RequestBody.create(params.toString(), MEDIA_TYPE_JSON)).enqueue(new Callback<PopupWordCopyResponse>() {
            @Override
            public void onResponse(Call<PopupWordCopyResponse> call, Response<PopupWordCopyResponse> response) {
                if (response == null) {
                    mPopupWordCopyActivityView.validateCopyFailure("단어 복사에 실패하였습니다.");
                    return;
                }
                final PopupWordCopyResponse popUpWordCopyResponse = response.body();

                if (popUpWordCopyResponse == null) {
                    mPopupWordCopyActivityView.validateCopyFailure(null);
                    return;
                } else if (popUpWordCopyResponse.getCode() == 100) {
                    //단어 복사 성공
                    Log.d("TAG", popUpWordCopyResponse.getMessage());
                    mPopupWordCopyActivityView.validateCopySuccess(popUpWordCopyResponse.getMessage());
                } else if (popUpWordCopyResponse.getCode() == 201) {
                    //유효하지 않는 토큰
                    Log.d("TAG", popUpWordCopyResponse.getMessage());

                    mPopupWordCopyActivityView.validateCopyFailure(popUpWordCopyResponse.getMessage());
                } else if (popUpWordCopyResponse.getCode() == 120) {
                    //유효하지 않는 토큰
                    Log.d("TAG", popUpWordCopyResponse.getMessage());

                    mPopupWordCopyActivityView.validateCopyFailure(popUpWordCopyResponse.getMessage());
                } else if (popUpWordCopyResponse.getCode() == 121) {
                    //유효하지 않는 토큰
                    Log.d("TAG", popUpWordCopyResponse.getMessage());
                    //유효하지 않는 토큰
                    mPopupWordCopyActivityView.validateCopyFailure(popUpWordCopyResponse.getMessage());
                } else if (popUpWordCopyResponse.getCode() == 122) {
                    //유효하지 않는 토큰
                    Log.d("TAG", popUpWordCopyResponse.getMessage());
                    mPopupWordCopyActivityView.validateCopyFailure(popUpWordCopyResponse.getMessage());
                } else if (popUpWordCopyResponse.getCode() == 300) {
                    //유효하지 않는 토큰
                    Log.d("TAG", popUpWordCopyResponse.getMessage());
                    mPopupWordCopyActivityView.validateCopyFailure(popUpWordCopyResponse.getMessage());
                } else if (popUpWordCopyResponse.getCode() == 301) {
                    //유효하지 않는 토큰
                    Log.d("TAG", popUpWordCopyResponse.getMessage());
                    mPopupWordCopyActivityView.validateCopyFailure(popUpWordCopyResponse.getMessage());
                } else if (popUpWordCopyResponse.getCode() == 400) {
                    //유효하지 않는 토큰
                    Log.d("TAG", popUpWordCopyResponse.getMessage());
                    mPopupWordCopyActivityView.validateCopyFailure(popUpWordCopyResponse.getMessage());
                } else if (popUpWordCopyResponse.getCode() == 500) {
                    //유효하지 않는 토큰
                    Log.d("TAG", popUpWordCopyResponse.getMessage());
                    mPopupWordCopyActivityView.validateCopyFailure(popUpWordCopyResponse.getMessage());
                } else if (popUpWordCopyResponse.getCode() == 800) {
                    //유효하지 않는 토큰
                    Log.d("TAG", popUpWordCopyResponse.getMessage());
                    mPopupWordCopyActivityView.validateCopyFailure(popUpWordCopyResponse.getMessage());
                } else {
                    Log.d("TAG", popUpWordCopyResponse.getMessage());
                    mPopupWordCopyActivityView.validateCopyFailure(popUpWordCopyResponse.getMessage());
                }

            }

            @Override
            public void onFailure(Call<PopupWordCopyResponse> call, Throwable t) {
                Log.d("TAG", t.getMessage());
                mPopupWordCopyActivityView.validateCopyFailure("네트워크 연결 실패");
            }
        });
    }
}
