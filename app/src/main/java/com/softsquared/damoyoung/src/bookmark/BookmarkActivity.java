package com.softsquared.damoyoung.src.bookmark;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;
import com.softsquared.damoyoung.src.bookmark.bookmarkDialog.BookmarkNewfolderDialog;
import com.softsquared.damoyoung.src.bookmark.interfaces.BookmarkActivityView;
import com.softsquared.damoyoung.src.history.HistoryActivity;
import com.softsquared.damoyoung.src.wordbook.WordbookActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookmarkActivity extends BaseActivity implements BookmarkActivityView {
    private ArrayList<BookmarkListItem> mBookmarkListItems = new ArrayList<>();
    private BookmarkListViewAdapater mBookmarkListViewAdapater;
    private ListView mLvBookmark;
    private TextView mTvEdit, mTvConfirm, mTvNewFolder;
    private String mName;
    private int mBookmarkNo;
    private int mDeletePosition;
    private int mEditPostion;
    private boolean mEditMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        mEditMode=false;
        mLvBookmark = findViewById(R.id.lv_bookmark);
        mTvEdit = findViewById(R.id.tv_bookmark_edit);
        mTvConfirm = findViewById(R.id.tv_bookmark_confirm);
        mTvNewFolder = findViewById(R.id.tv_bookmark_add_folder);


        mBookmarkListViewAdapater = new BookmarkListViewAdapater(mBookmarkListItems, getApplicationContext(), this);
        mLvBookmark.setAdapter(mBookmarkListViewAdapater);


        mBookmarkListViewAdapater.setOnFolderNameModifyListener(new BookmarkListViewAdapater.OnFolderNameModifyListener() {
            @Override
            public void OnFolderModify(String name, int pos) {
                mEditPostion = pos;
                mName = name;
                int bookmarkNo = mBookmarkListItems.get(mEditPostion).getBookmarkNo();
                modifyBookmark(bookmarkNo, mName);
            }
        });
        mBookmarkListViewAdapater.setOnCheckedChangeListener(new BookmarkListViewAdapater.OnCheckedChangeListener() {
            @Override
            public void OnCheckClick(int pos) {
                mDeletePosition = pos;
                int bookmarkNo = mBookmarkListItems.get(mDeletePosition).getBookmarkNo();
                deleteBookmark(bookmarkNo);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getBookmark();
    }

    public void postBookmark(String name) {

        final BookmarkService bookmarkService = new BookmarkService(this);
        try {
            if (name.length() == 0) {
                showCustomToast("폴더명을 입력하세요");
                return;
            }
            JSONObject params = new JSONObject();
            params.put("name", name);
            bookmarkService.postBookmark(params);
        } catch (JSONException e) {
            showCustomToast("");
        }
    }

    private void getBookmark() {

        final BookmarkService bookmarkService = new BookmarkService(this);
        bookmarkService.getBookmark();
    }

    public void deleteBookmark(int bookmarkNo) {
        final BookmarkService bookmarkService = new BookmarkService(this);
        bookmarkService.deleteBookmark(bookmarkNo);
    }

    public void modifyBookmark(int bookmarkNo, String name) {
        final BookmarkService bookmarkService = new BookmarkService(this);
        try {
            if (bookmarkNo == 0) {
                showCustomToast("유효하지 않은 북마크 번호입니다");
                return;
            }
            if (name.length() == 0) {
                showCustomToast("폴더명을 입력하세요");
                return;
            }
            JSONObject params = new JSONObject();
            params.put("name", name);
            bookmarkService.patchBookmark(bookmarkNo, params);
        } catch (JSONException e) {
            showCustomToast("");
        }
    }

    //onClick
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bookmark_arrow_back:
                finish();
                break;
            case R.id.tv_bookmark_add_folder:
                BookmarkNewfolderDialog mBookmarkNewFolderDialog = new BookmarkNewfolderDialog(this);
                mBookmarkNewFolderDialog.setDialogListener(new BookmarkNewfolderDialog.BookmarkNewFolderDialogListener() {
                    @Override
                    public void onPositiveClicked(String name) {

                        //dialog의 editText 내용을 가져오는 부분
                        if (name.length() != 0) {
                            mName = name;
                            postBookmark(name);
                        }
                    }
                });
                mBookmarkNewFolderDialog.show();
                break;
            case R.id.tv_bookmark_edit:
                mEditMode=true;
              editMode();
                refresh();
                break;
            case R.id.tv_bookmark_confirm:
                mEditMode=false;
                normalMode();
                refresh();
                break;
        }

    }


    public void refresh() {
        mBookmarkListViewAdapater.notifyDataSetChanged();
    }

    @Override
    public void validateMakeSuccess(String text) {
        getBookmark();
    }

    @Override
    public void validateMakeFailure(String message) {
        showCustomToast(message);
    }

    @Override
    public void validateDeleteSuccess(String text) {
        mBookmarkListItems.remove(mDeletePosition);
        refresh();
    }

    @Override
    public void validateDeleteFailure(String message) {
        showCustomToast(message);
    }

    @Override
    public void validateGetSuccess(String text, ArrayList<BookmarkListItem> data) {
        mBookmarkListItems.clear();
        mBookmarkListItems.add(new BookmarkListItem(0, "기록"));
        mBookmarkListItems.get(0).setFirst(true);
        for (int i = 0; i < data.size(); i++) {
            mBookmarkListItems.add(new BookmarkListItem(data.get(i).getBookmarkNo(), data.get(i).getTitle()));
        }

        if(mEditMode){
            editMode();
        }else{
            normalMode();
        }
        mBookmarkListViewAdapater.notifyDataSetChanged();
    }

    @Override
    public void validateGetFailure(String message) {
        mBookmarkListItems.clear();
        mBookmarkListItems.add(new BookmarkListItem(0, "기록"));
        mBookmarkListItems.get(0).setFirst(true);
        mBookmarkListViewAdapater.notifyDataSetChanged();
    }

    public void editMode(){
        for (int i = 0; i < mBookmarkListItems.size(); i++) {
            mBookmarkListItems.get(i).setEditMode(true);
        }
        mTvEdit.setVisibility(View.GONE);
        mTvConfirm.setVisibility(View.VISIBLE);
        mTvNewFolder.setVisibility(View.GONE);
    }
    public void normalMode(){
        for (int i = 0; i < mBookmarkListItems.size(); i++) {
            mBookmarkListItems.get(i).setEditMode(false);
        }
        mTvEdit.setVisibility(View.VISIBLE);
        mTvConfirm.setVisibility(View.GONE);
        mTvNewFolder.setVisibility(View.VISIBLE);
    }
    @Override
    public void vaildateModifySuccess(String text) {
        getBookmark();
    }

    @Override
    public void vaildateModifyFailure(String text) {
        if (text!=null){
            showCustomToast(text);
        }else{
            showCustomToast("네트워크 연결 실패");
        }
    }
}
