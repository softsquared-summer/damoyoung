package com.softsquared.damoyoung.src;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.softsquared.damoyoung.R;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    public ProgressDialog mProgressDialog;
    private int requestedOrientation;

    public void showCustomToast(final String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //API 8.0 Only fullscreen opaque activities can request orientation 안드로이드 이슈. 2019-12-08
        //세로 모드 설정 후 오류가 발생하면 무시한다.
        try {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } catch (IllegalStateException ignore) {

        }

    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }
}
