package com.softsquared.damoyoung.src.bookmark;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.bookmark.bookmarkDialog.BookmarkDeleteDialog;

import java.util.ArrayList;

public class BookmarkListViewAdapater extends BaseAdapter {

    private ArrayList<BookmarkListItem> mBookmarkListItems;
    private LayoutInflater mInflater;
    private Activity mActivity;

    // ListViewAdapter의 생성자
    public BookmarkListViewAdapater(ArrayList<BookmarkListItem> items, Context context,Activity activity) {
        mBookmarkListItems = items;
        mInflater = LayoutInflater.from(context);
        this.mActivity =activity;
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

        BookmarkListItem bookmarkListitem = mBookmarkListItems.get(position);
        if (convertView == null) {
            holder = new BookmarkListViewAdapater.ViewHolder();
            convertView = mInflater.inflate(R.layout.listview_bookmark_item, parent, false);
            holder.tvFolderName = convertView.findViewById(R.id.tv_bookmark_folder_name);
            holder.tvFolderEdit = convertView.findViewById(R.id.tv_bookmark_folder_delete);
            holder.ivFolderIcon = convertView.findViewById(R.id.iv_bookmark_folder_icon);
            convertView.setTag(holder);
        } else {
            holder = (BookmarkListViewAdapater.ViewHolder) convertView.getTag();
        }


        // 아이템 내 각 위젯에 데이터 반영 데이터 파싱
        holder.tvFolderName.setText(bookmarkListitem.getTitle());
        if (bookmarkListitem.getFirst()){//첫번째==기록, 폴더아이콘을 검은색으로 그 외는 흰색
            holder.ivFolderIcon.setImageResource(R.drawable.ic_folder_black);
        }else{
            holder.ivFolderIcon.setImageResource(R.drawable.ic_folder_white);
            if (bookmarkListitem.getEditMode()){
                holder.tvFolderEdit.setVisibility(View.VISIBLE);
                holder.tvFolderEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BookmarkDeleteDialog mCustomDeleteDialog = new BookmarkDeleteDialog(mActivity,pos);
                        mCustomDeleteDialog.setDialogListener(new BookmarkDeleteDialog.BookmarkDeleteDialogListener(){
                            @Override
                            public void onPositiveClicked(int pos) {

                                if(mCheckListener!=null){
                                    mCheckListener.OnCheckClick(pos);
                                }
                                    notifyDataSetChanged();
                            }

                        });

                        mCustomDeleteDialog.show();
                    }
                });
            }else{
                holder.tvFolderEdit.setVisibility(View.GONE);
            }
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

    //interface
    public interface OnCheckedChangeListener {
        void OnCheckClick(int pos);
    }
    private OnCheckedChangeListener mCheckListener=null;

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener){
        this.mCheckListener =listener;
    }
}