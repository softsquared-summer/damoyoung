package com.softsquared.damoyoung.src.quickBookmark;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.softsquared.damoyoung.R;

import java.util.ArrayList;

public class PopupBookmarkActivity extends Activity implements View.OnClickListener {

    //popup 액티비티 다이얼로그


    private TextView tvNewFolder, tvConfirm;
    private ArrayList<PopupBookmarkListViewItem> mLvBookmarkItems = new ArrayList<>();
    private PopupBookmarkListViewAdapter mLvBookmarkAdapter;
    private ListView mLvBookmarkList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_popup);


        //findViewById
        tvNewFolder = findViewById(R.id.tv_popup_new_folder);
        tvConfirm = findViewById(R.id.tv_popup_ok);
        mLvBookmarkList = findViewById(R.id.lv_popup_bookmark);

        //list dummy

        mLvBookmarkItems.add(new PopupBookmarkListViewItem("나의 리스트", false));
        mLvBookmarkItems.add(new PopupBookmarkListViewItem("나의 리스트", false));
        mLvBookmarkItems.add(new PopupBookmarkListViewItem("나의 리스트", false));
        mLvBookmarkItems.add(new PopupBookmarkListViewItem("나의 리스트", false));
        mLvBookmarkItems.add(new PopupBookmarkListViewItem("나의 리스트", false));
        mLvBookmarkItems.add(new PopupBookmarkListViewItem("나의 리스트", false));
        mLvBookmarkAdapter = new PopupBookmarkListViewAdapter(mLvBookmarkItems, getApplicationContext());
        mLvBookmarkList.setAdapter(mLvBookmarkAdapter);
    }

    public void addFolder() {
        mLvBookmarkItems.add(new PopupBookmarkListViewItem("나의 리스트", true));
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_popup_new_folder:
                addFolder();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvNewFolder.setTextColor(getColor(R.color.colorUnableFolder));
                } else {
                    tvNewFolder.setTextColor(getResources().getColor(R.color.colorUnableFolder));
                }
                tvNewFolder.setEnabled(false);
                mLvBookmarkAdapter.notifyDataSetChanged();
                Toast.makeText(this, "new folder", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_popup_ok:
                mLvBookmarkAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Confirm", Toast.LENGTH_SHORT).show();
                break;

        }

    }


}
