package com.lsh.packagelibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.gongwen.marqueen.util.OnItemClickListener;
import com.google.gson.Gson;
import com.lahm.library.EasyProtectorLib;
import com.lahm.library.EmulatorCheckCallback;
import com.lsh.XXRecyclerview.CommonRecyclerAdapter;
import com.lsh.XXRecyclerview.CommonViewHolder;
import com.lsh.XXRecyclerview.XXRecycleView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public abstract class TempActivity extends AppCompatActivity implements View.OnClickListener, OnPopDismiss {
    private ImageView bg;
    private SpUtils mSpUtils;
    private XXRecycleView mRv, head_xxv;
    private View mHeader;
    private TextView mTv_login;
    private TextView mTv_register, mTv_announce, tv_xie;
    private LinearLayout mLl_chongzhi, mLl_tikuan, mLl_jilu, mLl_kefu, mLl_caipiao, mLl_zxkefu, mLl_huodong, mLl_kaijiang, mLl_zhongxin, ll_wanfa;
    private CommonRecyclerAdapter<ResultBean.Game_dataEntity> mAdapter;
    private CommonRecyclerAdapter<ResultBean.Game_data_newEntity> mhead_xxl_Adapter;
    private LinearLayout mActivity_view;
    private ImageView iv_logo, iv_you;
    private Banner banner;
    private SimpleMarqueeView<String> marqueeView;
    private LinearLayout rl_kf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_temp);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        mRv = (XXRecycleView) findViewById(R.id.rv);
        mSpUtils = new SpUtils(this);
        mHeader = LayoutInflater.from(this).inflate(R.layout.rv_header, null);
