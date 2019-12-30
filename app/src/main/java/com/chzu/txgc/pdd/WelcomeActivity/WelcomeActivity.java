package com.chzu.txgc.pdd.WelcomeActivity;
import android.content.Intent;
import android.content.SharedPreferences;

import com.chzu.txgc.pdd.Activity.BaseActivity;
import com.chzu.txgc.pdd.Guide.GuideActivity;
import com.chzu.txgc.pdd.Guide.HandleActivity;
import com.chzu.txgc.pdd.R;
/**
 * 引导页面
 * 判断是否第一次进入、第一次进入需要进入引导页
 * */

public class WelcomeActivity extends BaseActivity {
    //是否是第一次使用
    private boolean isFirstUse;
    @Override
    public int bindLayout() {
        return R.layout.activity_welcome;
    }
    @Override
    protected void findViews() {
    }
    @Override
    protected void initListeners() {
    }
    @Override
    protected void initData() {
        SharedPreferences preferences = getSharedPreferences("isFirstUse", MODE_PRIVATE);
        isFirstUse = preferences.getBoolean("isFirstUse", true);
        if (isFirstUse) {
            startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));//引导页 GuideActivity.java
        } else {
            startActivity(new Intent(WelcomeActivity.this,HandleActivity.class));
        }
        finish();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isFirstUse", false);
        editor.commit();
    }
    @Override
    protected void onViewClicked(int viewId) {

    }
}
