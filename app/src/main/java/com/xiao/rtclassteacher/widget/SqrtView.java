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

public class SqrtView extends MyBaseView {
    private EditText index, main;
    private StringBuilder sqrtContent;

    public SqrtView(Context context, ViewGroup parent) {
        super(context, parent);
        LayoutInflater.from(mContext).inflate(R.layout.widget_sqrt, this);
        index = (EditText) findViewById(R.id.et_index);
        main = (EditText) findViewById(R.id.et_sqrt_main);
        mainLine = (ViewGroup) main.getParent();
        index.setOnEditorActionListener(this);
        main.setOnEditorActionListener(this);
        requestFocus();
        mParent.addView(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT)
            switch (v.getId()) {
                case R.id.et_index:
                    main.requestFocus();
                    break;
                case R.id.et_sqrt_main:
                    currentPartDone();
                    break;
            }
        return true;
    }

    @Override
    public String getInput() {
        if (content == null) {
            content = new StringBuilder();
        }
        content.append("sqrt[").append(getIndex()).append("]{").append(getMainLine()).append("}");
        return content.toString();
    }

    public String getIndex() {
        String i = index.getText().toString();
        if (i.equals(""))
            return "2";
        else
            return i;
    }

    public String getMainLine() {
        if (sqrtContent == null) {
            sqrtContent = new StringBuilder();
        }
        int childCount = mainLine.getChildCount();
        Log.i("test", "SqrtView child count: " + childCount);
        for (int i = 0; i < childCount; i++) {
            View v = mainLine.getChildAt(i);
            Log.i("test", "child: " + v);
            if (v.getId() == R.id.et_sqrt_main) {
                sqrtContent.append(((EditText) v).getText());
            } else {
                sqrtContent.append(cast2MyBaseView(v).getInput());
            }
        }

        return sqrtContent.toString();
    }
    //    @Override
//    public boolean handleDelete(ViewGroup focusViewGroup, View focusView) {
//        if (focusViewGroup.getChildCount() > 1) {
//            focusViewGroup.removeView(focusView);
//            focusViewGroup.getChildAt(focusViewGroup.getChildCount() - 1).requestFocus(View.FOCUS_UP);
//        } else if (isEmpty()) {
//            cast2MyBaseView(focusViewGroup).deleteSelf();
//        } else
//            index.requestFocus();
//        return true;
//    }

    public boolean isEmpty() {
        return main.getText().toString().equals("") && index.getText().toString().equals("");
    }
}
