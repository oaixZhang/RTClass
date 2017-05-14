package com.xiao.rtclassteacher.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class SimpleViewPagerAdapter extends PagerAdapter {
    private List<View> mViews;

    public SimpleViewPagerAdapter(List<View> list) {
        mViews = list;
    }


    //页面数量
    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object; //官方推荐写法
    }

    //实例化条目
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));//添加页卡
        return mViews.get(position);
    }

    //销毁条目
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }
}
