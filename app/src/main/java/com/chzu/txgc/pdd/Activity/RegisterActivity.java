package com.chzu.txgc.pdd.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.chzu.txgc.pdd.Bean.RegisterBean;
import com.chzu.txgc.pdd.Contact.CheckSumBuilder;
import com.chzu.txgc.pdd.Dao.MyDao;
import com.chzu.txgc.pdd.Implement.OnRequestResult;
import com.chzu.txgc.pdd.R;
import com.chzu.txgc.pdd.Utils.JsonUtils;
import com.chzu.txgc.pdd.Utils.MD5Utils;
import com.chzu.txgc.pdd.Utils.OkgoUtils;
import com.lzy.okgo.model.HttpHeaders;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.util.string.MD5;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity {
    private EditText edt_nickname;//昵称
    private EditText edt_phone;//电话
    private EditText edt_password;//密码
    private EditText edt_repassword;//确认密码
    private EditText vcode_edt;//验证码
    private TextView txt_btn;//验证码数字显示，默认是不可见的
    private Button djs_btn;//验证码按钮
    private Button register_btn;//注册
    private ImageView yj1;//密码
    private ImageView yj2;//确认密码
    boolean regis=false;
    MyDao myDao;
    EventHandler eh;//短信验证
    ZLoadingDialog zLoadingDialog;//对话框旋转
    RegisterBean registerBean;//注册的Bean接口
    private boolean isHideFirst1 = true;
    private boolean isHideFirst2 = true;
    @Override
    public int bindLayout() {
        return R.layout.activity_register;
    }
    @Override
    protected void findViews() {
        edt_nickname=(EditText)findViewById(R.id.edt_nickname);//昵称
        edt_phone = (EditText) findViewById(R.id.edt_phone);//电话
        edt_password = (EditText) findViewById(R.id.edt_password);//密码
        edt_repassword = (EditText) findViewById(R.id.edt_repassword);//确认密码
        vcode_edt=(EditText)findViewById(R.id.vcode_edt);//验证码数值
        djs_btn=(Button)findViewById(R.id.djs_btn);//短信验证
        register_btn = (Button) findViewById(R.id.register_btn);//注册按钮
        txt_btn=(TextView)findViewById(R.id.txt_btn);//隐藏的60s
        myDao=MyDao.getInstance(this);
        yj1=(ImageView)findViewById(R.id.yj1);
        yj2=(ImageView)findViewById(R.id.yj2);

        edt_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    yj1.setVisibility(View.VISIBLE);
                    yj1.setImageResource(R.drawable.close);
                } else {
                    // 此处为失去焦点时的处理内容
                    if(isHideFirst1 == false){
                        TransformationMethod method = PasswordTransformationMethod.getInstance();
                        edt_password.setTransformationMethod(method);
                        isHideFirst1 = true;
                    }
                    yj1.setVisibility(View.GONE);
                }
            }
        });
        edt_repassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    yj2.setVisibility(View.VISIBLE);
                    yj2.setImageResource(R.drawable.close);
                } else {
                    if(isHideFirst2 == false){
                        TransformationMethod method = PasswordTransformationMethod.getInstance();
                        edt_repassword.setTransformationMethod(method);
                        isHideFirst2 = true;
                    }
                    // 此处为失去焦点时的处理内容
                    yj2.setVisibility(View.GONE);
                }
            }
        });
    }
    @Override
    protected void initListeners() {
        djs_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        yj1.setOnClickListener(this);
        yj2.setOnClickListener(this);
    }
    @Override
    protected void initData() {
        eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        /*
                         * 数据库存储以及跳转
                         * */
                        String edt_nickname_value=edt_nickname.getText().toString().trim();//昵称
                        String edt_phone_value=edt_phone.getText().toString().trim();//电话
                        String edt_password_value=edt_password.getText().toString().trim();//密码
                        zLoadingDialog.dismiss();
                        register(edt_nickname_value,edt_phone_value,edt_password_value);
                        if(regis){
                            myDao.addUser(edt_phone_value, edt_password_value);//存储账号和密码就可以
                            ToastUtils.showShort("注册成功、进入登录界面");
                            SPUtils.getInstance().put("phone_value",edt_phone.getText().toString());
                            SPUtils.getInstance().put("password",edt_password.getText().toString());
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
                        ToastUtils.showShort("获取验证码失败");
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    private void register(String edt_nickname_value,String edt_phone_value,String edt_password_value) {//昵称电话、电话、密码
        if (!NetworkUtil.isNetAvailable(RegisterActivity.this)) {
            ToastHelper.showToast(RegisterActivity.this, R.string.network_is_not_available);
            return;
        }
        String url="https://api.netease.im/nimserver/user/create.action";
        String name="";
        try {
            name = URLEncoder.encode(edt_nickname_value, "UTF-8");//昵称
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //头部参数
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
        //身体部分
        Map<String,String> params=new HashMap<>();
        params.put("accid",edt_phone_value);
        params.put("name",name);
        params.put("token",edt_password_value);

        OkgoUtils.getInstance().post(url,params ,headers, 10086, new OnRequestResult() {
            @Override
            public void onSuccess(int code, String json) {
                if (TextUtils.isEmpty(json)) {
                    ToastUtils.showShort("注册失败");
                    LogUtils.e("注册失败:" + code + "\n");
                    regis = false;
                }
                try {
                    //{"code":200,"info":{"token":"123456","accid":"15651650320","name":"fhp"}}
                    LogUtils.e("hy", json);
                    String register_code = JsonUtils.getValue(json, "code");//获取的值   json获取里面的某一个值
                    if (register_code.equals("200")) {
                        regis = true;
                        registerBean = JsonUtils.getModel(json,"info", RegisterBean.class);
                    } else {//{"desc":"already register","code":414}
                        ToastUtils.showShort(JsonUtils.getValue(json, "desc"));
                        regis = false;
                    }
                }catch (JSONException e1){
                    e1.printStackTrace();
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showShort(msg);
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
        String edt_nickname_value=edt_nickname.getText().toString().trim();//昵称
        String edt_phone_value=edt_phone.getText().toString().trim();//电话
        String edt_password_value=edt_password.getText().toString().trim();//密码
        String edt_repassword_value=edt_repassword.getText().toString().trim();//确认密码
        String vcode_edt_value=vcode_edt.getText().toString().trim();//验证码
        switch (viewId) {
            case R.id.register_btn:

                if(TextUtils.isEmpty(edt_phone_value) || TextUtils.isEmpty(edt_password_value) || TextUtils.isEmpty(edt_repassword_value) || TextUtils.isEmpty(vcode_edt_value)) {
                    if(TextUtils.isEmpty(edt_nickname_value)){
                        ToastUtils.showShort("昵称不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(edt_phone_value)){
                        ToastUtils.showShort("手机号不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(edt_password_value)){
                        ToastUtils.showShort("密码不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(edt_repassword_value)){
                        ToastUtils.showShort("确认密码不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(vcode_edt_value)){
                        ToastUtils.showShort("验证码不能为空");
                        return;
                    }
                }else{
                    //注册按钮
                    if (RegexUtils.isMobileExact(edt_phone_value)) {
                        if (StringUtils.equals(edt_password_value,edt_repassword_value)) {
                            //查询是否已经注册
                            if(myDao.query_phone(edt_phone_value)){
                                ToastUtils.showShort("手机号已经注册");
                                goActivity(LoginActivity.class);
                                return;
                            }
                            else{
                                SMSSDK.submitVerificationCode("86", edt_phone_value, vcode_edt_value); //code 验证吗比较
                                zLoadingDialog = new ZLoadingDialog(RegisterActivity.this);
                                zLoadingDialog.setLoadingBuilder(Z_TYPE.SINGLE_CIRCLE)//设置类型SingleCircleBuilder
                                        .setLoadingColor(Color.BLACK)//颜色
                                        .setHintText("Loading...")
                                        .show();
                            }
                        } else {
                            ToastUtils.showShort("2次密码输入不一致");
                        }
                    } else {
                        ToastUtils.showShort("手机号不小心修改了");
                    }
                }
                break;
            case R.id.djs_btn:
                if (TextUtils.isEmpty(edt_phone_value)) {
                    ToastUtils.showShort("手机号不能为空");
                } else {
                    if (RegexUtils.isMobileExact(edt_phone_value)) {
                        SMSSDK.getVerificationCode("86", edt_phone_value);//发送短信验证码
                        djs_btn.setVisibility(View.GONE);
                        txt_btn.setVisibility(View.VISIBLE);
                        TimeCountDown timeCountDown = new TimeCountDown(60*1000,1000);
                        timeCountDown.start();
                        vcode_edt.setText("");//发送一次 清空一次
                    } else {
                        ToastUtils.showShort("请输入正确的手机号");
                    }
                }
                break;
            case R.id.yj1:
                if (isHideFirst1 == true) {
                    yj1.setImageResource(R.drawable.open);
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                    edt_password.setTransformationMethod(method1);
                    isHideFirst1 = false;
                } else {
                    yj1.setImageResource(R.drawable.close);
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    edt_password.setTransformationMethod(method);
                    isHideFirst1 = true;
                }
                // 光标的位置
                int index = edt_password.getText().toString().length();
                edt_password.setSelection(index);
                break;
            case R.id.yj2:
                if (isHideFirst2 == true) {
                    yj2.setImageResource(R.drawable.open);
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                    edt_repassword.setTransformationMethod(method1);
                    isHideFirst2 = false;
                } else {
                    yj2.setImageResource(R.drawable.close);
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    edt_repassword.setTransformationMethod(method);
                    isHideFirst2 = true;
                }
                // 光标的位置
                int index1 = edt_repassword.getText().toString().length();
                edt_repassword.setSelection(index1);
                break;
        }
    }

    public class TimeCountDown extends CountDownTimer {
        public TimeCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            //计时
            txt_btn.setClickable(false);
            txt_btn.setText(millisUntilFinished/1000+"s");
        }
        @Override
        public void onFinish() {
            txt_btn.setClickable(true);
            djs_btn.setVisibility(View.VISIBLE);
            txt_btn.setVisibility(View.GONE);
        }
    }
}
