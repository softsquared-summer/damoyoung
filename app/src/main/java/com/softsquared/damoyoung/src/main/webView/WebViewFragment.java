package com.softsquared.damoyoung.src.main.webView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.main.MainActivity;

import retrofit2.http.POST;


public class WebViewFragment extends Fragment {
//    private Activity activity;
    private WebView mWebView;
    private WebSettings mWebSettings;
    private String mKeyword="";
    private String mUrl="";

    public WebViewFragment(String url,String keyword){
        this.mUrl = url;
        this.mKeyword = keyword;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        mWebView = view.findViewById(R.id.webview);

        mWebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
        mWebView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        mWebSettings = mWebView.getSettings(); //세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스크립트 허용 여부
        mWebSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
        mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false); // 화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING); // 컨텐츠 사이즈 맞추기
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
        mWebSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부

        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                //This is the filter
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;


                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        ((MainActivity)getActivity()).onBackPressed();
                    }

                    return true;
                }

                return false;
            }
        });

        loadUrl(mKeyword);

        return view;
    }


    public String getUrl(){
        return mUrl;
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.pauseTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.resumeTimers();
    }

    public void loadUrl(String keyword){

        mWebView.clearCache(true);
        mWebView.reload();
        mWebView.loadUrl("about:blank");
            if (keyword.length()!=0){
                mWebView.loadUrl(getUrl()+ keyword); // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작
            }
            else {
                keyword="";
                mWebView.loadUrl(getUrl()+ keyword); // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작
            }

    }



}
