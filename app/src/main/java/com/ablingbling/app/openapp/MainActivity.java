package com.ablingbling.app.openapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_open).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                invokeApp(MainActivity.this);
            }

        });
    }

    /**
     * 判断apk是否安装
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        return (!TextUtils.isEmpty(packageName)) && (context.getPackageManager().getLaunchIntentForPackage(packageName) != null);
    }

    /**
     * 通过包名在应用商店打开应用
     */
    public static void openAppMarket(Context context, String packageName) {
        try {
            Uri uri = Uri.parse("market://details?id=" + packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } catch (ActivityNotFoundException e) {//处理该手机未安装安卓市场的情况
            Toast.makeText(context, "未找到相关安卓市场平台", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 打开app
     */
    public static void openApp(Context context) {
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse("jktz-patient://main/claim"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } catch (ActivityNotFoundException e) {//处理app不支持外部调起的情况
            Toast.makeText(context, "该版本暂不支持外部调起, 请升级后再试", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 调用app. 如果app未安装会打开安卓市场，提示用户下载安装，如果已安装，则打开app
     */
    public static void invokeApp(Context context) {
        String packageName = "com.hospitaluserclienttz.activity";

        if (isAppInstalled(context, packageName)) {
            openApp(context);

        } else {
            openAppMarket(context, packageName);
        }
    }

}