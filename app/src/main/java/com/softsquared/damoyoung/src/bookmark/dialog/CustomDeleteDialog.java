package com.softsquared.damoyoung.src.bookmark.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.softsquared.damoyoung.R;

public class CustomDeleteDialog extends Dialog {

    private TextView mTvDelete,mTvCancel;
    private Context mContext;
    private CustomDeleteDialogListener mCustomDeleteDialogListener;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.custom_dialog_bookmark_delete);

        //셋팅
        mTvDelete = findViewById(R.id.tv_bookmark_dialog_delete);
        mTvCancel = findViewById(R.id.tv_bookmark_dialog_delete_cancel);
        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)

        mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mCustomDeleteDialogListener.onPositiveClicked(position);
                    dismiss();

            }
        });
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    //생성자 생성
    public CustomDeleteDialog(@NonNull Context context,int pos){
    super(context);
        mContext=context;
        position= pos;
    }


    //dialog listener
    public void setDialogListener(CustomDeleteDialogListener customDeleteDialogListener) {
        this.mCustomDeleteDialogListener = customDeleteDialogListener;
    }

    public interface CustomDeleteDialogListener {
        void onPositiveClicked(int pos);
    }


}


