package netease.com.weather.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;

import netease.com.weather.BaseApplication;

/**
 * Created by user on 16-8-25.
 */
public class SystemUtils {

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getVersionCode() {
        int versionCode = 0;
        Context context = BaseApplication.getInstance();
        PackageManager pm = context.getPackageManager();//context为当前Activity上下文
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
