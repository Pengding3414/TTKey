package com.dingpeng3414.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Pengding on 2016/10/7.
 */

public class UtilSP {
    private SharedPreferences sharedPreferences;

    public UtilSP(Context context) {
        sharedPreferences = context.getSharedPreferences("tt-key", Activity.MODE_PRIVATE);
    }

    //访问输入代码界面
    public void setVisit(boolean visit) {
        sharedPreferences.edit().putBoolean("isVisit", visit).commit();
    }

    public boolean getVisit() {
        return sharedPreferences.getBoolean("isVisit", true);
    }

    //引导
    public void setFirstIn(boolean firsIn) {
        sharedPreferences.edit().putBoolean("isFirstIn", firsIn).commit();
    }

    public boolean getFirstIn() {
        return sharedPreferences.getBoolean("isFirstIn", true);
    }

}
