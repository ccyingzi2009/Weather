package netease.com.weather.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import netease.com.weather.R;
import netease.com.weather.ui.base.constants.Constants;

/**
 * Created by liu_shuai on 2016/12/4.
 */

public class EmotionUtils {

    public static int getSoftInputHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return SystemUtils.getScreenHeight() - rect.bottom;
    }

    public static boolean isSoftInputShow(Activity activity) {
        return ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).isActive();
    }

    public static void hideSoftInput(Activity activity, EditText editText) {
        InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void showSoftInput(Activity activity, EditText editText) {
        InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    public static void updateSoftInputMethod(Activity activity, int softInputMode) {
        if (!activity.isFinishing()) {
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            if (params.softInputMode != softInputMode) {
                params.softInputMode = softInputMode;
                activity.getWindow().setAttributes(params);
            }
        }
    }

    public static int getInputHeight(Context context) {
        return PrefHelper.getInt(Constants.PREF_INPUT_HEIGHT, (int) context.getResources().getDimension(R.dimen.reply_height_default));
    }

    public static void setInputHeight(int height) {
        PrefHelper.putInt(Constants.PREF_INPUT_HEIGHT, height);
    }
}
