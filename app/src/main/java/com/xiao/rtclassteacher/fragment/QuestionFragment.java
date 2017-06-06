package com.xiao.rtclassteacher.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.xiao.rtclassteacher.R;
import com.xiao.rtclassteacher.activity.QuestionActivity;
import com.xiao.rtclassteacher.bean.QuestionBean;
import com.xiao.rtclassteacher.utils.JsonUtil;
import com.xiao.rtclassteacher.utils.SharePreUtil;
import com.xiao.rtclassteacher.utils.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<QuestionBean> questionList;


    protected volatile ProgressDialog mDialog;

    protected ProgressBar pb;

    protected SharePreUtil sp;

    protected Activity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        sp = new SharePreUtil(mContext);

        View view = inflater.inflate(R.layout.fragment_question, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        initData();

        return view;

    }

    private void initData() {

        local();

//        request();
    }

    private void local() {
        String questionStr = sp.getValue("questions", "");
        Log.i("test", questionStr);
        questionList = JsonUtil.getGson().fromJson(questionStr,
                new TypeToken<ArrayList<QuestionBean>>() {
                }.getType());
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRecyclerView.setAdapter(new MyAdapter());
    }

    private void request() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dismissDialog();
                String questionStr = (String) msg.obj;
                questionList = JsonUtil.getGson().fromJson(questionStr,
                        new TypeToken<ArrayList<QuestionBean>>() {
                        }.getType());
                mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
                mRecyclerView.setAdapter(new MyAdapter());
            }
        };
        getProgressDialog().show();
        httpRequest(handler);
    }

    private void httpRequest(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String urlString = StringUtil.baseUrl + "question";
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.connect();
                    int code = con.getResponseCode();
                    Log.i("test", "response code: " + code);
                    Log.i("test", "response msg: " + con.getResponseMessage());
                    if (code == 200) {
                        InputStream is = con.getInputStream();
                        String str = StringUtil.readFromStream(is);
                        Log.i("test", "response body: " + str);
//                        JsonObject jo = new JsonParser().parse(str).getAsJsonObject();
//                        String s = jo.get("data").getAsString();
//                        Log.i("test", "response body data: " + s);
                        Message msg = new Message();
                        msg.obj = str;
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

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
                    Intent intent = new Intent(mContext, QuestionActivity.class);
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

    /**
     * @return
     * @Description: 显示进度条
     */
    protected ProgressDialog getProgressDialog() {
        if (mDialog == null) {
            String mMessage = "加载中...";

            mDialog = new ProgressDialog(mContext);
            mDialog.setTitle(null);
            mDialog.setMessage(mMessage);
            mDialog.setIndeterminate(true);
        }
        return mDialog;
    }

    /**
     * @Description: 关闭进度条
     */
    protected void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }


}
