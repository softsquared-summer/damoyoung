package com.softsquared.damoyoung.src.Main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.softsquared.damoyoung.src.Main.WebViewFirst.LongmanWebViewFragment;
import com.softsquared.damoyoung.src.Main.WebViewNaver.NaverWebViewFragment;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private static int PAGE_NUMBER = 2;
    NaverWebViewFragment mNaverWebViewFragment;
    LongmanWebViewFragment mLongmanWebViewFragment;
    private ArrayList<String> mFragmentTitles  = new ArrayList<>();

    MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mNaverWebViewFragment = new NaverWebViewFragment("welcome");
        mLongmanWebViewFragment = new LongmanWebViewFragment("welcome");
    } //꼭 있어야함


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mNaverWebViewFragment;
            case 1:
                return mLongmanWebViewFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }

    //제목 설정
    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position) {
            case 0:
                return "Longman";
            case 1:
                return "Naver";
            default:
                return null;
        }
    }



    }

