package netease.com.weather.ui.biz.pc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import netease.com.weather.R;
import netease.com.weather.data.event.LoginEvent;
import netease.com.weather.data.model.UserBean;
import netease.com.weather.ui.base.BaseActivity;
import netease.com.weather.ui.base.constants.Constants;
import netease.com.weather.util.PrefHelper;
import netease.com.weather.util.request.BaseRequest;
import netease.com.weather.util.request.LoginRequest;
import netease.com.weather.util.request.VolleyUtils;

/**
 * Created by liu_shuai on 16/8/28.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.username)
    AutoCompleteTextView username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.signup)
    Button signup;
    @BindView(R.id.login)
    Button login;

    public final static String PARAM_LOGIN_ID = "id";
    public final static String PARAM_LOGIN_PASSWD = "passwd";

    private String mInputUserName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.login)
    void doLogin() {
        showProgressDialog(R.string.update_check_title, R.string.login_loading);
        String url = Constants.URL_LOGIN;

        String usrName = username.getText().toString().trim();
        String passWd = password.getText().toString().trim();
        if (TextUtils.isEmpty(usrName) || TextUtils.isEmpty(passWd)) return;

        mInputUserName = usrName;

        Map<String, String> param = new HashMap<>();
        param.put(PARAM_LOGIN_ID, usrName);
        param.put(PARAM_LOGIN_PASSWD, passWd);
        LoginRequest request = new LoginRequest(url, param);
        request.setResponseListener(new BaseRequest.IResponseListener<UserBean>() {
            @Override
            public void onResponse(UserBean response) {
            }

            @Override
            public void onError(VolleyError error) {
                if (error != null && error.networkResponse != null) {
                    NetworkResponse response = error.networkResponse;
                    Map<String, String> header = response.headers;
                    String userId = "";
                    String userkey = "";
                    String userNum = "";
                    String userPasswd = "";
                    for (Map.Entry<String, String> entry : header.entrySet()) {
                        String cookie = entry.getValue();
                        if (!TextUtils.isEmpty(cookie)) {
                            String cookieArr[] = cookie.split(";");
                            String setCookie = cookieArr[0];
                            if (!TextUtils.isEmpty(setCookie)) {
                                if (setCookie.contains(AccountModel.USERID)) {
                                    userId = setCookie;
                                } else if (setCookie.contains(AccountModel.USERKEY)) {
                                    userkey = setCookie;
                                } else if (setCookie.contains(AccountModel.USERNUM)) {
                                    userNum = setCookie;
                                } else if (setCookie.contains(AccountModel.USERPASSWD)) {
                                    userPasswd = setCookie;
                                }
                            }
                        }
                    }

                    StringBuilder sb = new StringBuilder();
                    sb.append(userId).append("; ")
                            .append(userkey).append(";")
                            .append(userNum).append(";")
                            .append(userPasswd);

                    String requestCookie = sb.toString();
                    PrefHelper.putString(AccountModel.PREF_COOKIE, requestCookie);

                    KLog.d(userId + " ; " + userkey + " ; " + userNum + " ; " + userPasswd + " ; ");
                    if (userId.contains(mInputUserName)) {
                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new LoginEvent(mInputUserName));
                        finish();
                    }
                }
                dismissProgressDialog();
            }
        });

        VolleyUtils.addRequest(request, this);
    }
}
