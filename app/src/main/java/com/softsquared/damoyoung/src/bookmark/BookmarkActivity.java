package com.softsquared.damoyoung.src.bookmark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;
import com.softsquared.damoyoung.src.bookmark.bookmarkDialog.BookmarkNewfolderDialog;
import com.softsquared.damoyoung.src.history.HistoryActivity;
import com.softsquared.damoyoung.src.wordbook.WordbookActivity;

import java.util.ArrayList;

public class BookmarkActivity extends BaseActivity {
    private ArrayList<BookmarkListItem> mBookmarkListItems = new ArrayList<>();
    private BookmarkListViewAdapater mBookmarkListViewAdapater;
    private ListView mLvBookmark;
    private TextView mTvEdit,mTvConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        mLvBookmark = findViewById(R.id.lv_bookmark);
        mTvEdit = findViewById(R.id.tv_bookmark_edit);
        mTvConfirm = findViewById(R.id.tv_bookmark_confirm);

        //dummy
        mBookmarkListItems.add(new BookmarkListItem("기록"));
        mBookmarkListItems.add(new BookmarkListItem("나의 리스트"));
        mBookmarkListItems.add(new BookmarkListItem("안녕 "));
        mBookmarkListItems.get(0).setFirst(true);

        mBookmarkListViewAdapater = new BookmarkListViewAdapater(mBookmarkListItems, getApplicationContext(),this);
        mLvBookmark.setAdapter(mBookmarkListViewAdapater);

        mLvBookmark.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i==0){
                    startActivity(new Intent(BookmarkActivity.this, HistoryActivity.class));
                }else{
                    startActivity(new Intent(BookmarkActivity.this, WordbookActivity.class));

                }
            }
        });
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
                        if (name.length()!=0){
                           addBookmark(name);
                        }
                    }
                });

                mBookmarkNewFolderDialog.show();
                break;
            case R.id.tv_bookmark_edit :
                for(int i=0;i<mBookmarkListItems.size();i++){
                    mBookmarkListItems.get(i).setEditMode(true);
                }
                mTvEdit.setVisibility(View.GONE);
                mTvConfirm.setVisibility(View.VISIBLE);
                refresh();
                break;
        case R.id.tv_bookmark_confirm :
            for(int i=0;i<mBookmarkListItems.size();i++){
                mBookmarkListItems.get(i).setEditMode(false);
            }
            mTvEdit.setVisibility(View.VISIBLE);
            mTvConfirm.setVisibility(View.GONE);
            refresh();
            break;
        }

    }


    public void addBookmark(String name){
        mBookmarkListItems.add(new BookmarkListItem(name));
        mBookmarkListViewAdapater.notifyDataSetChanged();
    }

    public void refresh(){
        mBookmarkListViewAdapater.notifyDataSetChanged();
    }
}
