package com.dayecheng.dayecheng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.lsh.packagelibrary.ResultBean;
//import com.lsh.packagelibrary.SavePic;
//import com.lsh.packagelibrary.WebTwoActivity;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.StringCallback;
//
//import okhttp3.Call;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class Main3Activity extends AppCompatActivity {
    private String jump_url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(com.lsh.packagelibrary.R.style.Theme_AppCompat_Light_NoActionBar);
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main3);
//        get_data();
//        handler.sendEmptyMessageDelayed(1, 2500);
    }

    private void get_data() {
//        if (SavePic.isVpnUsed(this) || SavePic.isWifiProxy(this)) {
//            return;
//        }
//        OkHttpUtils
//                .post()
//                .url("http://sz.html2api.com/switch/api/get_url")
//                .addParams("order_id", "1")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Toast.makeText(Main3Activity.this, "网络异常，请检查网络", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        ResultBean result;
//                        try {
//                            result = new Gson().fromJson(response, ResultBean.class);
//                        }catch (Exception e){
//                            Toast.makeText(Main3Activity.this, "json解析异常", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        if (result.isJump()) {
//                            jump_url = result.getData();
//                            Intent intent = new Intent(Main3Activity.this, WebTwoActivity.class);
//                            intent.putExtra("aaurl", jump_url);
//                            startActivity(intent);
//                            finish();
////        }
//                        }
//                    }
//                });
    }

//    @SuppressLint("HandlerLeak")
//    public Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            Intent intent = new Intent(Main3Activity.this, WebActivity.class);
//            if (!TextUtils.isEmpty(jump_url)) {
//                intent.putExtra("aaurl", jump_url);
//            } else {
//                get_data();
//                handler.sendEmptyMessageDelayed(1, 2500);
//                return;
//            }
//            startActivity(intent);
//            finish();
//        }
//    };
}
