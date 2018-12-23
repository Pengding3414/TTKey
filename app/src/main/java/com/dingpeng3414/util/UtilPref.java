package com.dingpeng3414.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Pengding on 2016/10/7.
 */

public class UtilPref {
    private SharedPreferences sharedPreferences;

    public UtilPref(Context context) {
        sharedPreferences = context.getSharedPreferences("com.dingpeng3414.ttkey_preferences", Activity.DEFAULT_KEYS_SEARCH_GLOBAL);
    }

    //安全设置
    public boolean getSecurity() {
        return sharedPreferences.getBoolean("isPass", false);
    }

    public boolean getPassItem() {
        return sharedPreferences.getBoolean("isPassItem", false);
    }
    public String getPassItemString() {
        return sharedPreferences.getString("key_PassItem", "******");
    }


    public String getPass() {
        return sharedPreferences.getString("key_pass", "false");
    }

//    public boolean getKeyTime() {
//        return sharedPreferences.getBoolean("key_time", true);
//    }
}
