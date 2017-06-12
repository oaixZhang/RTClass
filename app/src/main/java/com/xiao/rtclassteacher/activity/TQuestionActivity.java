package com.xiao.rtclassteacher.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiao.rtclassteacher.R;
import com.xiao.rtclassteacher.adapter.SimpleViewPagerAdapter;
import com.xiao.rtclassteacher.bean.QuestionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于显示题目，但不可编辑
 */
public class TQuestionActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private Context mContext;
    private TextView indexTv;

    private String msg;
    private List<QuestionBean> questionList;
    private List<View> viewList;

    private Toolbar mToolBar;
    private String titleName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_display);
        mContext = TQuestionActivity.this;

        mToolBar = (Toolbar) findViewById(R.id.toolbar);

        mToolBar.setTitle("实时课堂");
        setSupportActionBar(mToolBar);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        indexTv = (TextView) findViewById(R.id.tv_index);

        initData();

        setUpViewPager();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_tool, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    private void initData() {
        viewList = new ArrayList<>();

        Intent intent = getIntent();
        titleName = intent.getStringExtra("titleName");
        if (titleName != null && !(titleName.equals("")))
            mToolBar.setTitle(titleName);
        questionList = (List<QuestionBean>) intent.getSerializableExtra("questionList");

//        withBackend();

        for (int i = 0; i < questionList.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.view_question_read_only, null);

            TextView tv_content = (TextView) view.findViewById(R.id.content);
            TextView tv_id = (TextView) view.findViewById(R.id.tv_id);
            TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
            final TextView tv_answer = (TextView) view.findViewById(R.id.tv_answer);
            Button answerBtn = (Button) view.findViewById(R.id.answer_btn);

            tv_content.setText(questionList.get(i).getContent());
            tv_id.setText(questionList.get(i).getQuestionid() + "");
            tv_type.setText(questionList.get(i).getTypeStr());
            tv_answer.setText(questionList.get(i).getAnswer());
            tv_answer.setVisibility(View.VISIBLE);

            answerBtn.setVisibility(View.GONE);
            viewList.add(view);
        }

    }

    private void setUpViewPager() {
        PagerAdapter mAdapter = new SimpleViewPagerAdapter(viewList);
        mViewPager.setAdapter(mAdapter);
        indexTv.setText(1 + "/" + viewList.size());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indexTv.setText((position + 1) + "/" + viewList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
