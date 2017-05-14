package com.xiao.rtclassteacher.widget;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.xiao.rtclassteacher.R;

public class ExponentView extends MyBaseView {
    private EditText index, main;

    public ExponentView(Context context, ViewGroup parent) {
        super(context, parent);
        LayoutInflater.from(mContext).inflate(R.layout.widget_exponent, this);
        index = (EditText) findViewById(R.id.et_index);
        main = (EditText) findViewById(R.id.et_exponent_main);
        mainLine = (ViewGroup) main.getParent();
        index.setOnEditorActionListener(this);
        main.setOnEditorActionListener(this);
        mParent.addView(this);
        requestFocus();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT)
            switch (v.getId()) {
                case R.id.et_index:
                    currentPartDone();
                    break;
                case R.id.et_exponent_main:
                    index.requestFocus();
                    break;
            }

        return true;
    }

    @Override
    public String getInput() {
        if (content == null) {
            content = new StringBuilder();
        }
        content.append("{").append(getMainLine()).append("}^").append(getIndex());
        return content.toString();
    }

    public String getIndex() {
        return index.getText().toString();
    }

    public String getMainLine() {
        StringBuilder sb = new StringBuilder();
        int childCount = mainLine.getChildCount();
        Log.i("test", "ExponentView child count: " + childCount);
        for (int i = 0; i < childCount; i++) {
            View v = mainLine.getChildAt(i);
            Log.i("test", "child: " + v);
            if (v.getId() == R.id.et_exponent_main) {
                sb.append(((EditText) v).getText());
            } else {
                sb.append(cast2MyBaseView(v).getInput());
            }
        }
        return sb.toString();
    }

    public boolean isEmpty() {
        return main.getText().toString().equals("") && index.getText().toString().equals("");
    }
}
