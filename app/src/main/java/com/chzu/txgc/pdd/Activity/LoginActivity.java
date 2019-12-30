package com.chzu.txgc.pdd.Activity;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chzu.txgc.pdd.Dao.MyDao;
import com.chzu.txgc.pdd.R;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;
public class LoginActivity extends BaseActivity {
    private EditText edt_number;//手机号
    private EditText edt_password;//密码
    private Button login_btn;//登录按钮
    private TextView register_txt;//注册文本按钮
    private TextView wjmm_txt;//忘记密码文本按钮
    private MyDao myDao;
    private ImageView yj;//小眼睛
    private boolean isHideFirst = true;

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void findViews() {
        edt_number = (EditText) findViewById(R.id.edt_number);//手机号
        edt_password = (EditText) findViewById(R.id.edt_password1);//密码
        login_btn = (Button) findViewById(R.id.login_btn);
        register_txt = (TextView) findViewById(R.id.register_txt);
        wjmm_txt = (TextView) findViewById(R.id.wjmm_txt);
        yj=(ImageView)findViewById(R.id.yj);
        myDao=MyDao.getInstance(this);//单例模式编写不需要再new新的对象
            edt_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        // 此处为得到焦点时的处理内容
                        yj.setVisibility(View.VISIBLE);
                        yj.setImageResource(R.drawable.close);
                    } else {
                        // 此处为失去焦点时的处理内容  如果显示需要加密变成*
                        if (isHideFirst == false) {
                            TransformationMethod method = PasswordTransformationMethod.getInstance();
                            edt_password.setTransformationMethod(method);
                            isHideFirst = true;
                        }
                        yj.setVisibility(View.GONE);
                    }
                }
            });
        }

    @Override
    protected void initListeners() {
        edt_number.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        register_txt.setOnClickListener(this);
        wjmm_txt.setOnClickListener(this);
        yj.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(SPUtils.getInstance().getString("phone_value")!=null && SPUtils.getInstance().getString("password")!=null){
            edt_number.setText(SPUtils.getInstance().getString("phone_value"));
            edt_password.setText(SPUtils.getInstance().getString("password"));
        }
    }

    @Override
    protected void onViewClicked(int viewId) {
        switch (viewId) {
            case R.id.login_btn:
                String userphone=edt_number.getText().toString().trim();
                String userpassword=edt_password.getText().toString().trim();


                if(TextUtils.isEmpty(userphone) || TextUtils.isEmpty(userpassword)){
                    if(TextUtils.isEmpty(userphone)){
                        ToastUtils.showShort("手机号不能为空");
                        return;
                    }
                    if(TextUtils.isEmpty(userpassword)){
                        ToastUtils.showShort("密码不能为空");
                        return;
                    }
                }else{
                    if(RegexUtils.isMobileExact(userphone)){//判断是不是正确的手机号
                        try{
                            NimUIKit.login(new LoginInfo(userphone, userpassword), new RequestCallback<LoginInfo>() {
                                @Override
                                public void onSuccess(LoginInfo loginInfo) {
                                    NimUIKitImpl.setAccount(loginInfo.getAccount());
                                    NimUIKit.setAccount(loginInfo.getAccount());
                                    ToastUtils.showShort("登录成功");

                                    SPUtils.getInstance().put("phone_value",edt_number.getText().toString());
                                    SPUtils.getInstance().put("password",edt_password.getText().toString());

                                    if(!myDao.query_phone(userphone)){//else   说明在手机中注册了
                                       myDao.addUser(userphone,userpassword);//login_num, String password, String nickname
                                    }
                                    goActivity(MainActivityActivity.class);
                                    finish();
                                }
                                @Override
                                public void onFailed(int i) {
                                    ToastUtils.showShort("登录失败" + i);
                                }
                                @Override
                                public void onException(Throwable throwable) {
                                    ToastUtils.showShort(throwable.getMessage());
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                            ToastUtils.showShort("系统出现异常");
                        }
                    }else{
                        ToastUtils.showShort("请输入正确的手机号");
                    }
                }
                break;
            case R.id.register_txt:
                goActivity(RegisterActivity.class);
                break;
            case R.id.wjmm_txt:
                goActivity(WjmmActivity.class);
                break;
            case R.id.yj:
                if (isHideFirst == true) {
                    yj.setImageResource(R.drawable.open);
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                    edt_password.setTransformationMethod(method1);
                    isHideFirst = false;
                } else {
                    yj.setImageResource(R.drawable.close);
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    edt_password.setTransformationMethod(method);
                    isHideFirst = true;
                }
                // 光标的位置
                int index = edt_password.getText().toString().length();
                edt_password.setSelection(index);
                break;

        }
    }
}
