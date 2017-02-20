package com.ckz.baisi.fragment.jinghuaContent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ckz.baisi.R;
import com.ckz.baisi.adapter.MyImageAdapter;
import com.ckz.baisi.appliction.MyAppliction;
import com.ckz.baisi.bean.BaisiData;
import com.ckz.baisi.request.GsonRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CKZ on 2017/2/7.
 */

public class TuijianFragment extends Fragment {
    private String content;
    private RecyclerView jinghuaRecycler;
    private List<BaisiData.ListBean> mData;
    private MyImageAdapter adapter;

    public TuijianFragment(){

    }

    public String getContent(){
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_content_layout,container,false);
        init(view);
        return view;
    }

    private void init(View view) {
        jinghuaRecycler = (RecyclerView) view.findViewById(R.id.content_list_view);
        mData = new ArrayList<>();
        adapter = new MyImageAdapter(getContext(),mData);
        jinghuaRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        jinghuaRecycler.setAdapter(adapter);
        getData();
    }
    private void getData(){
        GsonRequest<BaisiData> gsonRequest = new GsonRequest<BaisiData>(content, BaisiData.class, new Response.Listener<BaisiData>() {
            @Override
            public void onResponse(BaisiData baisiData) {
             List<BaisiData.ListBean> listBeen = baisiData.getList();
                listBeen.removeAll(mData);
                mData.addAll(listBeen);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
        MyAppliction.getRequestQueue().add(gsonRequest);
    }
}
