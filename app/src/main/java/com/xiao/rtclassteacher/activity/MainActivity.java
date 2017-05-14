package com.xiao.rtclassteacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.xiao.rtclassteacher.R;
import com.xiao.rtclassteacher.fragment.ClassFragment;
import com.xiao.rtclassteacher.fragment.HomeworkFragment;
import com.xiao.rtclassteacher.fragment.QuestionFragment;
import com.xiao.rtclassteacher.fragment.RTFragment;
import com.xiao.rtclassteacher.fragment.StuHomeworkFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolBar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private List<String> tabTitles;
    private List<Fragment> fragmentList;
    private FragmentPagerAdapter mAdapter;

    private boolean isTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (intent != null)
            isTeacher = intent.getBooleanExtra("isTeacher", true);
        System.out.println("MainActivity: isTeacher：" + isTeacher);
        initView();

        mToolBar.setTitle("实时课堂");
        setSupportActionBar(mToolBar);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        if (mNavigationView != null) {
            setUpNavigation();
        }

        setupViewPager();

    }

    private void setupViewPager() {
        tabTitles = new ArrayList<>();
        fragmentList = new ArrayList<>();

        if (isTeacher) {
            loadTeacherView();
        } else {
            loadStudentView();
        }
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
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

    private void loadTeacherView() {
        tabTitles.add("作业情况");
        tabTitles.add("班级");
        tabTitles.add("题库");
        fragmentList.add(new RTFragment());
        fragmentList.add(new ClassFragment());
        fragmentList.add(new QuestionFragment());
    }

    private void loadStudentView() {
        tabTitles.add("作业");
        tabTitles.add("题库");
        fragmentList.add(new StuHomeworkFragment());
        fragmentList.add(new QuestionFragment());
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    private void setUpNavigation() {

    }

}
