package com.softsquared.damoyoung.src.main;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;
import com.softsquared.damoyoung.src.bookmark.BookmarkActivity;
import com.softsquared.damoyoung.src.webView.WebViewFragment;
import com.softsquared.damoyoung.src.popUpBookmark.PopupBookmarkActivity;
import com.softsquared.damoyoung.src.setting.SettingActivity;
import com.softsquared.damoyoung.src.sitePriority.SitePriority;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.softsquared.damoyoung.src.ApplicationClass.sSharedPreferences;

public class MainActivity extends BaseActivity {


    private ClipboardManager clipBoard;
    private boolean mPrimaryClipFlag, isFirstSearch;
    private ArrayList<HistoryListViewItem> mRecentItemList = new ArrayList<>();
    private MainListViewAdapter mRecentLvAdapter;
    private ListView mLvRecentHistory;
    private Gson gson;
    private TabLayout mMainTabLayout;
    private EditText mEtSearch;
    private long time = 0;
    private String mKeyword = "";
    private String mSentence = "";
    private boolean mStringTypeIsKorean;
    private TextView mTvHistoryText;//최근 검색어 보여주는 텍스트뷰
    ArrayList<SitePriority> mEngSitePriorityList = new ArrayList<>();
    ArrayList<SitePriority> mKorSitePriorityList = new ArrayList<>();
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new Gson();
        mPrimaryClipFlag = true;
        isFirstSearch = true;
        mStringTypeIsKorean = false;
        //FindViewById
        mTvHistoryText = findViewById(R.id.tv_main_no_recent_history);
        mMainTabLayout = findViewById(R.id.tl_main);
        mEtSearch = findViewById(R.id.et_main_search);
        mLvRecentHistory = findViewById(R.id.lv_recent_history);

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

        mLvRecentHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String keyword = mRecentItemList.get(i).getKeyword();
                mKeyword = keyword;
                mEtSearch.setText(mKeyword);
                addKeyword(mKeyword);// 최근 검색어 키워드 등록
                if (stringType(mKeyword)) {
                    clearTab();
                    addTab(mKorSitePriorityList);
                    replaceFragment(new WebViewFragment(mKorSitePriorityList.get(0).getUrl(), mKeyword));
                } else {
                    clearTab();
                    addTab(mEngSitePriorityList);
                    replaceFragment(new WebViewFragment(mEngSitePriorityList.get(0).getUrl(), mKeyword));

                }
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
                    }
                    mTvHistoryText.setVisibility(View.GONE);
                    addKeyword(mKeyword);// 최근 검색어 키워드 등록
                    //검색어가 영어 일경우와 한국어일 경우 보여주는 사이트가 다름
                    //탭 레이아웃을 재 갱신해주는 부분
                    if (stringType(mKeyword)) {
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
                    refresh();
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });
        showHistory();

    }


    @Override
    protected void onStart() {
        super.onStart();
        //저장소에 있는 값 저장

        mRecentItemList.clear();
        String json = sSharedPreferences.getString("recentList", "");
        Type type = new TypeToken<ArrayList<HistoryListViewItem>>() {
        }.getType();
        if (gson.fromJson(json, type) != null) {
            mRecentItemList = gson.fromJson(json, type);
        } else {
        }

        //아직 검색창이 뜨지 않았을 경우
        if (isFirstSearch) {
            if (mRecentItemList.size() == 0) {
                mTvHistoryText.setVisibility(View.VISIBLE);
            } else {
                mTvHistoryText.setVisibility(View.GONE);
            }
        }

        mRecentLvAdapter = new MainListViewAdapter(mRecentItemList, getApplicationContext());
        mLvRecentHistory.setAdapter(mRecentLvAdapter); //리스트뷰의 어댑터
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

        mRecentLvAdapter.notifyDataSetChanged();


    }


    //fragment 교체 함수
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

                // mis-clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                // do your magic here
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.iv_main_favority:
                // mis-clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                //팝업 액티비티 실행
                if (isFirstSearch) {

                } else {
                    Intent intent = new Intent(this, PopupBookmarkActivity.class);
                    intent.putExtra("word", mKeyword);
                    intent.putExtra("sentence", mSentence);
                    intent.putExtra("type", 0);
                    startActivity(intent);
                }
                break;
            case R.id.iv_main_bookmark:
                //팝업 액티비티 실행
                // mis-clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                startActivity(new Intent(this, BookmarkActivity.class));
                break;
        }
    }

    //onStop에서 저장
    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        String json = gson.toJson(mRecentItemList);
        editor.putString("recentList", json);
        editor.apply();
    }

    @Override
    public void onActionModeStarted(final android.view.ActionMode mode) {
        final Menu menu = mode.getMenu();
        //안드로이드 자체 editText에 menu 인경우 Copy의 id는 16908321

//        final int copy1 = 34276370;
        final int copy1 = 34210660;//webview 내의 글씨의 copy
        final int copy2 = 16908321;//menu.getItem(1).getItemId()

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


        if (isFirstSearch) {
            showCustomToast("먼저 단어를 검색해주세요");
        } else {
            Intent intent = new Intent(this, PopupBookmarkActivity.class);
            intent.putExtra("word", mKeyword);
            intent.putExtra("sentence", mSentence);
            intent.putExtra("type", 1);
            startActivity(intent);
        }

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

    public void hideKeyboard() {
        mEtSearch.clearFocus();
        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public void refresh() {
        mRecentLvAdapter.notifyDataSetChanged();
    }

    //이전에 검색한 적 있으면 지우고 최상단으로 올림
    public void addKeyword(String keyword) {
        removeKeyword(keyword);
        mRecentItemList.add(0, new HistoryListViewItem(keyword));
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

    public void hideHistory() {
        mLvRecentHistory.setVisibility(View.GONE);
        mTvHistoryText.setVisibility(View.GONE);
        isFirstSearch = false;
    }

    public void showHistory() {
        mLvRecentHistory.setVisibility(View.VISIBLE);
        if (mRecentItemList.size() == 0) {
            mTvHistoryText.setVisibility(View.VISIBLE);
        } else {
            mTvHistoryText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() - time < 2000) {
            finish();
            return;
        }
    }


}
