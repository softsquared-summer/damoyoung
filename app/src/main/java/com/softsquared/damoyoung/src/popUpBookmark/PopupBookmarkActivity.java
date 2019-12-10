package com.softsquared.damoyoung.src.popUpBookmark;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;
import com.softsquared.damoyoung.src.bookmark.bookmarkDialog.BookmarkNewfolderDialog;
import com.softsquared.damoyoung.src.popUpBookmark.interfaces.PopUpBookmarkActivityView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PopupBookmarkActivity extends BaseActivity implements View.OnClickListener, PopUpBookmarkActivityView {

    //popup 액티비티 다이얼로그

    private ArrayList<PopupBookmarkListViewItem> mLvBookmarkItems = new ArrayList<>();
    private PopupBookmarkListViewAdapter mLvBookmarkAdapter;
    private ListView mLvBookmarkList;
    private String mWord, example;
    private int type;
    private String mName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_popup);


        mWord = getIntent().getStringExtra("word");
        example = getIntent().getStringExtra("sentence");
        type = getIntent().getIntExtra("type", 0);
        if (mWord == null) {
            mWord = "";
        }
        if (example == null) {
            example = "";
        }

        //findViewById
        mLvBookmarkList = findViewById(R.id.lv_popup_bookmark);
        mLvBookmarkAdapter = new PopupBookmarkListViewAdapter(mLvBookmarkItems, getApplicationContext());
        mLvBookmarkList.setAdapter(mLvBookmarkAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        getPopUpBookmark();

    }

    private void getPopUpBookmark() {

        final PopUpBookmarkService popUpbookmarkService = new PopUpBookmarkService(this);
        popUpbookmarkService.getPopUpBookmark();
    }

    public void postBookmark(String name) {

        final PopUpBookmarkService popUpBookmarkService = new PopUpBookmarkService(this);

        try {
            if (name.length() == 0) {
                showCustomToast("폴더명을 입력하세요");
                return;
            }
            JSONObject params = new JSONObject();
            params.put("name", name);
            popUpBookmarkService.postPopUpBookmark(params);
        } catch (JSONException e) {
            showCustomToast("");
        }
    }

    public void saveWord() {
        mLvBookmarkAdapter.notifyDataSetChanged();
        ArrayList<PopupBookmarkListViewItem> data = mLvBookmarkAdapter.getMainItem();
        ArrayList<Integer> bookmarkList = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isSelected()) {
                bookmarkList.add(data.get(i).getBookmarkNo());//선택된 북마크 번호를  순차적으로 리스트에 넣는다.
            }
        }

        if (bookmarkList.size() == 0) {
            showCustomToast("북마크를 선택해주세요");
            return;
        }
        JSONObject params = new JSONObject();

        try {
            JSONArray jArray = new JSONArray();//배열이 필요할때
            for (int i = 0; i < bookmarkList.size(); i++)//배열
            {
                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                sObject.put("markNum", bookmarkList.get(i));
                jArray.put(sObject);
            }
            params.put("word", mWord);
            params.put("example", example);
            params.put("type", type);
            params.put("bookmark", jArray);//배열을 넣음

            System.out.println(example);
            postWord(params);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void postWord(JSONObject param) {
        final PopUpBookmarkService popUpBookmarkService = new PopUpBookmarkService(this);
        popUpBookmarkService.postSaveWord(param);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_popup_new_folder:
                BookmarkNewfolderDialog mBookmarkNewfolderDialog = new BookmarkNewfolderDialog(this);
                mBookmarkNewfolderDialog.setDialogListener(new BookmarkNewfolderDialog.BookmarkNewFolderDialogListener() {
                    @Override
                    public void onPositiveClicked(String name) {

                        //dialog의 editText 내용을 가져오는 부분
                        if (name.length() != 0) {
                            mName = name;
                            postBookmark(name);
                        } else {
                            showCustomToast("북마크 제목을 입력하세요");
                        }
                    }
                });

                //API 낮은 버전에서 dialog 배경이 투명하지 않은 문제 해결
                mBookmarkNewfolderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mBookmarkNewfolderDialog.show();
                break;

            case R.id.tv_popup_ok:
                mLvBookmarkAdapter.notifyDataSetChanged();
                saveWord();
                break;

        }

    }

    @Override
    public void validateMakeSuccess(String text) {
        getPopUpBookmark();
    }

    @Override
    public void validateMakeFailure(String message) {
        showCustomToast(message);
    }

    @Override
    public void validateGetSuccess(String text, ArrayList<PopupBookmarkListViewItem> data) {
        mLvBookmarkItems.clear();
        for (int i = 0; i < data.size(); i++) {
            mLvBookmarkItems.add(new PopupBookmarkListViewItem(data.get(i).getBookmarkNo(), data.get(i).getKeyword()));
        }
        mLvBookmarkAdapter.notifyDataSetChanged();
    }

    @Override
    public void validateGetFailure(String message) {
        showCustomToast(message);
    }

    @Override
    public void validateSaveSuccess(String text) {
        showCustomToast(text);
        finish();
    }

    @Override
    public void validateSaveFailure(String message) {
        showCustomToast(message);
    }
}
