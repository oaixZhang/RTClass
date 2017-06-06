package com.xiao.rtclassteacher.bean;

/**
 * Created by xiao
 * 2017/6/5
 */

public class HomeWorkBean {
    private String date;
    private int[] questionIds;
    private int[] rightIds;
    private int status;
    private String statusStr;
    private int target;
    private int accuracy;

    public HomeWorkBean(String date, int[] questionIds, int[] rightIds, int status, int target) {
        this.date = date;
        this.questionIds = questionIds;
        this.rightIds = rightIds;
        this.status = status;
        this.target = target;
    }

    public HomeWorkBean() {
    }

    public int[] getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(int[] questionIds) {
        this.questionIds = questionIds;
    }

    public int[] getRightIds() {
        return rightIds;
    }

    public void setRightIds(int[] rightIds) {
        this.rightIds = rightIds;
    }

    public int getAccuracy() {
        return rightIds.length * 100 / questionIds.length;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        return status == 1 ? "已提交" : "未提交";
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }
}
