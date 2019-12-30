package com.chzu.txgc.pdd.Activity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chzu.txgc.pdd.AiliPay.AuthResult;
import com.chzu.txgc.pdd.AiliPay.OrderInfoUtil2_0;
import com.chzu.txgc.pdd.AiliPay.PayResult;
import com.chzu.txgc.pdd.Bean.AilipayBean;
import com.chzu.txgc.pdd.Bean.ChildinitBean;
import com.chzu.txgc.pdd.Dao.MyDao;
import com.chzu.txgc.pdd.R;
import com.chzu.txgc.pdd.Utils.JsonUtils;

import java.util.Map;
public class BuyActivity extends BaseActivity {
    ChildinitBean childinitBean;//获取数据源
    ImageView child_img_chuan;//图片
    TextView price_chuan;//价格
    TextView child_txt_chuan;//名称
    Button gwc;//加入购物车
    Button gm;//购买
    String pr="";
    //支付宝购买需要的参数
    public static final String APPID = "2016101300675595";
    public static final String RSA2_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCVVIy1S+E68vi5v2IuHmmFdULWIjMgyIIuxihLVxjDiq6Q967gFSrkG7sIpoi82JsuNu7Dv2nubCaTWXLrdysYDtsG6doALTVL+uIIxULmh4yV4kgnVG4YE++Syezp31lbxZSNGCs2bXbe0OicJTO5KwLEbSfWrIjtnvgXBolnVwzdkdhTxx2k7HWdON6PrYi3e+wcPGfkVI3CicHhgXnwaRuA0I6WMXIRUAYQeyG9XoWDX5nKEGakrRK4Yh4krxXAUbnsja9I5kGeiHKy42nyblc0jzzwGF+eA49OB2t0o++QDBpgmLbQ7/9PO2akFTK9vOQkz2wwz3CPQ6AR2VxNAgMBAAECggEAV8XHovSD4XtZGQzV75eunGQtMeIOPVG9uzJ8ErSR+LW2H72Fy26FtGhcShxguOd0Zvig5OvInvxwvCFSe/ainNR2hxzDJ2FrN0qyu0b/KLM5vguwL3Xf9EUbhyMrHthKFLgQtdIj7CzxNsyCPO1c9pTCXqRuSy9EmK7c0ePktZk9SJOd1gDo/gI7AnD3vWCqPR01htYJJcBpZN2x+6BVHBeJq/0sBdOQMB1q07+wnf3LOoD9uYeyxYHJNskXWHjNW77gxRklyoGtnZDGGmoa46VQyFpEa2ZskyKex7yLf2HqKdvdxzGxsWqOxyNJn0UiWQCbCSjrhz//PxSUulvrYQKBgQDWJvDBV0BQvQMN+n8qa6dvkzZ9Z3AIDXFVvxJICuGq7OsOBfYdXWhthy7t4ThhJVx3ktHcOUqt7z9RnCkf/r4tPMZr5Fi77TaA8j0jg2z8QpkA/sYOFUtV4dnMTaVtCQ93sKUx8JL6r7tw9facln8VZwJsQK3+EdfLrTbunRKYeQKBgQCygt3KAvKxnMl3IQcPCDIK1AjOTgFSK6nTSgD70Dc2nG0Z2cnccafLsAGB+hCfBY2t//8zkfjwKMZey9V6kof+ScqOLvO0IFmyQb9QCpxVaxaua8g1JoDg0EWUz7NgNmsFmdWWmgpJt1ZUDe6s8OsYlg/97ip6IPAJvqK6diXVdQKBgAGdPYdXjwaMxi8eykKZmSOAKpL7ap7E6yvISuz8GANf0c3DQb2q7JSWxkXIwtIIpA/KrYZJcESrXwDQeobhNrHEmYJKsFiKSzThnpBb90BXiCu3v+pvQqgdEbU2iJ6vyMwQKs6bj6AQHwuU7wzzHXYd1qjWO/mETOoeHiHiUTeRAoGAC7Zibvq6icTLwzE7kVTsU6GN4ltj4sxSnDZi0iLbPI4O6n9Lq1cZt1eZAboylPx8S0pcqDmPp9JFEbs2CpDKs3iL/cA+5TaqK/P8ZXgMwd4A4vjXbgVUt2ty5UyrbEilxPxO806wFh4dX+56FobIJhjSe8rAprq6hSko7CsMWHECgYBkZ9EDRKqI/Tl5KiR2vq4sooJ+c7N0tHiScDFWTIbEanrB0oW+GIMz8mPoPLzCR5DUo87vgMihcGJVHwWMVRmDZOTeTqr499gOYhoHH2zl8SCs4da0nLYJStCk8ni/ROUtAYBZ89widLtPUc9YFuUTFlWn3lyvdxaqJQucJmXgdQ==";
    public static final String RSA_PRIVATE = "";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showAlert(BuyActivity.this, getString(R.string.pay_success) + payResult);
                        LogUtils.e("hy",payResult.getResult());
                        LogUtils.e("pdd",payResult.getResultStatus());
                        LogUtils.e("wyj",payResult.getMemo());
                        //需要的信息： 商家id  seller_id   用户id app_id   购买的时间 timestamp  购买的金额 total_amount  商品订单号 out_trade_no
                        //验证应用程序id  auth_app_id  交易编号 trade_no  卖家id  2088102179366241
                        //alipay_trade_app_pay_response
                        //{{"alipay_trade_app_pay_response":{"code":"10000","msg":"Success","app_id":"2016101300675595","auth_app_id":"2016101300675595",
                        // "charset":"utf-8","timestamp":"2019-12-26 18:58:08","out_trade_no":"122618575619551",
                        // "total_amount":"3.00","trade_no":"2019122622001420991000131044","seller_id":"2088102179366241"},
                        // "sign":"qNfP1kvhIypQtmX858ef+xg1tHbUzjmi9hxphXD2x1v/kTW2iQFZcsJCDOMCvJBYnAkczGylk/K2als1V5ilyKhlDN0
                        // vbFUdWFiQIiCVX1XhJy990yWWF5RwHhphC8XkcWc9jlYDV8G9Pzakebs0O4v9FfZQk4kMDtFXW0bpg9Gf4PIl8I7LFq0XZ/
                        // 8TKYeC+/G3hN1G2BBO3Ys0KEZVh4n9+gFgJN/8SxjkzGRr/zwMwm0kI8meBvcgObS0HGJyJZsDvi1qk71dzaz5eYo90xGdCEp
                        // P3HAqn9UA5CjK10kZNQYwa1GzzvLWQBgLz4GssWEW3ufESvlI/lyWTTFD6w==","sign_type":"RSA2"}}
                        AilipayBean ailipayBean= JsonUtils.getModel(payResult.getResult(),"alipay_trade_app_pay_response",AilipayBean.class);
                        LogUtils.e("hy",ailipayBean.getApp_id()+"===="+ailipayBean.getAuth_app_id()+"====="+ailipayBean.getSeller_id());//获取到了新的数据，支付完成的数据

                    } else {
                        showAlert(BuyActivity.this, getString(R.string.pay_failed) + payResult.getMemo());
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        showAlert(BuyActivity.this, getString(R.string.auth_success) + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        showAlert(BuyActivity.this, getString(R.string.auth_failed) + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    @Override
    public int bindLayout() {
        return R.layout.activity_buy;
    }

    @Override
    protected void findViews() {
        child_img_chuan = (ImageView) findViewById(R.id.child_img_chuan);//图片
        price_chuan = (TextView) findViewById(R.id.price_chuan);//价格
        child_txt_chuan = (TextView) findViewById(R.id.child_txt_chuan);//名字
        Intent intent = getIntent();
        childinitBean = (ChildinitBean) intent.getSerializableExtra("banna");
        child_img_chuan.setImageResource(childinitBean.getImageId());//图片
        price_chuan.setText(childinitBean.getPrice());//价格
        child_txt_chuan.setText(childinitBean.getName());//名字
        pr=childinitBean.getPri();
        gwc = (Button) findViewById(R.id.gwc);//购物车
        gm = (Button) findViewById(R.id.gm);//购买
    }

    @Override
    protected void initListeners() {
        gwc.setOnClickListener(this);
        gm.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClicked(int viewId) {
        switch (viewId) {
            case R.id.gwc:
                showgwc();//购物车对话框
                break;
            case R.id.gm:
                boolean rsa2 = (RSA2_PRIVATE.length() > 0);
                LogUtils.e("hy",childinitBean.getPri());
                String number="1";//直接购买默认就是1
                Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,pr,number);//传值,多少钱、多少个
                String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

                String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
                String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
                final String orderInfo = orderParam + "&" + sign;
                final Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(BuyActivity.this);
                        Map<String, String> result = alipay.payV2(orderInfo, true);
                        Log.i("msp", result.toString());
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };// 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();

                break;
        }
    }

    /*
    * 自定义对话框视图
    * 1.新建一个Dialogd对话框
    * 2.新建一个view
    * 3.使用Window定义界面长度
    * 4.把view 放到Dialog对话框里面
    * */
    int number=1;
    public void showgwc() {
        Dialog gwc_dialog = new Dialog(this, R.style.Translucent_NoTitle);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_dialog_jrgwc, null);//其中数据源
        Button wp_decrease=view.findViewById(R.id.wp_decrease);//减
        Button wp_value=view.findViewById(R.id.wp_value);//数量的值
        Button wp_add=view.findViewById(R.id.wp_add);//加
        Button wp_ensure=view.findViewById(R.id.wp_ensure);//确定
        TextView wp_price=view.findViewById(R.id.wp_price);//价格的值
        ImageView wp_img=view.findViewById(R.id.wp_img);//图标
        wp_img.setImageResource(childinitBean.getImageId());
        wp_price.setText(childinitBean.getPri()+"￥");
        number=Integer.valueOf(wp_value.getText().toString());//数量
        wp_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //改变减号颜色
                wp_decrease.setTextColor(Color.parseColor("#ff666666"));//修改颜色
                number++;
                wp_value.setText(String.valueOf(number));
            }
        });
        wp_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number>1) {
                    number--;
                    wp_value.setText(String.valueOf(number));
                }else{
                    wp_decrease.setTextColor(Color.parseColor("#33000000"));//修改颜色
                }
            }
        });
        wp_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //数据库 主要字段是 图片的值、数量、价格
                MyDao.getInstance(BuyActivity.this).addGwc(childinitBean.getImageId(),wp_value.getText().toString(),String.valueOf(childinitBean.getPri()));
                LogUtils.e("hy",String.valueOf(childinitBean.getImageId())+"====="+wp_value.getText().toString()+"====="+childinitBean.getPri());
                ToastUtils.showShort("已经添加到数据库中");
                gwc_dialog.dismiss();
            }
        });
        gwc_dialog.setContentView(view);
        Window dialogWindow = gwc_dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.alpha = 1.0f; // 透明度
        dialogWindow.setAttributes(lp);
        gwc_dialog.show();

    }
    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }
    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }
}
