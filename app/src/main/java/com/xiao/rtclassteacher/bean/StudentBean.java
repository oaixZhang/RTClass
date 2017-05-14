package com.xiao.rtclassteacher.bean;

/**
 * Created by xiao on 2017/3/28.
 */

public class StudentBean {
    private String name;
    private String gender;
    private String imagePath;
    private boolean homework_submitted;

    public StudentBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
