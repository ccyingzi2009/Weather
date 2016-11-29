package netease.com.weather;

import android.support.multidex.MultiDexApplication;

import com.antfortune.freeline.FreelineCore;
import com.facebook.stetho.Stetho;

/**
 * Created by user on 16-3-17.
 */
public class BaseApplication extends MultiDexApplication {
    private static BaseApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        //Stetho.initializeWithDefaults(this);
        mInstance = this;
        //FreelineCore.init(this);
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }
}
