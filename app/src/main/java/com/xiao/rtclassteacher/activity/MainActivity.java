package com.xiao.rtclassteacher.activity;

import android.app.Activity;
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
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiao.rtclassteacher.R;
import com.xiao.rtclassteacher.bean.UserBean;
import com.xiao.rtclassteacher.fragment.ClassFragment;
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

    private Activity mContext = this;

    private UserBean user;

    private TextView tagTv, nameTv, phoneTv;
    private ImageView headImage;

    private List<String> tabTitles;
    private List<Fragment> fragmentList;
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (intent != null)
            user = (UserBean) intent.getSerializableExtra("user");

        Log.i("test", "MainActivity: user：" + user);
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

        RelativeLayout haderView = (RelativeLayout) mNavigationView.getHeaderView(0);
        tagTv = (TextView) haderView.findViewById(R.id.tag);
        phoneTv = (TextView) haderView.findViewById(R.id.phone);
        nameTv = (TextView) haderView.findViewById(R.id.username);
        headImage = (ImageView) haderView.findViewById(R.id.profile_image);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.input_demmo) {
                    Intent intent = new Intent(mContext, InputActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });


        nameTv.setText(user.getName());

        setupViewPager();

    }

    private void setupViewPager() {
        tabTitles = new ArrayList<>();
        fragmentList = new ArrayList<>();

        if (user.getTag() == 1) {
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
        tagTv.setText("教师");
        tabTitles.add("作业情况");
        tabTitles.add("班级");
        tabTitles.add("题库");
        fragmentList.add(new RTFragment());
        fragmentList.add(new ClassFragment());
        fragmentList.add(new QuestionFragment());
        headImage.setImageResource(R.mipmap.avatar);
    }

    private void loadStudentView() {
        tagTv.setText("学生");
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
