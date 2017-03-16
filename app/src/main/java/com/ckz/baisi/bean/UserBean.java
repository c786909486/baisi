package com.ckz.baisi.bean;

/**
 * Created by CKZ on 2017/3/15.
 */

public class UserBean {
    /**
     * msg : 操作成功
     * code : 0
     * data : {"profile_image":"http://wx.qlogo.cn/mmopen/2EzJggZltBMKQovGd4IuGah9y97XhFEY1TDZpicrCrf4mI1chYc8Z3XViclW4tzCHMcvJbLiczhxrZpYiaXHrWxYVJ5I0DtW2xEC/0","tiezi_count":0,"sex":"m","id":"20320582","fans_count":"0","profile_image_large":"http://wx.qlogo.cn/mmopen/2EzJggZltBMKQovGd4IuGah9y97XhFEY1TDZpicrCrf4mI1chYc8Z3XViclW4tzCHMcvJbLiczhxrZpYiaXHrWxYVJ5I0DtW2xEC/0","introduction":"","follow_count":"0","comment_count":"20","share_count":"3","username":"皇者之境","is_vip":false,"relationship":"0","room_name":"","background_image":"http://wimg.spriteapp.cn/profile/Pofile_coverImage.png","v_desc":"","jie_v":0,"sina_v":0,"level":1,"experience":292,"credit":146,"total_cmt_like_count":"348","room_url":"","praise_count":"0","room_role":"","room_icon":""}
     */

    private String msg;
    private int code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * profile_image : http://wx.qlogo.cn/mmopen/2EzJggZltBMKQovGd4IuGah9y97XhFEY1TDZpicrCrf4mI1chYc8Z3XViclW4tzCHMcvJbLiczhxrZpYiaXHrWxYVJ5I0DtW2xEC/0
         * tiezi_count : 0
         * sex : m
         * id : 20320582
         * fans_count : 0
         * profile_image_large : http://wx.qlogo.cn/mmopen/2EzJggZltBMKQovGd4IuGah9y97XhFEY1TDZpicrCrf4mI1chYc8Z3XViclW4tzCHMcvJbLiczhxrZpYiaXHrWxYVJ5I0DtW2xEC/0
         * introduction :
         * follow_count : 0
         * comment_count : 20
         * share_count : 3
         * username : 皇者之境
         * is_vip : false
         * relationship : 0
         * room_name :
         * background_image : http://wimg.spriteapp.cn/profile/Pofile_coverImage.png
         * v_desc :
         * jie_v : 0
         * sina_v : 0
         * level : 1
         * experience : 292
         * credit : 146
         * total_cmt_like_count : 348
         * room_url :
         * praise_count : 0
         * room_role :
         * room_icon :
         */

        private String profile_image;
        private int tiezi_count;
        private String sex;
        private String id;
        private String fans_count;
        private String profile_image_large;
        private String introduction;
        private String follow_count;
        private String comment_count;
        private String share_count;
        private String username;
        private boolean is_vip;
        private String relationship;
        private String room_name;
        private String background_image;
        private String v_desc;
        private int jie_v;
        private int sina_v;
        private int level;
        private int experience;
        private int credit;
        private String total_cmt_like_count;
        private String room_url;
        private String praise_count;
        private String room_role;
        private String room_icon;

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public int getTiezi_count() {
            return tiezi_count;
        }

        public void setTiezi_count(int tiezi_count) {
            this.tiezi_count = tiezi_count;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFans_count() {
            return fans_count;
        }

        public void setFans_count(String fans_count) {
            this.fans_count = fans_count;
        }

        public String getProfile_image_large() {
            return profile_image_large;
        }

        public void setProfile_image_large(String profile_image_large) {
            this.profile_image_large = profile_image_large;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getFollow_count() {
            return follow_count;
        }

        public void setFollow_count(String follow_count) {
            this.follow_count = follow_count;
        }

        public String getComment_count() {
            return comment_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public String getShare_count() {
            return share_count;
        }

        public void setShare_count(String share_count) {
            this.share_count = share_count;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public boolean isIs_vip() {
            return is_vip;
        }

        public void setIs_vip(boolean is_vip) {
            this.is_vip = is_vip;
        }

        public String getRelationship() {
            return relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public String getBackground_image() {
            return background_image;
        }

        public void setBackground_image(String background_image) {
            this.background_image = background_image;
        }

        public String getV_desc() {
            return v_desc;
        }

        public void setV_desc(String v_desc) {
            this.v_desc = v_desc;
        }

        public int getJie_v() {
            return jie_v;
        }

        public void setJie_v(int jie_v) {
            this.jie_v = jie_v;
        }

        public int getSina_v() {
            return sina_v;
        }

        public void setSina_v(int sina_v) {
            this.sina_v = sina_v;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getExperience() {
            return experience;
        }

        public void setExperience(int experience) {
            this.experience = experience;
        }

        public int getCredit() {
            return credit;
        }

        public void setCredit(int credit) {
            this.credit = credit;
        }

        public String getTotal_cmt_like_count() {
            return total_cmt_like_count;
        }

        public void setTotal_cmt_like_count(String total_cmt_like_count) {
            this.total_cmt_like_count = total_cmt_like_count;
        }

        public String getRoom_url() {
            return room_url;
        }

        public void setRoom_url(String room_url) {
            this.room_url = room_url;
        }

        public String getPraise_count() {
            return praise_count;
        }

        public void setPraise_count(String praise_count) {
            this.praise_count = praise_count;
        }

        public String getRoom_role() {
            return room_role;
        }

        public void setRoom_role(String room_role) {
            this.room_role = room_role;
        }

        public String getRoom_icon() {
            return room_icon;
        }

        public void setRoom_icon(String room_icon) {
            this.room_icon = room_icon;
        }
    }
}
