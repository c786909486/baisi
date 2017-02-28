package com.ckz.baisi.activity;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ckz.baisi.R;
import com.ckz.baisi.fragment.JingHuaFragment;
import com.ckz.baisi.fragment.ZuiXinFragment;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int JINGHUA = 0;
    private static final int XINTIE = 1;
    private static final int GUANZHU = 2;
    private static final int WO = 3;

    private int type;

    private TextView jinghua,xintie,fabu,guanzhu,wo;
    private JingHuaFragment jinhuaFragment;
    private ZuiXinFragment zuiXinFragment;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        fManager = getSupportFragmentManager();
        jinghua = (TextView) findViewById(R.id.main_btn_jinghua);
        xintie = (TextView) findViewById(R.id.main_btn_xintie);
        fabu = (TextView) findViewById(R.id.main_btn_fabu);
        guanzhu = (TextView) findViewById(R.id.main_btn_guanzhu);
        wo = (TextView) findViewById(R.id.main_btn_wo);



        jinghua.setOnClickListener(this);
        xintie.setOnClickListener(this);
        fabu.setOnClickListener(this);
        guanzhu.setOnClickListener(this);
        wo.setOnClickListener(this);
        jinghua.performClick();
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (jinhuaFragment !=null) transaction.hide(jinhuaFragment);
        if (zuiXinFragment !=null) transaction.hide(zuiXinFragment);
    }

    private void resetSeclect(){
        jinghua.setSelected(false);
        xintie.setSelected(false);
        guanzhu.setSelected(false);
        wo.setSelected(false);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = fManager.beginTransaction();
        hideAllFragment(transaction);
        switch (v.getId()){
            case R.id.main_btn_jinghua:
                resetSeclect();
                type = JINGHUA;
                jinghua.setSelected(true);
                if (jinhuaFragment == null){
                    jinhuaFragment = new JingHuaFragment();
                    transaction.add(R.id.main_fragment_content,jinhuaFragment);
                }else {
                    transaction.show(jinhuaFragment);
                }
                break;
            case R.id.main_btn_xintie:
                resetSeclect();
                type = XINTIE;
                xintie.setSelected(true);
                if (zuiXinFragment == null){
                    zuiXinFragment = new ZuiXinFragment();
                    transaction.add(R.id.main_fragment_content,zuiXinFragment);
                }else {
                    transaction.show(zuiXinFragment);
                }
                break;
            case R.id.main_btn_guanzhu:
                resetSeclect();
                type = GUANZHU;
                guanzhu.setSelected(true);

                break;
            case R.id.main_btn_wo:
                resetSeclect();
                type = WO;
                wo.setSelected(true);

                break;
            case R.id.main_btn_fabu:
                if (type == JINGHUA) transaction.show(jinhuaFragment);

                Toast.makeText(this,type+"",Toast.LENGTH_SHORT).show();
        }
        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
