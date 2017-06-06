package com.xiao.rtclassteacher.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.xiao.rtclassteacher.R;
import com.xiao.rtclassteacher.fragment.ClassFragment;
import com.xiao.rtclassteacher.fragment.HomeworkFragment;
import com.xiao.rtclassteacher.fragment.MemberFragment;
import com.xiao.rtclassteacher.fragment.NoticeFragment;
import com.xiao.rtclassteacher.fragment.QuestionFragment;
import com.xiao.rtclassteacher.fragment.RTFragment;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by xiao on 2017/3/26.
 */

public class ClassActivity extends AppCompatActivity {
    private CollapsingToolbarLayout mCollapsingToolbar;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;

    private List<String> tabTitles;
    private List<Fragment> fragmentList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        initView();
        mToolbar.setTitle("三年一班");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupViewPager();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    private void setupViewPager() {
        tabTitles = new ArrayList<>();
        tabTitles.add("作业情况");
        tabTitles.add("班级公告");
        tabTitles.add("班级成员");
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeworkFragment());
        fragmentList.add(new NoticeFragment());
        fragmentList.add(new MemberFragment());
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
