package netease.com.weather.util.request;

import com.android.volley.AuthFailureError;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import netease.com.weather.data.model.CommentResponseBean;

/**
 * Created by user on 16-10-27.
 */

public class CommentReplyRequest extends JsonRequest<CommentResponseBean> {

    private Map<String, String> mParams;

    public CommentReplyRequest(String url, Map<String, String> params, TypeToken<CommentResponseBean> typeToken) {
        super(Method.POST, url, typeToken, null);
        mParams = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> heaaders =  super.getHeaders();
        heaaders.put("X-Requested-With", "XMLHttpRequest");
        return heaaders;
    }
}