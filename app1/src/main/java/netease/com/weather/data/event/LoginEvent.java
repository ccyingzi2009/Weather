package netease.com.weather.data.event;

/**
 * Created by user on 16-9-20.
 */
public class LoginEvent {
    private String mUserId;

    public LoginEvent(String userId) {
        mUserId = userId;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }
}
