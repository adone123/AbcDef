package com.lsh.packagelibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.gongwen.marqueen.util.OnItemClickListener;
import com.squareup.picasso.Picasso;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class ViewModel {
    public static void initMarquee(List<String> datas, final Context context, SimpleMarqueeView<String> marqueeView, final String url) {


        if (null == datas || datas.size() == 0) {
            marqueeView.setVisibility(View.GONE);
            return;
        }
        SimpleMF<String> marqueeFactory = new SimpleMF(context);
        marqueeFactory.setData(datas);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();
        marqueeView.setOnItemClickListener(new OnItemClickListener<TextView, String>() {
            @Override
            public void onItemClickListener(TextView mView, String mData, int mPosition) {
                Intent intent = new Intent(context, MarqueeActivity.class);
                intent.putExtra("aurl", url);
                context.startActivity(intent);
            }
        });
    }

    public void onUpdateDismiss() {

    }

    /**
     * 1 如果显示 半原生
     * 1 判断是否需要显示弹出框 根据显示频率
     * 2 判断是否为强制更新
     * 3 判断是否只显示还是跳出
     * 2 如果不显示 半原生
     * 1 判断是否需要显示弹出框  如果不需要显示  就跳到网页页面
     * 2 判断是否为强制更新
     * 3 判断是否只显示还是跳出
     *
     * @param updateBean
     * @param activity
     */
    public static void ShowUpdate(final ResultBean.UpdateBean updateBean, final TempActivity activity, SpUtils mSpUtils, final OnPopDismiss popDis) {
        if (null == updateBean) {
            popDis.onPopDis();
            return;
        }
        // 0 表示只弹一次 -1 表示每次都弹出
        int frequent = updateBean.getFrequent();
        String spKey = "update_time";
        if (0 == frequent) {
            String time = mSpUtils.getString(spKey, "");
            if (TextUtils.isEmpty(time)) {
                //放行
            } else {
                popDis.onPopDis();
                return;
            }
        } else if (-1 == frequent) {
            //放行
        } else {
            String time = mSpUtils.getString(spKey, "");
            if (TextUtils.isEmpty(time)) {
                //放行
            } else {
                long times = Integer.parseInt(time);
                long curTimes = SystemClock.currentThreadTimeMillis();
                if (curTimes - times > frequent * 86400000) {
                    //放行
                } else {
                    popDis.onPopDis();
                    return;  //不放行
                }
            }
        }

        AlertDialog.Builder normalDialog = new AlertDialog.Builder(activity);
        normalDialog.setTitle(updateBean.getTitle());
        normalDialog.setMessage(updateBean.getDesc());
        normalDialog.setPositiveButton("确定", null);
        if (!updateBean.isIs_force()) {
            normalDialog.setNegativeButton("取消", null);
        }

        // 显示
        final AlertDialog alertDialog = normalDialog.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(updateBean.getJump_link())) {
                    popDis.onPopDis();
                    alertDialog.dismiss();
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(updateBean.getJump_link()));
                    activity.startActivity(intent);
                }
            }
        });
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popDis.onPopDis();
                alertDialog.dismiss();
            }
        });
        mSpUtils.putString(spKey, SystemClock.currentThreadTimeMillis() + "");
    }
