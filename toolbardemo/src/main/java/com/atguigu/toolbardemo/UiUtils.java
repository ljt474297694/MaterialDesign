package com.atguigu.toolbardemo;

import android.content.Context;

/**
 * Created by 李金桐 on 2017/3/11.
 * QQ: 474297694
 * 功能: xxxx
 */

public class UiUtils {


    //与屏幕分辨率相关的
    public static int dp2px(Context context,int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5);
    }

    public static int px2dp(Context context,int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }
}
