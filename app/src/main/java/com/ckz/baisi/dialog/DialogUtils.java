package com.ckz.baisi.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.ckz.baisi.R;

/**
 * Created by CKZ on 2017/3/18.
 */

public class DialogUtils {

    public static void setZBDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog alertDialog = builder.setTitle(R.string.waning)
                .setMessage(R.string.login_waning)
                .setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.trues, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.budejie.com/"));
//                        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                        context.startActivity(intent);
                    }
                }).create();
        alertDialog.show();

    }
}
