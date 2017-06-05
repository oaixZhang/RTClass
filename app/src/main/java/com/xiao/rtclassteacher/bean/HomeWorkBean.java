package com.xiao.rtclassteacher.bean;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by xiao
 * 2017/6/5
 */

public class HomeWorkBean {
    private String date;
    private int[] questionIds;
    private int[] rightIds;
    private int status;
    private int target;
    private float accuracy;

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

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
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
}
