package com.softsquared.damoyoung.src.history;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.main.HistoryListViewItem;
import com.softsquared.damoyoung.src.searchResult.SearchResultActivity;

import java.util.ArrayList;

public class HistoryListViewAdapter extends BaseAdapter {

    ArrayList<HistoryListViewItem> mHistoryItemList;
    LayoutInflater mInflater;
    Context mContext;

    // ListViewAdapter의 생성자
    public HistoryListViewAdapter(ArrayList<HistoryListViewItem> items, Context context) {
        mHistoryItemList = items;
        mInflater = LayoutInflater.from(context);
        mContext =context;
    }

    //뷰홀더
    public class ViewHolder {
        TextView titleTextView;
        CheckBox chkWord;
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return mHistoryItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        final ViewHolder holder;

        //리스트뷰 출력
        final HistoryListViewItem historyItem = mHistoryItemList.get(pos);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.listview_history_item, parent, false);
            holder.titleTextView = convertView.findViewById(R.id.tv_history_word);
            holder.chkWord = convertView.findViewById(R.id.cb_history);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득

        // 아이템 내 각 위젯에 데이터 반영 데이터 파싱
        holder.titleTextView.setText(historyItem.getKeyword());

        if (historyItem.getChkShow()) {
            holder.chkWord.setVisibility(View.VISIBLE);
            //check box logic
            //체크박스상태가 풀리지않는다.
            holder.chkWord.setOnCheckedChangeListener(null);
            holder.chkWord.setChecked(historyItem.isSelected());
            holder.chkWord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    historyItem.setSelected(isChecked);
                }
            });
            holder.titleTextView.setOnClickListener(null);

        } else {
            holder.chkWord.setVisibility(View.GONE);
            holder.titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SearchResultActivity.class);
                    intent.putExtra("keyword",historyItem.getKeyword());
                    mContext.startActivity(intent);
                }
            });
        }

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
        return mHistoryItemList.get(position);
    }

    public ArrayList<HistoryListViewItem> getItemList() {
        return mHistoryItemList;
    }

}
