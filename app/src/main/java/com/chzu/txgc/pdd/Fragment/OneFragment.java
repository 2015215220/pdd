package com.chzu.txgc.pdd.Fragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.blankj.utilcode.util.ToastUtils;
import com.chzu.txgc.pdd.ContactHelper.TeamCreateHelper;
import com.chzu.txgc.pdd.R;
import com.netease.nim.uikit.api.model.session.SessionCustomization;
import com.netease.nim.uikit.business.contact.selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.business.recent.RecentContactsFragment;
import com.netease.nim.uikit.business.session.constant.Extras;
import com.netease.nim.uikit.business.session.fragment.MessageFragment;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import java.util.ArrayList;

public class OneFragment extends LazyLoadFragment {
    RelativeLayout messageContainer;
    RecentContactsFragment recentContactsFragment;//聊天
    private SessionCustomization customization;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_one_fragment, container, false);
        messageContainer=(RelativeLayout) view.findViewById(R.id.messageContainer);
        recent();
        return view;
    }

    @Override
    protected void fetchData() {//初始化加载的
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//startActivityForResult
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
            final ArrayList<String> selected = data.getStringArrayListExtra(
                    ContactSelectActivity.RESULT_DATA);
            if (selected != null && !selected.isEmpty()) {
                TeamCreateHelper.createNormalTeam(getActivity(), selected, false, null);
            } else {
                ToastHelper.showToast(getActivity(), "请选择至少一个联系人！");
            }
        } else if (requestCode == 2) {
            final ArrayList<String> selected = data.getStringArrayListExtra(
                    ContactSelectActivity.RESULT_DATA);
            TeamCreateHelper.createAdvancedTeam(getActivity(), selected);
        }
    }

    private void recent() {
        recentContactsFragment = new RecentContactsFragment();
        recentContactsFragment.setContainerId(R.id.messageContainer);
        recentContactsFragment = (RecentContactsFragment) switchContent(recentContactsFragment);//初始化消息显示的 switchContent必须用这个
    }
    SessionTypeEnum sessionTypeEnum;
    protected MessageFragment fragment(String contactId) {
        switch (contactId){
            case "0":
                customization = NimUIKitImpl.commonP2PSessionCustomization;//单聊界面  commonP2PSessionCustomization  public
                sessionTypeEnum=SessionTypeEnum.P2P;
                break;
            case "1":
                customization=NimUIKitImpl.commonTeamSessionCustomization;//群聊
                sessionTypeEnum=SessionTypeEnum.Team;
                break;
            case "5":
               ToastUtils.showShort("超大群开发者按需实现");
                sessionTypeEnum=SessionTypeEnum.SUPER_TEAM;
                break;
        }
        Bundle arguments = new Bundle();
        arguments.putSerializable(Extras.EXTRA_TYPE, sessionTypeEnum);
        arguments.putString(Extras.EXTRA_ACCOUNT, contactId);
        arguments.putSerializable(Extras.EXTRA_CUSTOMIZATION, customization);
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(arguments);
        fragment.setContainerId(R.id.messageContainer);
        return fragment;
    }
    public TFragment switchContent(TFragment fragment) {
        return switchContent(fragment, false);
    }
    protected TFragment switchContent(TFragment fragment, boolean needAddToBackStack) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(fragment.getContainerId(), fragment);
        if (needAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        try {
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {

        }
        return fragment;
    }


}
