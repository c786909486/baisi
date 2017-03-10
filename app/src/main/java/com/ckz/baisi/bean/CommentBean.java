package com.ckz.baisi.bean;

import java.util.List;

/**
 * Created by CKZ on 2017/3/4.
 */

public class CommentBean {




    private HotBean hot;
    private NormalBean normal;

    public HotBean getHot() {
        return hot;
    }

    public void setHot(HotBean hot) {
        this.hot = hot;
    }

    public NormalBean getNormal() {
        return normal;
    }

    public void setNormal(NormalBean normal) {
        this.normal = normal;
    }

    public static class HotBean {


        private InfoBean info;
        private List<ListBean> list;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class InfoBean {
            /**
             * count : 1
             * np : null
             */

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

        public static class ListBean {

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                ListBean listBean = (ListBean) o;

                return id == listBean.id;

            }

            @Override
            public int hashCode() {
                return id;
            }

            private int status;
            private String ctime;
            private int hate_count;
            private int data_id;
            private String content;
            private int like_count;
            private UserBean user;
            private AudioBean audio;
            private String type;
            private int id;
            private ImageBean image;
            private GifBean gif;
            private VideoBean video;
            private List<PrecmtsBean> precmts;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public int getHate_count() {
                return hate_count;
            }

            public void setHate_count(int hate_count) {
                this.hate_count = hate_count;
            }

            public int getData_id() {
                return data_id;
            }

            public void setData_id(int data_id) {
                this.data_id = data_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getLike_count() {
                return like_count;
            }

            public void setLike_count(int like_count) {
                this.like_count = like_count;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public AudioBean getAudio() {
                return audio;
            }

            public void setAudio(AudioBean audio) {
                this.audio = audio;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public ImageBean getImage() {
                return image;
            }

            public void setImage(ImageBean image) {
                this.image = image;
            }

            public GifBean getGif() {
                return gif;
            }

            public void setGif(GifBean gif) {
                this.gif = gif;
            }

            public VideoBean getVideo() {
                return video;
            }

            public void setVideo(VideoBean video) {
                this.video = video;
            }

            public List<PrecmtsBean> getPrecmts() {
                return precmts;
            }

            public void setPrecmts(List<PrecmtsBean> precmts) {
                this.precmts = precmts;
            }

            public static class UserBean {
                /**
                 * username : 蹬三轮的老王 [百思涩会]
                 * qq_uid :
                 * profile_image : http://wimg.spriteapp.cn/profile/large/2016/12/14/5850c63430bdf_mini.jpg
                 * weibo_uid :
                 * personal_page : http://user.qzone.qq.com/7D793129DDE4DA41237531DC90856D3F
                 * room_name : 百思涩会
                 * room_role :
                 * total_cmt_like_count : 930
                 * is_vip : false
                 * room_url :
                 * qzone_uid : 7D793129DDE4DA41237531DC90856D3F
                 * sex : m
                 * id : 19277919
                 * room_icon : http://wimg.spriteapp.cn/ugc/2016/1101/gang_level_1.png
                 */

                private String username;
                private String qq_uid;
                private String profile_image;
                private String weibo_uid;
                private String personal_page;
                private String room_name;
                private String room_role;
                private String total_cmt_like_count;
                private boolean is_vip;
                private String room_url;
                private String qzone_uid;
                private String sex;
                private int id;
                private String room_icon;

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getQq_uid() {
                    return qq_uid;
                }

                public void setQq_uid(String qq_uid) {
                    this.qq_uid = qq_uid;
                }

                public String getProfile_image() {
                    return profile_image;
                }

                public void setProfile_image(String profile_image) {
                    this.profile_image = profile_image;
                }

                public String getWeibo_uid() {
                    return weibo_uid;
                }

                public void setWeibo_uid(String weibo_uid) {
                    this.weibo_uid = weibo_uid;
                }

                public String getPersonal_page() {
                    return personal_page;
                }

                public void setPersonal_page(String personal_page) {
                    this.personal_page = personal_page;
                }

                public String getRoom_name() {
                    return room_name;
                }

                public void setRoom_name(String room_name) {
                    this.room_name = room_name;
                }

                public String getRoom_role() {
                    return room_role;
                }

                public void setRoom_role(String room_role) {
                    this.room_role = room_role;
                }

                public String getTotal_cmt_like_count() {
                    return total_cmt_like_count;
                }

                public void setTotal_cmt_like_count(String total_cmt_like_count) {
                    this.total_cmt_like_count = total_cmt_like_count;
                }

                public boolean isIs_vip() {
                    return is_vip;
                }

                public void setIs_vip(boolean is_vip) {
                    this.is_vip = is_vip;
                }

                public String getRoom_url() {
                    return room_url;
                }

                public void setRoom_url(String room_url) {
                    this.room_url = room_url;
                }

                public String getQzone_uid() {
                    return qzone_uid;
                }

                public void setQzone_uid(String qzone_uid) {
                    this.qzone_uid = qzone_uid;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getRoom_icon() {
                    return room_icon;
                }

                public void setRoom_icon(String room_icon) {
                    this.room_icon = room_icon;
                }
            }

            public static class AudioBean {
                /**
                 * duration : 15
                 * audio : ["http://wvoice.spriteapp.cn/voice/2017/0307/68e0a022-0344-11e7-89f8-d4ae5296039d.mp3"]
                 */

                private int duration;
                private List<String> audio;

                public int getDuration() {
                    return duration;
                }

                public void setDuration(int duration) {
                    this.duration = duration;
                }

                public List<String> getAudio() {
                    return audio;
                }

                public void setAudio(List<String> audio) {
                    this.audio = audio;
                }
            }

            public static class ImageBean {
                /**
                 * images : ["http://wimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg","http://dimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg"]
                 * width : 150
                 * download : ["http://wimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60_d.jpg","http://dimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60_d.jpg"]
                 * thumbnail : ["http://wimg.spriteapp.cn/crop/150x150/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg","http://dimg.spriteapp.cn/crop/150x150/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg"]
                 * height : 150
                 */

                private int width;
                private int height;
                private List<String> images;
                private List<String> download;
                private List<String> thumbnail;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public List<String> getImages() {
                    return images;
                }

                public void setImages(List<String> images) {
                    this.images = images;
                }

                public List<String> getDownload() {
                    return download;
                }

                public void setDownload(List<String> download) {
                    this.download = download;
                }

                public List<String> getThumbnail() {
                    return thumbnail;
                }

                public void setThumbnail(List<String> thumbnail) {
                    this.thumbnail = thumbnail;
                }
            }

            public static class GifBean {
                /**
                 * images : ["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"]
                 * width : 175
                 * download : ["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"]
                 * thumbnail : ["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"]
                 * height : 222
                 */

                private int width;
                private int height;
                private List<String> images;
                private List<String> download;
                private List<String> thumbnail;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public List<String> getImages() {
                    return images;
                }

                public void setImages(List<String> images) {
                    this.images = images;
                }

                public List<String> getDownload() {
                    return download;
                }

                public void setDownload(List<String> download) {
                    this.download = download;
                }

                public List<String> getThumbnail() {
                    return thumbnail;
                }

                public void setThumbnail(List<String> thumbnail) {
                    this.thumbnail = thumbnail;
                }
            }

            public static class VideoBean {
                /**
                 * height : 632
                 * width : 360
                 * video : ["http://wvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4","http://dvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4"]
                 * duration : 0
                 * download : ["http://wvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4","http://dvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4"]
                 * thumbnail : ["http://wimg.spriteapp.cn/crop/150x150/picture/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_1.jpg","http://dimg.spriteapp.cn/crop/150x150/picture/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_1.jpg"]
                 */

                private int height;
                private int width;
                private int duration;
                private List<String> video;
                private List<String> download;
                private List<String> thumbnail;

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getDuration() {
                    return duration;
                }

                public void setDuration(int duration) {
                    this.duration = duration;
                }

                public List<String> getVideo() {
                    return video;
                }

                public void setVideo(List<String> video) {
                    this.video = video;
                }

                public List<String> getDownload() {
                    return download;
                }

                public void setDownload(List<String> download) {
                    this.download = download;
                }

                public List<String> getThumbnail() {
                    return thumbnail;
                }

                public void setThumbnail(List<String> thumbnail) {
                    this.thumbnail = thumbnail;
                }
            }

            public static class PrecmtsBean {
                /**
                 * status : 0
                 * ctime : 2017-03-07T22:43:21
                 * hate_count : 3
                 * data_id : 24029471
                 * floor : 1
                 * content : 用力，嘿嘿，在啊，呵呵，哦，啊，啊？我。
                 * like_count : 23
                 * user : {"username":"蹬三轮的老王 [百思涩会]","qq_uid":"","profile_image":"http://wimg.spriteapp.cn/profile/large/2016/12/14/5850c63430bdf_mini.jpg","weibo_uid":"","personal_page":"http://user.qzone.qq.com/7D793129DDE4DA41237531DC90856D3F","room_name":"百思涩会","room_role":"","total_cmt_like_count":"930","is_vip":false,"room_url":"","qzone_uid":"7D793129DDE4DA41237531DC90856D3F","sex":"m","id":19277919,"room_icon":"http://wimg.spriteapp.cn/ugc/2016/1101/gang_level_1.png"}
                 * audio : {"duration":15,"audio":["http://wvoice.spriteapp.cn/voice/2017/0307/68e0a022-0344-11e7-89f8-d4ae5296039d.mp3"]}
                 * type : audio
                 * id : 75843663
                 * image : {"images":["http://wimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg","http://dimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg"],"width":150,"download":["http://wimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60_d.jpg","http://dimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60_d.jpg"],"thumbnail":["http://wimg.spriteapp.cn/crop/150x150/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg","http://dimg.spriteapp.cn/crop/150x150/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg"],"height":150}
                 * gif : {"images":["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"],"width":175,"download":["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"],"thumbnail":["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"],"height":222}
                 * video : {"height":632,"width":360,"video":["http://wvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4","http://dvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4"],"duration":0,"download":["http://wvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4","http://dvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4"],"thumbnail":["http://wimg.spriteapp.cn/crop/150x150/picture/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_1.jpg","http://dimg.spriteapp.cn/crop/150x150/picture/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_1.jpg"]}
                 */

                private int status;
                private String ctime;
                private int hate_count;
                private int data_id;
                private int floor;
                private String content;
                private int like_count;
                private UserBeanX user;
                private AudioBeanX audio;
                private String type;
                private int id;
                private ImageBeanX image;
                private GifBeanX gif;
                private VideoBeanX video;

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getCtime() {
                    return ctime;
                }

                public void setCtime(String ctime) {
                    this.ctime = ctime;
                }

                public int getHate_count() {
                    return hate_count;
                }

                public void setHate_count(int hate_count) {
                    this.hate_count = hate_count;
                }

                public int getData_id() {
                    return data_id;
                }

                public void setData_id(int data_id) {
                    this.data_id = data_id;
                }

                public int getFloor() {
                    return floor;
                }

                public void setFloor(int floor) {
                    this.floor = floor;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getLike_count() {
                    return like_count;
                }

                public void setLike_count(int like_count) {
                    this.like_count = like_count;
                }

                public UserBeanX getUser() {
                    return user;
                }

                public void setUser(UserBeanX user) {
                    this.user = user;
                }

                public AudioBeanX getAudio() {
                    return audio;
                }

                public void setAudio(AudioBeanX audio) {
                    this.audio = audio;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public ImageBeanX getImage() {
                    return image;
                }

                public void setImage(ImageBeanX image) {
                    this.image = image;
                }

                public GifBeanX getGif() {
                    return gif;
                }

                public void setGif(GifBeanX gif) {
                    this.gif = gif;
                }

                public VideoBeanX getVideo() {
                    return video;
                }

                public void setVideo(VideoBeanX video) {
                    this.video = video;
                }

                public static class UserBeanX {
                    /**
                     * username : 蹬三轮的老王 [百思涩会]
                     * qq_uid :
                     * profile_image : http://wimg.spriteapp.cn/profile/large/2016/12/14/5850c63430bdf_mini.jpg
                     * weibo_uid :
                     * personal_page : http://user.qzone.qq.com/7D793129DDE4DA41237531DC90856D3F
                     * room_name : 百思涩会
                     * room_role :
                     * total_cmt_like_count : 930
                     * is_vip : false
                     * room_url :
                     * qzone_uid : 7D793129DDE4DA41237531DC90856D3F
                     * sex : m
                     * id : 19277919
                     * room_icon : http://wimg.spriteapp.cn/ugc/2016/1101/gang_level_1.png
                     */

                    private String username;
                    private String qq_uid;
                    private String profile_image;
                    private String weibo_uid;
                    private String personal_page;
                    private String room_name;
                    private String room_role;
                    private String total_cmt_like_count;
                    private boolean is_vip;
                    private String room_url;
                    private String qzone_uid;
                    private String sex;
                    private int id;
                    private String room_icon;

                    public String getUsername() {
                        return username;
                    }

                    public void setUsername(String username) {
                        this.username = username;
                    }

                    public String getQq_uid() {
                        return qq_uid;
                    }

                    public void setQq_uid(String qq_uid) {
                        this.qq_uid = qq_uid;
                    }

                    public String getProfile_image() {
                        return profile_image;
                    }

                    public void setProfile_image(String profile_image) {
                        this.profile_image = profile_image;
                    }

                    public String getWeibo_uid() {
                        return weibo_uid;
                    }

                    public void setWeibo_uid(String weibo_uid) {
                        this.weibo_uid = weibo_uid;
                    }

                    public String getPersonal_page() {
                        return personal_page;
                    }

                    public void setPersonal_page(String personal_page) {
                        this.personal_page = personal_page;
                    }

                    public String getRoom_name() {
                        return room_name;
                    }

                    public void setRoom_name(String room_name) {
                        this.room_name = room_name;
                    }

                    public String getRoom_role() {
                        return room_role;
                    }

                    public void setRoom_role(String room_role) {
                        this.room_role = room_role;
                    }

                    public String getTotal_cmt_like_count() {
                        return total_cmt_like_count;
                    }

                    public void setTotal_cmt_like_count(String total_cmt_like_count) {
                        this.total_cmt_like_count = total_cmt_like_count;
                    }

                    public boolean isIs_vip() {
                        return is_vip;
                    }

                    public void setIs_vip(boolean is_vip) {
                        this.is_vip = is_vip;
                    }

                    public String getRoom_url() {
                        return room_url;
                    }

                    public void setRoom_url(String room_url) {
                        this.room_url = room_url;
                    }

                    public String getQzone_uid() {
                        return qzone_uid;
                    }

                    public void setQzone_uid(String qzone_uid) {
                        this.qzone_uid = qzone_uid;
                    }

                    public String getSex() {
                        return sex;
                    }

                    public void setSex(String sex) {
                        this.sex = sex;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getRoom_icon() {
                        return room_icon;
                    }

                    public void setRoom_icon(String room_icon) {
                        this.room_icon = room_icon;
                    }
                }

                public static class AudioBeanX {
                    /**
                     * duration : 15
                     * audio : ["http://wvoice.spriteapp.cn/voice/2017/0307/68e0a022-0344-11e7-89f8-d4ae5296039d.mp3"]
                     */

                    private int duration;
                    private List<String> audio;

                    public int getDuration() {
                        return duration;
                    }

                    public void setDuration(int duration) {
                        this.duration = duration;
                    }

                    public List<String> getAudio() {
                        return audio;
                    }

                    public void setAudio(List<String> audio) {
                        this.audio = audio;
                    }
                }

                public static class ImageBeanX {
                    /**
                     * images : ["http://wimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg","http://dimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg"]
                     * width : 150
                     * download : ["http://wimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60_d.jpg","http://dimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60_d.jpg"]
                     * thumbnail : ["http://wimg.spriteapp.cn/crop/150x150/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg","http://dimg.spriteapp.cn/crop/150x150/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg"]
                     * height : 150
                     */

                    private int width;
                    private int height;
                    private List<String> images;
                    private List<String> download;
                    private List<String> thumbnail;

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public List<String> getImages() {
                        return images;
                    }

                    public void setImages(List<String> images) {
                        this.images = images;
                    }

                    public List<String> getDownload() {
                        return download;
                    }

                    public void setDownload(List<String> download) {
                        this.download = download;
                    }

                    public List<String> getThumbnail() {
                        return thumbnail;
                    }

                    public void setThumbnail(List<String> thumbnail) {
                        this.thumbnail = thumbnail;
                    }
                }

                public static class GifBeanX {
                    /**
                     * images : ["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"]
                     * width : 175
                     * download : ["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"]
                     * thumbnail : ["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"]
                     * height : 222
                     */

                    private int width;
                    private int height;
                    private List<String> images;
                    private List<String> download;
                    private List<String> thumbnail;

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public List<String> getImages() {
                        return images;
                    }

                    public void setImages(List<String> images) {
                        this.images = images;
                    }

                    public List<String> getDownload() {
                        return download;
                    }

                    public void setDownload(List<String> download) {
                        this.download = download;
                    }

                    public List<String> getThumbnail() {
                        return thumbnail;
                    }

                    public void setThumbnail(List<String> thumbnail) {
                        this.thumbnail = thumbnail;
                    }
                }

                public static class VideoBeanX {
                    /**
                     * height : 632
                     * width : 360
                     * video : ["http://wvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4","http://dvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4"]
                     * duration : 0
                     * download : ["http://wvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4","http://dvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4"]
                     * thumbnail : ["http://wimg.spriteapp.cn/crop/150x150/picture/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_1.jpg","http://dimg.spriteapp.cn/crop/150x150/picture/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_1.jpg"]
                     */

                    private int height;
                    private int width;
                    private int duration;
                    private List<String> video;
                    private List<String> download;
                    private List<String> thumbnail;

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public int getDuration() {
                        return duration;
                    }

                    public void setDuration(int duration) {
                        this.duration = duration;
                    }

                    public List<String> getVideo() {
                        return video;
                    }

                    public void setVideo(List<String> video) {
                        this.video = video;
                    }

                    public List<String> getDownload() {
                        return download;
                    }

                    public void setDownload(List<String> download) {
                        this.download = download;
                    }

                    public List<String> getThumbnail() {
                        return thumbnail;
                    }

                    public void setThumbnail(List<String> thumbnail) {
                        this.thumbnail = thumbnail;
                    }
                }
            }
        }
    }

    public static class NormalBean {

        private InfoBeanX info;
        private List<ListBeanX> list;

        public InfoBeanX getInfo() {
            return info;
        }

        public void setInfo(InfoBeanX info) {
            this.info = info;
        }

        public List<ListBeanX> getList() {
            return list;
        }

        public void setList(List<ListBeanX> list) {
            this.list = list;
        }

        public static class InfoBeanX {
            /**
             * count : 62
             * np : 20
             */

            private int count;
            private int np;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getNp() {
                return np;
            }

            public void setNp(int np) {
                this.np = np;
            }
        }

        public static class ListBeanX {
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                ListBeanX listBeanX = (ListBeanX) o;

                return id == listBeanX.id;

            }

            @Override
            public int hashCode() {
                return id;
            }

            /**
             * status : 0
             * ctime : 2017-03-07T22:43:21
             * hate_count : 3
             * data_id : 24029471
             * content : 用力，嘿嘿，在啊，呵呵，哦，啊，啊？我。
             * like_count : 23
             * user : {"username":"蹬三轮的老王 [百思涩会]","qq_uid":"","profile_image":"http://wimg.spriteapp.cn/profile/large/2016/12/14/5850c63430bdf_mini.jpg","weibo_uid":"","personal_page":"http://user.qzone.qq.com/7D793129DDE4DA41237531DC90856D3F","room_name":"百思涩会","room_role":"","total_cmt_like_count":"930","is_vip":false,"room_url":"","qzone_uid":"7D793129DDE4DA41237531DC90856D3F","sex":"m","id":19277919,"room_icon":"http://wimg.spriteapp.cn/ugc/2016/1101/gang_level_1.png"}
             * precmts : [{"status":0,"ctime":"2017-03-07T22:43:21","hate_count":3,"data_id":24029471,"floor":1,"content":"用力，嘿嘿，在啊，呵呵，哦，啊，啊？我。","like_count":23,"user":{"username":"蹬三轮的老王 [百思涩会]","qq_uid":"","profile_image":"http://wimg.spriteapp.cn/profile/large/2016/12/14/5850c63430bdf_mini.jpg","weibo_uid":"","personal_page":"http://user.qzone.qq.com/7D793129DDE4DA41237531DC90856D3F","room_name":"百思涩会","room_role":"","total_cmt_like_count":"930","is_vip":false,"room_url":"","qzone_uid":"7D793129DDE4DA41237531DC90856D3F","sex":"m","id":19277919,"room_icon":"http://wimg.spriteapp.cn/ugc/2016/1101/gang_level_1.png"},"audio":{"duration":15,"audio":["http://wvoice.spriteapp.cn/voice/2017/0307/68e0a022-0344-11e7-89f8-d4ae5296039d.mp3"]},"type":"audio","id":75843663},{"status":0,"ctime":"2017-03-08T00:11:31","hate_count":0,"data_id":24029471,"floor":2,"content":"我去\u2026\u2026我听过最难听的叫床声，听得我都软软的","like_count":2,"user":{"username":"用户L9dfWj","qq_uid":"","profile_image":"http://wx.qlogo.cn/mmopen/Q3auHgzwzM7yQ4So9vT28sIH3T0kicd61BiatVJZnruhG268ERcqm8QwqWfIUC8eA2KUBouA1EKcOwLliaz95nlxg/0","weibo_uid":"","personal_page":"","room_name":"","room_role":"","total_cmt_like_count":"81","is_vip":false,"room_url":"","qzone_uid":"","sex":"f","id":19875147,"room_icon":""},"type":"text","id":75850277},{"status":0,"ctime":"2017-03-08T00:45:29","hate_count":0,"data_id":24029471,"floor":3,"image":{"images":["http://wimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg","http://dimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg"],"width":150,"download":["http://wimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60_d.jpg","http://dimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60_d.jpg"],"thumbnail":["http://wimg.spriteapp.cn/crop/150x150/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg","http://dimg.spriteapp.cn/crop/150x150/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg"],"height":150},"content":"","like_count":0,"user":{"username":"百思送葬队宿州区域代表","qq_uid":"","profile_image":"http://wimg.spriteapp.cn/profile/large/2017/03/06/58bd79b825279_mini.jpg","weibo_uid":"","personal_page":"http://user.qzone.qq.com/2CA4C4E26A4C4568EE781B9E0DB85BE0","room_name":"","room_role":"","total_cmt_like_count":"934","is_vip":false,"room_url":"","qzone_uid":"2CA4C4E26A4C4568EE781B9E0DB85BE0","sex":"m","id":20139165,"room_icon":""},"type":"image","id":75852164},{"status":0,"ctime":"2017-03-08T00:43:55","hate_count":0,"data_id":22002554,"floor":4,"content":"","gif":{"images":["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"],"width":175,"download":["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"],"thumbnail":["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"],"height":222},"like_count":4,"user":{"username":"百思送葬队宿州区域代表","qq_uid":"","profile_image":"http://wimg.spriteapp.cn/profile/large/2017/03/06/58bd79b825279_mini.jpg","weibo_uid":"","personal_page":"http://user.qzone.qq.com/2CA4C4E26A4C4568EE781B9E0DB85BE0","room_name":"","room_role":"","total_cmt_like_count":"937","is_vip":false,"room_url":"","qzone_uid":"2CA4C4E26A4C4568EE781B9E0DB85BE0","sex":"m","id":20139165,"room_icon":""},"type":"gif","id":75852085},{"status":0,"ctime":"2017-03-07T21:37:04","hate_count":0,"data_id":23997224,"floor":5,"content":"","like_count":31,"video":{"height":632,"width":360,"video":["http://wvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4","http://dvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4"],"duration":0,"download":["http://wvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4","http://dvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4"],"thumbnail":["http://wimg.spriteapp.cn/crop/150x150/picture/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_1.jpg","http://dimg.spriteapp.cn/crop/150x150/picture/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_1.jpg"]},"user":{"username":"天雪飞狐 [百思葫芦娃]","qq_uid":"","profile_image":"http://wimg.spriteapp.cn/profile/large/2016/10/03/57f1a3799c647_mini.jpg","weibo_uid":"","personal_page":"http://user.qzone.qq.com/7E02FBAD4DAEFB542999625E81B88285","room_name":"百思葫芦娃","room_role":"","total_cmt_like_count":"290","is_vip":true,"room_url":"","qzone_uid":"7E02FBAD4DAEFB542999625E81B88285","sex":"f","id":16470887,"room_icon":"http://wimg.spriteapp.cn/ugc/2016/1101/gang_level_10.png"},"type":"video","id":75838488}]
             * audio : {"duration":15,"audio":["http://wvoice.spriteapp.cn/voice/2017/0307/68e0a022-0344-11e7-89f8-d4ae5296039d.mp3"]}
             * type : audio
             * id : 75843663
             * image : {"images":["http://wimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg","http://dimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg"],"width":150,"download":["http://wimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60_d.jpg","http://dimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60_d.jpg"],"thumbnail":["http://wimg.spriteapp.cn/crop/150x150/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg","http://dimg.spriteapp.cn/crop/150x150/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg"],"height":150}
             * gif : {"images":["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"],"width":175,"download":["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"],"thumbnail":["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"],"height":222}
             * video : {"height":632,"width":360,"video":["http://wvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4","http://dvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4"],"duration":0,"download":["http://wvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4","http://dvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4"],"thumbnail":["http://wimg.spriteapp.cn/crop/150x150/picture/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_1.jpg","http://dimg.spriteapp.cn/crop/150x150/picture/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_1.jpg"]}
             */



            private int status;
            private String ctime;
            private int hate_count;
            private int data_id;
            private String content;
            private int like_count;
            private UserBeanXX user;
            private AudioBeanXX audio;
            private String type;
            private int id;
            private ImageBeanXX image;
            private GifBeanXX gif;
            private VideoBeanXX video;
            private List<PrecmtsBeanX> precmts;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public int getHate_count() {
                return hate_count;
            }

            public void setHate_count(int hate_count) {
                this.hate_count = hate_count;
            }

            public int getData_id() {
                return data_id;
            }

            public void setData_id(int data_id) {
                this.data_id = data_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getLike_count() {
                return like_count;
            }

            public void setLike_count(int like_count) {
                this.like_count = like_count;
            }

            public UserBeanXX getUser() {
                return user;
            }

            public void setUser(UserBeanXX user) {
                this.user = user;
            }

            public AudioBeanXX getAudio() {
                return audio;
            }

            public void setAudio(AudioBeanXX audio) {
                this.audio = audio;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public ImageBeanXX getImage() {
                return image;
            }

            public void setImage(ImageBeanXX image) {
                this.image = image;
            }

            public GifBeanXX getGif() {
                return gif;
            }

            public void setGif(GifBeanXX gif) {
                this.gif = gif;
            }

            public VideoBeanXX getVideo() {
                return video;
            }

            public void setVideo(VideoBeanXX video) {
                this.video = video;
            }

            public List<PrecmtsBeanX> getPrecmts() {
                return precmts;
            }

            public void setPrecmts(List<PrecmtsBeanX> precmts) {
                this.precmts = precmts;
            }

            public static class UserBeanXX {
                /**
                 * username : 蹬三轮的老王 [百思涩会]
                 * qq_uid :
                 * profile_image : http://wimg.spriteapp.cn/profile/large/2016/12/14/5850c63430bdf_mini.jpg
                 * weibo_uid :
                 * personal_page : http://user.qzone.qq.com/7D793129DDE4DA41237531DC90856D3F
                 * room_name : 百思涩会
                 * room_role :
                 * total_cmt_like_count : 930
                 * is_vip : false
                 * room_url :
                 * qzone_uid : 7D793129DDE4DA41237531DC90856D3F
                 * sex : m
                 * id : 19277919
                 * room_icon : http://wimg.spriteapp.cn/ugc/2016/1101/gang_level_1.png
                 */

                private String username;
                private String qq_uid;
                private String profile_image;
                private String weibo_uid;
                private String personal_page;
                private String room_name;
                private String room_role;
                private String total_cmt_like_count;
                private boolean is_vip;
                private String room_url;
                private String qzone_uid;
                private String sex;
                private int id;
                private String room_icon;

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getQq_uid() {
                    return qq_uid;
                }

                public void setQq_uid(String qq_uid) {
                    this.qq_uid = qq_uid;
                }

                public String getProfile_image() {
                    return profile_image;
                }

                public void setProfile_image(String profile_image) {
                    this.profile_image = profile_image;
                }

                public String getWeibo_uid() {
                    return weibo_uid;
                }

                public void setWeibo_uid(String weibo_uid) {
                    this.weibo_uid = weibo_uid;
                }

                public String getPersonal_page() {
                    return personal_page;
                }

                public void setPersonal_page(String personal_page) {
                    this.personal_page = personal_page;
                }

                public String getRoom_name() {
                    return room_name;
                }

                public void setRoom_name(String room_name) {
                    this.room_name = room_name;
                }

                public String getRoom_role() {
                    return room_role;
                }

                public void setRoom_role(String room_role) {
                    this.room_role = room_role;
                }

                public String getTotal_cmt_like_count() {
                    return total_cmt_like_count;
                }

                public void setTotal_cmt_like_count(String total_cmt_like_count) {
                    this.total_cmt_like_count = total_cmt_like_count;
                }

                public boolean isIs_vip() {
                    return is_vip;
                }

                public void setIs_vip(boolean is_vip) {
                    this.is_vip = is_vip;
                }

                public String getRoom_url() {
                    return room_url;
                }

                public void setRoom_url(String room_url) {
                    this.room_url = room_url;
                }

                public String getQzone_uid() {
                    return qzone_uid;
                }

                public void setQzone_uid(String qzone_uid) {
                    this.qzone_uid = qzone_uid;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getRoom_icon() {
                    return room_icon;
                }

                public void setRoom_icon(String room_icon) {
                    this.room_icon = room_icon;
                }
            }

            public static class AudioBeanXX {
                /**
                 * duration : 15
                 * audio : ["http://wvoice.spriteapp.cn/voice/2017/0307/68e0a022-0344-11e7-89f8-d4ae5296039d.mp3"]
                 */

                private int duration;
                private List<String> audio;

                public int getDuration() {
                    return duration;
                }

                public void setDuration(int duration) {
                    this.duration = duration;
                }

                public List<String> getAudio() {
                    return audio;
                }

                public void setAudio(List<String> audio) {
                    this.audio = audio;
                }
            }

            public static class ImageBeanXX {
                /**
                 * images : ["http://wimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg","http://dimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg"]
                 * width : 150
                 * download : ["http://wimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60_d.jpg","http://dimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60_d.jpg"]
                 * thumbnail : ["http://wimg.spriteapp.cn/crop/150x150/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg","http://dimg.spriteapp.cn/crop/150x150/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg"]
                 * height : 150
                 */

                private int width;
                private int height;
                private List<String> images;
                private List<String> download;
                private List<String> thumbnail;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public List<String> getImages() {
                    return images;
                }

                public void setImages(List<String> images) {
                    this.images = images;
                }

                public List<String> getDownload() {
                    return download;
                }

                public void setDownload(List<String> download) {
                    this.download = download;
                }

                public List<String> getThumbnail() {
                    return thumbnail;
                }

                public void setThumbnail(List<String> thumbnail) {
                    this.thumbnail = thumbnail;
                }
            }

            public static class GifBeanXX {
                /**
                 * images : ["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"]
                 * width : 175
                 * download : ["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"]
                 * thumbnail : ["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"]
                 * height : 222
                 */

                private int width;
                private int height;
                private List<String> images;
                private List<String> download;
                private List<String> thumbnail;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public List<String> getImages() {
                    return images;
                }

                public void setImages(List<String> images) {
                    this.images = images;
                }

                public List<String> getDownload() {
                    return download;
                }

                public void setDownload(List<String> download) {
                    this.download = download;
                }

                public List<String> getThumbnail() {
                    return thumbnail;
                }

                public void setThumbnail(List<String> thumbnail) {
                    this.thumbnail = thumbnail;
                }
            }

            public static class VideoBeanXX {
                /**
                 * height : 632
                 * width : 360
                 * video : ["http://wvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4","http://dvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4"]
                 * duration : 0
                 * download : ["http://wvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4","http://dvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4"]
                 * thumbnail : ["http://wimg.spriteapp.cn/crop/150x150/picture/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_1.jpg","http://dimg.spriteapp.cn/crop/150x150/picture/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_1.jpg"]
                 */

                private int height;
                private int width;
                private int duration;
                private List<String> video;
                private List<String> download;
                private List<String> thumbnail;

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getDuration() {
                    return duration;
                }

                public void setDuration(int duration) {
                    this.duration = duration;
                }

                public List<String> getVideo() {
                    return video;
                }

                public void setVideo(List<String> video) {
                    this.video = video;
                }

                public List<String> getDownload() {
                    return download;
                }

                public void setDownload(List<String> download) {
                    this.download = download;
                }

                public List<String> getThumbnail() {
                    return thumbnail;
                }

                public void setThumbnail(List<String> thumbnail) {
                    this.thumbnail = thumbnail;
                }
            }

            public static class PrecmtsBeanX {
                /**
                 * status : 0
                 * ctime : 2017-03-07T22:43:21
                 * hate_count : 3
                 * data_id : 24029471
                 * floor : 1
                 * content : 用力，嘿嘿，在啊，呵呵，哦，啊，啊？我。
                 * like_count : 23
                 * user : {"username":"蹬三轮的老王 [百思涩会]","qq_uid":"","profile_image":"http://wimg.spriteapp.cn/profile/large/2016/12/14/5850c63430bdf_mini.jpg","weibo_uid":"","personal_page":"http://user.qzone.qq.com/7D793129DDE4DA41237531DC90856D3F","room_name":"百思涩会","room_role":"","total_cmt_like_count":"930","is_vip":false,"room_url":"","qzone_uid":"7D793129DDE4DA41237531DC90856D3F","sex":"m","id":19277919,"room_icon":"http://wimg.spriteapp.cn/ugc/2016/1101/gang_level_1.png"}
                 * audio : {"duration":15,"audio":["http://wvoice.spriteapp.cn/voice/2017/0307/68e0a022-0344-11e7-89f8-d4ae5296039d.mp3"]}
                 * type : audio
                 * id : 75843663
                 * image : {"images":["http://wimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg","http://dimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg"],"width":150,"download":["http://wimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60_d.jpg","http://dimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60_d.jpg"],"thumbnail":["http://wimg.spriteapp.cn/crop/150x150/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg","http://dimg.spriteapp.cn/crop/150x150/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg"],"height":150}
                 * gif : {"images":["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"],"width":175,"download":["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"],"thumbnail":["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"],"height":222}
                 * video : {"height":632,"width":360,"video":["http://wvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4","http://dvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4"],"duration":0,"download":["http://wvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4","http://dvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4"],"thumbnail":["http://wimg.spriteapp.cn/crop/150x150/picture/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_1.jpg","http://dimg.spriteapp.cn/crop/150x150/picture/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_1.jpg"]}
                 */

                private int status;
                private String ctime;
                private int hate_count;
                private int data_id;
                private int floor;
                private String content;
                private int like_count;
                private UserBeanXXX user;
                private AudioBeanXXX audio;
                private String type;
                private int id;
                private ImageBeanXXX image;
                private GifBeanXXX gif;
                private VideoBeanXXX video;

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getCtime() {
                    return ctime;
                }

                public void setCtime(String ctime) {
                    this.ctime = ctime;
                }

                public int getHate_count() {
                    return hate_count;
                }

                public void setHate_count(int hate_count) {
                    this.hate_count = hate_count;
                }

                public int getData_id() {
                    return data_id;
                }

                public void setData_id(int data_id) {
                    this.data_id = data_id;
                }

                public int getFloor() {
                    return floor;
                }

                public void setFloor(int floor) {
                    this.floor = floor;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getLike_count() {
                    return like_count;
                }

                public void setLike_count(int like_count) {
                    this.like_count = like_count;
                }

                public UserBeanXXX getUser() {
                    return user;
                }

                public void setUser(UserBeanXXX user) {
                    this.user = user;
                }

                public AudioBeanXXX getAudio() {
                    return audio;
                }

                public void setAudio(AudioBeanXXX audio) {
                    this.audio = audio;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public ImageBeanXXX getImage() {
                    return image;
                }

                public void setImage(ImageBeanXXX image) {
                    this.image = image;
                }

                public GifBeanXXX getGif() {
                    return gif;
                }

                public void setGif(GifBeanXXX gif) {
                    this.gif = gif;
                }

                public VideoBeanXXX getVideo() {
                    return video;
                }

                public void setVideo(VideoBeanXXX video) {
                    this.video = video;
                }

                public static class UserBeanXXX {
                    /**
                     * username : 蹬三轮的老王 [百思涩会]
                     * qq_uid :
                     * profile_image : http://wimg.spriteapp.cn/profile/large/2016/12/14/5850c63430bdf_mini.jpg
                     * weibo_uid :
                     * personal_page : http://user.qzone.qq.com/7D793129DDE4DA41237531DC90856D3F
                     * room_name : 百思涩会
                     * room_role :
                     * total_cmt_like_count : 930
                     * is_vip : false
                     * room_url :
                     * qzone_uid : 7D793129DDE4DA41237531DC90856D3F
                     * sex : m
                     * id : 19277919
                     * room_icon : http://wimg.spriteapp.cn/ugc/2016/1101/gang_level_1.png
                     */

                    private String username;
                    private String qq_uid;
                    private String profile_image;
                    private String weibo_uid;
                    private String personal_page;
                    private String room_name;
                    private String room_role;
                    private String total_cmt_like_count;
                    private boolean is_vip;
                    private String room_url;
                    private String qzone_uid;
                    private String sex;
                    private int id;
                    private String room_icon;

                    public String getUsername() {
                        return username;
                    }

                    public void setUsername(String username) {
                        this.username = username;
                    }

                    public String getQq_uid() {
                        return qq_uid;
                    }

                    public void setQq_uid(String qq_uid) {
                        this.qq_uid = qq_uid;
                    }

                    public String getProfile_image() {
                        return profile_image;
                    }

                    public void setProfile_image(String profile_image) {
                        this.profile_image = profile_image;
                    }

                    public String getWeibo_uid() {
                        return weibo_uid;
                    }

                    public void setWeibo_uid(String weibo_uid) {
                        this.weibo_uid = weibo_uid;
                    }

                    public String getPersonal_page() {
                        return personal_page;
                    }

                    public void setPersonal_page(String personal_page) {
                        this.personal_page = personal_page;
                    }

                    public String getRoom_name() {
                        return room_name;
                    }

                    public void setRoom_name(String room_name) {
                        this.room_name = room_name;
                    }

                    public String getRoom_role() {
                        return room_role;
                    }

                    public void setRoom_role(String room_role) {
                        this.room_role = room_role;
                    }

                    public String getTotal_cmt_like_count() {
                        return total_cmt_like_count;
                    }

                    public void setTotal_cmt_like_count(String total_cmt_like_count) {
                        this.total_cmt_like_count = total_cmt_like_count;
                    }

                    public boolean isIs_vip() {
                        return is_vip;
                    }

                    public void setIs_vip(boolean is_vip) {
                        this.is_vip = is_vip;
                    }

                    public String getRoom_url() {
                        return room_url;
                    }

                    public void setRoom_url(String room_url) {
                        this.room_url = room_url;
                    }

                    public String getQzone_uid() {
                        return qzone_uid;
                    }

                    public void setQzone_uid(String qzone_uid) {
                        this.qzone_uid = qzone_uid;
                    }

                    public String getSex() {
                        return sex;
                    }

                    public void setSex(String sex) {
                        this.sex = sex;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getRoom_icon() {
                        return room_icon;
                    }

                    public void setRoom_icon(String room_icon) {
                        this.room_icon = room_icon;
                    }
                }

                public static class AudioBeanXXX {
                    /**
                     * duration : 15
                     * audio : ["http://wvoice.spriteapp.cn/voice/2017/0307/68e0a022-0344-11e7-89f8-d4ae5296039d.mp3"]
                     */

                    private int duration;
                    private List<String> audio;

                    public int getDuration() {
                        return duration;
                    }

                    public void setDuration(int duration) {
                        this.duration = duration;
                    }

                    public List<String> getAudio() {
                        return audio;
                    }

                    public void setAudio(List<String> audio) {
                        this.audio = audio;
                    }
                }

                public static class ImageBeanXXX {
                    /**
                     * images : ["http://wimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg","http://dimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg"]
                     * width : 150
                     * download : ["http://wimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60_d.jpg","http://dimg.spriteapp.cn/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60_d.jpg"]
                     * thumbnail : ["http://wimg.spriteapp.cn/crop/150x150/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg","http://dimg.spriteapp.cn/crop/150x150/ugc/2017/0308/78d4b9c7-0355-11e7-a63a-90b11c47ff60.jpg"]
                     * height : 150
                     */

                    private int width;
                    private int height;
                    private List<String> images;
                    private List<String> download;
                    private List<String> thumbnail;

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public List<String> getImages() {
                        return images;
                    }

                    public void setImages(List<String> images) {
                        this.images = images;
                    }

                    public List<String> getDownload() {
                        return download;
                    }

                    public void setDownload(List<String> download) {
                        this.download = download;
                    }

                    public List<String> getThumbnail() {
                        return thumbnail;
                    }

                    public void setThumbnail(List<String> thumbnail) {
                        this.thumbnail = thumbnail;
                    }
                }

                public static class GifBeanXXX {
                    /**
                     * images : ["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"]
                     * width : 175
                     * download : ["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"]
                     * thumbnail : ["http://wimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif","http://dimg.spriteapp.cn/ugc/2017/0308/40a1caee-0355-11e7-8f73-1866daeb0df1.gif"]
                     * height : 222
                     */

                    private int width;
                    private int height;
                    private List<String> images;
                    private List<String> download;
                    private List<String> thumbnail;

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public List<String> getImages() {
                        return images;
                    }

                    public void setImages(List<String> images) {
                        this.images = images;
                    }

                    public List<String> getDownload() {
                        return download;
                    }

                    public void setDownload(List<String> download) {
                        this.download = download;
                    }

                    public List<String> getThumbnail() {
                        return thumbnail;
                    }

                    public void setThumbnail(List<String> thumbnail) {
                        this.thumbnail = thumbnail;
                    }
                }

                public static class VideoBeanXXX {
                    /**
                     * height : 632
                     * width : 360
                     * video : ["http://wvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4","http://dvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4"]
                     * duration : 0
                     * download : ["http://wvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4","http://dvideo.spriteapp.cn/video/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_wpd.mp4"]
                     * thumbnail : ["http://wimg.spriteapp.cn/crop/150x150/picture/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_1.jpg","http://dimg.spriteapp.cn/crop/150x150/picture/2017/0307/26808642-033b-11e7-acba-1866daeb0df1_1.jpg"]
                     */

                    private int height;
                    private int width;
                    private int duration;
                    private List<String> video;
                    private List<String> download;
                    private List<String> thumbnail;

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public int getDuration() {
                        return duration;
                    }

                    public void setDuration(int duration) {
                        this.duration = duration;
                    }

                    public List<String> getVideo() {
                        return video;
                    }

                    public void setVideo(List<String> video) {
                        this.video = video;
                    }

                    public List<String> getDownload() {
                        return download;
                    }

                    public void setDownload(List<String> download) {
                        this.download = download;
                    }

                    public List<String> getThumbnail() {
                        return thumbnail;
                    }

                    public void setThumbnail(List<String> thumbnail) {
                        this.thumbnail = thumbnail;
                    }
                }
            }
        }
    }
}