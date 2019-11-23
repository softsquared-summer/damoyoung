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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        mLvBookmark = findViewById(R.id.lv_bookmark);
        mTvEdit = findViewById(R.id.tv_bookmark_edit);
        mTvConfirm = findViewById(R.id.tv_bookmark_confirm);
        mTvNewFolder = findViewById(R.id.tv_bookmark_add_folder);


        mBookmarkListViewAdapater = new BookmarkListViewAdapater(mBookmarkListItems, getApplicationContext(), this);
        mLvBookmark.setAdapter(mBookmarkListViewAdapater);
        mLvBookmark.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //기록 i==0
                if (i == 0) {
                    startActivity(new Intent(BookmarkActivity.this, HistoryActivity.class));
                } else {
                    mBookmarkNo = mBookmarkListItems.get(i).getBookmarkNo();
                    if (mBookmarkNo == 0) {
                        showCustomToast("북마크에 접근할 수 없습니다.");
                        return;
                    }
                    String bookmarkTitle = mBookmarkListItems.get(i).getTitle();
                    Intent intent = new Intent(BookmarkActivity.this, WordbookActivity.class);
                    intent.putExtra("bookmarkNo", mBookmarkNo);
                    intent.putExtra("bookmarkTitle", bookmarkTitle);
                    startActivity(intent);
                }
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
                for (int i = 0; i < mBookmarkListItems.size(); i++) {
                    mBookmarkListItems.get(i).setEditMode(true);
                }
                mTvEdit.setVisibility(View.GONE);
                mTvConfirm.setVisibility(View.VISIBLE);
                mTvNewFolder.setVisibility(View.GONE);
                refresh();
                break;
            case R.id.tv_bookmark_confirm:
                for (int i = 0; i < mBookmarkListItems.size(); i++) {
                    mBookmarkListItems.get(i).setEditMode(false);
                }
                mTvEdit.setVisibility(View.VISIBLE);
                mTvConfirm.setVisibility(View.GONE);
                mTvNewFolder.setVisibility(View.VISIBLE);
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
        mBookmarkListViewAdapater.notifyDataSetChanged();
    }

    @Override
    public void validateGetFailure(String message) {
        mBookmarkListItems.clear();
        mBookmarkListItems.add(new BookmarkListItem(0, "기록"));
        mBookmarkListItems.get(0).setFirst(true);
        mBookmarkListViewAdapater.notifyDataSetChanged();
    }
}
