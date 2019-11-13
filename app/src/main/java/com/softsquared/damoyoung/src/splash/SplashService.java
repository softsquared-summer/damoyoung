package com.softsquared.damoyoung.src.splash;

import com.softsquared.damoyoung.src.splash.Interfaces.SplashActivityView;
import com.softsquared.damoyoung.src.splash.Interfaces.SplashRetrofitInterface;
import com.softsquared.damoyoung.src.splash.models.SplashResponse;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.softsquared.damoyoung.src.ApplicationClass.MEDIA_TYPE_JSON;
import static com.softsquared.damoyoung.src.ApplicationClass.getRetrofit;

public class SplashService {

    private final SplashActivityView mSplashActivityView;

    SplashService(final SplashActivityView splashActivityView) {
        this.mSplashActivityView = splashActivityView;
    }

    void getAutoLoginCheck(JSONObject params) {

        final SplashRetrofitInterface splashRetrofitInterface = getRetrofit().create(SplashRetrofitInterface.class);
        splashRetrofitInterface.autoLoginAccess(RequestBody.create(params.toString(), MEDIA_TYPE_JSON)).enqueue(new Callback<SplashResponse>() {
            @Override
            public void onResponse(Call<SplashResponse> call, Response<SplashResponse> response) {
                if (response == null) {
                    mSplashActivityView.validateFailure("자동로그인실패");
                    return;
                }
                final SplashResponse splashResponse = response.body();

                if (splashResponse == null) {
                    mSplashActivityView.validateFailure(null);
                    return;
                } else if (splashResponse.getCode() == 201) {
                    //유효하지 않는 토큰
                    mSplashActivityView.validateFailure(splashResponse.getMessage());
                } else {
                    mSplashActivityView.validateSuccess(splashResponse.getMessage());
                }

            }

            //다른 네트워크 다른 함수
            //enum써라

            @Override
            public void onFailure(Call<SplashResponse> call, Throwable t) {
                mSplashActivityView.validateFailure(null);
            }
        });


    }


}
