package netease.com.weather.util.html;

/**
 * Created by user on 16-8-26.
 */
abstract class BaseHandler<T> implements HtmlHandler<T> {

    private T mData;

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    @Override
    public T process(String html) {
        return null;
    }

    @Override
    public void save(String key) {

    }

}
