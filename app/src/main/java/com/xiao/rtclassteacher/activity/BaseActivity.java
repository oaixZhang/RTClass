package com.xiao.rtclassteacher.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.xiao.rtclassteacher.utils.SharePreUtil;

/**
 * Created by xiao
 * 2017/6/5
 */

public class BaseActivity extends AppCompatActivity {

    /**
     * progressDialog
     */
    protected ProgressDialog mDialog;
    protected Activity mContext = this;
    protected SharePreUtil sp;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        sp = new SharePreUtil(this);
    }

    /**
     * @return
     * @Description: 显示进度条
     */
    protected ProgressDialog getProgressDialog() {
        if (mDialog == null) {
            String mMessage = "正在获取...";

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
