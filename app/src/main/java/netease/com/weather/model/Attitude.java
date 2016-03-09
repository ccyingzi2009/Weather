package netease.com.weather.model;

import java.util.List;

/**
 * Created by liu_shuai on 16/3/8.
 */
public class Attitude {

    /**
     * endtime : 2016-1-10
     * attitudeList : [{"title":"头条看板第三篇","img750":"http://img1.cache.netease.com/3g/2016/1/8/20160108170119ba58f.jpg","img1080":"http://img2.cache.netease.com/3g/2016/1/8/20160108170123ca5ea.jpg"},{"title":"头条看板第二篇","img750":"http://img1.cache.netease.com/3g/2016/1/8/201601081701040c864.jpg","img1080":"http://img2.cache.netease.com/3g/2016/1/8/20160108170109b0ac5.jpg"},{"title":"头条看板第一篇","img750":"http://img6.cache.netease.com/3g/2016/1/8/201601081700475267b.jpg","img1080":"http://img6.cache.netease.com/3g/2016/1/8/20160108170053fdb2e.jpg"}]
     * sid : S1443064291440
     * starttime : 2015-12-1
     * adlogo : http://img4.cache.netease.com/m/2015/10/30/20151030191430ae78c.png
     */

    private String endtime;
    private String sid;
    private String starttime;
    private String adlogo;
    /**
     * title : 头条看板第三篇
     * img750 : http://img1.cache.netease.com/3g/2016/1/8/20160108170119ba58f.jpg
     * img1080 : http://img2.cache.netease.com/3g/2016/1/8/20160108170123ca5ea.jpg
     */

    private List<AttitudeListEntity> attitudeList;

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public void setAdlogo(String adlogo) {
        this.adlogo = adlogo;
    }

    public void setAttitudeList(List<AttitudeListEntity> attitudeList) {
        this.attitudeList = attitudeList;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getSid() {
        return sid;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getAdlogo() {
        return adlogo;
    }

    public List<AttitudeListEntity> getAttitudeList() {
        return attitudeList;
    }

    public static class AttitudeListEntity {
        private String title;
        private String img750;
        private String img1080;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setImg750(String img750) {
            this.img750 = img750;
        }

        public void setImg1080(String img1080) {
            this.img1080 = img1080;
        }

        public String getTitle() {
            return title;
        }

        public String getImg750() {
            return img750;
        }

        public String getImg1080() {
            return img1080;
        }
    }
}
