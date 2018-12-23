package com.dingpeng3414.ttkey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dingpeng3414.util.UtilPref;
import com.dingpeng3414.util.UtilSP;


public class WelcomeActivity extends Activity {

    private boolean isFirstIn = false;

    private static final int TIME = 2000;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    private static final int GO_VISIT = 1002;
    private UtilSP utilSP;
    private UtilPref utilPref;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;

                case GO_VISIT:
                    goVisit();
                    break;

                case GO_GUIDE:
                    goGuide();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        utilSP = new UtilSP(this);
        utilPref = new UtilPref(this);
        init();
    }

    private void init() {

        isFirstIn = utilSP.getFirstIn();

        if (!isFirstIn && utilPref.getSecurity()) {
            mHandler.sendEmptyMessageDelayed(GO_VISIT, TIME);

        } else if (!isFirstIn) {
            //主界面
            mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
        } else {
            //引导
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
            utilSP.setFirstIn(false);
        }

    }

    private void goVisit() {
        Intent i = new Intent(WelcomeActivity.this, VisitActivity.class);
        startActivity(i);
        finish();
    }

    private void goHome() {
        Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void goGuide() {
        Intent i = new Intent(WelcomeActivity.this, Guide.class);
        startActivity(i);
        finish();
    }

}
