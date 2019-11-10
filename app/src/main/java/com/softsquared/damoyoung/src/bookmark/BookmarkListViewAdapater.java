package com.softsquared.damoyoung.src.bookmark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softsquared.damoyoung.R;

import java.util.ArrayList;

public class BookmarkListViewAdapater extends BaseAdapter {

    private ArrayList<BookmarkListItem> mBookmarkListItems;
    private LayoutInflater mInflater;

    // ListViewAdapter의 생성자
    public BookmarkListViewAdapater(ArrayList<BookmarkListItem> items, Context context) {
        mBookmarkListItems = items;
        mInflater = LayoutInflater.from(context);
    }

    //뷰홀더
    public class ViewHolder {
        TextView tvFolderName, tvFolderEdit;
        ImageView ivFolderIcon;
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return mBookmarkListItems.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        final BookmarkListViewAdapater.ViewHolder holder;

        //역순으로 보여주는 리스트뷰 출력만 역순으로 해준다
        BookmarkListItem bookmarkListitem = mBookmarkListItems.get(position);
        if (convertView == null) {
            holder = new BookmarkListViewAdapater.ViewHolder();
            convertView = mInflater.inflate(R.layout.listview_bookmark_item, parent, false);
            holder.tvFolderName = convertView.findViewById(R.id.tv_bookmark_folder_name);
            holder.tvFolderEdit = convertView.findViewById(R.id.tv_bookmark_folder_edit);
            holder.ivFolderIcon = convertView.findViewById(R.id.iv_bookmark_folder_icon);
            convertView.setTag(holder);
        } else {
            holder = (BookmarkListViewAdapater.ViewHolder) convertView.getTag();
        }


        // 아이템 내 각 위젯에 데이터 반영 데이터 파싱
        holder.tvFolderName.setText(bookmarkListitem.getTitle());
        if (bookmarkListitem.getFirst()){
            holder.ivFolderIcon.setImageResource(R.drawable.ic_folder_black);
        }else{
            holder.ivFolderIcon.setImageResource(R.drawable.ic_folder_white);
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
        return mBookmarkListItems.get(position);
    }
}