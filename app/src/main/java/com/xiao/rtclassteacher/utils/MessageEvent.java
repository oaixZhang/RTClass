package com.xiao.rtclassteacher.utils;

/**
 * Created by xiao
 * 2017/5/10
 */

public class MessageEvent {
    private String msg;

    public MessageEvent(String str) {
        msg = str;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
