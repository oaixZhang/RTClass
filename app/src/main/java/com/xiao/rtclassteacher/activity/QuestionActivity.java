package com.xiao.rtclassteacher.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.xiao.rtclassteacher.R;

import io.github.kexanie.library.MathView;

/*
 * Created by xiao on 2017/3/28.
 */

public class QuestionActivity extends AppCompatActivity {
    private MathView mMathView;
    private Toolbar mToolBar;
    private FloatingActionButton mRightBtn, mWrongBtn;
    private ImageView mRightImage, mWrongImage;

    String tex = "This come from string. You can insert inline formula:" +
            " \\(ax^2 + bx + c = 0\\) " +
            "or displayed formula: $$\\sum_{i=0}^n i^2 = \\frac{(n^2+n)(2n+1)}{6}$$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        initView();


        mToolBar.setTitle("三年二班-xiaozhang的作业");
        setSupportActionBar(mToolBar);
        mMathView.setText(tex);

        mRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRightImage.setVisibility(View.GONE);
                mWrongImage.setVisibility(View.GONE);
                mRightImage.setVisibility(View.VISIBLE);
            }
        });
        mWrongBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRightImage.setVisibility(View.GONE);
                mWrongImage.setVisibility(View.GONE);
                mWrongImage.setVisibility(View.VISIBLE);
            }
        });


    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mMathView = (MathView) findViewById(R.id.math_view);
        mRightImage = (ImageView) findViewById(R.id.iv_right);
        mWrongImage = (ImageView) findViewById(R.id.iv_wrong);
        mRightBtn = (FloatingActionButton) findViewById(R.id.fab_right);
        mWrongBtn = (FloatingActionButton) findViewById(R.id.fab_wrong);
    }

}
