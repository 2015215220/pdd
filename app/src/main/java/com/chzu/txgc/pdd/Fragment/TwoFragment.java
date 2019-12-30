package com.chzu.txgc.pdd.Fragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chzu.txgc.pdd.Activity.AddFriendActivity;
import com.chzu.txgc.pdd.Uikit.AddTeamActivity;
import com.chzu.txgc.pdd.Activity.LoginActivity;
import com.chzu.txgc.pdd.Activity.SearchActivity;
import com.chzu.txgc.pdd.R;
import com.chzu.txgc.pdd.Uikit.SystemMessageActivity;
import com.netease.nim.uikit.api.model.session.SessionCustomization;
import com.netease.nim.uikit.business.contact.ContactsFragment;
import com.netease.nim.uikit.business.session.constant.Extras;
import com.netease.nim.uikit.business.session.fragment.MessageFragment;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

public class TwoFragment extends LazyLoadFragment implements ContactsFragment.onRecentClickedListener1 {
    private MessageFragment messageFragment;
    ContactsFragment contactsFragment;//通讯录
    RelativeLayout messageContainer1;
    ImageView addfriend;
    private SessionCustomization customization;
    private TopRightMenu topMenu;
    RelativeLayout verify_relativelayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_two_fragment, container, false);
        messageContainer1=(RelativeLayout) view.findViewById(R.id.messageContainer1);
        addfriend=(ImageView)view.findViewById(R.id.addfriend);//添加好友
        verify_relativelayout=view.findViewById(R.id.verify_relativelayout);

        recent();
        addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topMenu=new TopRightMenu(getActivity());
                List<MenuItem> menuItems = new ArrayList<>();//bean文件
                menuItems.add(new MenuItem(R.drawable.tianjiahaoyou,"添加好友"));
                menuItems.add(new MenuItem(R.drawable.chuangjianqunliao,"创建群"));
                menuItems.add(new MenuItem(R.drawable.sousuoqunzu,"搜索群"));
                menuItems.add(new MenuItem(R.drawable.zhuxiao,"注销"));
                topMenu.setHeight(580)
                        .setWidth(420)
                        .showIcon(true)
                        .dimBackground(true)
                        .needAnimationStyle(true)
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)
                        .addMenuList(menuItems)
                        .dimBackground(true)
                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position) {
                                if (position==0){
                                    Intent intent=new Intent(getActivity(), AddFriendActivity.class);
                                    startActivity(intent);
                                }else if(position==1){
                                    Intent intent=new Intent(getActivity(), AddTeamActivity.class);
                                    startActivity(intent);
                                }else if(position==2){
                                    Intent intent=new Intent(getActivity(), SearchActivity.class);
                                    startActivity(intent);
                                }else if(position==3){
                                    ToastUtils.showShort("注销");
                                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intent);
                                    SPUtils.getInstance().put("password","");
                                    getActivity().finish();
                                }
                            }
                        }).showAsDropDown(addfriend,  -330, 0);//存放图片的名字
            }
        });
        verify_relativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SystemMessageActivity.start(getActivity());
            }
        });

        return view;
    }

    private void recent() {
        contactsFragment =new ContactsFragment();
        contactsFragment.setListener(this);
        contactsFragment.setContainerId(R.id.messageContainer1);
        contactsFragment = (ContactsFragment) switchContent(contactsFragment);//联系人的界面
    }

    @Override
    public void onRecentClicked(String recent) {
        //这个是单击之后出现的效果，第二句显示的是界面效果
        messageFragment = fragment(recent);
        messageFragment = (MessageFragment) switchContent(messageFragment);
    }

    protected MessageFragment fragment(String contactId) {
        customization = NimUIKitImpl.commonP2PSessionCustomization;//单聊界面
        Bundle arguments = new Bundle();
        arguments.putSerializable(Extras.EXTRA_TYPE, SessionTypeEnum.P2P);
        arguments.putString(Extras.EXTRA_ACCOUNT, contactId);
        arguments.putSerializable(Extras.EXTRA_CUSTOMIZATION, customization);
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(arguments);
        fragment.setContainerId(R.id.messageContainer1);
        return fragment;
    }
    public TFragment switchContent(TFragment fragment) {
        return switchContent(fragment, false);
    }

    protected TFragment switchContent(TFragment fragment, boolean needAddToBackStack) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(fragment.getContainerId(), fragment);//**********replaced的
        if (needAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        try {
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {

        }
        return fragment;
    }
    @Override
    protected void fetchData() {
        //初始化加载的
    }

}
