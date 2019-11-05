package com.softsquared.damoyoung.src.setting;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.view.View;
import android.widget.TextView;

import com.softsquared.damoyoung.R;
import com.softsquared.damoyoung.src.BaseActivity;
import com.softsquared.damoyoung.src.priority.PriorityActivity;

public class SettingActivity  extends BaseActivity {

    TextView tvSettingPriority;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.tv_setting_priority:

                startActivity(new Intent(this, PriorityActivity.class));
                break;
            case R.id.iv_setting_close:
                finish();
                break;
        }
    }
}
