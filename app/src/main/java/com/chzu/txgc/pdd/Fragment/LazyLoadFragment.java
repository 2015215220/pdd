package com.chzu.txgc.pdd.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

/*
* 预加载
* Fragment 懒加载
* 主要工作原理是
* 程序切换到哪一个页面，才加载哪一个页面，提高网络合理性，不然加载会特别卡
* */
public abstract class LazyLoadFragment extends Fragment {
    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();//false
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    protected abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(true);//网上是false 改成true就差不多了
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }
}
