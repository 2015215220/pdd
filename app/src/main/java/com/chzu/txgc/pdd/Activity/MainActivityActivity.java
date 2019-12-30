package com.chzu.txgc.pdd.Activity;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alipay.sdk.app.EnvUtils;
import com.chzu.txgc.pdd.Fragment.FourFragment;
import com.chzu.txgc.pdd.Fragment.OneFragment;
import com.chzu.txgc.pdd.Fragment.ThreeFragment;
import com.chzu.txgc.pdd.Fragment.TwoFragment;
import com.chzu.txgc.pdd.FragmentAdapter.MyFragmentAdapter;
import com.chzu.txgc.pdd.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivityActivity extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String[] tabName = {"消息", "联系人", "发现", "我"};
    int[] tab_img = {R.drawable.liaotian, R.drawable.users, R.drawable.faxian, R.drawable.wo};
    int[] tab_img1 = {R.drawable.liaotian1, R.drawable.users1, R.drawable.faxian1, R.drawable.wo1};

    @Override
    public int bindLayout() {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);//沙箱环境模拟初始化
        return R.layout.activity_usermain;
    }

    @Override
    protected void findViews() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
    }

    @Override
    protected void initListeners() {

    }

    public void setTabItem(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_tab_item, null);
        TextView textView = (TextView) view.findViewById(R.id.tab_name);
        textView.setText(tabName[position]);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_img);
        imageView.setImageResource(tab_img[position]);//选择显示liaotian1、user1、faxian1、wo1
        tabLayout.getTabAt(position).setCustomView(view);
        //初始化的时候消息以及图片的颜色是有的，所以下标是0
        ((TextView) tabLayout.getTabAt(0).getCustomView().findViewById(R.id.tab_name)).setTextColor(Color.BLUE);
        tabLayout.getTabAt(0).getCustomView().findViewById(R.id.tab_name).setVisibility(View.VISIBLE);
        ((ImageView) tabLayout.getTabAt(0).getCustomView().findViewById(R.id.tab_img)).setImageResource(tab_img1[0]);
        tabLayout.getTabAt(0).getCustomView().findViewById(R.id.tab_img).setVisibility(View.VISIBLE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setStatus(tab);//加载
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                setStatus(tab);//释放
            }
        });
    }
    /**
     * 设置tablayout选中状态
     *
     * @param tab
     */
    private void setStatus(TabLayout.Tab tab) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            if (i == tab.getPosition()) {
                ((TextView) tabLayout.getTabAt(i).getCustomView().findViewById(R.id.tab_name)).setTextColor(Color.BLUE);
                tabLayout.getTabAt(i).getCustomView().findViewById(R.id.tab_name).setVisibility(View.VISIBLE);
                ((ImageView) tabLayout.getTabAt(i).getCustomView().findViewById(R.id.tab_img)).setImageResource(tab_img1[i]);
                tabLayout.getTabAt(i).getCustomView().findViewById(R.id.tab_img).setVisibility(View.VISIBLE);
            } else {
                ((TextView) tabLayout.getTabAt(i).getCustomView().findViewById(R.id.tab_name)).setTextColor(Color.BLACK);
                ((ImageView) tabLayout.getTabAt(i).getCustomView().findViewById(R.id.tab_img)).setImageResource(tab_img[i]);
            }
        }
    }

    @Override
    protected void initData() {
        // 创建一个集合,装填Fragment
        ArrayList<Fragment> fragments = new ArrayList<>();// 装填
        fragments.add(new OneFragment());
        fragments.add(new TwoFragment());
        fragments.add(new ThreeFragment());
        fragments.add(new FourFragment());// 创建ViewPager适配器
        MyFragmentAdapter myPagerAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        myPagerAdapter.setFragments(fragments);// 给ViewPager设置适配器
        viewPager.setAdapter(myPagerAdapter);// 使用 TabLayout 和 ViewPager 相关联
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < fragments.size(); i++) {
            setTabItem(i);
        }
    }
    @Override
    protected void onViewClicked(int viewId) {

    }
}
