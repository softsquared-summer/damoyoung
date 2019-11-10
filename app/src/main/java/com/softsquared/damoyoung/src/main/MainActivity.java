package com.softsquared.damoyoung.src.main;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;
import com.softsquared.damoyoung.src.bookmark.BookmarkActivity;
import com.softsquared.damoyoung.src.main.webView.WebViewFragment;
import com.softsquared.damoyoung.src.main.interfaces.MainActivityView;
import com.softsquared.damoyoung.src.quickBookmark.PopupBookmarkActivity;
import com.softsquared.damoyoung.src.quickBookmark.PopupBookmarkListViewAdapter;
import com.softsquared.damoyoung.src.setting.SettingActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;
import static com.softsquared.damoyoung.src.ApplicationClass.sSharedPreferences;

public class MainActivity extends BaseActivity implements MainActivityView {


    private ClipboardManager clipBoard;
    private boolean mPrimaryClipFlag, isFirstSearch;
    private ArrayList<MainListViewItem> mRecentItemList = new ArrayList<>();
    private MainListViewAdapter mRecentLvAdapter;
    private ListView mLvRecentHistory;
    private Gson gson;
    //    private ViewPager mMainViewPager;
    private TabLayout mMainTabLayout;
    //    private MainViewPagerAdapter mMainVpAdapter;
    private EditText mEtSearch;
    private String mKeyword = "";
    private ImageView mIvSetting, mIvFavority, mIvBookmark;
    private ArrayList<String> mEnglishUrlList = new ArrayList<>();
    private ArrayList<String> mFragmentTitles = new ArrayList<>();
    WebViewFragment mWebViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // edited here
            getWindow().setStatusBarColor(getColor(R.color.colorStatusBar));
        } else {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorStatusBar));
        }
        //dummy data
        mEnglishUrlList.add("https://www.google.com/search?q=");
        mEnglishUrlList.add("https://www.google.com.ua/search?safe=on&site=imghp&tbm=isch&q=");
        mEnglishUrlList.add("https://dict.naver.com/enendict/#/search?query=");
        mEnglishUrlList.add("https://alldic.daum.net/search.do?dic=ee&q=");
        mEnglishUrlList.add("https://www.ldoceonline.com/dictionary/");
        mEnglishUrlList.add("https://www.lexico.com/en/definition/");
        mEnglishUrlList.add("https://www.urbandictionary.com/define.php?term=");
        mEnglishUrlList.add("https://www.merriam-webster.com/dictionary/");
        mEnglishUrlList.add("https://en.wikipedia.org/wiki/");
        mEnglishUrlList.add("https://dictionary.cambridge.org/dictionary/english/ok?q=");
        mEnglishUrlList.add("https://en.wiktionary.org/wiki/");
        mEnglishUrlList.add("https://www.macmillandictionary.com/dictionary/british/");
        mEnglishUrlList.add("https://www.dictionary.com/browse/");
        mEnglishUrlList.add("https://www.thefreedictionary.com/");

        mFragmentTitles.add("Google");
        mFragmentTitles.add("Google Images");
        mFragmentTitles.add("Naver");
        mFragmentTitles.add("Daum");
        mFragmentTitles.add("Longman");
        mFragmentTitles.add("Oxford");
        mFragmentTitles.add("Urban");
        mFragmentTitles.add("Webster");
        mFragmentTitles.add("Wikipedia");
        mFragmentTitles.add("Cambridge");
        mFragmentTitles.add("Wiktionary");
        mFragmentTitles.add("Macmillan Dictionary");
        mFragmentTitles.add("Dictionary");
        mFragmentTitles.add("The Free Dictionary");


        mPrimaryClipFlag = true;
        isFirstSearch = true;

        //최근 목록 불러오기
        gson = new Gson();
        String json = sSharedPreferences.getString("recentList", "");
        Type type = new TypeToken<ArrayList<MainListViewItem>>() {
        }.getType();
        if (gson.fromJson(json, type) != null) {
            mRecentItemList = gson.fromJson(json, type);
        } else {
            //mRecentItemList 값 초기화
        }

        //디바이스값 아이디
