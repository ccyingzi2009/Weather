package netease.com.weather.ui.biz.article;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import netease.com.weather.R;
import netease.com.weather.data.DataLoadingSubject;
import netease.com.weather.data.model.ArticleSingleBean;
import netease.com.weather.ui.base.PageAdapter;
import netease.com.weather.ui.biz.pics.PicShowActivity;
import netease.com.weather.util.StringUtils;
import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by user on 16-4-21.
 */
public class ArticleNewAdapter extends PageAdapter<ArticleSingleBean> implements DataLoadingSubject.DataLoadingCallbacks {

    private ArticleActivity mActivity;

    public ArticleNewAdapter(ArticleActivity activity, OnFooterViewCallback callback) {
        super(callback);
        mActivity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_article_item, parent, false));
    }

    @Override
    public void onBindBasicItemView(RecyclerView.ViewHolder holder, int position) {
        bindCommentHolder(mData.get(position), (CommentHolder) holder);

    }

    private void bindCommentHolder(ArticleSingleBean article, CommentHolder holder) {
        //holder.content.setText(article.getContent());
        holder.userName.setText(article.getUser());

        String content = article.getContent();
        String[] contentSec = content.split(StringUtils.IMG_NODE);
        List<String> mImgUrls = article.getImgs();
        int position = 0;

        holder.contentContainer.removeAllViews();

        //绑定图片和文字
        for (int i = 0; i < contentSec.length; i++) {
            String line = contentSec[i];
            //绑定图片
            if (TextUtils.isEmpty(line) && mImgUrls != null && !mImgUrls.isEmpty() && mImgUrls.size() > position) {
                addImg(holder, mImgUrls, position++);
                continue;
            }
            addContent(holder, line);
            addImg(holder, mImgUrls, position++);
        }

        //绑定引用
        String quote = article.getQuote();
        holder.articleQuote.setText(Html.fromHtml(quote));
        if (!TextUtils.isEmpty(quote)) {
            holder.quoteContainer.setVisibility(View.VISIBLE);
        } else {
            holder.quoteContainer.setVisibility(View.GONE);
        }

    }

    private void addImg(CommentHolder holder, final List<String> mImgUrls, int position) {
        if (mImgUrls.size() <= position) {
            return;
        }
        View imgItem = LayoutInflater.from(mActivity).inflate(R.layout.activity_article_item_img, null, false);
        holder.contentContainer.addView(imgItem);
        final ImageView imageView = (ImageView) imgItem.findViewById(R.id.article_img);
        Glide.with(mActivity).load(mImgUrls.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new GlideDrawableImageViewTarget(imageView, 1));

        imgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable(PicShowActivity.PIC_SHOW_IMGS, (ArrayList) mImgUrls);
                Intent intent = new Intent(mActivity, PicShowActivity.class);
                intent.putExtras(args);
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mActivity, imageView, PicShowActivity.SCENE_IMAGE).toBundle());
                }else {
                    mActivity.startActivity(intent);
                }*/
                mActivity.startActivity(intent);
            }
        });
    }

    private void addContent(CommentHolder holder, String line) {
        View imgItem = LayoutInflater.from(mActivity).inflate(R.layout.activity_article_item_content, null, false);
        holder.contentContainer.addView(imgItem);
        final TextView contentView = (TextView) imgItem.findViewById(R.id.content);
        contentView.setMovementMethod(LinkMovementMethod.getInstance());
        //contentView.setText(Html.fromHtml(line));


        line = line.replaceAll("<img", "#img");
        String html = Html.fromHtml(line).toString();
        SpannableStringBuilder sb = new SpannableStringBuilder(html);
        Pattern pattern = Pattern.compile("#img\\s*src=\"/img/ubb/([^#>]+)\"\\s*style=([^#>]+>)");
        Matcher matcher = pattern.matcher(sb);
        while (matcher.find()) {
            String emojArr[] = matcher.group(1).split("/");
            if (emojArr.length < 2) {
                return;
            }
            GifDrawable gifDrawable = null;
            try {
                gifDrawable = new GifDrawable(mActivity.getAssets(), "img/" + emojArr[0] + "/" + emojArr[0] + emojArr[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (gifDrawable != null) {
                gifDrawable.setBounds(0, 0, gifDrawable.getIntrinsicWidth() * 2 , gifDrawable.getIntrinsicHeight() * 2);
                sb.setSpan(new ImageSpan(gifDrawable), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                gifDrawable.setCallback(new Drawable.Callback() {
                    @Override
                    public void invalidateDrawable(@Nullable Drawable drawablem) {
                        contentView.invalidate();
                    }

                    @Override
                    public void scheduleDrawable(@Nullable Drawable drawablem, @Nullable Runnable runnablem, long lm) {
                        contentView.postDelayed(runnablem, lm);
                    }

                    @Override
                    public void unscheduleDrawable(@Nullable Drawable drawablem, @Nullable Runnable runnablem) {
                        contentView.removeCallbacks(runnablem);

                    }
                });
            }
        }
        contentView.setText(sb);

    }

    @Override
    public void dataStartedLoading() {
        showFooter(true);
    }

    @Override
    public void dataFinishedLoading() {
        showFooter(false);
    }


    /* package */ static class CommentHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.article_user_name)
        TextView userName;
        @BindView(R.id.content_container)
        LinearLayout contentContainer;
        @BindView(R.id.quote_container)
        View quoteContainer;
        @BindView(R.id.article_quote)
        TextView articleQuote;

        public CommentHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
