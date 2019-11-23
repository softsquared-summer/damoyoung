package com.softsquared.damoyoung.src.bookmark.bookmarkDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.softsquared.damoyoung.R;

public class BookmarkNewfolderDialog extends Dialog {

    private TextView mTvNewFolderAdd;
    private EditText mEtNewFolderName;
    private Context mContext;
    private BookmarkNewFolderDialogListener mBookmarkNewFolderDialogListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.custom_dialog_bookmark_newfolder);

        //셋팅
        mTvNewFolderAdd = findViewById(R.id.tv_bookmark_dialog_new_folder_add);
        mEtNewFolderName = findViewById(R.id.et_bookmark_dialog_new_folder_name);
        //클릭 리스너 셋팅
        mTvNewFolderAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mEtNewFolderName.getText().toString();
                if(name.length()!=0){
                    //interface
                    mBookmarkNewFolderDialogListener.onPositiveClicked(name);
                    dismiss();
                }
                else{
                    Toast.makeText(mContext, "폴더명을 입력하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //생성자 생성
    public BookmarkNewfolderDialog(@NonNull Context context){
    super(context);
        mContext=context;
    }


    //dialog listener
    public void setDialogListener(BookmarkNewFolderDialogListener bookmarkNewFolderDialogListener) {
        this.mBookmarkNewFolderDialogListener = bookmarkNewFolderDialogListener;
    }
    //dialog interface
    public interface BookmarkNewFolderDialogListener {
        void onPositiveClicked(String name);
    }


}


