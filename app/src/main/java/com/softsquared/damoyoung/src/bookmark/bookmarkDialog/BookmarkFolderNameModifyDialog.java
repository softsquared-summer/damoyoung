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

public class BookmarkFolderNameModifyDialog extends Dialog {

    private TextView mTvFolderModifyConfirm;
    private EditText mEtFolderModifyName;
    private Context mContext;
    private BookmarkFolderNameModifyDialogListener mBookmarkFolderNameModifyDialogListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.custom_dialog_bookmark_folder_name_modify);

        //셋팅
        mTvFolderModifyConfirm = findViewById(R.id.tv_bookmark_dialog_folder_name_modify_ok);
        mEtFolderModifyName = findViewById(R.id.et_bookmark_dialog_folder_name_modify);
        //클릭 리스너 셋팅
        mTvFolderModifyConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mEtFolderModifyName.getText().toString();
                if(name.length()!=0){
                    //interface dialog -> adapter
                    mBookmarkFolderNameModifyDialogListener.onPositiveClicked(name);
                    dismiss();
                }
                else{
                    Toast.makeText(mContext, "폴더명을 입력하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //생성자 생성
    public BookmarkFolderNameModifyDialog(@NonNull Context context){
    super(context);
        mContext=context;
    }


    //dialog listener
    public void setDialogListener(BookmarkFolderNameModifyDialogListener bookmarkFolderNameModifyDialogListener) {
        this.mBookmarkFolderNameModifyDialogListener = bookmarkFolderNameModifyDialogListener;
    }
    //dialog interface
    public interface BookmarkFolderNameModifyDialogListener {
        void onPositiveClicked(String name);
    }


}


