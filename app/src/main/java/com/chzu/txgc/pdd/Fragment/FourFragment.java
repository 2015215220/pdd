package com.chzu.txgc.pdd.Fragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.LogUtils;
import com.chzu.txgc.pdd.AiliPay.AuthResult;
import com.chzu.txgc.pdd.AiliPay.OrderInfoUtil2_0;
import com.chzu.txgc.pdd.AiliPay.PayResult;
import com.chzu.txgc.pdd.Implement.OnRequestResult;
import com.chzu.txgc.pdd.R;
import com.chzu.txgc.pdd.Utils.OkgoUtils;
import com.lzy.okgo.OkGo;

import java.util.HashMap;
import java.util.Map;
public class FourFragment extends LazyLoadFragment implements OnRequestResult {
    Button zf;
    Map<String, String> parms;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_four_fragment, container, false);
        zf=(Button) view.findViewById(R.id.zf);

        zf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String url="http://v.juhe.cn/joke/content/list.php";
               parms = new HashMap<>();
               parms.put("sort", "asc");
               parms.put("key", "00cf1df15847756f21012f0313b82929");//hy75c6c0a4c52e3a2dd7b614cd91237ced wyj6c2021204905af8b753a22d51db3e829
               parms.put("pagesize", "20");
               parms.put("time", "1418816972");
               OkgoUtils.getInstance().get(url,parms,33,FourFragment.this);
            }
        });
        return view;
    }

    @Override
    protected void fetchData() {
        //初始化加载的
    }

    @Override
    public void onSuccess(int code, String json) {
        LogUtils.e("hy",json);
    }

    @Override
    public void onFailed(int code, String msg) {

    }
}