//        String deviceId = Settings.Secure.getString(this.getContentResolver(),
//                Settings.Secure.ANDROID_ID);
//       System.out.println(deviceId);

        mMainTabLayout = findViewById(R.id.tl_main);
//        mMainViewPager = findViewById(R.id.vp_main);
        mEtSearch = findViewById(R.id.et_main_search);
        mLvRecentHistory = findViewById(R.id.lv_recent_history);


        mRecentLvAdapter = new MainListViewAdapter(mRecentItemList, getApplicationContext());
//        mMainVpAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mLvRecentHistory.setAdapter(mRecentLvAdapter); //리스트뷰의 어댑터


        //더미데이터 생성
        for (int i = 0; i < mFragmentTitles.size(); i++) {
            mMainTabLayout.addTab(mMainTabLayout.newTab().setText(mFragmentTitles.get(i)));
        }
//        replaceFragment(new WebViewFragment(mEnglishUrlList.get(0),mKeyword));

        //default
        replaceFragment(new WebViewFragment(mEnglishUrlList.get(0), "welcome"));

        mMainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                replaceFragment(new WebViewFragment(mEnglishUrlList.get(pos), mKeyword));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        clipBoard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        mLvRecentHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String keyword = mRecentItemList.get(i).getKeyword();
                mKeyword = keyword;
                mEtSearch.setText(mKeyword);
                addKeyword(mKeyword);// 최근 검색어 키워드 등록
                replaceFragment(new WebViewFragment(mEnglishUrlList.get(0), mKeyword));
                hideHistory();
                refresh();

            }
        });
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

        //검색창 리스너설정
        //클릭하면 URL과 키워드를 넘겨준다
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword = mEtSearch.getText().toString();
                    mKeyword = keyword;
                    if (isFirstSearch) {
                        hideHistory();
                        isFirstSearch = false;
                    }
                    addKeyword(keyword);// 최근 검색어 키워드 등록
                    new Handler().postDelayed(
                            new Runnable() {
                                @Override public void run() {
                                    mMainTabLayout.getTabAt(0).select();
                                }
                            }, 100);
//                    replaceFragment(new WebViewFragment(mEnglishUrlList.get(0), keyword));
                    refresh();
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });


        showHistory();
        refresh();//어댑터 갱신
    }

    private void replaceFragment(WebViewFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);

        transaction.commit();
    }

    //onClick 메소드
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_main_setting:
                //설정으로 이동
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.iv_main_favority:
                //팝업 액티비티 실행
                startActivity(new Intent(this, PopupBookmarkActivity.class));
                break;
            case R.id.iv_main_bookmark:
                //팝업 액티비티 실행
                startActivity(new Intent(this, BookmarkActivity.class));
                break;
        }
    }


    //이전에 검색한 적 있으면 지우고 최상단으로 올림
    public void addKeyword(String keyword) {
        removeKeyword(keyword);
        mRecentItemList.add(0, new MainListViewItem(keyword));
        mRecentLvAdapter.notifyDataSetChanged();
    }

    //같은 기록이 있으면 삭제
    public void removeKeyword(String keyword) {
        for (int i = 0; i < mRecentItemList.size(); i++) {
            if (mRecentItemList.get(i).getKeyword().equals(keyword)) {
                mRecentItemList.remove(i);
                break;
            }
        }
    }

    //onStop에서 저장
    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        String json = gson.toJson(mRecentItemList);
        editor.putString("recentList", json);
        editor.commit();
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
            System.out.println(pasteData + "클립보드에 데이터 X");
//
//        } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {
//            System.out.println(pasteData+"클립보드에 텍스트 타입X");
////
        } else {
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            pasteData = item.getText().toString();

        }

        System.out.println(pasteData + "저장된 예문");
//        Toast.makeText(this, pasteData+"예문이 저장되었습니다.", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void validateSuccess(String text) {

    }

    @Override
    public void validateFailure(String message) {

    }

    public void hideKeyboard() {
        mEtSearch.clearFocus();
        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public void refresh() {
        mRecentLvAdapter.notifyDataSetChanged();
    }

    public void hideHistory() {
        mLvRecentHistory.setVisibility(View.GONE);
    }

    public void showHistory() {
        mLvRecentHistory.setVisibility(View.VISIBLE);
    }


}
