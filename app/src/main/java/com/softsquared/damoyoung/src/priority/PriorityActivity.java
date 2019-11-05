package com.softsquared.damoyoung.src.priority;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;

import java.util.ArrayList;

public class PriorityActivity extends BaseActivity implements StartDragListener {
    RecyclerView mRvPriority;
    PriorityRecyclerViewAdapter mRvAdapter;
    ArrayList<String> mEngSitePriorityList = new ArrayList<>();
    ArrayList<String> mKorSitePriorityList = new ArrayList<>();
    private Button btnEngToEng,btnKorToEng;
    private ItemTouchHelper touchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_priority);

        mRvPriority = findViewById(R.id.rv_site_priority);

        btnEngToEng=findViewById(R.id.btn_eng_to_eng);
        btnKorToEng=findViewById(R.id.btn_kor_to_eng);
        populateRecyclerView();
    }

    private void populateRecyclerView() {
        mKorSitePriorityList.add("네이버");
        mKorSitePriorityList.add("다음");
        mKorSitePriorityList.add("구글");

        mEngSitePriorityList.add("Google");
        mEngSitePriorityList.add("Google Images");
        mEngSitePriorityList.add("Naver");
        mEngSitePriorityList.add("Daum");
        mEngSitePriorityList.add("Longman");
        mEngSitePriorityList.add("Oxford");
        mEngSitePriorityList.add("Urban");
        mEngSitePriorityList.add("Webster");
        mEngSitePriorityList.add("Wikipedia");
        mEngSitePriorityList.add("Wiktionary");
        mEngSitePriorityList.add("Macmillan Dictionary");
        mEngSitePriorityList.add("NetLingo");
        mEngSitePriorityList.add("The Free Dictionary");

        mRvAdapter = new PriorityRecyclerViewAdapter(mEngSitePriorityList,this);


        //아이템 터치 헬퍼를 리사이클러뷰의 장착한다.
        ItemTouchHelper.Callback callback =
                new ItemMoveCallback(mRvAdapter);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRvPriority);

        mRvPriority.setAdapter(mRvAdapter);
        mRvPriority.addItemDecoration(new DividerItemDecoration(mRvPriority.getContext(), DividerItemDecoration.VERTICAL));
    }


    public void requestDrag(RecyclerView.ViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_eng_to_eng :
                btnEngToEng.setBackground(getDrawable(R.drawable.custom_button_on));
                btnKorToEng.setBackground(getDrawable(R.drawable.custom_button_off));
                btnEngToEng.setTextColor(getResources().getColor(R.color.colorDamoyoungWhite));
                btnKorToEng.setTextColor(getResources().getColor(R.color.colorBlack));
                mRvAdapter.changeData(mEngSitePriorityList);
                mRvAdapter.notifyDataSetChanged();

                break;
            case R.id.btn_kor_to_eng :
                btnEngToEng.setBackground(getDrawable(R.drawable.custom_button_off));
                btnKorToEng.setBackground(getDrawable(R.drawable.custom_button_on));
                btnEngToEng.setTextColor(getResources().getColor(R.color.colorBlack));
                btnKorToEng.setTextColor(getResources().getColor(R.color.colorDamoyoungWhite));
                mRvAdapter.changeData(mKorSitePriorityList);
                mRvAdapter.notifyDataSetChanged();
                break;


            case R.id.iv_site_priority_arrow_back:
                finish();
                break;
        }
    }
}
