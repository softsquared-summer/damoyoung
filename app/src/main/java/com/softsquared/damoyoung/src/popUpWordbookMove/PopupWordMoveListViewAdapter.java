package com.softsquared.damoyoung.src.popUpWordbookMove;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.softsquared.damoyoung.R;

import java.util.ArrayList;

public class PopupWordMoveListViewAdapter extends BaseAdapter {

    private ArrayList<PopupWordMoveListViewItem> mLvBookmarkItems;
    LayoutInflater mInflater;
    Context mContext;

    // ListViewAdapter의 생성자
    public PopupWordMoveListViewAdapter(ArrayList<PopupWordMoveListViewItem> items, Context context) {
        mLvBookmarkItems = items;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    //뷰홀더
    public class ViewHolder {
        TextView titleTextView;
        CheckBox chkBookmark;
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
        final PopupWordMoveListViewItem popupBookmarkListViewItem = mLvBookmarkItems.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.listview_popup_bookmark_item, parent, false);
            holder.titleTextView = convertView.findViewById(R.id.tv_popup_bookmark_folder_name);
            holder.chkBookmark = convertView.findViewById(R.id.chk_popup_bookmark_folder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //todo 리스트뷰 연결작업

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득

        // 아이템 내 각 위젯에 데이터 반영 데이터 파싱

            holder.titleTextView.setText(popupBookmarkListViewItem.getKeyword());
            holder.titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.chkBookmark.setChecked(!popupBookmarkListViewItem.isSelected());
                }
            });
            holder.chkBookmark.setOnCheckedChangeListener(null);
            holder.chkBookmark.setChecked(popupBookmarkListViewItem.isSelected());
            holder.chkBookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //set your object's last status

                            popupBookmarkListViewItem.setChecked(isChecked);


                    }

//                    if(mCheckListener!=null){
//                        mCheckListener.OnCheckClick();
//                    }

            });



        return convertView;
    }

    public ArrayList<PopupWordMoveListViewItem> getMainItem(){
        return mLvBookmarkItems;
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
