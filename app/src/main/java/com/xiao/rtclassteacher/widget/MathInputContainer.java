package com.xiao.rtclassteacher.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.xiao.rtclassteacher.utils.DeleteEvent;
import com.xiao.rtclassteacher.utils.InputEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by xiao
 * 2017/5/13
 */

public class MathInputContainer extends LinearLayout {
    public MathInputContainer(Context context) {
        this(context, null);
    }

    public MathInputContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MathInputContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        EventBus.getDefault().register(this);
    }

    //eventbus的处理逻辑
    @Subscribe
    public void onMyMessageEvent(InputEvent inputEvent) {
        if (hasFocus()) {
            MathLinearView mathLinearView = (MathLinearView) getFocusedChild();
            switch (inputEvent.getInputCharactor()) {
                case "／":
                    mathLinearView.insert(0);
                    break;
                case "√":
                    mathLinearView.insert(1);
                    break;
                case "^":
                    mathLinearView.insert(2);
                    break;
                case "_":
                    mathLinearView.insert(3);
                    break;
            }
        }
    }

    //eventbus的处理逻辑
    @Subscribe
    public void onMyDeleteEvent(DeleteEvent event) {
        if (hasFocus()) {
            MathLinearView mathLinearView = (MathLinearView) getFocusedChild();
            event.setHandled(mathLinearView.delete());
            event.onResult();
        }
    }

    public String getInputContent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getChildCount(); i++) {
            sb.append(((MathLinearView) getChildAt(i)).getInput());
        }
        return sb.toString();
    }

}
