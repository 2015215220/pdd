package com.chzu.txgc.pdd.FragmentAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    public void setFragments(ArrayList<Fragment> fragments) {
        mFragmentList = fragments;
    }
    public MyFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragmentList.get(position);
        return fragment;
    }
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
