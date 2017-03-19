package com.ckz.baisi.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ckz.baisi.R;
import com.ckz.baisi.dialog.DialogUtils;

/**
 * Created by CKZ on 2017/3/14.
 */

public class GuanzhuFragment extends Fragment implements View.OnClickListener{
    private TextView loginBtn,registerBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View notLogin = inflater.inflate(R.layout.not_login_layout,null);
        loginBtn = (TextView) notLogin.findViewById(R.id.login_btn);
        registerBtn = (TextView) notLogin.findViewById(R.id.register_btn);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        return notLogin;
    }

    @Override
    public void onClick(View v) {
        DialogUtils.setZBDialog(getContext());
    }
}
