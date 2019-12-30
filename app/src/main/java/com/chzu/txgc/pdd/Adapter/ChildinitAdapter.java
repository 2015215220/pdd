package com.chzu.txgc.pdd.Adapter;

import android.content.Context;
import com.chzu.txgc.pdd.Bean.ChildinitBean;
import com.chzu.txgc.pdd.R;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

public class ChildinitAdapter extends EasyRVAdapter<ChildinitBean> {
    public ChildinitAdapter(Context context, List<ChildinitBean> list, int... layoutIds) {
        super(context, list, layoutIds);//需要生成的方法
    }

    @Override
    protected void onBindData(EasyRVHolder viewHolder, int position, ChildinitBean item) {
        viewHolder.setImageResource(R.id.child_img,item.getImageId())
                  .setText(R.id.child_txt,item.getName())
                  .setText(R.id.price,item.getPrice())
                  .setText(R.id.fk_value,item.getFk());
    }
}
