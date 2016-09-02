package netease.com.weather.util.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.util.Map;

import netease.com.weather.data.model.UserBean;

/**
 * Created by liu_shuai on 16/8/28.
 */
public class LoginRequest extends BaseRequest<UserBean> {

    private Map<String, String> mParams;

    public LoginRequest(String url, Map<String, String> params) {
        super(Method.POST, url, null);
        mParams = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected Response<UserBean> parseNetworkResponse(NetworkResponse response) {
        Map<String, String> headers = response.headers;
        UserBean userBean = new UserBean();
        return Response.success(userBean, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public void setRedirectUrl(String redirectUrl) {
        super.setRedirectUrl(redirectUrl);
    }
}
