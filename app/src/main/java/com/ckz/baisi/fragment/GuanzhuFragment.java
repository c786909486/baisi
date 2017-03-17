package com.ckz.baisi.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ckz.baisi.R;

/**
 * Created by CKZ on 2017/3/14.
 */

public class GuanzhuFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View notLogin = inflater.inflate(R.layout.not_login_layout,null);
        return notLogin;
    }
}
