package com.dingpeng3414.api;

import com.dingpeng3414.local.bean.AccountPassword;

import java.util.List;

/**
 * Created by dingp on 2016/3/30.
 */
public interface LocalDataBase {
    public int addAccountPassword(AccountPassword accountPassword);//添加一条accountPassword 数据

    public int deleteAccountPassword(int id);//删除一条accountPassword数据

    public List<AccountPassword> getAccountPasswordAll();//获取所有accountPassword数据

    public AccountPassword getAccountPassword(String appName);//获取一条accountPassword数据

    public List<AccountPassword> category(String categoryString);//根据类别获取这个类别下的所有accountPassword数据

    public void updataAccountPassword(AccountPassword accountPassword, int id);//更新一条数据
}
