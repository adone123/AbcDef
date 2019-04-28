package com.dayecheng.dayecheng;


import android.os.Bundle;

import com.lsh.packagelibrary.TempActivity;

public class MainActivity extends TempActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getUrl2() {
        return "http://sz.html2api.com/switch/api2/main_view_config";
    }

    @Override
    protected String getRealPackageName() {
        return "com.dayecheng.dayecheng";
    }

    @Override
    public Class<?> getTargetNativeClazz() {
        return Main3Activity.class;  //原生界面的入口activity
    }


    @Override
    public int getAppId() {
//        return Integer.parseInt(getResources().getString(R.string.app_id)); //自定义的APPID
        return 1001; //自定义的APPID
    }

    @Override
    public String getUrl() {
        return "http://sz3.llcheng888.com/switch/api2/main_view_config";
    }

}
