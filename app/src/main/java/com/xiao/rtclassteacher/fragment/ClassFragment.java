package com.xiao.rtclassteacher.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xiao.rtclassteacher.R;
import com.xiao.rtclassteacher.activity.ClassActivity;
import com.xiao.rtclassteacher.bean.ClassBean;

import java.util.ArrayList;
import java.util.List;

public class ClassFragment extends Fragment {
    private FragmentActivity mContext;
    private RecyclerView mRecyclerView;

    private List<ClassBean> classList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mContext = getActivity();

        initData();

        return view;
    }

    private void initData() {
        classList = new ArrayList<>();
        classList.add(new ClassBean("三年一班"));
        classList.add(new ClassBean("三年二班"));
        classList.add(new ClassBean("三年三班"));
        classList.add(new ClassBean("三年四班"));
        ClassListAdapter mAdapter = new ClassListAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

    class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder = new MyViewHolder
                    (LayoutInflater.from(mContext).inflate(R.layout.class_list_item, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.classNameTv.setText(classList.get(position).getClassName());
            holder.hoemwordBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, ClassActivity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return classList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView classNameTv;
            Button hoemwordBtn;
            Button noticeBtn;

            MyViewHolder(View itemView) {
                super(itemView);
                classNameTv = (TextView) itemView.findViewById(R.id.class_name);
                hoemwordBtn = (Button) itemView.findViewById(R.id.btn_homework);
                noticeBtn = (Button) itemView.findViewById(R.id.btn_class_notice);
            }
        }
    }
}
