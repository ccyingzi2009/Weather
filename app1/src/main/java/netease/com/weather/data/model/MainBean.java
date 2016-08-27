package netease.com.weather.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liu_shuai on 16/3/17.
 */
public class MainBean implements Serializable{
    List<MainSlider> mSliders;

    public List<MainSlider> getSliders() {
        return mSliders;
    }

    public void setSliders(List<MainSlider> sliders) {
        mSliders = sliders;
    }
}
