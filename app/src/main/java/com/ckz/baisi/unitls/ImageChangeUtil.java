package com.ckz.baisi.unitls;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by CKZ on 2017/2/27.
 */

public class ImageChangeUtil {

    public static Bitmap drawableToBitmap(Drawable drawable, ImageView currentItem) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        int reqW = currentItem.getHeight();
        int reqH = currentItem.getWidth();
        float widthMultiple = w / reqW * 1.0f;
        float heithMultiple = h / reqH * 1.0f;
        float multiple = Math.max(widthMultiple, heithMultiple);
        w = (int) (w / multiple);
        h = (int) (h / multiple);

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, 600);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

}
