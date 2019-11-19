package com.softsquared.damoyoung.src.popUpWordbookCopy;

import com.softsquared.damoyoung.src.popUpWordbookMove.interfaces.PopupWordCopyActivityView;
import com.softsquared.damoyoung.src.popUpWordbookMove.interfaces.PopupWordMoveRetrofitInterface;
import com.softsquared.damoyoung.src.popUpWordbookMove.models.PopUpWordMoveResponse;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softsquared.damoyoung.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.softsquared.damoyoung.src.ApplicationClass.getRetrofit;

public class PopupWordCopyService {

    private final PopupWordCopyActivityView mPopupWordMoveActivityView;

    public PopupWordCopyService(final PopupWordCopyActivityView popupWordMoveActivityView) {
        this.mPopupWordMoveActivityView = popupWordMoveActivityView;
    }

    void getPopUpBookmark() {

        final PopupWordMoveRetrofitInterface popupWordMoveRetrofitInterface = getRetrofit().create(PopupWordMoveRetrofitInterface.class);
        popupWordMoveRetrofitInterface.getPopUpBookmark().enqueue(new Callback<PopUpWordMoveResponse>() {
            @Override
            public void onResponse(Call<PopUpWordMoveResponse> call, Response<PopUpWordMoveResponse> response) {
                if (response == null) {
                    mPopupWordMoveActivityView.validateGetFailure("북마크 조회에 실패하였습니다.");
                    return;
                }
                final PopUpWordMoveResponse popUpWordMoveResponse = response.body();

                if (popUpWordMoveResponse == null) {
                    mPopupWordMoveActivityView.validateGetFailure(null);
                    return;
                } else if (popUpWordMoveResponse.getCode() == 201) {
                    //유효하지 않는 토큰
                    mPopupWordMoveActivityView.validateGetFailure(popUpWordMoveResponse.getMessage());
                }  else {
                    //북마크 조회 성공//code 100
                    mPopupWordMoveActivityView.validateGetSuccess(popUpWordMoveResponse.getMessage(), popUpWordMoveResponse.getBookmarkListItems());
                }

            }
            @Override
            public void onFailure(Call<PopUpWordMoveResponse> call, Throwable t) {
                mPopupWordMoveActivityView.validateGetFailure(null);
            }
        });
    }

    void patchWordMove(JSONObject params) {

        final PopupWordMoveRetrofitInterface popUpbookmarkRetrofitInterface = getRetrofit().create(PopupWordMoveRetrofitInterface.class);
        popUpbookmarkRetrofitInterface.patchMovedWord(RequestBody.create(params.toString(), MEDIA_TYPE_JSON)).enqueue(new Callback<PopUpWordMoveResponse>() {
            @Override
            public void onResponse(Call<PopUpWordMoveResponse> call, Response<PopUpWordMoveResponse> response) {
                if (response == null) {
                    mPopupWordMoveActivityView.validateMoveFailure("단어 저장 실패하였습니다.");
                    return;
                }
                final PopUpWordMoveResponse popUpWordMoveResponse = response.body();

                if (popUpWordMoveResponse == null) {
                    mPopupWordMoveActivityView.validateMoveFailure(null);
                    return;
                } else if (popUpWordMoveResponse.getCode() == 100) {
                    //단어 이동 성공
                    mPopupWordMoveActivityView.validateMoveSuccess(popUpWordMoveResponse.getMessage());
                } else if (popUpWordMoveResponse.getCode() == 201) {
                    //유효하지 않는 토큰
                    mPopupWordMoveActivityView.validateMoveFailure(popUpWordMoveResponse.getMessage());
                } else {
                    mPopupWordMoveActivityView.validateMoveFailure(popUpWordMoveResponse.getMessage());
                }

            }
            @Override
            public void onFailure(Call<PopUpWordMoveResponse> call, Throwable t) {
                mPopupWordMoveActivityView.validateMoveFailure(null);
            }
        });
    }
}
