package com.dingpeng3414.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dingpeng3414.local.bean.AccountPassword;
import com.dingpeng3414.ttkey.R;

import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter<MainListViewHolder> {

    private List<AccountPassword> mData;

    public MainListAdapter() {
    }

    public void setmData(List<AccountPassword> mData) {
        this.mData = mData;
    }

    @Override
    public MainListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item_information_list, parent, false);
        return new MainListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MainListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
