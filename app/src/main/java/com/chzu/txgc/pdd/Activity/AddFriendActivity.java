package com.chzu.txgc.pdd.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.chzu.txgc.pdd.R;
import com.netease.nim.uikit.api.wrapper.NimToolBarOptions;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.activity.ToolBarOptions;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.dialog.EasyEditDialog;
import com.netease.nim.uikit.common.ui.widget.ClearableEditTextWithIcon;
import com.netease.nim.uikit.common.util.sys.NetworkUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.constant.VerifyType;
import com.netease.nimlib.sdk.friend.model.AddFriendData;

import java.util.HashMap;

public class AddFriendActivity extends UI {
    private ClearableEditTextWithIcon searchEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ToolBarOptions options = new NimToolBarOptions();
        options.titleString = "添加好友";
        setToolBar(R.id.toolbar, options);
        findViews();
        initActionbar();
    }

    private void findViews() {
        searchEdit = findView(R.id.search_friend_edit);///添加好友页面显示请输入账号，限20位字母或者数字  界面设计在layout下面
        searchEdit.setDeleteImage(R.drawable.nim_grey_delete_icon);
    }

    private void initActionbar() {
        TextView toolbarView = findView(R.id.action_bar_right_clickable_textview);
        toolbarView.setText("添加好友");
        toolbarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(searchEdit.getText().toString())) {
                    ToastHelper.showToast(AddFriendActivity.this, R.string.not_allow_empty);
                } else if (searchEdit.getText().toString().equals(SPUtils.getInstance().getString("phone_value"))) {
                    ToastHelper.showToast(AddFriendActivity.this, "不能添加自己为好友");
                } else {
                    HashMap<String, String> searchParams = new HashMap<String, String>();
                    searchParams.put("action", "addfriend");
                    searchParams.put("accid", SPUtils.getInstance().getString("phone_value"));
                    searchParams.put("faccid", searchEdit.getText().toString());//输入的账号
                    //doAddFriend(null, true);  // 直接加为好友
                    onAddFriendByVerify(); // 发起好友验证请求
                }
            }
        });
    }

    /**
     * 通过验证方式添加好友
     */
    private void onAddFriendByVerify() {
        final EasyEditDialog requestDialog = new EasyEditDialog(this);
        requestDialog.setEditTextMaxLength(32);
        requestDialog.setTitle(getString(R.string.add_friend_verify_tip));
        requestDialog.addNegativeButtonListener(R.string.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDialog.dismiss();
            }
        });
        requestDialog.addPositiveButtonListener(R.string.send, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDialog.dismiss();
                String msg = requestDialog.getEditMessage();
                doAddFriend(msg, false);
            }
        });
        requestDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        requestDialog.show();
    }

    private void doAddFriend(String msg, boolean addDirectly) {
        if (!NetworkUtil.isNetAvailable(this)) {
            ToastHelper.showToast(AddFriendActivity.this, R.string.network_is_not_available);
            return;
        }
        if (searchEdit.getText().toString().equals(SPUtils.getInstance().getString("phone_value"))) {
            ToastHelper.showToast(AddFriendActivity.this, "不能添加自己为好友");
        }
        final VerifyType verifyType = addDirectly ? VerifyType.DIRECT_ADD : VerifyType.VERIFY_REQUEST;
        DialogMaker.showProgressDialog(this, "", true);
        NIMClient.getService(FriendService.class).addFriend(new AddFriendData(searchEdit.getText().toString(), verifyType, msg))
                .setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                        DialogMaker.dismissProgressDialog();
                        if (VerifyType.DIRECT_ADD == verifyType) {
                            ToastHelper.showToast(AddFriendActivity.this, "添加好友成功");
                        } else {
                            ToastHelper.showToast(AddFriendActivity.this, "添加好友请求发送成功");
                        }
                    }
                    @Override
                    public void onFailed(int code) {
                        DialogMaker.dismissProgressDialog();
                        if (code == 408) {
                            ToastHelper.showToast(AddFriendActivity.this, R.string.network_is_not_available);
                        } else {
                            ToastHelper.showToast(AddFriendActivity.this, "on failed:" + code);
                        }
                    }

                    @Override
                    public void onException(Throwable exception) {
                        DialogMaker.dismissProgressDialog();
                    }
                });
    }


}
