package com.dingpeng3414.lmpl;

import android.content.ContentValues;

import com.dingpeng3414.api.LocalDataBase;
import com.dingpeng3414.local.bean.AccountPassword;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by dingp on 2016/3/30.
 */
public class LocalDataBaseLmpl implements LocalDataBase {
    private String appName = "appName";//应用名：eg csdn，知乎，书旗小说
    private String accountName = "accountName";
    private String accountPassword = "accountPassword";
    private String category = "category";//类别：eg 银行，网址
    private String email = "email";
    private String phone = "phone";
    private String url = "url";
    private String remarks = "remarks";//备注

    @Override
    public int addAccountPassword(AccountPassword accountPassword) {//添加一条accountPassword 数据
        if (accountPassword.save()) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public int deleteAccountPassword(int id) {//删除一条accountPassword数据
        int i = DataSupport.delete(AccountPassword.class, id);
        return i;
    }

    @Override
    public List<AccountPassword> getAccountPasswordAll() {//获取所有accountPassword数据
        List<AccountPassword> ac = DataSupport.findAll(AccountPassword.class);
        return ac;
    }

    @Override
    public AccountPassword getAccountPassword(String appName) {//获取一条accountPassword数据
        List<AccountPassword> ac = DataSupport.where("appName = ?", appName).find(AccountPassword.class);
        return ac.get(0);
    }

    @Override
    public List<AccountPassword> category(String categoryString) {//根据类别获取这个类别下的所有accountPassword数据
        List<AccountPassword> accountPasswordList = DataSupport.where("category = ?", categoryString).find(AccountPassword.class);
        return accountPasswordList;
    }

    @Override
    public void updataAccountPassword(AccountPassword accountPassword, int id) {//更新一条数据
        ContentValues values = new ContentValues();
        values.put(appName, accountPassword.getAppName());
        values.put(accountName, accountPassword.getAccountName());
        values.put(this.accountPassword, accountPassword.getAccountPassword());
        values.put(this.category, accountPassword.getCategory());
        values.put(this.email, accountPassword.getEmail());
        values.put(this.phone, accountPassword.getPhone());
        values.put(url, accountPassword.getUrl());
        values.put(remarks, accountPassword.getRemarks());
        DataSupport.update(AccountPassword.class, values, id);
    }
}
