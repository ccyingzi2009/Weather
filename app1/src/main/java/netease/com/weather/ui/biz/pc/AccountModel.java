package netease.com.weather.ui.biz.pc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import netease.com.weather.data.model.UserBean;
import netease.com.weather.ui.MainActivity;
import netease.com.weather.util.JsonUtils;
import netease.com.weather.util.PrefHelper;

/**
 * Created by liu_shuai on 16/8/28.
 */
public class AccountModel {
    public final static String USERID = "main[UTMPUSERID]";
    public final static String USERKEY = "main[UTMPKEY]";
    public final static String USERNUM = "main[UTMPNUM]";
    public final static String USERPASSWD = "main[PASSWORD]";

    public final static String PREF_COOKIE = "pref_cookie";
    public final static String PREF_USER_BEAN = "pref_user_bean";
    public final static String PREF_FACE_URL = "pref_face_url";
    public final static String PREF_USER_NAME = "pref_user_name";
    public final static String PREF_USER_ID = "pref_user_id";

    public final static String PARAM_USERID = "param_userid";


    public static void saveAccount(UserBean userBean) {
        PrefHelper.putString(PREF_FACE_URL, userBean.getFace_url());
        PrefHelper.putString(PREF_USER_NAME, userBean.getUser_name());
        PrefHelper.putString(PREF_USER_ID, userBean.getId());

        PrefHelper.putString(PREF_USER_BEAN, JsonUtils.toJson(userBean));

    }

    public static String getUserId() {
        return PrefHelper.getString(PREF_USER_ID, "");
    }

    public static UserBean getUserBean() {
        String userStr = PrefHelper.getString(PREF_USER_BEAN, "");
        if (!TextUtils.isEmpty(userStr)) {
            return JsonUtils.fromJson(userStr, UserBean.class);
        }
        return null;
    }

    public final static boolean isLogin() {
        return !TextUtils.isEmpty(getUserId());
    }

    public static void gotoLogin(AppCompatActivity activity) {
        activity.startActivity(new Intent(activity, LoginActivity.class));
    }
}
