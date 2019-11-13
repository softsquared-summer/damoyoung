package com.softsquared.damoyoung.src.history.historyDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.softsquared.damoyoung.R;

public class HistoryDeleteDialog extends Dialog {

    private TextView mTvDelete,mTvCancel,mTvContent;
    private Context mContext;
    private DeleteDialogListener mDeleteDialogListener;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.custom_dialog_history_delete);

        //todo history 다이얼로그 셋팅하기
        //셋팅
        mTvContent = findViewById(R.id.tv_history_dialog_delete_content);
        mTvDelete = findViewById(R.id.tv_history_dialog_delete);
        mTvCancel = findViewById(R.id.tv_history_dialog_delete_cancel);
        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)

        mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDeleteDialogListener.onPositiveClicked();
                dismiss();
            }
        });
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDeleteDialogListener.onNegativeClicked();
                dismiss();
            }
        });
    }

    //생성자 생성
    public HistoryDeleteDialog(@NonNull Context context){
    super(context);
        mContext=context;
    }


    //dialog listener
    public void setDialogListener(DeleteDialogListener deleteDialogListener) {
        this.mDeleteDialogListener = deleteDialogListener;
    }

    public interface DeleteDialogListener {
        void onPositiveClicked();
        void onNegativeClicked();
    }


}


