package com.chzu.txgc.pdd.Guide;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.chzu.txgc.pdd.Activity.BaseActivity;
import com.chzu.txgc.pdd.Activity.LoginActivity;
import com.chzu.txgc.pdd.R;
public class HandleActivity extends BaseActivity {
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg){
            if(msg.what == 1000){
                goActivity(LoginActivity.class);
                finish();
            }
        }
    };
    @Override
    public int bindLayout() {
        return R.layout.activity_handle;
    }
    @Override
    protected void findViews() {
    }
    @Override
    protected void initListeners() {
    }
    @Override
    protected void initData() {
        handler.sendEmptyMessageDelayed(1000,3000);
    }
    @Override
    protected void onViewClicked(int viewId) {
    }
}
