package com.softsquared.damoyoung.src.wordbook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;
import com.softsquared.damoyoung.src.popUpWordbookMove.PopupWordMoveActivity;
import com.softsquared.damoyoung.src.splash.SplashActivity;
import com.softsquared.damoyoung.src.wordbook.interfaces.WordbookActivityView;
import com.softsquared.damoyoung.src.wordbook.itemClass.Word;
import com.softsquared.damoyoung.src.wordbook.wordbookDialog.WordbookDeleteDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class WordbookActivity extends BaseActivity implements View.OnClickListener, WordbookActivityView {

    private ExpandableListView mExLvWordbook; // ExpandableListView 변수 선언
    private WordbookExListViewAdapter mWordbookExListViewAdapter; // 위 ExpandableListView를 받을 CustomAdapter(2번 class에 해당)를 선언

    private ArrayList<Word> mWordList = new ArrayList<>(); // ExpandableListView의 Parent 항목이 될 List 변수 선언
    private ArrayList<WordbookExListItem> mSentenceList = new ArrayList<>(); // ExpandableListView의 Parent 항목이 될 List 변수 선언
    private HashMap<Word, ArrayList<WordbookExListItem>> mChildList = new HashMap<>(); // 위 ParentList와 ChildList를 연결할 HashMap 변수 선언

    private TextView mTvWordbookTitle; // todo intent로 제목 받아오기
    private TextView mTvWordbookConfirm, mTvWordBookDelete, mTvWordBookOrder, mTvWordBookAllSelect, mTvWordBookMove, mTvWordBookCopy, mTvWordBookEdit;
    private View mVempty;
    private ImageView mIvAllArrowDown;
    private Button btnMemorized, btnUnmemorized;
    private Boolean mAllExpand;
    private int mBookmarkNo;
    private String memorized;
    private int mGp, mCp;
    private String mBookmarkTitle;
    private boolean mAllSelect = false;
    private boolean mEditMode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordbook);
        //initialized
        Intent intent = getIntent();
        int bookmarkNo = intent.getIntExtra("bookmarkNo", 0);
        mBookmarkTitle = intent.getStringExtra("bookmarkTitle");
        mBookmarkNo = bookmarkNo;
        memorized = "N";
        mEditMode=false;
        mAllExpand = true;

        //findViewById
        mTvWordbookTitle = findViewById(R.id.tv_wordbook_title);
        btnMemorized = findViewById(R.id.btn_memorization);
        btnUnmemorized = findViewById(R.id.btn_unmemorization);
        mIvAllArrowDown = findViewById(R.id.iv_wordbook_all_arrow_down);
        mExLvWordbook = findViewById(R.id.elv_wordbook);
        mTvWordbookConfirm = findViewById(R.id.tv_wordbook_confirm);
        mTvWordBookEdit = findViewById(R.id.tv_wordbook_edit);
        mTvWordBookDelete = findViewById(R.id.tv_wordbook_delete);
        mTvWordBookAllSelect = findViewById(R.id.tv_wordbook_all_select);
        mTvWordBookMove = findViewById(R.id.tv_wordbook_move);
        mTvWordBookOrder = findViewById(R.id.tv_wordbook_order);
        mTvWordBookCopy = findViewById(R.id.tv_wordbook_copy);
        mVempty = findViewById(R.id.tv_wordbook_empty_view);

        //set listener
        mExLvWordbook.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });
        mExLvWordbook.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });
        mExLvWordbook.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        //onBind
        mTvWordbookTitle.setText(mBookmarkTitle);


    }
    @Override
    protected void onResume() {
        super.onResume();
        //activity dialog에서 pause되기때문
        getWordbook(mBookmarkNo, memorized);

    }

    public void getWordbook(int bookmarkNo, String memorized) {

        final WordbookService wordbookService = new WordbookService(this);
        try {
            JSONObject params = new JSONObject();
            params.put("is_memorized", memorized);
            wordbookService.getWordbook(bookmarkNo, params);
        } catch (JSONException e) {
            showCustomToast("");
        }
    }

    @Override
    public void validateGetSuccess(String text, ArrayList<WordbookExListItem> items) {
        mWordList.clear();
        mSentenceList.clear();
        mChildList.clear();

        for (int i = 0; i < items.size(); i++) {
            WordbookExListItem temp = items.get(i);
            if (mEditMode){
                mWordList.add(new Word(temp.getWordNo(), temp.getWord(),true));//parent 단어
            }else{
                mWordList.add(new Word(temp.getWordNo(), temp.getWord(),false));//parent 단어
            }
            mSentenceList = new ArrayList<>();
            for (int j = 0; j < temp.getSentenceList().size(); j++) {
                mSentenceList.add(new WordbookExListItem(temp.getSentenceList()));
            }
            mChildList.put(mWordList.get(i), mSentenceList);//0번째 인자에 항상 새로운 값이 들어오기 때문이다.
        }


        mWordbookExListViewAdapter = new WordbookExListViewAdapter(this, this, mWordList, mChildList);
        mExLvWordbook.setAdapter(mWordbookExListViewAdapter);
        mWordbookExListViewAdapter.setOnSentenceClickListener(new WordbookExListViewAdapter.OnSentenceClickListener() {
            @Override
            public void OnSentenceClickListener(View v, int gp, int cp) {

                mGp = gp;
                mCp = cp;
                System.out.println("mGP : " + mGp + " mCp : " + mCp);
                int sentenceNo = mWordbookExListViewAdapter.getChild(gp, cp).getSentenceList().get(cp).getSentenceNo();
                deleteSentence(mBookmarkNo, sentenceNo);
                //todo 여기서 deleteSentence를 해야한다.

            }
        });
        if (mWordList.size() != 0) {
            if (mAllExpand) {
                for (int i = 0; i < mWordbookExListViewAdapter.getGroupCount(); i++) {
                    mExLvWordbook.collapseGroup(i);
                }

                mIvAllArrowDown.setImageResource(R.drawable.ic_arrow_up);
            } else {
                for (int i = 0; i < mWordbookExListViewAdapter.getGroupCount(); i++) {
                    mExLvWordbook.expandGroup(i);
                }

                mIvAllArrowDown.setImageResource(R.drawable.ic_arrow_down);
            }

        } else {
            normalMode();
        }
        mWordbookExListViewAdapter.notifyDataSetChanged();
    }
    public void deleteWord(int bookmarkNo) {

        final WordbookService wordbookService = new WordbookService(this);
        mWordbookExListViewAdapter.notifyDataSetChanged();
        ArrayList<Word> data = mWordbookExListViewAdapter.getParent();
        ArrayList<Integer> deleteWordList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isSelected()) {
                //단어 번호 json 으로 담기
                deleteWordList.add(mWordList.get(i).getWordNo());
            }
        }

        if (data.size() == 0) {
            showCustomToast("삭제할 단어를 선택하세요");
            return;
        }
        try {
            JSONObject params = new JSONObject();
            JSONArray jArray = new JSONArray();//배열이 필요할때
            for (int i = 0; i < deleteWordList.size(); i++)//배열
            {
                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                sObject.put("wordNo", deleteWordList.get(i));
                jArray.put(sObject);
            }
            params.put("wordList", jArray);//배열을 넣음
            wordbookService.deleteWord(bookmarkNo, params); // 서비스 호출
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void validateDeleteSuccess(String text) {
        showCustomToast("단어가 삭제되었습니다.");
        mEditMode=true;
        mAllExpand=false;
        getWordbook(mBookmarkNo, memorized);

    }

    //todo
    public void deleteSentence(int bookmarkNo, int sentenceNo) {
        final WordbookService wordbookService = new WordbookService(this);
        wordbookService.deleteSentence(bookmarkNo, sentenceNo);
    }

    @Override
    public void vaildateSentenceDeleteSuccess(String text) {

        showCustomToast("예문이 삭제되었습니다.");
        mEditMode=true;
        mAllExpand=false;
        getWordbook(mBookmarkNo, memorized);
    }


    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_memorization:
                btnMemorized.setBackground(getDrawable(R.drawable.custom_button_on));
                btnUnmemorized.setBackground(getDrawable(R.drawable.custom_button_off));
                btnMemorized.setTextColor(getResources().getColor(R.color.colorDamoyoungWhite));
                btnUnmemorized.setTextColor(getResources().getColor(R.color.colorBlack));
                memorized = "Y";
                getWordbook(mBookmarkNo, memorized);
                break;
            case R.id.btn_unmemorization:
                btnMemorized.setBackground(getDrawable(R.drawable.custom_button_off));
                btnUnmemorized.setBackground(getDrawable(R.drawable.custom_button_on));
                btnMemorized.setTextColor(getResources().getColor(R.color.colorBlack));
                btnUnmemorized.setTextColor(getResources().getColor(R.color.colorDamoyoungWhite));
                memorized = "N";
                getWordbook(mBookmarkNo, memorized);
                break;
            case R.id.iv_wordbook_arrow_back:
                finish();
                break;
            case R.id.iv_wordbook_all_arrow_down:
                if (mWordList.size() != 0) {
                    if (mAllExpand) {
                        for (int i = 0; i < mWordbookExListViewAdapter.getGroupCount(); i++) {
                            mExLvWordbook.collapseGroup(i);
                        }
                        mAllExpand = false;
                        mIvAllArrowDown.setImageResource(R.drawable.ic_arrow_up);
                    } else {
                        for (int i = 0; i < mWordbookExListViewAdapter.getGroupCount(); i++) {
                            mExLvWordbook.expandGroup(i);
                        }
                        mAllExpand = true;
                        mIvAllArrowDown.setImageResource(R.drawable.ic_arrow_down);
                    }

                } else {
                }
                mWordbookExListViewAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_wordbook_order:
                break;
            case R.id.tv_wordbook_edit:
                editMode();
                break;
            case R.id.tv_wordbook_confirm:
                normalMode();
                break;
            case R.id.tv_wordbook_all_select:
                if (mAllSelect) {
                    for (int i = 0; i < mWordList.size(); i++) {
                        mWordList.get(i).setChecked(false);
                    }
                    mAllSelect = !mAllSelect;
                } else {
                    for (int i = 0; i < mWordList.size(); i++) {
                        mWordList.get(i).setChecked(true);
                    }
                    mAllSelect = !mAllSelect;
                }
                mWordbookExListViewAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_wordbook_copy:
                showCustomToast("복사");
                break;
            case R.id.tv_wordbook_move:
                showCustomToast("이동");
                ArrayList<Word> words = mWordbookExListViewAdapter.getParent();
                ArrayList<Integer> moveWordList = new ArrayList<>();
                for (int i = 0; i < words.size(); i++) {
                    if (words.get(i).isSelected()) {
                        //북마크 번호 json 으로 담기
                        moveWordList.add(mWordList.get(i).getWordNo());
                    }
                }
                if (moveWordList.size() == 0) {
                    showCustomToast("단어를 선택해주세요");
                } else {

                    Intent intent = new Intent(this, PopupWordMoveActivity.class);
                    intent.putExtra("wordNo", moveWordList);
                    intent.putExtra("bookmarkNo", mBookmarkNo);
                    startActivity(intent);

                }
                break;
            case R.id.tv_wordbook_delete:
                ArrayList<Word> data = mWordbookExListViewAdapter.getParent();
                ArrayList<Integer> deleteWordList = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).isSelected()) {
                        //북마크 번호 json 으로 담기
                        deleteWordList.add(mWordList.get(i).getWordNo());
                    }
                }
                if (deleteWordList.size() == 0) {
                    showCustomToast("단어를 선택해주세요");

                } else {
                    WordbookDeleteDialog mWordbookDeleteDialog = new WordbookDeleteDialog(this);
                    mWordbookDeleteDialog.setDialogListener(new WordbookDeleteDialog.WordbookDeleteDialogListener() {
                        @Override
                        public void onPositiveClicked() {
                            deleteWord(mBookmarkNo);
                        }
                    });

                    mWordbookDeleteDialog.show();
                }
                break;
        }
    }


    public void editMode() {
        mTvWordBookCopy.setVisibility(View.VISIBLE);
        mTvWordBookMove.setVisibility(View.VISIBLE);
        mTvWordBookDelete.setVisibility(View.VISIBLE);
        mTvWordBookAllSelect.setVisibility(View.VISIBLE);
        mTvWordbookConfirm.setVisibility(View.VISIBLE);
        mTvWordBookEdit.setVisibility(View.GONE);
        mVempty.setVisibility(View.GONE);
        mTvWordBookOrder.setVisibility(View.GONE);


        for (int i = 0; i < mWordList.size(); i++) {
            mWordList.get(i).setEditMode(true);
        }
        if (mWordList.size() != 0) {
            for (int i = 0; i < mWordbookExListViewAdapter.getGroupCount(); i++) {
                mExLvWordbook.expandGroup(i);
                mAllExpand = true;
            }
            mIvAllArrowDown.setImageResource(R.drawable.ic_arrow_down);
        }

        mWordbookExListViewAdapter.notifyDataSetChanged();

    }

    public void normalMode() {
        mTvWordBookCopy.setVisibility(View.GONE);
        mTvWordBookMove.setVisibility(View.GONE);
        mTvWordBookDelete.setVisibility(View.GONE);
        mTvWordBookAllSelect.setVisibility(View.GONE);
        mTvWordbookConfirm.setVisibility(View.GONE);
        mTvWordBookEdit.setVisibility(View.VISIBLE);
        mVempty.setVisibility(View.VISIBLE);
        mTvWordBookOrder.setVisibility(View.VISIBLE);

        for (int i = 0; i < mWordList.size(); i++) {
            mWordList.get(i).setEditMode(false);
        }
        if (mWordList.size() != 0) {
            if (mAllExpand) {
                for (int i = 0; i < mWordbookExListViewAdapter.getGroupCount(); i++) {
                    mExLvWordbook.collapseGroup(i);

                }
                mAllExpand = false;
                mIvAllArrowDown.setImageResource(R.drawable.ic_arrow_up);
            }
        }else{
            mAllExpand = false;
            mIvAllArrowDown.setImageResource(R.drawable.ic_arrow_up);
        }
        mWordbookExListViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void validateGetFailure(String message) {

        showCustomToast(message);
    }

    @Override
    public void validateMoveSuccess(String text) {

    }

    @Override
    public void validateMoveFailure(String message) {

    }

    @Override
    public void validateCopySuccess(String text) {

    }

    @Override
    public void validateCopyFailure(String message) {

    }


    @Override
    public void vaildateSentenceDeleteFailure(String text) {
        showCustomToast(text);
    }




    @Override
    public void validateDeleteFailure(String message) {
        showCustomToast("네트워크가 불안정합니다.");
    }

}
