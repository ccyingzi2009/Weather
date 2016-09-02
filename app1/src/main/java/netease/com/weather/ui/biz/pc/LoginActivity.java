package netease.com.weather.ui.biz.pc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import netease.com.weather.R;
import netease.com.weather.data.model.UserBean;
import netease.com.weather.ui.base.BaseActivity;
import netease.com.weather.ui.base.constants.Constants;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.login)
    void doLogin() {
        String url = Constants.URL_LOGIN;
        
        String usrName = username.getText().toString().trim();
        String passWd = password.getText().toString().trim();
        if (TextUtils.isEmpty(usrName) || TextUtils.isEmpty(passWd)) return;
        Map<String, String> param = new HashMap<>();
        param.put(PARAM_LOGIN_ID, usrName);
        param.put(PARAM_LOGIN_PASSWD, passWd);
        LoginRequest request = new LoginRequest(url, param);
        request.setResponseListener(new BaseRequest.IResponseListener<UserBean>() {
            @Override
            public void onResponse(UserBean response) {
                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
        VolleyUtils.addRequest(new LoginRequest(url, param), this);
    }
}