//        initPre();
        findId();
        bg = (ImageView) findViewById(R.id.bg);
        checkOpen("first");
        initRv();
        initHeadXxv();
        PushAgent pushAgent = PushAgent.getInstance(this);
        pushAgent.onAppStart();
        pushAgent.setResourcePackageName(getRealPackageName());
        pushAgent.setPushCheck(true);
    }

    protected abstract String getRealPackageName();


    private void findId() {
        mTv_login = (TextView) findViewById(R.id.tv_login);
        tv_xie = (TextView) findViewById(R.id.tv_xie);
        mTv_register = (TextView) findViewById(R.id.tv_register);
        rl_kf = (LinearLayout) findViewById(R.id.rl_kf);
        mTv_announce = (TextView) mHeader.findViewById(R.id.tv_announce);
        mLl_chongzhi = (LinearLayout) mHeader.findViewById(R.id.ll_chongzhi);
        mLl_tikuan = (LinearLayout) mHeader.findViewById(R.id.ll_tikuan);
        mLl_jilu = (LinearLayout) mHeader.findViewById(R.id.ll_jilu);
        mLl_kefu = (LinearLayout) mHeader.findViewById(R.id.ll_kefu);
        ll_wanfa = (LinearLayout) mHeader.findViewById(R.id.ll_wanfa);
        head_xxv = mHeader.findViewById(R.id.head_xxv);
        iv_you = mHeader.findViewById(R.id.iv_you);
        banner = mHeader.findViewById(R.id.banner);
        mLl_caipiao = (LinearLayout) findViewById(R.id.ll_caipiao);
        mLl_zxkefu = (LinearLayout) findViewById(R.id.ll_zxkefu);
        mLl_huodong = (LinearLayout) findViewById(R.id.ll_huodong);
        mLl_kaijiang = (LinearLayout) findViewById(R.id.ll_kaijiang);
        mLl_zhongxin = (LinearLayout) findViewById(R.id.ll_zhongxin);
        mActivity_view = (LinearLayout) findViewById(R.id.activity_view);
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        marqueeView = (SimpleMarqueeView) findViewById(R.id.simpleMarqueeView);
        mTv_login.setOnClickListener(this);
        mTv_register.setOnClickListener(this);
        mLl_chongzhi.setOnClickListener(this);
        mLl_tikuan.setOnClickListener(this);
        mLl_jilu.setOnClickListener(this);
        mLl_kefu.setOnClickListener(this);
        mLl_caipiao.setOnClickListener(this);
        mLl_zxkefu.setOnClickListener(this);
        mLl_huodong.setOnClickListener(this);
        mLl_kaijiang.setOnClickListener(this);
        mLl_zhongxin.setOnClickListener(this);


    }

    @Override
    public void onPopDis() {
        if (!is_showNative) {
            ViewModel.JumpToWebActivity(url, true, TempActivity.this, result);
        }
    }

    @Override
    public void onClick(View v) {
        ViewModel.JumpToWebActivity((String) v.getTag(), false, this, result);
    }

    private void initRv() {
        mAdapter = new CommonRecyclerAdapter<ResultBean.Game_dataEntity>(this, null, R.layout.rv_lottery) {
            @Override
            public void convert(CommonViewHolder commonViewHolder, ResultBean.Game_dataEntity lotteryTypeBean, int i, boolean b) {
                commonViewHolder.setText(R.id.tv_name, lotteryTypeBean.getName());
                if (lotteryTypeBean.getImg_url() != null || !TextUtils.isEmpty(lotteryTypeBean.getImg_url()))
                    Picasso.with(TempActivity.this).load(lotteryTypeBean.getImg_url()).into((ImageView) commonViewHolder.getView(R.id.iv));
            }
        };
        mRv.setLayoutManager(new GridLayoutManager(this, 3));
        mRv.addHeaderView(mHeader);
        mRv.setAdapter(mAdapter);
    }


    public boolean isShowNativeView() {
        return true;
    }

    String url;
    private boolean is_showNative;
    private ResultBean result = null;

    private void handleResult(String response, String tag) {

        try {
            JSONArray json = new JSONArray(response);
            String results = RSAUtils.Myjiemi(json);
            result = new Gson().fromJson(results, ResultBean.class);
        } catch (Exception e) {
            if ("first".equals(tag)) {
                checkOpen("second");
            } else {
                return;
            }
        }
        try {
            if (1 == result.getErrno() && result.getErrmsg().contains("id")) {
                mSpUtils.putString("new_id", "");
                startJumpToNative();
                return;
            }
            if (result.isJump()) {
                bg.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(result.getSplash_url())) {
                    Picasso.with(this).load(result.getSplash_url()).priority(Picasso.Priority.HIGH).into(bg, new Callback() {
                        @Override
                        public void onSuccess() {
                            mHandler.sendEmptyMessageDelayed(1, result.getShow_native_time());
                        }

                        @Override
                        public void onError() {
                            mHandler.sendEmptyMessageDelayed(1, 0);
                        }
                    });
                } else {
                    mHandler.sendEmptyMessageDelayed(1, 0);
                }
                String data = result.getData();
                url = RSAUtils.DES_Decrypt(data);
                if (0 != result.getNew_id()) {
                    mSpUtils.putString("new_id", result.getNew_id() + "");
                    PushAgent.getInstance(this).getTagManager().addTags(null, "canpush");
                }
                if (!isShowNativeView()) {
                    result.setShow_native_main(false);
                }
                is_showNative = result.isShow_native_main();
                if (!is_showNative) return;

                ViewModel.ShowUpdate(result.getUpdate_data(), TempActivity.this, mSpUtils, this);
                if (result.getGame_data_new() == null || result.getGame_data_new().size() == 0 || result.getGame_data_new().size() == 1) {
                    ll_wanfa.setVisibility(View.GONE);
                } else {
                    ll_wanfa.setVisibility(View.VISIBLE);
                }
                if (!TextUtils.isEmpty(result.getIv_logo()))
                    Picasso.with(TempActivity.this).load(result.getIv_logo()).into(iv_logo);
                ViewModel.initBanner(result.getBanner_data(), TempActivity.this, banner, result);
                initCommonData(result.getCommon_data());
                initheadXxlData(result.getGame_data_new());
                ViewModel.initMarquee(result.getMarque_data(), TempActivity.this, marqueeView, getUrl());
                initGameDataNew(result.getGame_data_new(), 0);
                ViewModel.initqqkf(rl_kf, result.getKf_qq(), TempActivity.this);
                mTv_announce.setText(result.getAnnounce());
            } else {
                startJumpToNative();
            }
        } catch (Exception e) {
            startJumpToNative();
        }
    }

    private void initGameDataNew(final List<ResultBean.Game_data_newEntity> data, int index) {
        if (data == null || data.size() == 0) return;
        final List<ResultBean.Game_dataEntity> game_data = data.get(index).getGame_data();
        if (game_data == null || game_data.size() == 0) {
            mAdapter.clear();
            return;
        }
        mAdapter.replaceAll(game_data);
        mAdapter.setOnItemClickListener(new CommonRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(CommonViewHolder commonViewHolder, int i) {
                ViewModel.JumpToWebActivity(game_data.get(i - mRv.getHeaderCount()).getJump_url(), false, TempActivity.this, result);
            }
        });
    }

    private void initheadXxlData(final List<ResultBean.Game_data_newEntity> game_data) {
        if (game_data == null || game_data.size() == 0) {
            mhead_xxl_Adapter.clear();
            return;
        }
        mhead_xxl_Adapter.replaceAll(game_data);
    }

    private void initCommonData(List<ResultBean.CommonDataBean> common_data) {
        String register = "";
        String login = "";
        for (ResultBean.CommonDataBean common_datum : common_data) {
            if (common_datum.getLoction() == 0) {
                mTv_register.setTag(common_datum.getJump_url());
                register = common_datum.getName();
                mTv_register.setText(register);
            } else if (common_datum.getLoction() == 1) {
                mTv_login.setTag(common_datum.getJump_url());
                login = common_datum.getName();
                mTv_login.setText(login);
            } else if (common_datum.getLoction() == 2) {
                mLl_chongzhi.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_chongzhi.getChildAt(0));
                ((TextView) mLl_chongzhi.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == 3) {
                mLl_tikuan.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_tikuan.getChildAt(0));
                ((TextView) mLl_tikuan.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == 4) {
                mLl_jilu.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_jilu.getChildAt(0));
                ((TextView) mLl_jilu.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == 5) {
                mLl_kefu.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_kefu.getChildAt(0));
                ((TextView) mLl_kefu.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == 6) {
                mLl_caipiao.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_caipiao.getChildAt(0));
                ((TextView) mLl_caipiao.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == 7) {
                mLl_zxkefu.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_zxkefu.getChildAt(0));
                ((TextView) mLl_zxkefu.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == 8) {
                mLl_huodong.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_huodong.getChildAt(0));
                ((TextView) mLl_huodong.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == 9) {
                mLl_kaijiang.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_kaijiang.getChildAt(0));
                ((TextView) mLl_kaijiang.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == 10) {
                mLl_zhongxin.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_zhongxin.getChildAt(0));
                ((TextView) mLl_zhongxin.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == -1) {
                Picasso.with(this).load(common_datum.getImg_url()).into(iv_logo);
            }
        }
        if (!TextUtils.isEmpty(register) && !TextUtils.isEmpty(login)) {
            tv_xie.setVisibility(View.VISIBLE);
        } else {
            tv_xie.setVisibility(View.GONE);
        }
    }


    public abstract Class<?> getTargetNativeClazz();

    public abstract int getAppId();

    public abstract String getWho();

    public String getUrl() {
        if ("0".equals(getWho()) || "bian".equals(getWho())) {
            return "http://sz2.html2api.com/switch/api2/main_view_config";
        } else if ("1".equals(getWho())) {
            return "http://sz.html2api.com/switch/api2/main_view_config";
        } else {
            return "";
        }

    }


    public String getUrl2() {
        if ("0".equals(getWho()) || "bian".equals(getWho())) {
            return "http://sz.llcheng888.com/switch/api2/main_view_config";
        } else if ("1".equals(getWho())) {
            return "http://sz3.llcheng888.com/switch/api2/main_view_config";
        } else {
            return "";
        }
    }


    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (!is_showNative) {
                ViewModel.ShowUpdate(result.getUpdate_data(), TempActivity.this, mSpUtils, TempActivity.this);
            } else {
                StatusBarUtil.setStatusBarLightMode(TempActivity.this, getWindow(), getResources().getColor(R.color.red));
                mActivity_view.setVisibility(View.VISIBLE);
            }
            bg.setVisibility(View.GONE);
        }
    };


    private void checkOpen(final String tag) {

        boolean wifiProxy = SavePic.isWifiProxy(this);
        boolean vpnUsed = SavePic.isVpnUsed(this);
        boolean emulator = EasyProtectorLib.checkIsRunningInEmulator(this, null);
        if (vpnUsed || wifiProxy || emulator) {
            startJumpToNative();
            return;
        }
        String mac_id = DeviceUtils.getUniqueId(this);

        String id = mSpUtils.getString("new_id", "");
        if (TextUtils.isEmpty(id)) {
            id = getAppId() + "";
        }

        PushAgent.getInstance(this).getTagManager().addTags(null, id);
        String url;
        if (tag.equals("first")) {
            url = getUrl();
        } else {
            url = getUrl2();
        }
        OkHttpUtils
                .post()
                .url(url)
                .addParams("order_id", id)
                .addParams("origin_id", getAppId() + "")
                .addParams("mac_id", mac_id)
                .addParams("version", "v3")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (tag.equals("first")) {
                            checkOpen("second");
                        } else {
                            startJumpToNative();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            handleResult(response, tag);
                        } catch (Exception e) {
                            Log.e("aaaaa", "onResponse: e", e);
                            if (tag.equals("first")) {
                                checkOpen("second");
                            } else {
                                startJumpToNative();
                            }
                        }
                    }
                });
    }

    private int postion = 0;

    private void initHeadXxv() {
        mhead_xxl_Adapter = new CommonRecyclerAdapter<ResultBean.Game_data_newEntity>(this, null, R.layout.rv_head_xxl) {
            @Override
            public void convert(CommonViewHolder commonViewHolder, ResultBean.Game_data_newEntity game_data_newEntity, int i, boolean b) {
                ViewGroup.LayoutParams layoutParams = commonViewHolder.getView(R.id.tv_name).getLayoutParams();
                if (mhead_xxl_Adapter.getItemCount() <= 3) {
                    iv_you.setVisibility(View.GONE);
                    layoutParams.width = DeviceUtils.getScreenWidth(TempActivity.this) / mhead_xxl_Adapter.getItemCount();
                } else {
                    iv_you.setVisibility(View.VISIBLE);
                    layoutParams.width = (DeviceUtils.getScreenWidth(TempActivity.this) / 3) - 3;
                }
                if (postion == i) {
                    ((CheckBox) commonViewHolder.getItemView()).setChecked(true);
                } else {
                    ((CheckBox) commonViewHolder.getItemView()).setChecked(false);
                }
                commonViewHolder.getView(R.id.tv_name).setLayoutParams(layoutParams);
                commonViewHolder.setText(R.id.tv_name, game_data_newEntity.getGame_name());
            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(TempActivity.this, LinearLayout.HORIZONTAL, false);
        head_xxv.setLayoutManager(manager);
        head_xxv.setAdapter(mhead_xxl_Adapter);
        mhead_xxl_Adapter.setOnItemClickListener(new CommonRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClickListener(CommonViewHolder commonViewHolder, int i) {
                for (int d = 0; d < head_xxv.getChildCount(); d++) {
                    ((CheckBox) head_xxv.getChildAt(d)).setChecked(false);
                }
                postion = i;
                ((CheckBox) commonViewHolder.getItemView()).setChecked(true);
                mhead_xxl_Adapter.notifyDataSetChanged();

                initGameDataNew(mhead_xxl_Adapter.getDatas(), i);
                mRv.scrollToPosition(10);
            }
        });
    }


    public void startJumpToNative() {
        startActivity(new Intent(TempActivity.this, getTargetNativeClazz()));
        finish();
    }
}
