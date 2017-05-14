package com.xiao.rtclassteacher.widget;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiao.rtclassteacher.R;

public class MathLinearView extends MyBaseView {

    public MathLinearView(Context context, ViewGroup parent) {
        super(context, parent);
        setId(R.id.container);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));
        setBackground(getResources().getDrawable(R.drawable.bg_line, null));
        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);
        EditText input = (EditText) LayoutInflater.from(mContext).inflate(R.layout.widget_singleline, null);
        input.setOnEditorActionListener(this);
        addView(input);

        //为输入框获取焦点
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildAt(getChildCount() - 1).requestFocus();
            }
        });
        mParent.addView(this);
    }

    //找到焦点控件的容器
    private ViewGroup findFocusedViewGroup() {
        View focusChild = findFocus();
        ViewParent parent = focusChild.getParent();
        if (parent != null)
            return (ViewGroup) parent;
        else
            return this;
    }

    public void insert(int type) {
        if (findFocus().getId() == R.id.et_index)
            Toast.makeText(mContext, "暂不支持", Toast.LENGTH_SHORT).show();
        else
            switch (type) {
                case 0:
                    new FracView(mContext, findFocusedViewGroup());
                    break;
                case 1:
                    new SqrtView(mContext, findFocusedViewGroup());
                    break;
                case 2:
                    new ExponentView(mContext, findFocusedViewGroup());
                    break;
                case 3:
                    new XBottomView(mContext, findFocusedViewGroup());
                    break;
            }
    }

    @Override
    public String getInput() {
        if (content == null)
            content = new StringBuilder();
        int childCount = getChildCount();
        Log.i("test", "MathLinearView child count: " + childCount);
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            Log.i("test", "child: " + view);
            if (view.getId() == R.id.et_input) {
                content.append(((EditText) view).getText());
            } else {
                content.append(cast2MyBaseView(view).getInput());
            }
        }
        return content.toString();
    }

    public boolean delete() {
        ViewGroup focusViewGroup = findFocusedViewGroup();
        EditText focusView = (EditText) findFocus();
        System.out.println("focusedView: " + focusView);
        System.out.println("FocusedViewGroup " + focusViewGroup);
        System.out.println("FocusedViewGroup s parent" + focusViewGroup.getParent());
        if (focusView.getText().toString().equals(""))
            return cast2MyBaseView(focusViewGroup).handleDelete(focusViewGroup, focusView);
        return false;
    }

    @Override
    public boolean handleDelete(ViewGroup focusViewGroup, View focusView) {
        if (focusViewGroup.getChildCount() > 1) {
            focusViewGroup.removeView(focusView);
            focusViewGroup.getChildAt(focusViewGroup.getChildCount() - 1).requestFocus(View.FOCUS_UP);
        }
        return true;
    }

    //换行
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            new MathLinearView(mContext, mParent);
            mParent.getChildAt(mParent.getChildCount() - 1).requestFocus();
        }
        return true;
    }

}
