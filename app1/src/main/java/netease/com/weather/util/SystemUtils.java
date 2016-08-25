package netease.com.weather.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by user on 16-8-25.
 */
public class SystemUtils {

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }
}
