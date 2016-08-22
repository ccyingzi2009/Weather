package netease.com.weather.util.request;

import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import netease.com.weather.util.html.HtmlHandler;

/**
 * Created by user on 16-3-17.
 */
public class HtmlRequest<T> extends BaseRequest<T> {

    private HtmlHandler<T> mHtmlHandler;
    private String mEncode = "GBK";

    public HtmlRequest(String url, HtmlHandler<T> htmlHandler) {
        this(url, htmlHandler, "GBK");
    }

    public HtmlRequest(String url, HtmlHandler<T> htmlHandler, String encode) {
        super(url);
        mHtmlHandler = htmlHandler;
        mEncode = encode;
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String html = new String(response.data, mEncode);
            if (!TextUtils.isEmpty(html)) {
                Element element = Jsoup.parse(html);
                T o = mHtmlHandler.process(element);
                if (o != null) {
                    return Response.success(o, HttpHeaderParser.parseCacheHeaders(response));
                } else {
                    return Response.error(new VolleyError("data is empty"));
                }
            } else {
                return Response.error(new VolleyError("Result ret error"));
            }
        } catch (Exception e) {
            return Response.error(new VolleyError(e));
        }
    }
}