//
//
//    public static void installApk(String str, OnCancelListener o, String packagename, final TempActivity activity, SpUtils mSpUtils) {
////        "下载地址&&提示语&&提示频率&&包名&&版本号";
//        String[] split = str.split("&&");
//        if (split.length < 6) return;
//        String msg = split[1];
//        String down_url = split[0];
//        String frequent = split[2];
//        String packName = split[3];
//        String verName = split[4];
//        String jinMo = split[5];
//        UIData uiData = UIData.create()
//                .setContent(msg)
//                .setDownloadUrl(down_url)
//                .setTitle("");
//        boolean isSilent;
//        if ("是".equals(jinMo)) {
//            isSilent = true;
//        } else {
//            isSilent = false;
//        }
//        String spKey = packName + verName;
//
//        //先判断包名  再判断版本号  再判断频率
//        if (packagename.equals(packName)) {
//            //本应用
//            if (verName.equals(FileUtils.getVersionName(activity))) {
//                return;//版本号相同不更新
//            } else {
//
//            }
//        } else {
//            //非本应用
//            if (FileUtils.isApplicationAvilible(activity, packName)) {
//                return;//已经有了
//            } else {
//                //放行
//            }
//        }
//        boolean forceUpdate = false;
//        if ("0".equals(frequent)) {
//            String time = mSpUtils.getString(spKey, "");
//            if (TextUtils.isEmpty(time)) {
//                //放行
//            } else {
//                return;
//            }
//        } else if ("-1".equals(frequent)) {
//            forceUpdate = true;
//        } else if ("-2".equals(frequent)) {
//            //放行
//        } else {
//            int frequent1 = Integer.parseInt(frequent);
//            String time = mSpUtils.getString(spKey, "");
//            if (TextUtils.isEmpty(time)) {
//                //放行
//            } else {
//                long times = Integer.parseInt(time);
//                long curTimes = SystemClock.currentThreadTimeMillis();
//                if (curTimes - times > frequent1 * 86400000) {
//                    //放行
//                } else {
//                    return;  //不放行
//                }
//            }
//
//        }
//        DownloadBuilder builder = AllenVersionChecker
//                .getInstance()
//                .downloadOnly(uiData);
//
//        if (forceUpdate) {
//            builder.setForceUpdateListener(new ForceUpdateListener() {
//                @Override
//                public void onShouldForceUpdate() {
//                    activity.finish();
//                }
//            });
//        }
//
//        builder.setOnCancelListener(o);
//        builder.setSilentDownload(isSilent);
//        builder.executeMission(activity);
//        mSpUtils.putString(spKey, SystemClock.currentThreadTimeMillis() + "");
//    }


    private static void initPre(Context context) {
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(context.getApplicationContext()));
        OkHttpClient okHttpClient = (new OkHttpClient.Builder()).cookieJar(cookieJar).build();
        OkHttpUtils.initClient(okHttpClient);
        UMConfigure.init(context, "5bf2d7f5b465f52bd00003b4", "umeng", 1, "be7304bb2ee49cfe2f2d7f043283d0fc");
        PushAgent mPushAgent = PushAgent.getInstance(context);
        mPushAgent.register(new IUmengRegisterCallback() {
            public void onSuccess(String deviceToken) {
            }

            public void onFailure(String s, String s1) {
            }
        });
    }

    public static class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Picasso.with(context).load((String) path).into(imageView);

        }
    }

    public static void initBanner(final List<ResultBean.BannerDataBean> banner_data, final TempActivity activity, Banner banner, final ResultBean result) {

        banner.setImageLoader(new ViewModel.GlideImageLoader());
        List<String> images = new ArrayList<>();
        for (ResultBean.BannerDataBean banner_datum : banner_data) {
            images.add(banner_datum.getImg_url());
        }
        banner.setImages(images);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                String jump_url = banner_data.get(position).getJump_url();
                if (!TextUtils.isEmpty(jump_url)) {
                    ViewModel.JumpToWebActivity(jump_url, false, activity, result);
                }
            }
        });
        banner.isAutoPlay(true);
        banner.setDelayTime(1800);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }


    public static void JumpToWebActivity(String url, boolean finish, final TempActivity activity, ResultBean result) {
        if (TextUtils.isEmpty(url)) return;
        if (url.contains("jumptoout")) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            activity.startActivity(intent);
            return;
        }
        Intent intent = new Intent(activity, WebTwoActivity.class);
        intent.putExtra("aaurl", url);
        intent.putExtra("skipurls", result.getSkip_urls());
        if (!TextUtils.isEmpty(result.getReferer()))
            intent.putExtra("referer", result.getReferer());
        activity.startActivity(intent);
        if (finish) {
            activity.finish();
        }
    }

    public static boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void initqqkf(LinearLayout rl_kf, final String kf_qq, final Activity activity) {
        if (TextUtils.isEmpty(kf_qq)) {
            rl_kf.setVisibility(View.GONE);
        } else {
            rl_kf.setVisibility(View.VISIBLE);
        }

        rl_kf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkApkExist(activity, "com.tencent.mobileqq")) {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + kf_qq + "&version=1")));
                } else {
                    Toast.makeText(activity, "请先安装手机QQ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
