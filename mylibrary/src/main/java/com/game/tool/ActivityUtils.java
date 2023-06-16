package com.game.tool;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityUtils {

    // 跳转到目标Activity
    public static void startActivity(Context context, Class<? extends Activity> targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        context.startActivity(intent);
    }

    // 跳转到目标Activity并传递参数
    public static void startActivityWithExtra(Context context, Class<? extends Activity> targetActivity, String key, String value) {
        Intent intent = new Intent(context, targetActivity);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    // 跳转到目标Activity并清空任务栈
    public static void startActivityClearTop(Context context, Class<? extends Activity> targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    // 跳转到目标Activity并获取结果
    public static void startActivityForResult(Activity activity, Class<? extends Activity> targetActivity, int requestCode) {
        Intent intent = new Intent(activity, targetActivity);
        activity.startActivityForResult(intent, requestCode);
    }

    // 跳转到目标Activity并获取结果，并传递参数
    public static void startActivityForResultWithExtra(Activity activity, Class<? extends Activity> targetActivity, int requestCode, String key, String value) {
        Intent intent = new Intent(activity, targetActivity);
        intent.putExtra(key, value);
        activity.startActivityForResult(intent, requestCode);
    }
}