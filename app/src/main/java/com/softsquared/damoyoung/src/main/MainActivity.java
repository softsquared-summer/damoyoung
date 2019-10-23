package com.softsquared.damoyoung.src.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;
import com.softsquared.damoyoung.src.main.interfaces.MainActivityView;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class MainActivity extends BaseActivity implements MainActivityView {

    private WebView mWebView;
    private WebSettings mWebSettings;
    ClipboardManager clipBoard;
    boolean mPrimaryClipFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        //콜백메소드를 통해 클립보드가 변경되었을때 클립보드를 가져올 수 있게 하였다.
        clipBoard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {

                if (mPrimaryClipFlag) {
                    mPrimaryClipFlag = pasteSentence();
                }
                //PrimaryClipChanged 가 2번 실행되는 오류가 발생했다.
                //따라서 플래그를 통해 한번만 클립보드를 갖고오게했다

            }
        });
        // 웹뷰 시작
        mWebView = (WebView) findViewById(R.id.webView);

        mWebView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
        mWebSettings = mWebView.getSettings(); //세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true); // 웹페이지 자바스클비트 허용 여부
        mWebSettings.setSupportMultipleWindows(false); // 새창 띄우기 허용 여부
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false); // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.setLoadWithOverviewMode(true); // 메타태그 허용 여부
        mWebSettings.setUseWideViewPort(true); // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false); // 화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(false); // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용 여부
        mWebSettings.setDomStorageEnabled(true); // 로컬저장소 허용 여부

        mWebView.loadUrl("https://www.oxfordlearnersdictionaries.com/"); // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작

    }

    @Override
    public void onActionModeStarted(final android.view.ActionMode mode) {
        final Menu menu = mode.getMenu();

        final int copy = menu.getItem(0).getItemId();


        menu.add("예문저장")
                .setEnabled(true)
                .setVisible(true)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        mPrimaryClipFlag = true;// 클립보드 제어 플래그 초기화
                        menu.performIdentifierAction(copy, 0);// 복사 메뉴를 강제적으로 실행하게 했다.
                        return false;
                    }
                });

        super.onActionModeStarted(mode);
    }

    //클립보드에서 스트링 값을 가져오는 구문이다.
    //pastData에 클립보드 값을 가져올 수 있다.
    public boolean pasteSentence() {

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String pasteData = "";

        // 클립보드에 데이터가 없거나 텍스트 타입이 아닌 경우
        if (!(clipboard.hasPrimaryClip())) {

        } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {

        } else {
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            pasteData = item.getText().toString();
        }

        System.out.println(pasteData);
        return false;
    }

    @Override
    public void validateSuccess(String text) {

    }

    @Override
    public void validateFailure(String message) {

    }
}
