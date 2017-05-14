package com.xiao.rtclassteacher.utils;

/**
 * Created by xiao
 * 2017/5/12
 */

public class DeleteEvent {
    private HandleDeleteListener mListener;
    private boolean isHandled;

    public interface HandleDeleteListener {
        void onHandledResult(boolean isHandled);
    }

    public DeleteEvent(HandleDeleteListener listener) {
        mListener = listener;
    }

    public boolean isHandled() {
        return isHandled;
    }

    public void setHandled(boolean handled) {
        isHandled = handled;
    }

    public void onResult() {
        if (mListener != null)
            mListener.onHandledResult(isHandled);
    }

}
