package netease.com.weather.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.socks.library.KLog;

/**
 * Created by user on 16-6-20.
 */

public class JumpView extends Button{
    public JumpView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KLog.d("JumpView:onInterceptTouchEvent");
        return true;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KLog.d("JumpViewr:onInterceptTouchEvent");
        return super.onTouchEvent(event);
    }

}
