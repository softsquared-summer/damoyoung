package com.softsquared.damoyoung.src.wordbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.main.MainActivity;
import com.softsquared.damoyoung.src.searchResult.SearchResultActivity;
import com.softsquared.damoyoung.src.wordbook.models.Word;
import com.softsquared.damoyoung.src.wordbook.wordbookDialog.SentenceDeleteDialog;

import java.util.ArrayList;
import java.util.HashMap;


public class WordbookExListViewAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<Word> mParentList; //제목이니까 String으로 받아도 된다.
    private ArrayList<WordbookExListItem> mChildList;
    private WordbookChildListViewHolder mWordbookChildListViewHolder;
    private HashMap<Word, ArrayList<WordbookExListItem>> mChildHashMap;
    private Activity mActivity;

    // CustomExpandableListViewAdapter 생성자
    public WordbookExListViewAdapter(Activity activity, Context context, ArrayList<Word> parentList, HashMap<Word, ArrayList<WordbookExListItem>> childHashMap) {
        this.mContext = context;
        this.mParentList = parentList;
        this.mChildHashMap = childHashMap;
        this.mActivity = activity;
    }

    public ArrayList<Word> getParent() {
        return mParentList;
    }

    /* ParentListView에 대한 method */
    @Override
    public Word getGroup(int groupPosition) { // ParentList의 position을 받아 해당 TextView에 반영될 String을 반환
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
        final Word parentData = getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater groupInfla = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = groupInfla.inflate(R.layout.exlv_wordbook_parent, parent, false);
//            convertView.setTag(mWordbookChildListViewHolder);
        }

        TextView tvParent = convertView.findViewById(R.id.tv_wordbook_parent_title);
        ImageView ivIndicator = convertView.findViewById(R.id.iv_wordbook_parent_indicator);
        CheckBox cbWord = convertView.findViewById(R.id.cb_wordbook_parent);
        ImageView ivMemorizedArrow = convertView.findViewById(R.id.iv_wordbook_parent_arrow_memorized);
        //onBind

        tvParent.setText(parentData.getWord());

//        편집 모드일 경우
        if (parentData.isEditMode()) {
            ivIndicator.setVisibility(View.GONE);
            ivMemorizedArrow.setVisibility(View.GONE);
            cbWord.setVisibility(View.VISIBLE);

            cbWord.setOnCheckedChangeListener(null);
            cbWord.setChecked(parentData.isSelected());
            cbWord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //set your object's last status
                    parentData.setChecked(isChecked);
                }


            });
            tvParent.setOnClickListener(null);
        } else {
            ivIndicator.setVisibility(View.VISIBLE);
            ivMemorizedArrow.setVisibility(View.VISIBLE);
            cbWord.setVisibility(View.GONE);

            tvParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SearchResultActivity.class);
                    intent.putExtra("keyword",parentData.getWord());
                    mContext.startActivity(intent);

                }
            });

            if (isExpanded) {
                ivIndicator.setImageResource(R.drawable.ic_arrow_up);
            } else {
                ivIndicator.setImageResource(R.drawable.ic_arrow_down);
            }

            if (parentData.isMemorized()) {
                ivMemorizedArrow.setImageResource(R.drawable.ic_arrow_right);
                ivMemorizedArrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (memorizedImageClickListener != null) {
                            memorizedImageClickListener.OnMemorizedClickListener(view, groupPosition);
                        }
                    }
                });
            } else {
                ivMemorizedArrow.setImageResource(R.drawable.ic_arrow_left);
                ivMemorizedArrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (memorizedImageClickListener != null) {
                            memorizedImageClickListener.OnMemorizedClickListener(view, groupPosition);
                        }
                    }
                });

            }


        }

        return convertView;
    }

    /* 여기서부터 ChildListView에 대한 method */
    @Override
    public WordbookExListItem getChild(int groupPosition, int childPosition) {
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
        final WordbookExListItem childData = getChild(gP, cP);

        if (convertView == null) {
            LayoutInflater childInfla = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = childInfla.inflate(R.layout.exlv_wordbook_child, null);

            mWordbookChildListViewHolder = new WordbookChildListViewHolder();
            mWordbookChildListViewHolder.tvSentence = convertView.findViewById(R.id.tv_wordbook_child_sentence);
            mWordbookChildListViewHolder.ivDelete = convertView.findViewById(R.id.iv_wordbook_child_delete);


            convertView.setTag(mWordbookChildListViewHolder);
        } else {
            mWordbookChildListViewHolder = (WordbookChildListViewHolder) convertView.getTag();
        }

        //on BInd 부분

        if (childData != null) {

            mWordbookChildListViewHolder.tvSentence.setText(childData.getSentenceList().get(childPosition).getSetense());

            if (getGroup(gP).isEditMode()) {
                mWordbookChildListViewHolder.ivDelete.setVisibility(View.VISIBLE);
                mWordbookChildListViewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {

                        SentenceDeleteDialog mSentenceDeleteDialog = new SentenceDeleteDialog(mActivity);
                        mSentenceDeleteDialog.setDialogListener(new SentenceDeleteDialog.SentenceDeleteDialogListener() {
                            @Override
                            public void onPositiveClicked() {

                                if (mClickListener != null) {
                                    System.out.println(childData.getSentenceList().get(cP).getSentenceNo());

                                    mClickListener.OnSentenceClickListener(view, gP, cP);
                                }
                                notifyDataSetChanged();
                            }

                        });

                        mSentenceDeleteDialog.show();  //todo 딜리트 눌렀을때 차일드 삭제하기
                    }
                });
            } else {
                mWordbookChildListViewHolder.ivDelete.setVisibility(View.GONE);

            }
        }
        return convertView;

    }

    public interface OnSentenceClickListener {
        void OnSentenceClickListener(View v, int gp, int cp);
    }

    private OnSentenceClickListener mClickListener = null;

    public void setOnSentenceClickListener(OnSentenceClickListener listener) {
        this.mClickListener = listener;
    }

    public interface OnMemorizedImageClickListener {
        void OnMemorizedClickListener(View v, int gp);
    }

    private OnMemorizedImageClickListener memorizedImageClickListener = null;

    public void setOnMemorizedImageClickListener(OnMemorizedImageClickListener listener) {
        this.memorizedImageClickListener = listener;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    } // stable ID인지 boolean 값으로 반환

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    } // 선택여부를 boolean 값으로 반환


    public class WordbookChildListViewHolder {
        TextView tvSentence;
        ImageView ivDelete;
    }

//        public class WordbookParentListViewHolder {
//            TextView tvParent;
//            ImageView ivIndicator;
//            CheckBox cbWord;
//        }

}

