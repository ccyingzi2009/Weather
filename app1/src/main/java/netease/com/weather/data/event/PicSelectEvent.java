package netease.com.weather.data.event;

/**
 * Created by liu_shuai on 2016/12/1.
 */

public class PicSelectEvent {
    private String mPicName;

    public PicSelectEvent(String picName) {
        mPicName = picName;
    }

    public String getPicName() {
        return mPicName;
    }

    public void setPicName(String picName) {
        mPicName = picName;
    }
}
