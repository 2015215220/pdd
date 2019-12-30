package com.chzu.txgc.pdd.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSONException;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chzu.txgc.pdd.Contact.CheckSumBuilder;
import com.chzu.txgc.pdd.Dao.MyDao;
import com.chzu.txgc.pdd.Implement.OnRequestResult;
import com.chzu.txgc.pdd.R;
import com.chzu.txgc.pdd.Utils.OkgoUtils;
import com.lzy.okgo.model.HttpHeaders;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
public class WjmmActivity extends BaseActivity {
    private EditText cz_phone;//重置电话
    private EditText vcode_cz;//重置验证码
    private Button djs_cz;//单击
    private TextView txt_cz;//隐藏60秒
    private EditText cz_password;//请重置密码
    private Button register_czbtn;//重置密码
    private ImageView yj;//眼睛
    MyDao myDao;
    EventHandler eh;
    ZLoadingDialog zLoadingDialog;
    boolean regis=false;
    private boolean isHideFirst = true;
    @Override
    public int bindLayout() {
        return R.layout.activity_wjmm;
    }
    @Override
    protected void findViews() {
        cz_phone = (EditText) findViewById(R.id.cz_phone);//重置电话
        vcode_cz = (EditText) findViewById(R.id.vcode_cz);//重置验证码
        djs_cz = (Button) findViewById(R.id.djs_cz);//单击
        txt_cz = (TextView) findViewById(R.id.txt_cz);//隐藏60秒
        cz_password = (EditText) findViewById(R.id.cz_password);//请重置密码
        register_czbtn = (Button) findViewById(R.id.register_czbtn);//重置密码
        yj=(ImageView)findViewById(R.id.yj);
        myDao=MyDao.getInstance(this);

        cz_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    yj.setVisibility(View.VISIBLE);
                    yj.setImageResource(R.drawable.close);
                } else {
                    // 此处为失去焦点时的处理内容
                    if(isHideFirst == false){
                        TransformationMethod method = PasswordTransformationMethod.getInstance();
                        cz_password.setTransformationMethod(method);
                        isHideFirst = true;
                    }
                    yj.setVisibility(View.GONE);
                }
            }
        });
    }
    @Override
    protected void initListeners() {
        djs_cz.setOnClickListener(this);//短信监听
        register_czbtn.setOnClickListener(this);//重置按钮
        yj.setOnClickListener(this);
    }
    @Override
    protected void initData() {
        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        String cz_phone_value=cz_phone.getText().toString().trim();//电话
                        String cz_password_value=cz_password.getText().toString().trim();//重置密码
                        update(cz_phone_value,cz_password_value);
                        if(regis) {
                            myDao.updateUser(cz_phone_value, cz_password_value);//密码已经更新
                            ToastUtils.showShort("密码更新完成，进入登录界面");
                            SPUtils.getInstance().put("phone_value",cz_phone.getText().toString());
                            SPUtils.getInstance().put("password",cz_password.getText().toString());
                            goActivity(LoginActivity.class);
                            finish();
                        }
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        ToastUtils.showShort("发送验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码失败
                        zLoadingDialog.dismiss();
                        ToastUtils.showShort("验证码校验失败");
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码失败
                        zLoadingDialog.dismiss();
                        ToastUtils.showShort("获取验证码失败");
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    private void update(String edt_phone_value,String edt_password_value) {//昵称电话、电话、密码
        if (!NetworkUtil.isNetAvailable(WjmmActivity.this)) {
            ToastHelper.showToast(WjmmActivity.this, R.string.network_is_not_available);
            return;
        }
        String url="https://api.netease.im/nimserver/user/update.action";
        HttpHeaders headers=new HttpHeaders();
        String appKey="1ab863550523720e07fad8ed130b4e5c";
        String appSecret = "41c25d684abc";
        String nonce =  "12345";
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);
        headers.put("appkey", appKey);
        headers.put("Nonce", nonce);
        headers.put("CurTime", curTime);
        headers.put("CheckSum", checkSum);
        headers.put("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
        Map<String,String> params=new HashMap<>();
        params.put("accid",edt_phone_value);
        params.put("token",edt_password_value);
        OkgoUtils.getInstance().post(url,params ,headers, 10086, new OnRequestResult() {
            @Override
            public void onSuccess(int code, String json) {
                if (TextUtils.isEmpty(json)){
                    ToastUtils.showShort("更新失败");
                    LogUtils.e("更新失败:"+code+"\n");
                    regis=false;
                    zLoadingDialog.dismiss();
                }
                try{
                    String cz_phone_value=cz_phone.getText().toString().trim();//电话
                    String cz_password_value=cz_password.getText().toString().trim();//重置密码
                    com.alibaba.fastjson.JSONObject resObj= com.alibaba.fastjson.JSONObject.parseObject(json);
                    int resCode=resObj.getIntValue("code");
                    if (resCode==200){
                        if(myDao.query_phone(cz_phone_value)){//本地数据库已经存储的数据
                            regis=true;
                            zLoadingDialog.dismiss();
                        }else{
                            //添加到数据库中，因为别的手机安装没有存储
                            myDao.addUser(cz_phone_value,cz_password_value);
                            regis=true;
                            zLoadingDialog.dismiss();
                        }
                    }else {
                        //{"desc":"18855054358 not register","code":414}
                        String error=resObj.getString("desc");
                        if(error.contains("not register")){
                            ToastUtils.showShort("手机号没有注册过，进入注册界面");
                            regis=false;
                            zLoadingDialog.dismiss();
                            goActivity(RegisterActivity.class);
                            finish();
                        }else{
                            regis=false;
                            ToastUtils.showShort(error);
                            zLoadingDialog.dismiss();
                        }
                    }
                }catch (JSONException e1){
                    e1.printStackTrace();
                    zLoadingDialog.dismiss();
                }
            }
            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showShort(msg);
                zLoadingDialog.dismiss();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        SMSSDK.unregisterEventHandler(eh);
    }

    @Override
    protected void onViewClicked(int viewId) {
        String cz_phone_value=cz_phone.getText().toString().trim();//电话
        String vcode_cz_value=vcode_cz.getText().toString().trim();//验证码
        String cz_password_value=cz_password.getText().toString().trim();//重置密码
        switch (viewId) {
            case R.id.register_czbtn:
                if(StringUtils.isEmpty(cz_phone_value) || StringUtils.isEmpty(vcode_cz_value) || StringUtils.isEmpty(cz_password_value)){
                    if(TextUtils.isEmpty(cz_phone_value)){
                        ToastUtils.showShort("手机号不能为空");
                        return;
                    }if(TextUtils.isEmpty(vcode_cz_value)){
                        ToastUtils.showShort("密码不能为空");
                        return;
                    }if(TextUtils.isEmpty(cz_password_value)){
                        ToastUtils.showShort("确认密码不能为空");
                        return;
                    }
                }else{
                    if(RegexUtils.isMobileExact(cz_phone_value)){
                            SMSSDK.submitVerificationCode("86", cz_phone_value, vcode_cz_value);
                            zLoadingDialog = new ZLoadingDialog(WjmmActivity.this);
                            zLoadingDialog.setLoadingBuilder(Z_TYPE.SINGLE_CIRCLE)//设置类型SingleCircleBuilder
                                    .setLoadingColor(Color.BLACK)//颜色
                                    .setHintText("Loading...")
                                    .show();
                    }else{
                        ToastUtils.showShort("手机号不小心修改了");
                    }
                }
                break;
            case R.id.djs_cz:
                if (TextUtils.isEmpty(cz_phone_value)) {
                    ToastUtils.showShort("手机号不能为空");
                }else{
                    if(RegexUtils.isMobileExact(cz_phone_value)){
                        SMSSDK.getVerificationCode("86",cz_phone_value);
                        djs_cz.setVisibility(View.GONE);
                        txt_cz.setVisibility(View.VISIBLE);
                        TimeCountDown timeCountDown = new TimeCountDown(60*1000,1000);
                        timeCountDown.start();
                        vcode_cz.setText("");
                    }else{
                        ToastUtils.showShort("请输入正确的手机号");
                    }
                }
                break;
            case R.id.yj:
                if (isHideFirst == true) {
                    yj.setImageResource(R.drawable.open);
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                    cz_password.setTransformationMethod(method1);
                    isHideFirst = false;
                } else {
                    yj.setImageResource(R.drawable.close);
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    cz_password.setTransformationMethod(method);
                    isHideFirst = true;
                }
                // 光标的位置
                int index = cz_password.getText().toString().length();
                cz_password.setSelection(index);
                break;
        }
    }
    public class TimeCountDown extends CountDownTimer {
        public TimeCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {//计时
            txt_cz.setClickable(false);
            txt_cz.setText(millisUntilFinished/1000+"s");
        }
        @Override
        public void onFinish() {
            txt_cz.setClickable(true);
            djs_cz.setVisibility(View.VISIBLE);
            txt_cz.setVisibility(View.GONE);
        }
    }
}
