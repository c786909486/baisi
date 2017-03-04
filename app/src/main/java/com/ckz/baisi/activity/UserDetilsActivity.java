package com.ckz.baisi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ckz.baisi.R;

public class UserDetilsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detils);
        String userId = getIntent().getBundleExtra("Id").getString("userId");
        Log.d("UserId",userId);
    }
}
