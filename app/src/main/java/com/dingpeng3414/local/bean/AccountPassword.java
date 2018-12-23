package com.dingpeng3414.local.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by dingp on 2016/3/30.
 */
public class AccountPassword extends DataSupport {
    private int _id;
    private int zitiColor;//字体颜色
    private String appName;//应用名：eg csdn，知乎，书旗小说
    private String accountName;
    private String accountPassword;
    private String category;//类别：eg 银行，网址
    private String email;
    private String phone;
    private String url;
    private String remarks;//备注

    public int getZitiColor() {
        return zitiColor;
    }

    public void setZitiColor(int zitiColor) {
        this.zitiColor = zitiColor;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
