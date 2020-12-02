package com.softsquared.damoyoung.src.popUpWordbookMove;

import com.softsquared.damoyoung.src.popUpWordbookMove.interfaces.PopupWordMoveActivityView;
import com.softsquared.damoyoung.src.popUpWordbookMove.interfaces.PopupWordMoveRetrofitInterface;
import com.softsquared.damoyoung.src.popUpWordbookMove.models.PopUpWordMoveData;
import com.softsquared.damoyoung.src.popUpWordbookMove.models.PopUpWordMoveResponse;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softsquared.damoyoung.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.softsquared.damoyoung.src.ApplicationClass.getRetrofit;

public class PopupWordMoveService {

    private final PopupWordMoveActivityView mPopupWordMoveActivityView;

    public PopupWordMoveService(final PopupWordMoveActivityView popupWordMoveActivityView) {
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
                } else if (popUpWordMoveResponse.getCode() == 100) {
                    //북마크 조회 성공 code 100
                    mPopupWordMoveActivityView.validateGetSuccess(popUpWordMoveResponse.getMessage(), popUpWordMoveResponse.getBookmarkListItems());

                }  else {
                    mPopupWordMoveActivityView.validateGetFailure(popUpWordMoveResponse.getMessage());

                }

            }
            @Override
            public void onFailure(Call<PopUpWordMoveResponse> call, Throwable t) {
                mPopupWordMoveActivityView.validateGetFailure(null);
            }
        });
    }

    void patchWordMove(PopUpWordMoveData data) {

        final PopupWordMoveRetrofitInterface popUpbookmarkRetrofitInterface = getRetrofit().create(PopupWordMoveRetrofitInterface.class);
        popUpbookmarkRetrofitInterface.patchMovedWord(data).enqueue(new Callback<PopUpWordMoveResponse>() {
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
