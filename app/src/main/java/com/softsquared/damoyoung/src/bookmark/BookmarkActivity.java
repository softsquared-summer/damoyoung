package com.softsquared.damoyoung.src.bookmark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;
import com.softsquared.damoyoung.src.main.MainListViewAdapter;
import com.softsquared.damoyoung.src.wordbook.WordbookActivity;

import java.util.ArrayList;

public class BookmarkActivity extends BaseActivity {
    private ArrayList<BookmarkListItem> mBookmarkListItems = new ArrayList<>();
    private BookmarkListViewAdapater mBookmarkListViewAdapater;
    private ListView mLvBookmark;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        mLvBookmark = findViewById(R.id.lv_bookmark);

        //dummy
        mBookmarkListItems.add(new BookmarkListItem("제목"));
        mBookmarkListItems.add(new BookmarkListItem("제목"));
        mBookmarkListItems.add(new BookmarkListItem("제목"));
        mBookmarkListItems.add(new BookmarkListItem("제목"));
        mBookmarkListItems.add(new BookmarkListItem("제목"));
        mBookmarkListItems.add(new BookmarkListItem("제목"));
        mBookmarkListItems.add(new BookmarkListItem("제목"));
        mBookmarkListItems.add(new BookmarkListItem("제목"));

        mBookmarkListItems.get(0).setFirst(true);

        mBookmarkListViewAdapater = new BookmarkListViewAdapater(mBookmarkListItems,getApplicationContext());
        mLvBookmark.setAdapter(mBookmarkListViewAdapater);

        mLvBookmark.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                startActivity(new Intent(BookmarkActivity.this, WordbookActivity.class));
            }
        });
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_bookmark_arrow_back :
                finish();
                break;
        }
    }

}
