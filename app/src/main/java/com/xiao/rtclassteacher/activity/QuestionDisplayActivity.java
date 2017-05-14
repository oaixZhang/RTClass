package com.xiao.rtclassteacher.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xiao.rtclassteacher.R;
import com.xiao.rtclassteacher.adapter.KeyboardAdapter;
import com.xiao.rtclassteacher.adapter.RecyclerItemClickListener;
import com.xiao.rtclassteacher.adapter.SimpleViewPagerAdapter;
import com.xiao.rtclassteacher.bean.QuestionBean;
import com.xiao.rtclassteacher.widget.MathLinearView;

import java.util.ArrayList;
import java.util.List;

public class QuestionDisplayActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private Context mContext;
    private TextView indexTv;

    private String msg;
    private List<QuestionBean> questionBeanList;
    private List<View> viewList;

    private String DEFAULT_KEY = "＋－×÷＞＜＝";
    private InputMethodManager mInputMethodManager;
    private LinearLayout keyboardLayout;
    private boolean isKeyBoardVisiable;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_display);
        mContext = QuestionDisplayActivity.this;
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        initView();

        initData();

        setUpViewPager();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        indexTv = (TextView) findViewById(R.id.tv_index);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //keyboard
        keyboardLayout = (LinearLayout) findViewById(R.id.ll_keyboard);
        LinearLayout functionLine = (LinearLayout) findViewById(R.id.function_line);
        TextView switTv = (TextView) functionLine.getChildAt(0);
        switTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputMethodManager.showInputMethodPicker();
            }
        });
        TextView spaceTv = (TextView) functionLine.getChildAt(1);
        spaceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        TextView backTv = (TextView) functionLine.getChildAt(2);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        TextView enTv = (TextView) functionLine.getChildAt(3);
        enTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 7));
        KeyboardAdapter keyboardAdapter = new KeyboardAdapter(mContext, DEFAULT_KEY);
        keyboardAdapter.setOnItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {

            }
        });
        recyclerView.setAdapter(keyboardAdapter);
    }

    private void initData() {
        viewList = new ArrayList<>();
        questionBeanList = new ArrayList<>();

        Intent intent = getIntent();
        if (intent != null) {
            msg = intent.getStringExtra("msg");
        }
        if (msg != null) {
            JsonArray jsonArray = new JsonParser().parse(msg).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = (JsonObject) jsonArray.get(i);
                QuestionBean questionBean = new QuestionBean();
                questionBean.setTitle(jsonObject.get("title").getAsString());
                questionBean.setQuestionid(jsonObject.get("questionid").getAsInt());
                questionBean.setType(jsonObject.get("type").getAsString());
                questionBean.setConclusion(jsonObject.get("conclusion").getAsString());
                View view = LayoutInflater.from(mContext).inflate(R.layout.view_question, null);
                TextView tv_title = (TextView) view.findViewById(R.id.content);
                TextView tv_id = (TextView) view.findViewById(R.id.tv_id);
                TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
                LinearLayout input = (LinearLayout) view.findViewById(R.id.ll_input);
                MathLinearView mathLinearView = new MathLinearView(mContext, input);
                input.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isKeyBoardVisiable)
                            keyboardLayout.setVisibility(View.VISIBLE);
                    }
                });

                tv_title.setText(questionBean.getTitle());
                tv_id.setText(questionBean.getQuestionid() + "");
                tv_type.setText(questionBean.getType());
                questionBeanList.add(questionBean);
                viewList.add(view);
            }
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
