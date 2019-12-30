package com.chzu.txgc.pdd.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.chzu.txgc.pdd.Implement.PermissionCallback;
import com.chzu.txgc.pdd.Utils.PermissionUtils;


public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        mContext = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Utils.init(this);
        initPermissions();
        setContentView(bindLayout());
        findViews();
        initListeners();
        initData();
    }

    private void initPermissions() {
        PermissionUtils.getPermission(this, new PermissionCallback() {
            @Override
            public void onSuccess() {
                LogUtils.e("hy","权限管理");
            }

            @Override
            public void onFailed() {
                ToastUtils.showShort("请打开存储权限，否则APP将无法正常使用");
                System.exit(0);
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA
                , Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE);
    }


    @Override
    public void onClick(View view) {
        onViewClicked(view.getId());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void goActivity(Class clazz){
        Intent intent = new Intent(mContext,clazz);
        startActivity(intent);
    }

    public void goActivityWithData(Class clazz, Bundle bundle){
        Intent intent = new Intent(mContext,clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void goActivityForResult(Class clazz, int requestCode){
        Intent intent = new Intent(mContext,clazz);
        startActivityForResult(intent,requestCode);
    }

    public void goActivityWithDataForResult(Class clazz, Bundle bundle, int requestCode){
        Intent intent = new Intent(mContext,clazz);
        intent.putExtras(bundle);
        startActivityForResult(intent,requestCode);
    }

    public abstract int bindLayout();//设置layoutId
    protected abstract void findViews();//findViews
    protected abstract void initListeners();//初始化控件监听器
    protected abstract void initData();//初始化数据
    protected  abstract  void onViewClicked(int viewId);
}
