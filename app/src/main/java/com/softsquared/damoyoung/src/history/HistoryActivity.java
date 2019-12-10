package com.softsquared.damoyoung.src.history;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;
import com.softsquared.damoyoung.src.history.historyDialog.HistoryDeleteDialog;
import com.softsquared.damoyoung.src.main.HistoryListViewItem;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.softsquared.damoyoung.src.ApplicationClass.sSharedPreferences;

public class HistoryActivity extends BaseActivity {
    private ArrayList<HistoryListViewItem> mHistoryListItems = new ArrayList<>();
    private HistoryListViewAdapter mHistoryListViewAdapter;
    private ListView mLvHistory;
    private TextView mTvEdit, mTvConfirm, mTvAllSelect, mTvDelete;
    private Gson gson;
    private boolean isAllSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        isAllSelect=false;

        mTvEdit = findViewById(R.id.tv_bookmark_history_edit);
        mTvConfirm = findViewById(R.id.tv_bookmark_history_confirm);
        mTvAllSelect = findViewById(R.id.tv_bookmark_history_all_select);
        mTvDelete = findViewById(R.id.tv_bookmark_history_delete);
        mLvHistory = findViewById(R.id.lv_bookmark_history);

        gson = new Gson();
        String json = sSharedPreferences.getString("recentList", "");
        Type type = new TypeToken<ArrayList<HistoryListViewItem>>() {
        }.getType();
        if (gson.fromJson(json, type) != null) {
            mHistoryListItems = gson.fromJson(json, type);
        } else { }

        mHistoryListViewAdapter = new HistoryListViewAdapter(mHistoryListItems, this);
        mLvHistory.setAdapter(mHistoryListViewAdapter);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        for (int i = 0; i < mHistoryListItems.size(); i++) {
            mHistoryListItems.get(i).setSelected(false);
        }
        mHistoryListViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        //종료 전 편집과 체크박스를 풀어주고 저장소에 값을 저장한다.
        for (int i = 0; i < mHistoryListItems.size(); i++) {
            mHistoryListItems.get(i).setChkShow(false);
            mHistoryListItems.get(i).setSelected(false);
        }
        mHistoryListViewAdapter.notifyDataSetChanged();
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        String json = gson.toJson(mHistoryListItems);
        editor.putString("recentList", json);
        editor.apply();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_history_arrow_back: //뒤로가기
                finish();
                break;
            case R.id.tv_bookmark_history_edit: //편집
                for (int i = 0; i < mHistoryListItems.size(); i++) {
                    mHistoryListItems.get(i).setChkShow(true);
                }
                mTvEdit.setVisibility(View.GONE);
                mTvAllSelect.setVisibility(View.VISIBLE);
                mTvDelete.setVisibility(View.VISIBLE);
                mTvConfirm.setVisibility(View.VISIBLE);
                mHistoryListViewAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_bookmark_history_all_select: //전체 선택
                if (isAllSelect){
                    for (int i = 0; i < mHistoryListItems.size(); i++) {
                        mHistoryListItems.get(i).setSelected(false);
                    }
                    isAllSelect=false;
                }
                else{
                    for (int i = 0; i < mHistoryListItems.size(); i++) {
                        mHistoryListItems.get(i).setSelected(true);
                    }
                    isAllSelect=true;
                }

                mHistoryListViewAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_bookmark_history_confirm:
                for (int i = 0; i < mHistoryListItems.size(); i++) {
                    mHistoryListItems.get(i).setChkShow(false);
                    mHistoryListItems.get(i).setSelected(false);
                }
                mTvEdit.setVisibility(View.VISIBLE);
                mTvAllSelect.setVisibility(View.GONE);
                mTvDelete.setVisibility(View.GONE);
                mTvConfirm.setVisibility(View.GONE);
                mHistoryListViewAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_bookmark_history_delete:

                //어댑터에서 아이템리스트를 가져와 현재 체크되어있는 갯수를 카운팅
                ArrayList<HistoryListViewItem> data = mHistoryListViewAdapter.getItemList();
                int count = 0;
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).isSelected()) {count++;}
                }
                if (count != 0) {
                    //하나 이상 체크 된 경우 삭제 다이얼로그 띄우기
                    HistoryDeleteDialog mDeleteDialog = new HistoryDeleteDialog(this);
                    mDeleteDialog.setDialogListener(new HistoryDeleteDialog.DeleteDialogListener() {
                        @Override
                        public void onPositiveClicked() {
                            //다이얼로그에서 삭제를 눌렀을 경우 선택한 단어를 삭제한다
                            ArrayList<HistoryListViewItem> data = mHistoryListViewAdapter.getItemList();
                            int size=data.size()-1;
                            for (int i=size; i >= 0; i--) {
                                if (data.get(i).isSelected()) {
                                    mHistoryListItems.remove(i);
                                }
                            }
                            showCustomToast("삭제되었습니다.");
                            mHistoryListViewAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onNegativeClicked() {
                            ArrayList<HistoryListViewItem> data = mHistoryListViewAdapter.getItemList();
                            for (int i = 0; i < data.size(); i++) {
                                mHistoryListItems.get(i).setSelected(false);
                            }
                            mHistoryListViewAdapter.notifyDataSetChanged();
                        }
                    });
                    mDeleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    mDeleteDialog.show();
                } else showCustomToast("단어를 하나 이상 선택해주세요");


        }

    }
}
