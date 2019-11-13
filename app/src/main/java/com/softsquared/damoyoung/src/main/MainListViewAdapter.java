package com.softsquared.damoyoung.src.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softsquared.damoyoung.R;

import java.util.ArrayList;

public class MainListViewAdapter extends BaseAdapter {

    private ArrayList<HistoryListViewItem> mRecentItemList;
    LayoutInflater mInflater;
    // ListViewAdapter의 생성자
    public MainListViewAdapter(ArrayList<HistoryListViewItem> items, Context context) {
        mRecentItemList =items;
        mInflater=LayoutInflater.from(context);
    }
    //뷰홀더
    public class ViewHolder {
        TextView titleTextView;
    }
    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return mRecentItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        final ViewHolder holder;

        //역순으로 보여주는 리스트뷰 출력만 역순으로 해준다
        HistoryListViewItem recentlistViewItem = mRecentItemList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.listview_main_recent_item, parent, false);
            holder.titleTextView = convertView.findViewById(R.id.tv_recent_search_name);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득

        // 아이템 내 각 위젯에 데이터 반영 데이터 파싱
        holder.titleTextView.setText(recentlistViewItem.getKeyword());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return mRecentItemList.get(position);
    }

}
