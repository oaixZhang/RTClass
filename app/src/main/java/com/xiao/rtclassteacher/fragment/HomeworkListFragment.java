package com.xiao.rtclassteacher.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiao.rtclassteacher.R;
import com.xiao.rtclassteacher.activity.QuestionActivity;
import com.xiao.rtclassteacher.bean.StudentBean;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by xiao on 2017/3/28.
 */

public class HomeworkListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<StudentBean> studentBeanList;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mContext = getActivity();
        initData();
        return view;
    }

    private void initData() {
        studentBeanList = new ArrayList<>();
        studentBeanList.add(new StudentBean("xiaoz"));
        studentBeanList.add(new StudentBean("peter"));
        studentBeanList.add(new StudentBean("shangm"));
        studentBeanList.add(new StudentBean("lisi"));
        studentBeanList.add(new StudentBean("jieshi"));
        studentBeanList.add(new StudentBean("gavin"));
        studentBeanList.add(new StudentBean("lisi"));
        studentBeanList.add(new StudentBean("zhangsan"));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(new MyAdapter());
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.homework_list_item, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.nameTv.setText(studentBeanList.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, QuestionActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return studentBeanList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView nameTv;

            public MyViewHolder(View itemView) {
                super(itemView);
                nameTv = (TextView) itemView.findViewById(R.id.tv_name);
            }
        }
    }
}
