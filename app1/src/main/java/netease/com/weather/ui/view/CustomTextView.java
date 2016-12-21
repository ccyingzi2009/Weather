package netease.com.weather.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import netease.com.weather.util.UrlUtils;
import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by liu_shuai on 2016/12/21.
 */

public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setText(String content) {
        content = content.replace("<img", "#img");
        Spannable sp = (Spannable) Html.fromHtml(content);
        //清楚所有spanner
        SpannableStringBuilder style = new SpannableStringBuilder(sp);
        style.clearSpans();
        //拦截链接
        URLSpan urlSpans[] = sp.getSpans(0, sp.length(), URLSpan.class);
        if (urlSpans.length > 0) {

            for (final URLSpan urlSpan : urlSpans) {
                style.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), urlSpan.getURL(), Toast.LENGTH_SHORT).show();
                        //
                        UrlUtils.startActivity(getContext(), urlSpan.getURL());
                    }
                }, sp.getSpanStart(urlSpan), sp.getSpanEnd(urlSpan), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }

        //设置emotion
        Pattern pattern = Pattern.compile("#img\\s*src=\"/img/ubb/([^#>]+)\"\\s*style=([^#>]+>)");
        Matcher matcher = pattern.matcher(style);
        while (matcher.find()) {
            String emojArr[] = matcher.group(1).split("/");
            if (emojArr.length < 2) {
                return;
            }
            GifDrawable gifDrawable = null;
            try {
                gifDrawable = new GifDrawable(getContext().getAssets(), "img/" + emojArr[0] + "/" + emojArr[0] + emojArr[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (gifDrawable != null) {
                gifDrawable.setBounds(0, 0, gifDrawable.getIntrinsicWidth() * 2, gifDrawable.getIntrinsicHeight() * 2);
                style.setSpan(new ImageSpan(gifDrawable), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                gifDrawable.setCallback(new Drawable.Callback() {
                    @Override
                    public void invalidateDrawable(@Nullable Drawable drawablem) {
                        invalidate();
                    }

                    @Override
                    public void scheduleDrawable(@Nullable Drawable drawablem, @Nullable Runnable runnablem, long lm) {
                        postDelayed(runnablem, lm);
                    }

                    @Override
                    public void unscheduleDrawable(@Nullable Drawable drawablem, @Nullable Runnable runnablem) {
                        removeCallbacks(runnablem);

                    }
                });
            }
        }
        SpannableString ss = new SpannableString(style);

        super.setText(ss);
    }

}
