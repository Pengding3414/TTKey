package com.dingpeng3414.Fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dingpeng3414.api.LocalDataBase;
import com.dingpeng3414.lmpl.LocalDataBaseLmpl;
import com.dingpeng3414.local.bean.AccountPassword;
import com.dingpeng3414.service.ClipService;
import com.dingpeng3414.ttkey.AddActivity;
import com.dingpeng3414.ttkey.InfoScrollingActivity;
import com.dingpeng3414.ttkey.R;
import com.dingpeng3414.util.UtilPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NewsFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private static final String ARG_POSITION = "position";
    private UtilPref utilPref;
    private int position;
    private View view;
    private SimpleAdapter simpleAdapter;
    private ListView lv;
    private NotificationManager manger;
    private FrameLayout fl;
    private Button button;
    private String[] listSt;
    private int[] listIn;

    public static NewsFragment newInstance(int position) {
        NewsFragment f = new NewsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        utilPref = new UtilPref(getActivity());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,Gravity.CENTER);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        layoutParams.weight = 1.0f;
        fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);

        final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

        button = new Button(getActivity());
        params.setMargins(margin, margin, margin, margin);

        button.setLayoutParams(layoutParams);
//        button.setWidth(LayoutParams.WRAP_CONTENT);
//        button.setHeight(LayoutParams.WRAP_CONTENT);
        button.setGravity(Gravity.CENTER);
        button.setText("新建 TT - Key");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

        lv = new ListView(getActivity());
        lv.setDivider(null);//去除lv item分割线
        listSt = new String[]{"info_list_cname", "info_list_acname", "info_list_acpass", "tv_category", "item_text"};
        listIn = new int[]{R.id.info_list_cname, R.id.info_list_acname, R.id.info_list_acpass, R.id.tv_category, R.id.item_text};
//        int[] listIn = new int[]{R.id.info_list_iv, R.id.info_list_cname, R.id.info_list_acname, R.id.info_list_acpass};
        return getAccountPasswordListSize();
    }

    //    //
    private void start() {
        Intent intent = new Intent(getActivity(), AddActivity.class);
        intent.putExtra("sign", "add");
        intent.putExtra("title", "添加");
        startActivity(intent);
    }

    private void bindingSinmpleAdapter() {//绑定adapter
        this.lv.setAdapter(this.simpleAdapter);
    }

    private FrameLayout getAccountPasswordListSize() {
        List<AccountPassword> accountPasswordList;
        LocalDataBase localDataBase = new LocalDataBaseLmpl();
        String[] categories = getResources().getStringArray(R.array.categoriesAll);
        if (position == 0) {
            accountPasswordList = localDataBase.getAccountPasswordAll();
        } else {
            accountPasswordList = localDataBase.category(categories[position]);
        }
        int x = accountPasswordList.size();

        if (x == 0) {
//            fl.addView(button);
        } else {
            List<Map<String, Object>> item = getDateListView(x, accountPasswordList);
            simpleAdapter = new SimpleAdapter(getActivity(), item, R.layout.listview_item_information_list, listSt, listIn);
            this.bindingSinmpleAdapter();
            this.lv.setOnItemClickListener(this);
            this.lv.setOnItemLongClickListener(this);
            fl.addView(lv);
        }

        return fl;
    }


    private List<Map<String, Object>> getDateListView(int x, List<AccountPassword> accountPasswordList) {
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        for (int i = x - 1; i >= 0; i--) {
            Map<String, Object> itemMap = new HashMap<String, Object>();
            itemMap.put("info_list_cname", accountPasswordList.get(i).getAppName());
            itemMap.put("info_list_acname", accountPasswordList.get(i).getAccountName());

            if (utilPref.getPassItem()) {
                itemMap.put("info_list_acpass", accountPasswordList.get(i).getAccountPassword());
                itemMap.put("item_text", accountPasswordList.get(i).getAccountPassword());
            } else {
                itemMap.put("item_text", accountPasswordList.get(i).getAccountPassword());
                itemMap.put("info_list_acpass", utilPref.getPassItemString());
            }

            if (position == 0) {
                itemMap.put("tv_category", accountPasswordList.get(i).getCategory());
            }
            items.add(itemMap);
        }
        return items;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView) view.findViewById(R.id.info_list_cname);
        String st = tv.getText().toString();
        startInfo(st);
    }
    private void startInfo(String st){
//        Intent intent = new Intent(getActivity(), InformationActivity.class);
        Intent intent = new Intent(getActivity(), InfoScrollingActivity.class);
        intent.putExtra("itemName", st);
        startActivity(intent);
    }
    //长按将账户密码 发送到通知栏
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "下拉通知栏", Toast.LENGTH_SHORT).show();
        sendCustomerNotification(view);
        return true;
    }

    private void sendCustomerNotification(View view) {
        manger = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 16) {
            Notification notification = new Notification();
            notification.icon = R.mipmap.ic_launcher;
            notification.tickerText = "复制信息";
            notification.contentView = new RemoteViews(getActivity().getPackageName(), R.layout.suspension_window_layout);
            RemoteViews remotvViews = new RemoteViews(getActivity().getPackageName(), R.layout.suspension_window_layout);

            TextView tv = (TextView) view.findViewById(R.id.info_list_cname);
            TextView tv1 = (TextView) view.findViewById(R.id.tv_category);
            TextView tv2 = (TextView) view.findViewById(R.id.info_list_acname);
            TextView tv3 = (TextView) view.findViewById(R.id.info_list_acpass);
            TextView tv4 = (TextView) view.findViewById(R.id.item_text);

            remotvViews.setTextViewText(R.id.info_list_cname, tv.getText().toString());
            remotvViews.setTextViewText(R.id.tv_category, tv1.getText().toString());
            remotvViews.setTextViewText(R.id.info_list_acname, tv2.getText().toString());
            remotvViews.setTextViewText(R.id.info_list_acpass, tv3.getText().toString());
//账户复制
            Intent intent = new Intent(getActivity(), ClipService.class);
            intent.putExtra("data", tv2.getText().toString());

//            Toast.makeText(getActivity(),  tv2.getText().toString() + "", Toast.LENGTH_SHORT).show();

            PendingIntent PIntent = PendingIntent.getService(getActivity(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            remotvViews.setOnClickPendingIntent(R.id.bt1, PIntent);

//密码复制
            Intent intentPass = new Intent(getActivity(), ClipService.class);
            intentPass.putExtra("data", tv4.getText().toString());

            PendingIntent PIntentPass = PendingIntent.getService(getActivity(), 1, intentPass, PendingIntent.FLAG_CANCEL_CURRENT);
            remotvViews.setOnClickPendingIntent(R.id.bt2, PIntentPass);

            notification.bigContentView = remotvViews;
            manger.notify(10, notification);
        } else {
            Toast.makeText(getActivity(), "系统版本过低，不支持此种样式！", Toast.LENGTH_SHORT).show();
        }
    }
}