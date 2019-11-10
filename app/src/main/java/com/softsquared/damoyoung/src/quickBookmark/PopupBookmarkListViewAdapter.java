package com.softsquared.damoyoung.src.quickBookmark;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.main.MainListViewItem;

import java.util.ArrayList;

public class PopupBookmarkListViewAdapter extends BaseAdapter {

    private ArrayList<PopupBookmarkListViewItem> mLvBookmarkItems;
    LayoutInflater mInflater;
    Context mContext;

    // ListViewAdapter의 생성자
    public PopupBookmarkListViewAdapter(ArrayList<PopupBookmarkListViewItem> items, Context context) {
        mLvBookmarkItems = items;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    //뷰홀더
    public class ViewHolder {
        EditText titleTextView;
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return mLvBookmarkItems.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        final ViewHolder holder;

        //역순으로 보여주는 리스트뷰 출력만 역순으로 해준다
        PopupBookmarkListViewItem popupBookmarkListViewItem = mLvBookmarkItems.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.listview_popup_bookmark_item, parent, false);
            holder.titleTextView = convertView.findViewById(R.id.et_popup_bookmark_folder_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //todo 리스트뷰 연결작업

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득

        // 아이템 내 각 위젯에 데이터 반영 데이터 파싱

        if (popupBookmarkListViewItem.isNew()) {
            holder.titleTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        int pos = getCount()-1;
                        final EditText wordEdit = (EditText) v;
                        mLvBookmarkItems.get(pos).setKeyword(wordEdit.getText().toString());
                        notifyDataSetChanged();
                    }
                }
            });



        } else {
            holder.titleTextView.setText(popupBookmarkListViewItem.getKeyword());
            holder.titleTextView.setEnabled(false);
            holder.titleTextView.setBackground(null);
        }
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            holder.titleTextView.setTextColor(mContext.getColor(R.color.colorBlack));
        } else {
            holder.titleTextView.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
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
        return mLvBookmarkItems.get(position);
    }
}
