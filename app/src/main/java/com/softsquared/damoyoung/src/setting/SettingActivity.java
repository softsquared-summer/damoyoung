package com.softsquared.damoyoung.src.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;
import com.softsquared.damoyoung.src.priority.PriorityActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class SettingActivity extends BaseActivity {

    private ExpandableListView mSettingExListView; // ExpandableListView 변수 선언
    private SettingExListViewAdapter mSettingExListViewAdapter; // 위 ExpandableListView를 받을 CustomAdapter(2번 class에 해당)를 선언

    private ArrayList<String> mParentList = new ArrayList<>(); // ExpandableListView의 Parent 항목이 될 List 변수 선언
    private ArrayList<String> mChildListFirstContent = new ArrayList<>(); // ExpandableListView의 Parent 항목이 될 List 변수 선언
    private ArrayList<String> mChildListSecondContent = new ArrayList<>(); // ExpandableListView의 Parent 항목이 될 List 변수 선언
    private ArrayList<String> mChildListThirdContent = new ArrayList<>(); // ExpandableListView의 Parent 항목이 될 List 변수 선언
    private HashMap<String, ArrayList<String>> mChildList = new HashMap<>(); // 위 ParentList와 ChildList를 연결할 HashMap 변수 선언


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mSettingExListView = findViewById(R.id.elv_setting);


        mParentList.add("앱 버전");
        mParentList.add("앱 소개");
        mParentList.add("개발자 정보");
        mChildListFirstContent.add(getString(R.string.app_version));
        mChildListSecondContent.add(getString(R.string.app_info));
        mChildListThirdContent.add(getString(R.string.developer_info));
        mChildList.put(mParentList.get(0), mChildListFirstContent);
        mChildList.put(mParentList.get(1), mChildListSecondContent);
        mChildList.put(mParentList.get(2), mChildListThirdContent);


        mSettingExListViewAdapter = new SettingExListViewAdapter(this, mParentList, mChildList);
        mSettingExListView.setAdapter(mSettingExListViewAdapter);
        mSettingExListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });
        mSettingExListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });
        mSettingExListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_setting_priority:
                startActivity(new Intent(this, PriorityActivity.class));
                break;
            case R.id.iv_setting_close:
                finish();
                break;
        }
    }
}
