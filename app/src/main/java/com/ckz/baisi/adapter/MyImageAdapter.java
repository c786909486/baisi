package com.ckz.baisi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ckz.baisi.R;
import com.ckz.baisi.bean.BaisiData;

import java.util.List;

/**
 * Created by CKZ on 2017/2/7.
 */

public class MyImageAdapter extends RecyclerView.Adapter<MyImageAdapter.PicViewHolder> {

    private List<BaisiData.ListBean> mData;
    private Context context;
    private LayoutInflater inflater;

    public MyImageAdapter(Context context,List<BaisiData.ListBean> mData){
        this.context =context;
        this.mData = mData;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tuijian_item , null);
        PicViewHolder holder = new PicViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(PicViewHolder holder, int position) {
         holder.textView.setText(mData.get(position).getText());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    class PicViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public PicViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_txt);
        }
    }
}
