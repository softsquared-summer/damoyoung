package com.softsquared.damoyoung.src.searchResult;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;
import com.softsquared.damoyoung.src.webView.WebViewFragment;
import com.softsquared.damoyoung.src.popUpBookmark.PopupBookmarkActivity;
import com.softsquared.damoyoung.src.sitePriority.SitePriority;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.softsquared.damoyoung.src.ApplicationClass.sSharedPreferences;

public class SearchResultActivity extends BaseActivity {


    private ClipboardManager clipBoard;
    private boolean mPrimaryClipFlag;
    private TabLayout mMainTabLayout;
    private String mKeyword = "";
    private String mSentence = "";
    private boolean mStringTypeIsKorean;
    private Gson gson;
    ArrayList<SitePriority> mEngSitePriorityList = new ArrayList<>();
    ArrayList<SitePriority> mKorSitePriorityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        //intent
        Intent intent = getIntent();
        mKeyword = intent.getStringExtra("keyword");
        stringType(mKeyword);//mStringTypeIsKorean 변수를 선언

        //initialize
        gson = new Gson();
        mPrimaryClipFlag = true; // Clipboard 이중 실행 방지 제어시 필요
        mStringTypeIsKorean = false;//초기값은 영어

        //FindViewById
        mMainTabLayout = findViewById(R.id.tl_search_result);
        mMainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                if (!mStringTypeIsKorean) {
                    replaceFragment(new WebViewFragment(mEngSitePriorityList.get(pos).getUrl(), mKeyword));
                } else {
                    replaceFragment(new WebViewFragment(mKorSitePriorityList.get(pos).getUrl(), mKeyword));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


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

    }


    @Override
    protected void onStart() {
        super.onStart();
        //저장소에 있는 값 저장

        String engJson = sSharedPreferences.getString("siteEngList", "");
        String korJson = sSharedPreferences.getString("siteKorList", "");
        Type siteType = new TypeToken<ArrayList<SitePriority>>() {
        }.getType();
        if (gson.fromJson(engJson, siteType) != null) {
            mEngSitePriorityList = gson.fromJson(engJson, siteType);
        } else {
            //영어 사이트 목록
            mEngSitePriorityList.add(new SitePriority("Google", "https://www.google.com/search?q="));
            mEngSitePriorityList.add(new SitePriority("Naver", "https://dict.naver.com/enendict/#/search?query="));
            mEngSitePriorityList.add(new SitePriority("Daum", "https://alldic.daum.net/search.do?dic=ee&q="));
            mEngSitePriorityList.add(new SitePriority("Longman", "https://www.ldoceonline.com/dictionary/"));
            mEngSitePriorityList.add(new SitePriority("Oxford", "https://www.lexico.com/en/definition/"));
            mEngSitePriorityList.add(new SitePriority("Urban", "https://www.urbandictionary.com/define.php?term="));
            mEngSitePriorityList.add(new SitePriority("Webster", "https://www.merriam-webster.com/dictionary/"));
            mEngSitePriorityList.add(new SitePriority("Wikipedia", "https://en.wikipedia.org/wiki/"));
            mEngSitePriorityList.add(new SitePriority("Cambridge", "https://dictionary.cambridge.org/dictionary/english/ok?q="));
            mEngSitePriorityList.add(new SitePriority("Wiktionary", "https://en.wiktionary.org/wiki/"));
            mEngSitePriorityList.add(new SitePriority("Macmillan Dictionary", "https://www.macmillandictionary.com/dictionary/british/"));
            mEngSitePriorityList.add(new SitePriority("NetLingo", "https://www.dictionary.com/browse/"));
            mEngSitePriorityList.add(new SitePriority("The Free Dictionary", "https://www.thefreedictionary.com/"));

        }
        if (gson.fromJson(korJson, siteType) != null) {
            mKorSitePriorityList = gson.fromJson(korJson, siteType);
        } else {
            //한글 사이트 목록
            mKorSitePriorityList.add(new SitePriority("네이버", "https://endic.naver.com/search.nhn?sLn=kr&searchOption=all&query="));
            mKorSitePriorityList.add(new SitePriority("다음", "https://dic.daum.net/search.do?dic=eng&q="));
            mKorSitePriorityList.add(new SitePriority("구글", "https://www.google.com/search?q="));
        }

        if (mStringTypeIsKorean) {
            clearTab();
            addTab(mKorSitePriorityList);
        } else {
            clearTab();
            addTab(mEngSitePriorityList);
        }
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mMainTabLayout.getTabAt(0).select();
                    }
                }, 100);

    }


    //fragment 교체 함수
    private void replaceFragment(WebViewFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container_search_result, fragment);
        transaction.commit();
    }

    //onClick 메소드
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search_result_close:
                //설정으로 이동
                 finish();
                break;
        }
    }


    @Override
    public void onActionModeStarted(final android.view.ActionMode mode) {
        final Menu menu = mode.getMenu();
        final int copy1 = 34276370;
        final int copy2 = 16908321;

        if (menu.getItem(0).getItemId() == copy1) {
            menu.add("예문저장")
                    .setEnabled(true)
                    .setVisible(true)
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            mPrimaryClipFlag = true;// 클립보드 제어 플래그 초기화
                            menu.performIdentifierAction(copy1, 0);// 복사 메뉴를 강제적으로 실행하게 했다.
                            return false;
                        }
                    });
        } else if (menu.getItem(1).getItemId() == copy2) {
            menu.add("예문저장")
                    .setEnabled(true)
                    .setVisible(true)
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            mPrimaryClipFlag = true;// 클립보드 제어 플래그 초기화
                            menu.performIdentifierAction(copy2, 0);// 복사 메뉴를 강제적으로 실행하게 했다.
                            return false;
                        }
                    });
        } else {

        }
        //웹뷰 내에 34276370 복사 안드로이드 자체 16908321 복사
        super.onActionModeStarted(mode);
    }

    //클립보드에서 스트링 값을 가져오는 구문이다.
    //pastData에 클립보드 값을 가져올 수 있다.
    public boolean pasteSentence() {

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String pasteData = "";


        // 클립보드에 데이터가 없거나 텍스트 타입이 아닌 경우
        if (!(clipboard.hasPrimaryClip())) {
            showCustomToast("예문을 저장할 수 없습니다.");
//        } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {
//            System.out.println(pasteData+"클립보드에 텍스트 타입X");
//            }
        } else {
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            pasteData = item.getText().toString();
            mSentence = pasteData;
        }

            Intent intent = new Intent(this, PopupBookmarkActivity.class);
            intent.putExtra("word", mKeyword);
            intent.putExtra("sentence", mSentence);
            intent.putExtra("type", 1);
            startActivity(intent);

        return false;
    }


    public void clearTab() {
        mMainTabLayout.removeAllTabs();
    }

    public void addTab(ArrayList<SitePriority> data) {
        for (int i = 0; i < data.size(); i++) {
            mMainTabLayout.addTab(mMainTabLayout.newTab().setText(data.get(i).getTitle()));
        }
    }

    public boolean stringType(String str) {

        int index = str.charAt(0);
        boolean isKoreaLanguage;
        if (index >= 48 && index <= 57) {
            isKoreaLanguage = false;
        } else if (index >= 65 && index <= 122) {
            isKoreaLanguage = false;
        } else {
            isKoreaLanguage = true;
        }

        mStringTypeIsKorean = isKoreaLanguage;
        return mStringTypeIsKorean;
    }


}
