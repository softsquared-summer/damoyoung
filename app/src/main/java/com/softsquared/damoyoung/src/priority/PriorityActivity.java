package com.softsquared.damoyoung.src.priority;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;
import com.softsquared.damoyoung.src.sitePriority.SitePriority;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.softsquared.damoyoung.src.ApplicationClass.sSharedPreferences;

public class PriorityActivity extends BaseActivity implements StartDragListener {
    RecyclerView mRvPriority;
    PriorityRecyclerViewAdapter mRvAdapter;
    ArrayList<SitePriority> mEngSitePriorityList = new ArrayList<>();
    ArrayList<SitePriority> mKorSitePriorityList = new ArrayList<>();
    private Button btnEngToEng,btnKorToEng;
    private ItemTouchHelper touchHelper;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_priority);

        mRvPriority = findViewById(R.id.rv_site_priority);

        btnEngToEng=findViewById(R.id.btn_eng_to_eng);
        btnKorToEng=findViewById(R.id.btn_kor_to_eng);
        //최근 목록 불러오기
        gson = new Gson();
        String engJson = sSharedPreferences.getString("siteEngList", "");
        String korJson = sSharedPreferences.getString("siteKorList", "");
        Type type = new TypeToken<ArrayList<SitePriority>>() {
        }.getType();
        if (gson.fromJson(engJson, type) != null) {
            mEngSitePriorityList = gson.fromJson(engJson, type);
        } else {
            mEngSitePriorityList.add(new SitePriority("Google","https://www.google.com/search?q="));
            mEngSitePriorityList.add(new SitePriority("Naver","https://dict.naver.com/enendict/#/search?query="));
            mEngSitePriorityList.add(new SitePriority("Daum","https://alldic.daum.net/search.do?dic=ee&q="));
            mEngSitePriorityList.add(new SitePriority("Longman","https://www.ldoceonline.com/dictionary/"));
            mEngSitePriorityList.add(new SitePriority("Oxford","https://www.lexico.com/en/definition/"));
            mEngSitePriorityList.add(new SitePriority("Urban","https://www.urbandictionary.com/define.php?term="));
            mEngSitePriorityList.add(new SitePriority("Webster","https://www.merriam-webster.com/dictionary/"));
            mEngSitePriorityList.add(new SitePriority("Wikipedia","https://en.wikipedia.org/wiki/"));
            mEngSitePriorityList.add(new SitePriority("Cambridge","https://dictionary.cambridge.org/dictionary/english/ok?q="));
            mEngSitePriorityList.add(new SitePriority("Wiktionary","https://en.wiktionary.org/wiki/"));
            mEngSitePriorityList.add(new SitePriority("Macmillan Dictionary","https://www.macmillandictionary.com/dictionary/british/"));
            mEngSitePriorityList.add(new SitePriority("NetLingo","https://www.dictionary.com/browse/"));
            mEngSitePriorityList.add(new SitePriority("The Free Dictionary","https://www.thefreedictionary.com/"));

        }

        if (gson.fromJson(korJson, type) != null) {
            mKorSitePriorityList = gson.fromJson(korJson, type);
        } else {
            mKorSitePriorityList.add(new SitePriority("네이버","https://endic.naver.com/search.nhn?sLn=kr&searchOption=all&query="));
            mKorSitePriorityList.add(new SitePriority("다음","https://dic.daum.net/search.do?dic=eng&q="));
            mKorSitePriorityList.add(new SitePriority("구글","https://www.google.com/search?q="));
        }
        populateRecyclerView();
    }

    private void populateRecyclerView() {

        mRvAdapter = new PriorityRecyclerViewAdapter(mEngSitePriorityList,this,getApplicationContext());


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

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        String engJson = gson.toJson(mEngSitePriorityList);
        String korJson = gson.toJson(mKorSitePriorityList);
        editor.putString("siteEngList", engJson);
        editor.putString("siteKorList", korJson);
        editor.commit();
    }
}
