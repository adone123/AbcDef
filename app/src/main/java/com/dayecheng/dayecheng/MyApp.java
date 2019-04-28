package com.dayecheng.dayecheng;

import android.app.Application;

import com.lsh.packagelibrary.CasePackageApp;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import cn.jpush.android.api.JPushInterface;


public class MyApp extends CasePackageApp {
    @Override
    public void onCreate() {
        super.onCreate();
//        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
//        JPushInterface.setDebugMode(true);
//        UMConfigure.init(this, "5b804972f43e485f0100000d", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "e3add858459ef14267795ba26a641e10");
//        PushAgent mPushAgent = PushAgent.getInstance(this);
////注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String deviceToken) {
//                //注册成功会返回device token
//            }
//            @Override
//            public void onFailure(String s, String s1) {
//            }
//        });

//        UMConfigure.setLogEnabled(true);
    }
}
