package com.xiao.rtclassteacher.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xiao.rtclassteacher.R;
import com.xiao.rtclassteacher.activity.QuestionDisplayActivity;
import com.xiao.rtclassteacher.utils.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * Created by xiao on 2017/4/20.
 */

public class StuHomeworkFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private Context mContext;

    private RelativeLayout rl;

    private Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_homework_stu, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        rl = (RelativeLayout) view.findViewById(R.id.rl);

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "click", Toast.LENGTH_SHORT).show();
                mHandler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        String s = (String) msg.obj;
                        Intent intent = new Intent(mContext, QuestionDisplayActivity.class);
                        intent.putExtra("msg", s);
                        startActivity(intent);
                        return false;
                    }
                });
                httpRequest(mHandler);
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(new MyAdapter());
        return view;
    }

    private void httpRequest(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL urlString = new URL(StringUtil.baseUrl + "test/user.html");
                    HttpURLConnection conn = (HttpURLConnection) urlString.openConnection();
                    conn.setRequestMethod("POST");
                    conn.connect();
                    int code = conn.getResponseCode();
                    System.out.println("response code: " + code);
                    System.out.println("response msg: " + conn.getResponseMessage());
                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        String response = StringUtil.readFromStream(is);
                        System.out.println("response body: " + response);
                        Message msg = new Message();
                        msg.obj = response;
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    class MyAdapter extends RecyclerView.Adapter<StuHomeworkFragment.MyAdapter.MyViewHolder> {


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.homework_list_stu_item, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 7;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
