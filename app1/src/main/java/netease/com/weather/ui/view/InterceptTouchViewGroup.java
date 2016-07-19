package netease.com.weather.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.socks.library.KLog;


/**
 * Created by user on 16-6-28.
 */

public class InterceptTouchViewGroup extends LinearLayout {

    public InterceptTouchViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptTouchViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        KLog.d("ViewGroup:onInterceptTouchEvent");
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KLog.d("ViewGroup:onTouchEvent");
        return super.onTouchEvent(event);
    }
}
