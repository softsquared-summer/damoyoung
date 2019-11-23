package com.softsquared.damoyoung.src.setting;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softsquared.damoyoung.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SettingExListViewAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<String> mParentList; //제목이니까 String으로 받아도 된다.
    private SettingExListViewAdapter.SettingChildListViewHolder mSettingChildListViewHolder;
    private HashMap<String, ArrayList<String>> mChildHashMap;

    // CustomExpandableListViewAdapter 생성자
    public SettingExListViewAdapter(Context context, ArrayList<String> parentList, HashMap<String, ArrayList<String>> childHashMap) {
        this.mContext = context;
        this.mParentList = parentList;
        this.mChildHashMap = childHashMap;
    }


    /* ParentListView에 대한 method */
    @Override
    public String getGroup(int groupPosition) { // ParentList의 position을 받아 해당 TextView에 반영될 String을 반환
        return mParentList.get(groupPosition);
    }

    @Override
    public int getGroupCount() { // ParentList의 원소 개수를 반환
        return mParentList.size();
    }

    @Override
    public long getGroupId(int groupPosition) { // ParentList의 position을 받아 long값으로 반환
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        // ParentList의 View
//        final WordbookParentListViewHolder mWordbookParentListViewHolder;
        if (convertView == null) {
            LayoutInflater groupInfla = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = groupInfla.inflate(R.layout.exlv_setting_parent, parent, false);
        }

        //setting
        ImageView ivIndicator = convertView.findViewById(R.id.iv_setting_parent_indicator);

        //onBind
        TextView tvParent = convertView.findViewById(R.id.tv_setting_parent_title);
        tvParent.setText(getGroup(groupPosition));

        if (isExpanded) {
            ivIndicator.setImageResource(R.drawable.ic_arrow_up);
        } else {
            ivIndicator.setImageResource(R.drawable.ic_arrow_down);
        }


        return convertView;
    }

    /* 여기서부터 ChildListView에 대한 method */
    @Override
    public String getChild(int groupPosition, int childPosition) {
        // groupPostion과 childPosition을 통해 childList의 원소를 얻어옴
        return this.mChildHashMap.get(this.mParentList.get(groupPosition)).get(childPosition);

    }

    @Override
    public int getChildrenCount(int groupPosition) { // ChildList의 크기를 int 형으로 반환
        return this.mChildHashMap.get(this.mParentList.get(groupPosition)).size();

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) { // ChildList의 ID로 long 형 값을 반환
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        // ChildList의 View. 위 ParentList의 View를 얻을 때와 비슷하게 Layout 연결 후, layout 내 TextView, ImageView를 연결
        final int gP = groupPosition;
        final int cP = childPosition;
        final String childData = getChild(gP, cP);

        if (convertView == null) {
            LayoutInflater childInfla = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = childInfla.inflate(R.layout.exlv_setting_child, null);

            //viewholder 생성
            mSettingChildListViewHolder = new SettingChildListViewHolder();
            mSettingChildListViewHolder.tvContent = convertView.findViewById(R.id.tv_setting_child_content);


            convertView.setTag(mSettingChildListViewHolder);
        } else {
            mSettingChildListViewHolder = (SettingChildListViewHolder) convertView.getTag();
        }

        //on BInd 부분
        if (childData != null) {
            mSettingChildListViewHolder.tvContent.setText(childData);
        }
        return convertView;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    } // stable ID인지 boolean 값으로 반환

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    } // 선택여부를 boolean 값으로 반환


    public class SettingChildListViewHolder {
        TextView tvContent;
    }
}
