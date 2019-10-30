package com.softsquared.damoyoung.src.Main;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;
import com.softsquared.damoyoung.src.Main.WebViewFirst.LongmanWebViewFragment;
import com.softsquared.damoyoung.src.Main.WebViewNaver.NaverWebViewFragment;
import com.softsquared.damoyoung.src.Main.interfaces.MainActivityView;
import com.softsquared.damoyoung.src.Setting.SettingActivity;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class MainActivity extends BaseActivity implements MainActivityView {


    ClipboardManager clipBoard;
    boolean mPrimaryClipFlag = true;



    private ViewPager mMainViewPager;
    private TabLayout mMainTabLayout;
    private MainViewPagerAdapter mMainVpAdapter;
    private EditText mEtSearch;
    private String keyword="welcome";
    private ImageView mIvSetting,mIvFavority,mIvBookmark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //디바이스값 아이디
//        String deviceId = Settings.Secure.getString(this.getContentResolver(),
//                Settings.Secure.ANDROID_ID);
//       System.out.println(deviceId);
        mMainTabLayout = findViewById(R.id.tl_main);
        mMainViewPager = findViewById(R.id.vp_main);
        mEtSearch = findViewById(R.id.et_main_search);

        mMainVpAdapter = new MainViewPagerAdapter(getSupportFragmentManager());

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

        mMainTabLayout.setupWithViewPager(mMainViewPager); //Viewpager와 TabLayout을 연결해주는 코드!
        mMainViewPager.setAdapter(mMainVpAdapter); //Viewpager에 선택된 fragment를 띄워준다.

        mEtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //Enter키눌렀을떄 처리
                    String keyword = mEtSearch.getText().toString();

                    ((NaverWebViewFragment) mMainVpAdapter.getItem(0)).updateData(keyword);
                    ((LongmanWebViewFragment) mMainVpAdapter.getItem(1)).updateData(keyword);
                    refresh();
                    hideKeyboard();
                    Toast.makeText(MainActivity.this, "검색완료", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {

               if(actionId==EditorInfo.IME_ACTION_SEARCH){
                   String keyword = mEtSearch.getText().toString();

                   ((NaverWebViewFragment) mMainVpAdapter.getItem(0)).updateData(keyword);
                   ((LongmanWebViewFragment) mMainVpAdapter.getItem(1)).updateData(keyword);
                   refresh();
                   hideKeyboard();
                   return true;
               }
                return false;
            }
        });


    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_main_setting :
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }

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

    public  void hideKeyboard(){
        mEtSearch.clearFocus();
        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public void refresh(){
        mMainVpAdapter.notifyDataSetChanged();
    }
}
