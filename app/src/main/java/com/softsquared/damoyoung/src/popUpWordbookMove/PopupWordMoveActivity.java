package com.softsquared.damoyoung.src.popUpWordbookMove;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;
import com.softsquared.damoyoung.src.popUpWordbookMove.interfaces.PopupWordMoveActivityView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PopupWordMoveActivity extends BaseActivity implements View.OnClickListener, PopupWordMoveActivityView {

    //popup 액티비티 다이얼로그


    private ArrayList<PopupWordMoveListViewItem> mLvBookmarkItems = new ArrayList<>();
    private PopupWordMoveListViewAdapter mLvBookmarkAdapter;
    private ListView mLvBookmarkList;
    private ArrayList<Integer> mWordList = new ArrayList<>();
    private int mBookmarkNo = 0;
    private int mNewBookmarkNo = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordbook_move);

        mWordList = (ArrayList<Integer>) getIntent().getSerializableExtra("wordNo");
        mBookmarkNo = getIntent().getIntExtra("bookmarkNo", 0);

        //findViewById
        mLvBookmarkList = findViewById(R.id.lv_wordbook_dialog_move);
        mLvBookmarkAdapter = new PopupWordMoveListViewAdapter(mLvBookmarkItems, getApplicationContext());
        mLvBookmarkList.setAdapter(mLvBookmarkAdapter);

        //listenr
        mLvBookmarkAdapter.setOnCheckedChangeListener(new PopupWordMoveListViewAdapter.OnCheckedChangeListener() {
            @Override
            public void OnCheckClick(int pos) {
                for (int i = 0; i < mLvBookmarkAdapter.getCount(); i++) {
                    if (i == pos) {
                        mLvBookmarkItems.get(i).setChecked(true);
                    } else {
                        mLvBookmarkItems.get(i).setChecked(false);
                    }
                }
                mLvBookmarkAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        getPopUpBookmark();

    }

    private void getPopUpBookmark() {
        final PopupWordMoveService popupWordMoveService = new PopupWordMoveService(this);
        popupWordMoveService.getPopUpBookmark();
    }


    public void moveWord() {
        mLvBookmarkAdapter.notifyDataSetChanged();
        ArrayList<PopupWordMoveListViewItem> data = mLvBookmarkAdapter.getMainItem();

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isSelected()) {
                mNewBookmarkNo = data.get(i).getBookmarkNo();//선택된 북마크 번호를  순차적으로 리스트에 넣는다.
            }
        }

        if (mNewBookmarkNo == 0) {
            showCustomToast("북마크를 선택해주세요");
            return;
        }

        if (mNewBookmarkNo == mBookmarkNo) {
            showCustomToast("같은 단어장으론 이동할 수 없습니다.");
            return;
        }
        try {
            JSONArray wordArray = new JSONArray();//배열이 필요할때
            for (int i = 0; i < mWordList.size(); i++)//배열
            {
                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                sObject.put("wordNo", mWordList.get(i));
                wordArray.put(sObject);
            }

            JSONObject params = new JSONObject();
            params.put("wordList", wordArray);//배열을 넣음
            params.put("bookmarkNo", mBookmarkNo);
            params.put("newbookmarkNo", mNewBookmarkNo);

            patchWordMove(params);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void patchWordMove(JSONObject param) {
        final PopupWordMoveService popupWordMoveService = new PopupWordMoveService(this);
        popupWordMoveService.patchWordMove(param);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wordbook_move_dialog_confirm:
                mLvBookmarkAdapter.notifyDataSetChanged();
                moveWord();
                break;

        }

    }

    @Override
    public void validateGetSuccess(String text, ArrayList<PopupWordMoveListViewItem> data) {
        mLvBookmarkItems.clear();
        for (int i = 0; i < data.size(); i++) {
            mLvBookmarkItems.add(new PopupWordMoveListViewItem(data.get(i).getBookmarkNo(), data.get(i).getKeyword()));
        }
        mLvBookmarkAdapter.notifyDataSetChanged();
    }

    @Override
    public void validateGetFailure(String message) {
        showCustomToast(message);
    }

    @Override
    public void validateMoveSuccess(String text) {
        showCustomToast(text);
        finish();
    }

    @Override
    public void validateMoveFailure(String message) {
        showCustomToast(message);
    }


}
