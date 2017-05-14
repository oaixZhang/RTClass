package com.xiao.rtclassteacher.widget;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiao.rtclassteacher.R;

public class FracView extends MyBaseView {
    private EditText fraction, denominator;
    private LinearLayout fractionLine, denominatorLine;

    public FracView(Context context, ViewGroup parent) {
        super(context, parent);
        LayoutInflater.from(mContext).inflate(R.layout.widget_frac, this);
        fractionLine = (LinearLayout) findViewById(R.id.ll_fraction);
        denominatorLine = (LinearLayout) findViewById(R.id.ll_denominator);
        fraction = (EditText) findViewById(R.id.et_fraction);
        denominator = (EditText) findViewById(R.id.et_denominator);
        fraction.setOnEditorActionListener(this);
        denominator.setOnEditorActionListener(this);
        fractionLine.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return fraction.onTouchEvent(event);
            }
        });
        denominatorLine.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return denominator.onTouchEvent(event);
            }
        });
        mParent.addView(this);
        requestFocus();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT)
            switch (v.getId()) {
                case R.id.et_fraction:
                    denominator.requestFocus();
                    break;
                case R.id.et_denominator:
                    currentPartDone();
                    break;
            }
        return true;
    }

    public void addFractionLineEnd() {
        EditText et = (EditText) LayoutInflater.from(mContext).inflate(R.layout.widget_singleline, null);
        et.setOnEditorActionListener(this);
        et.setId(R.id.et_fraction);
        ((ViewGroup) fraction.getParent()).addView(et);
        et.requestFocus();
    }

    public void addDenominatorLineEnd() {
        EditText et = (EditText) LayoutInflater.from(mContext).inflate(R.layout.widget_singleline, null);
        et.setOnEditorActionListener(this);
        et.setId(R.id.et_denominator);
        ((ViewGroup) denominator.getParent()).addView(et);
        et.requestFocus();
    }

    public boolean isEmpty() {
        return fraction.getText().toString().equals("") && denominator.getText().toString().equals("");
    }

    @Override
    public String getInput() {
        if (content == null)
            content = new StringBuilder();
        content.append("{").append(getFraction()).append("}").append("/")
                .append("{").append(getDenominator()).append("}");
        return content.toString();
    }

    @Override
    public boolean handleDelete(ViewGroup focusViewGroup, View focusView) {
        if (focusViewGroup.getChildCount() > 1) {
            focusViewGroup.removeView(focusView);
            focusViewGroup.getChildAt(focusViewGroup.getChildCount() - 1).requestFocus(View.FOCUS_UP);
        } else if (isEmpty()) {
            cast2MyBaseView(focusViewGroup).deleteSelf();
        } else {
            if (focusView.getId() == R.id.et_fraction)
                denominator.requestFocus();
            else
                fraction.requestFocus();
        }
        return true;
    }

    public String getFraction() {
        StringBuffer sb = new StringBuffer();
        int childCount = fractionLine.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = fractionLine.getChildAt(i);
            if (v.getId() == R.id.et_fraction) {
                sb.append(((EditText) v).getText());
            } else {
                sb.append(cast2MyBaseView(v).getInput());
            }
        }
        return sb.toString();
    }

    public String getDenominator() {
        StringBuffer sb = new StringBuffer();
        int childCount = denominatorLine.getChildCount();
        Log.i("test", "denominator child count: " + childCount);
        for (int i = 0; i < childCount; i++) {
            View v = denominatorLine.getChildAt(i);
            Log.i("test", "child: " + v);
            if (v.getId() == R.id.et_denominator) {
                sb.append(((EditText) v).getText());
            } else {
                sb.append(cast2MyBaseView(v).getInput());
            }
        }
        return sb.toString();
    }
}
