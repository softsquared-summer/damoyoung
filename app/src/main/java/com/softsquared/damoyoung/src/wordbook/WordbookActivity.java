package com.softsquared.damoyoung.src.wordbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.behavior.SwipeDismissBehavior;
import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class WordbookActivity extends BaseActivity {

    private ExpandableListView mExLvWordbook; // ExpandableListView 변수 선언
    private WordbookExListViewAdapter mWordbookExListViewAdapter; // 위 ExpandableListView를 받을 CustomAdapter(2번 class에 해당)를 선언

    private ArrayList<String> mWordList = new ArrayList<>(); // ExpandableListView의 Parent 항목이 될 List 변수 선언
    private ArrayList<String> mSentenceList1 = new ArrayList<>(); // ExpandableListView의 Child 항목이 될 List를 각각 선언
    private ArrayList<String> mSentenceList2 = new ArrayList<>(); // ExpandableListView의 Child 항목이 될 List를 각각 선언
    private ArrayList<String> mSentenceList3 = new ArrayList<>(); // ExpandableListView의 Child 항목이 될 List를 각각 선언
    private HashMap<String, ArrayList<String>> mChildList = new HashMap<>(); // 위 ParentList와 ChildList를 연결할 HashMap 변수 선언

    private TextView mTvwordbookTitle;
    private Button btnMemorized,btnUnmemorized;

    //    private ArrayList<MyPageListData> mProductList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordbook);

        //findViewById
        mTvwordbookTitle = findViewById(R.id.tv_wordbook_title);
        btnMemorized = findViewById(R.id.btn_memorization);
        btnUnmemorized = findViewById(R.id.btn_unmemorization);

        mExLvWordbook = findViewById(R.id.elv_wordbook);


        mExLvWordbook.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });
        mExLvWordbook.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });
        mExLvWordbook.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        //dummy
            mWordList.add("Hello");
            mWordList.add("Indicator");
            mWordList.add("School");
            mSentenceList1.add("Hello this is brand new SanE hoooooooooooowwwwwwwww");
        mSentenceList1.add("Hello this is brand new SanE hoooooooooooowwwwwwwww");
        mSentenceList2.add("Hello this is brand new SanE hoooooooooooowwwwwwwww");
        mSentenceList2.add("Hello this is brand new SanE hoooooooooooowwwwwwwww");
        mSentenceList2.add("Hello this is brand new SanE hoooooooooooowwwwwwwww");
        mSentenceList2.add("Hello this is brand new SanE hoooooooooooowwwwwwwww");
            mSentenceList3.add("Hello this is brand new SanE hoooooooooooowwwwwwwww");
        mSentenceList3.add("Hello this is brand new SanE hoooooooooooowwwwwwwww");
        validateSuccess();

        }



//    private void getMyPage() {
//
//        final MyPageService myPageService = new MyPageService(this);
//        showProgressDialog();
//        myPageService.getMyPage();
//    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_memorization:
                btnMemorized.setBackground(getDrawable(R.drawable.custom_button_on));
                btnUnmemorized.setBackground(getDrawable(R.drawable.custom_button_off));
                btnMemorized.setTextColor(getResources().getColor(R.color.colorDamoyoungWhite));
                btnUnmemorized.setTextColor(getResources().getColor(R.color.colorBlack));
                break;
            case R.id.btn_unmemorization:
                btnMemorized.setBackground(getDrawable(R.drawable.custom_button_off));
                btnUnmemorized.setBackground(getDrawable(R.drawable.custom_button_on));
                btnMemorized.setTextColor(getResources().getColor(R.color.colorBlack));
                btnUnmemorized.setTextColor(getResources().getColor(R.color.colorDamoyoungWhite));
                break;
            case R.id.iv_wordbook_arrow_back:
                finish();
                break;
            case R.id.iv_wordbook_all_arrow_down:
                break;
        }
    }

    public void validateSuccess() {


        mChildList.put(mWordList.get(0), mSentenceList1);//0번째 인자에 항상 새로운 값이 들어오기 때문이다.
        mChildList.put(mWordList.get(1), mSentenceList2);//0번째 인자에 항상 새로운 값이 들어오기 때문이다.
        mChildList.put(mWordList.get(2), mSentenceList3);//0번째 인자에 항상 새로운 값이 들어오기 때문이다.


        mWordbookExListViewAdapter = new WordbookExListViewAdapter(this, mWordList, mChildList);
        mExLvWordbook.setAdapter(mWordbookExListViewAdapter);
        mWordbookExListViewAdapter.notifyDataSetChanged();
    }

//
//    @Override
//    public void validateFailure(String message) {
//
//        hideProgressDialog();
//        showCustomToast(message);
//    }
}
