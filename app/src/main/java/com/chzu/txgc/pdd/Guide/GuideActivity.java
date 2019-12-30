package com.chzu.txgc.pdd.Guide;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.chzu.txgc.pdd.Activity.BaseActivity;
import com.chzu.txgc.pdd.Activity.LoginActivity;
import com.chzu.txgc.pdd.R;

import java.util.ArrayList;

public class GuideActivity extends BaseActivity {
    private ViewPager guidepager;
    private int[] mImageIds = new int[]{R.drawable.weclome_first, R.drawable.weclome_second, R.drawable.weclome_third};
    private ArrayList<ImageView> mImageViewList;
    private LinearLayout llContainer;
    private ImageView ivRedPoint;
    private int mPaintDis;
    private Button start_btn;

    @Override
    public int bindLayout() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_guide;
    }

    @Override
    protected void findViews() {
        guidepager = (ViewPager) findViewById(R.id.vp_guide);
        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        ivRedPoint = (ImageView) findViewById(R.id.iv_red);
        start_btn = (Button) findViewById(R.id.start_btn);
    }

    @Override
    protected void initListeners() {
        start_btn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView view = new ImageView(this);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setBackgroundResource(mImageIds[i]);
            mImageViewList.add(view);
            ImageView pointView = new ImageView(this);
            pointView.setImageResource(R.drawable.dian);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                params.leftMargin = 20;
            }
            pointView.setLayoutParams(params);
            llContainer.addView(pointView);
        }

        GuideAdapter adapter = new GuideAdapter();
        guidepager.setAdapter(adapter);//viewpage
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ivRedPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mPaintDis = llContainer.getChildAt(1).getLeft() - llContainer.getChildAt(0).getLeft();
            }
        });
        guidepager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动过程中的回调
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int letfMargin = (int) (mPaintDis * positionOffset) + position * mPaintDis;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
                params.leftMargin = letfMargin;
                ivRedPoint.setLayoutParams(params);
            }
            @Override
            public void onPageSelected(int position) {
                if (position == mImageViewList.size() - 1) {
                    start_btn.setVisibility(View.VISIBLE);
                } else {
                    start_btn.setVisibility(View.GONE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    protected void onViewClicked(int viewId) {
        switch (viewId) {
            case R.id.start_btn:
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
        }
    }

    class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImageViewList.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = mImageViewList.get(position);
            container.addView(view);
            return view;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
