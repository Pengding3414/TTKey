package com.dingpeng3414.service;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import java.lang.reflect.Method;

public class ClipService extends Service {


    public ClipService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String data = intent.getStringExtra("data");

//        Toast.makeText(this, data + "", Toast.LENGTH_SHORT).show();

        ClipboardManager cmb = (ClipboardManager) getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(null, data));

        Toast.makeText(this, "复制到粘贴板", Toast.LENGTH_SHORT).show();
        collapseStatusBar(getApplication());

        return super.onStartCommand(intent, flags, startId);
    }

    //收起下拉通知栏
    public static void collapseStatusBar(Context context) {
        try {
            Object statusBarManager = context.getSystemService("statusbar");
            Method collapse;
            if (Build.VERSION.SDK_INT <= 16) {
                collapse = statusBarManager.getClass().getMethod("collapse");
            } else {
                collapse = statusBarManager.getClass().getMethod("collapsePanels");
            }
            collapse.invoke(statusBarManager);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
