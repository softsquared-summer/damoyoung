package com.softsquared.damoyoung.src.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;
import com.softsquared.damoyoung.src.main.MainActivity;
import com.softsquared.damoyoung.src.splash.Interfaces.SplashActivityView;

import org.json.JSONException;
import org.json.JSONObject;

import static com.softsquared.damoyoung.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.softsquared.damoyoung.src.ApplicationClass.sSharedPreferences;


public class SplashActivity extends BaseActivity implements SplashActivityView {

    private String androidNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        //자동 로그인 기능 활성화

        try {
            androidNum = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

            if (androidNum==null){
                throw new Exception();
            }
        }catch (Exception e ) {
            e.printStackTrace();
        }

        System.out.println(androidNum);



        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 500); // 1초 후에 hd handler 실행


    }


    private void postUserCheck() {

        final SplashService splashService = new SplashService(this);
        try {
            //로그인 서비스 API
            JSONObject params = new JSONObject();
            params.put("androidNum", androidNum);
            splashService.postUserCheck(params);
        } catch (JSONException e) {
            showCustomToast("");
        }
    }

    @Override
    public void validateSuccess(String text, String jwt) {

        SharedPreferences.Editor editor = sSharedPreferences.edit();

        //androidId를 넘기면
        //jwt 저장
        editor.putString(X_ACCESS_TOKEN, jwt);
        editor.apply(); // ediot.commit() -> editor.apply(); 변경

        startActivity(new Intent(getApplication(), MainActivity.class)); //로딩이 끝난 후, MainActivity로 이동
        SplashActivity.this.finish(); // 로딩페이지 Activity 제거
    }

    @Override
    public void validateFailure(String message) {
        startActivity(new Intent(getApplication(), MainActivity.class)); //로딩이 끝난 후, MainActivity로 이동
        SplashActivity.this.finish(); // 로딩페이지 Activity 제거
    }

    private class splashhandler implements Runnable {
        public void run() {
            postUserCheck();
        }

    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }

}

