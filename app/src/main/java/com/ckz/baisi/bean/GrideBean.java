package com.ckz.baisi.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CKZ on 2017/3/18.
 */

public class GrideBean implements Serializable{



    private InfoBean info;
    private List<TagListBean> tag_list;
    private List<SquareListBean> square_list;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<TagListBean> getTag_list() {
        return tag_list;
    }

    public void setTag_list(List<TagListBean> tag_list) {
        this.tag_list = tag_list;
    }

    public List<SquareListBean> getSquare_list() {
        return square_list;
    }

    public void setSquare_list(List<SquareListBean> square_list) {
        this.square_list = square_list;
    }

    public static class InfoBean  implements Serializable{

        private int count;
        private Object np;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Object getNp() {
            return np;
        }

        public void setNp(Object np) {
            this.np = np;
        }
    }

    public static class TagListBean  implements Serializable{


        private int theme_id;
        private String theme_name;

        public int getTheme_id() {
            return theme_id;
        }

        public void setTheme_id(int theme_id) {
            this.theme_id = theme_id;
        }

        public String getTheme_name() {
            return theme_name;
        }

        public void setTheme_name(String theme_name) {
            this.theme_name = theme_name;
        }
    }

    public static class SquareListBean implements Serializable {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SquareListBean that = (SquareListBean) o;

            return id == that.id;

        }

        @Override
        public int hashCode() {
            return id;
        }

        private String ipad;
        private String iphone;
        private String name;
        private String url;
        private String android;
        private int id;
        private String icon;

        public String getIpad() {
            return ipad;
        }

        public void setIpad(String ipad) {
            this.ipad = ipad;
        }

        public String getIphone() {
            return iphone;
        }

        public void setIphone(String iphone) {
            this.iphone = iphone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAndroid() {
            return android;
        }

        public void setAndroid(String android) {
            this.android = android;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
