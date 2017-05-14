package com.xiao.rtclassteacher.bean;

/**
 * Created by xiao on 2017/3/26.
 */

public class ClassBean {
    private String className;
    private int memberCount;
    private String imagePath;

    public ClassBean(String name) {
        className = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
