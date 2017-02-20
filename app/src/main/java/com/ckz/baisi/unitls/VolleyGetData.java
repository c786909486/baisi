package com.ckz.baisi.unitls;

import com.ckz.baisi.bean.BaisiData;

import java.util.List;



/**
 * Created by CKZ on 2017/2/14.
 */

public class VolleyGetData<T> {

    private String url;
    private List<T> baisiListBean;

    public VolleyGetData(String url, List<T> baisiListBean){
        this.url = url;
        this.baisiListBean = baisiListBean;
    }

}
