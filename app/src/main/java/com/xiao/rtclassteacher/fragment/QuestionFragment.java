package com.xiao.rtclassteacher.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiao.rtclassteacher.R;
import com.xiao.rtclassteacher.activity.QuestionDisplayActivity;
import com.xiao.rtclassteacher.bean.QuestionBean;
import com.xiao.rtclassteacher.utils.JsonUtil;
import com.xiao.rtclassteacher.utils.SharePreUtil;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private Context mContext;
    private List<QuestionBean> questionList;
    private SharePreUtil sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        sp = new SharePreUtil(mContext);

        View view = inflater.inflate(R.layout.fragment_question, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        initData();

        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRecyclerView.setAdapter(new MyAdapter());

        return view;

    }

    private void initData() {

//        questionList = new ArrayList<>();
//        questionList.add(new QuestionBean(1, 2, "某届乒乓球比赛中，甲选手与乙选手在决赛中相遇.若每局比赛，甲选手获胜的概率为2/3，乙选手获胜的概率为1/3 ，每局比赛相互独立，比赛采用五局三胜制（即五局中先胜三局者为胜，比赛结束）. 则甲选手获胜的概率",
//                "zheshidaan", "/&sqrt"));
//        questionList.add(new QuestionBean(2, 1, "一个圆柱形容器的容积为V立方米，开始用一根小水管向容器内注水，水面高度达到容器高度一半后，改用一根口径为小水管2倍的大水管注水．向容器中注满水的全过程共用时间t分．求两根水管各自注水的速度",
//                "zheshidaan", "/&sqrt"));
//        questionList.add(new QuestionBean(3, 1, "若a的值使得x2+4x+a=（x+2）2﹣1成立，则a的值为",
//                "zheshidaan", "/&sqrt"));
//        questionList.add(new QuestionBean(4, 0, "不等式|2x-1|>2x的解集为 ",
//                "zheshidaan", "/&sqrt"));
//        Log.i("test", JsonUtil.toJson(questionList));
//        sp.setValue("questions", JsonUtil.toJson(questionList));
        String questionStr = sp.getValue("questions", "");
        Log.i("test", questionStr);
        questionList = JsonUtil.getGson().fromJson(questionStr,
                new TypeToken<ArrayList<QuestionBean>>() {
                }.getType());
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder =
                    new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.question_list_item, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.contentTv.setText(questionList.get(position).getContent());
            holder.typeTv.setText(questionList.get(position).getTypeStr());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, QuestionDisplayActivity.class);
                    Bundle bundle = new Bundle();
                    List<QuestionBean> list = new ArrayList<>();
                    list.add(questionList.get(position));
                    bundle.putSerializable("questionList", (Serializable) list);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return questionList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView typeTv, contentTv;

            public MyViewHolder(View itemView) {
                super(itemView);
                typeTv = (TextView) itemView.findViewById(R.id.question_type);
                contentTv = (TextView) itemView.findViewById(R.id.question_content);
            }
        }
    }


}
