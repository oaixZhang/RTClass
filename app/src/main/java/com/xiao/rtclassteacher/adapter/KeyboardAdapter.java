package com.xiao.rtclassteacher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiao.rtclassteacher.R;

/**
 * Created by xiao on 2017/4/16.
 */

public class KeyboardAdapter extends RecyclerView.Adapter<KeyboardAdapter.MyViewHolder> {

    private RecyclerItemClickListener mListener;
    private String keys;
    private Context mContext;

    public KeyboardAdapter(Context context, String s) {
        this.keys = s;
        this.mContext = context;
    }

    public void setOnItemClickListener(RecyclerItemClickListener listener) {
        this.mListener = listener;
    }

    public void setKeys(String str) {
        keys = str;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.keyboard_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClickListener(v, position);
                }
            }
        });
        holder.tv.setText(keys.charAt(position) + "");
    }

    @Override
    public int getItemCount() {
        return keys.length();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.keyboard_tv);
        }
    }
}
