package com.xiao.rtclassteacher.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.xiao.rtclassteacher.utils.SharePreUtil;

public abstract class BaseFragment extends Fragment {


    /**
     * Activity mContext
     */
    protected Activity mContext;

    /**
     * progressDialog
     */
    protected volatile ProgressDialog mDialog;

    /**
     * ProgressBar
     */
    protected ProgressBar pb;


    /**
     * SharedPreferences
     */
    protected SharePreUtil sp;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        sp = new SharePreUtil(mContext);
        return super.onCreateView(inflater, container, savedInstanceState);
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

    protected ProgressDialog getProgressDialog(String message, boolean isCancelable) {
        if (mDialog == null) {
            String mMessage = message;
            mDialog = new ProgressDialog(mContext);
            mDialog.setTitle(null);
            mDialog.setMessage(message);
            mDialog.setIndeterminate(true);
            mDialog.setCancelable(isCancelable);
            mDialog.setCanceledOnTouchOutside(isCancelable);
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


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
