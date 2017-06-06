package com.xiao.rtclassteacher.bean;

import java.io.Serializable;

/**
 * Created by xiao
 * 2017/6/5
 */

public class UserBean implements Serializable {
    private String name;
    private String account;
    private String password;
    private String phone;
    private int tag;

    public UserBean(String name, String account, String password, String phone, int tag) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.phone = phone;
        this.tag = tag;
    }

    public UserBean(String name, String account, String password, int tag) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
