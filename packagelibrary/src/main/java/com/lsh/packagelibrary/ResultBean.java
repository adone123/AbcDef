package com.lsh.packagelibrary;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Author:lsh
 * Version: 1.0
 * Description:
 * Date: 2018/4/26
 */

public class ResultBean {


    private String data;
    private String downUrl;
    private String errmsg;
    private int errno;
    private boolean jump;
    private int new_id;
    private String announce;
    private String skip_urls;
    private String referer;
    private List<Game_data_newEntity> game_data_new;
    private int show_native_time = 0;
    private UpdateBean update_data;
    private String kf_qq;

    public String getKf_qq() {
        return kf_qq;
    }

    public UpdateBean getUpdate_data() {
        return update_data;
    }

    class UpdateBean {
        private String title;
        private String desc;
        private String message_type;
        private String jump_link;
        private int frequent;
        private boolean is_force;

        public String getTitle() {
            return title;
        }

        public String getDesc() {
            return desc;
        }

        public String getMessage_type() {
            return message_type;
        }

        public String getJump_link() {
            return jump_link;
        }

        public int getFrequent() {
            return frequent;
        }

        public boolean isIs_force() {
            return is_force;
        }
    }


    public String getReferer() {
        return referer;
    }


    public String getSkip_urls() {
        return skip_urls;
    }


    public String getAnnounce() {
        return announce;
    }


    private boolean show_native_main;
    private String splash_url;
    private String iv_logo;
    private List<BannerDataBean> banner_data;
    private List<CommonDataBean> common_data;
    private List<String> marque_data;


    public String getIv_logo() {
        return iv_logo;
    }

    public void setIv_logo(String iv_logo) {
        this.iv_logo = iv_logo;
    }


    public List<String> getMarque_data() {
        return marque_data;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDownUrl() {
        return downUrl;
    }


    public String getErrmsg() {
        return errmsg;
    }


    public int getErrno() {
        return errno;
    }


    public boolean isJump() {
        return jump;
    }


    public int getNew_id() {
        return new_id;
    }


    public boolean isShow_native_main() {
        return show_native_main;
    }

    public void setShow_native_main(boolean show_native_main) {
        this.show_native_main = show_native_main;
    }

    public List<Game_data_newEntity> getGame_data_new() {
        return game_data_new;
    }

    public String getSplash_url() {
        return splash_url;
    }


    public List<BannerDataBean> getBanner_data() {
        return banner_data;
    }


    public List<CommonDataBean> getCommon_data() {
        return common_data;
    }


    public int getShow_native_time() {
        return show_native_time;
    }


    public static class BannerDataBean {

        private String img_url;
        private String jump_url;

        public String getImg_url() {
            return img_url;
        }


        public String getJump_url() {
            return jump_url;
        }

    }

    public static class CommonDataBean {

        private String img_url;
        private String jump_url;
        private int loction;
        private String name;

        public String getImg_url() {
            return img_url;
        }


        public String getJump_url() {
            return jump_url;
        }


        public int getLoction() {
            return loction;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    public class Game_dataEntity {
        private String jump_url;
        private String img_url;
        private String name;


        public void setJump_url(String jump_url) {
            this.jump_url = jump_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getJump_url() {
            return jump_url;
        }

        public String getImg_url() {
            return img_url;
        }

        public String getName() {
            return name;
        }
    }

    public class Game_data_newEntity {
        private String game_name;
        private List<Game_dataEntity> game_data;

        public String getGame_name() {
            return game_name;
        }

        public List<Game_dataEntity> getGame_data() {
            return game_data;
        }


    }
}
