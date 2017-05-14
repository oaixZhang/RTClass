package com.xiao.rtclassteacher.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiao.rtclassteacher.R;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by xiao on 2017/3/28.
 */

public class HomeworkFragment extends Fragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private List<Fragment> fragmentList;
    private List<String> titleList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homework, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        initData();
        return view;
    }

    private void initData() {
        titleList = new ArrayList<>();
        titleList.add("2017.3.28");
        titleList.add("2017.3.27");
        titleList.add("2017.3.26");
        titleList.add("2017.3.25");
        mTabLayout.addTab(mTabLayout.newTab().setText(titleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titleList.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titleList.get(3)));
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeworkListFragment());
        fragmentList.add(new HomeworkListFragment());
        fragmentList.add(new HomeworkListFragment());
        fragmentList.add(new HomeworkListFragment());
        FragmentPagerAdapter mPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titleList.get(position);
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
