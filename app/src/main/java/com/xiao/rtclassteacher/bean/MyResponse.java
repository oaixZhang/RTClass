package com.xiao.rtclassteacher.bean;

/**
 * Created by xiao
 * on 2017/6/3.
 */
public class MyResponse {
    private int code;
    private String msg;
    private Object data;

    public MyResponse(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public MyResponse(Object data) {
        this.code = 200;
        this.msg = "ok";
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
