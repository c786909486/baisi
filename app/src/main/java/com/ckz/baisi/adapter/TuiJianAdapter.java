package com.ckz.baisi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ckz.baisi.R;
import com.ckz.baisi.bean.TJDYBean;
import com.ckz.baisi.unitls.MyIntegerUtils;

import java.util.List;

/**
 * Created by CKZ on 2017/3/19.
 */

public class TuiJianAdapter extends BaseAdapter {
    private Context context;
    private List<TJDYBean.RecTagsBean> mData;

    public TuiJianAdapter(Context context,List<TJDYBean.RecTagsBean> mData){
        this.context = context;
        this.mData = mData;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null){
            holder = new MyViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.ding_yue_layout,parent,false);
            holder.tuijianIcon = (ImageView) convertView.findViewById(R.id.tuijian_icon);
            holder.tuijianName = (TextView) convertView.findViewById(R.id.tuijian_name);
            holder.dingyueCount = (TextView) convertView.findViewById(R.id.dingyue_count);
            holder.tieziCount = (TextView) convertView.findViewById(R.id.tiezi_count);
            holder.followBtn = (TextView) convertView.findViewById(R.id.follow_btn);
            convertView.setTag(holder);
        }else {
            holder = (MyViewHolder) convertView.getTag();
        }
        Glide.with(context).load(mData.get(position).getImage_list()).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate().into(holder.tuijianIcon);
        holder.tuijianName.setText(mData.get(position).getTheme_name());
        holder.dingyueCount.setText(MyIntegerUtils.int2k(mData.get(position).getSub_number()));
        holder.tieziCount.setText(mData.get(position).getPost_num()+"");
        return convertView;
    }

    private class MyViewHolder{
        ImageView tuijianIcon;
        TextView tuijianName,dingyueCount,tieziCount,followBtn;
    }
}
