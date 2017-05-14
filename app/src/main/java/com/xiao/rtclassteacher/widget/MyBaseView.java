package com.xiao.rtclassteacher.widget;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiao.rtclassteacher.R;

public class MyBaseView extends LinearLayout implements TextView.OnEditorActionListener {
    protected Context mContext;
    protected ViewGroup mParent;
    protected ViewGroup mainLine;
    protected StringBuilder content;

    public MyBaseView(Context context, ViewGroup parent) {
        super(context);
        mContext = context;
        mParent = parent;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }

    protected void currentPartDone() {
        System.out.println(mParent);
        switch (mParent.getId()) {
            case R.id.ll_denominator:
                ((FracView) mParent.getParent().getParent()).addDenominatorLineEnd();
                break;
            case R.id.ll_fraction:
                ((FracView) mParent.getParent().getParent()).addFractionLineEnd();
                break;
            case R.id.container:
                cast2MyBaseView(mParent).addCurrentLineEnd();
                break;
            case R.id.ll_sqrt_main:
                cast2MyBaseView(mParent).addCurrentLineEnd(R.id.et_sqrt_main);
                break;
            case R.id.ll_exponent_main:
                cast2MyBaseView(mParent).addCurrentLineEnd(R.id.et_exponent_main);
                break;
            case R.id.ll_main:
                cast2MyBaseView(mParent).addCurrentLineEnd(R.id.et_main);
                break;
        }
    }

    public void addCurrentLineEnd(int mId) {
        EditText et = (EditText) LayoutInflater.from(mContext).inflate(R.layout.widget_singleline, null);
        et.setOnEditorActionListener(this);
        et.setId(mId);
        et.requestFocus();
        mainLine.addView(et);
    }

    public void addCurrentLineEnd() {
        EditText et = (EditText) LayoutInflater.from(mContext).inflate(R.layout.widget_singleline, null);
        et.setOnEditorActionListener(this);
        et.requestFocus();
        addView(et);
    }

    public MyBaseView cast2MyBaseView(ViewGroup current) {
        ViewGroup viewGroup = current;
        while (!(viewGroup instanceof MyBaseView)) {
            viewGroup = (ViewGroup) viewGroup.getParent();
        }
        return (MyBaseView) viewGroup;
    }

    public MyBaseView cast2MyBaseView(View current) {
        View view = current;
        while (!(view instanceof MyBaseView)) {
            view = (View) view.getParent();
        }
        return (MyBaseView) view;
    }

    public boolean handleDelete(ViewGroup focusViewGroup, View focusView) {
        if (focusViewGroup.getChildCount() > 1) {
            focusViewGroup.removeView(focusView);
            focusViewGroup.getChildAt(focusViewGroup.getChildCount() - 1).requestFocus(View.FOCUS_UP);
        } else
            cast2MyBaseView(focusViewGroup).deleteSelf();
        return true;
    }

    public void deleteSelf() {
        mParent.removeView(this);
        mParent.getChildAt(mParent.getChildCount() - 1).requestFocus(FOCUS_UP);
    }

    public String getInput() {
        return null;
    }
}
