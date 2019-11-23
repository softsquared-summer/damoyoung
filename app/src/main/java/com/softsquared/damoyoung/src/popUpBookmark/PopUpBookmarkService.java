package com.softsquared.damoyoung.src.popUpBookmark;

import com.softsquared.damoyoung.src.popUpBookmark.interfaces.PopUpBookmarkActivityView;
import com.softsquared.damoyoung.src.popUpBookmark.interfaces.PopUpBookmarkRetrofitInterface;
import com.softsquared.damoyoung.src.popUpBookmark.models.PopUpBookmarkResponse;
import com.softsquared.damoyoung.src.popUpBookmark.models.PopUpBookmarkGetResponse;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softsquared.damoyoung.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.softsquared.damoyoung.src.ApplicationClass.getRetrofit;

public class PopUpBookmarkService {

    private final PopUpBookmarkActivityView mPopUpBookmarkActivityView;

    public PopUpBookmarkService(final PopUpBookmarkActivityView popUpbookmarkActivityView) {
        this.mPopUpBookmarkActivityView = popUpbookmarkActivityView;
    }

    void getPopUpBookmark() {

        final PopUpBookmarkRetrofitInterface popUpbookmarkRetrofitInterface = getRetrofit().create(PopUpBookmarkRetrofitInterface.class);
        popUpbookmarkRetrofitInterface.getPopUpBookmark().enqueue(new Callback<PopUpBookmarkGetResponse>() {
            @Override
            public void onResponse(Call<PopUpBookmarkGetResponse> call, Response<PopUpBookmarkGetResponse> response) {
                if (response == null) {
                    mPopUpBookmarkActivityView.validateGetFailure("북마크 조회에 실패하였습니다.");
                    return;
                }
                final PopUpBookmarkGetResponse popUpBookmarkGetResponse = response.body();

                if (popUpBookmarkGetResponse == null) {
                    mPopUpBookmarkActivityView.validateGetFailure(null);
                    return;
                } else if (popUpBookmarkGetResponse.getCode() == 201) {
                    //유효하지 않는 토큰
                    mPopUpBookmarkActivityView.validateGetFailure(popUpBookmarkGetResponse.getMessage());
                } else {
                    //북마크 조회 성공//code 100
                    mPopUpBookmarkActivityView.validateGetSuccess(popUpBookmarkGetResponse.getMessage(), popUpBookmarkGetResponse.getBookmarkListItems());
                }

            }

            @Override
            public void onFailure(Call<PopUpBookmarkGetResponse> call, Throwable t) {
                mPopUpBookmarkActivityView.validateGetFailure(null);
            }
        });
    }

    void postPopUpBookmark(JSONObject params) {

        final PopUpBookmarkRetrofitInterface popUpbookmarkRetrofitInterface = getRetrofit().create(PopUpBookmarkRetrofitInterface.class);
        popUpbookmarkRetrofitInterface.postPopUpBookmark(RequestBody.create(params.toString(), MEDIA_TYPE_JSON)).enqueue(new Callback<PopUpBookmarkResponse>() {
            @Override
            public void onResponse(Call<PopUpBookmarkResponse> call, Response<PopUpBookmarkResponse> response) {
                if (response == null) {
                    mPopUpBookmarkActivityView.validateMakeFailure("북마크 조회에 실패하였습니다.");
                    return;
                }
                final PopUpBookmarkResponse popUpBookmarkResponse = response.body();

                if (popUpBookmarkResponse == null) {
                    mPopUpBookmarkActivityView.validateMakeFailure(null);
                    return;
                } else if (popUpBookmarkResponse.getCode() == 201) {
                    //유효하지 않는 토큰
                    mPopUpBookmarkActivityView.validateMakeFailure(popUpBookmarkResponse.getMessage());
                } else if (popUpBookmarkResponse.getCode() == 102) {
                    //북마크 이름을 안했을시
                    mPopUpBookmarkActivityView.validateMakeFailure(popUpBookmarkResponse.getMessage());
                } else {
                    //북마크 등록 성공
                    mPopUpBookmarkActivityView.validateMakeSuccess(popUpBookmarkResponse.getMessage());
                }

            }

            @Override
            public void onFailure(Call<PopUpBookmarkResponse> call, Throwable t) {
                mPopUpBookmarkActivityView.validateMakeFailure(null);
            }
        });
    }

    void postSaveWord(JSONObject params) {

        final PopUpBookmarkRetrofitInterface popUpbookmarkRetrofitInterface = getRetrofit().create(PopUpBookmarkRetrofitInterface.class);
        popUpbookmarkRetrofitInterface.postSaveWord(RequestBody.create(params.toString(), MEDIA_TYPE_JSON)).enqueue(new Callback<PopUpBookmarkResponse>() {
            @Override
            public void onResponse(Call<PopUpBookmarkResponse> call, Response<PopUpBookmarkResponse> response) {
                if (response == null) {
                    mPopUpBookmarkActivityView.validateSaveFailure("단어 저장 실패하였습니다.");
                    return;
                }
                final PopUpBookmarkResponse popUpBookmarkResponse = response.body();

                if (popUpBookmarkResponse == null) {
                    mPopUpBookmarkActivityView.validateSaveFailure(null);
                    return;
                } else if (popUpBookmarkResponse.getCode() == 100) {
                    //예문 저장 성공
                    mPopUpBookmarkActivityView.validateSaveSuccess(popUpBookmarkResponse.getMessage());
                } else if (popUpBookmarkResponse.getCode() == 201) {
                    //유효하지 않는 토큰
                    mPopUpBookmarkActivityView.validateSaveFailure(popUpBookmarkResponse.getMessage());
                } else {
                    mPopUpBookmarkActivityView.validateSaveFailure(popUpBookmarkResponse.getMessage());
                }

            }

            @Override
            public void onFailure(Call<PopUpBookmarkResponse> call, Throwable t) {
                mPopUpBookmarkActivityView.validateSaveFailure(null);
            }
        });
    }
}
