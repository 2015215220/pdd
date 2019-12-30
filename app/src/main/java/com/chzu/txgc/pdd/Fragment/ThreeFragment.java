package com.chzu.txgc.pdd.Fragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chzu.txgc.pdd.Activity.BuyActivity;
import com.chzu.txgc.pdd.Adapter.ChildinitAdapter;
import com.chzu.txgc.pdd.Bean.ChildinitBean;
import com.chzu.txgc.pdd.R;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;

import java.util.ArrayList;
import java.util.List;


public class ThreeFragment extends LazyLoadFragment {
    RecyclerView childrearing;
    ChildinitAdapter childinitAdapter;
    List<ChildinitBean> childinitBeans=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_three_fragment, container, false);
        initChild();//初始化数据
        childrearing=(RecyclerView)view.findViewById(R.id.childrearing);
        childinitAdapter=new ChildinitAdapter(getActivity(),childinitBeans,R.layout.activity_child);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL, false);
        childrearing.setLayoutManager(gridLayoutManager);
        childinitAdapter.setOnItemClickListener(new EasyRVAdapter.OnItemClickListener<ChildinitBean>() {
            @Override
            public void onItemClick(View view, int position, ChildinitBean item) {
                ChildinitBean childinitBean=childinitBeans.get(position);//得到数据进行传输
                Intent intent = new Intent(getActivity(), BuyActivity.class);
                intent.putExtra("banna", childinitBean);
                startActivity(intent);
                LogUtils.e("hy",childinitBean);
            }
        });
        childrearing.setAdapter(childinitAdapter);
        return view;
    }

    private void initChild() {
        ChildinitBean apple = new ChildinitBean("Banana", R.drawable.banna,"￥3","18","3");
        childinitBeans.add(apple);//添加数据源
        //价格例如 3-30
        String qj=apple.getPrice();
        String qj_value=qj.substring(1);
        int count=Integer.parseInt(qj_value)*10;
        apple.setPrice("￥"+String.valueOf(qj_value)+"-"+String.valueOf(count));
        //多少人例如34人付款
        String person=apple.getFk();
        apple.setFk(person+"人付款");

        ChildinitBean banana = new ChildinitBean("Banana", R.drawable.ic_capture_bg,"￥2.5","15","2.5");
        //价格例如 3-30
        childinitBeans.add(banana);//添加数据源
        String qj1=banana.getPrice();
        String qj_value1=qj1.substring(1);
        Double count1=Double.parseDouble(qj_value1)*10;
        banana.setPrice("￥"+String.valueOf(qj_value1)+"-"+String.valueOf(count1));
        //多少人例如34人付款
        String person1=banana.getFk();
        banana.setFk(person1+"人付款");

        ChildinitBean apple1 = new ChildinitBean("Banana", R.drawable.image_choose_normal,"￥3","20","3");
        childinitBeans.add(apple1);//添加数据源
        String qj2=apple1.getPrice();
        String qj_value2=qj2.substring(1);
        int count2=Integer.parseInt(qj_value2)*10;
        apple1.setPrice("￥"+String.valueOf(qj_value2)+"-"+String.valueOf(count2));
        //多少人例如34人付款
        String person2=apple1.getFk();
        apple1.setFk(person2+"人付款");

        ChildinitBean banana2 = new ChildinitBean("Banana", R.drawable.image_preview_choose_selected,"￥2.5","21","2.5");
        childinitBeans.add(banana2);//添加数据源
        String qj3=banana2.getPrice();
        String qj_value3=qj3.substring(1);
        Double count3=Double.parseDouble(qj_value3)*10;
        banana2.setPrice("￥"+String.valueOf(qj_value3)+"-"+String.valueOf(count3));
        //多少人例如34人付款
        String person3=banana2.getFk();
        banana2.setFk(person3+"人付款");

    }
    @Override
    protected void fetchData() {
        //初始化加载的
    }



}
